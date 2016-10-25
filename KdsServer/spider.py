import requests
import re

baseUrl = 'http://club.pchome.net'


##
# 爬帖子列表
##
def __spide_topic_list(eachclass):
    info = {}
    title_info = re.search('<span class="n3">(.*?)</span>', eachclass, re.S).group(1)
    info['title'] = re.search('title=.*?>(.*?)</a>', title_info, re.S).group(1)
    info['topicLink'] = re.search('href="(.*?)"', title_info, re.S).group(1)
    if re.search('rel="(.*?)"', title_info, re.S):
        info['imgPreview'] = re.search('rel="(.*?)"', title_info, re.S).group(1)
    else:
        info['imgPreview'] = "None"
    if re.search('_(\d)_.html', title_info, re.S):
        pages = re.findall('_(\d)_.html', title_info, re.S)
        info['pageCount'] = pages[-1]
    else:
        info['pageCount'] = "1"

    info['clickNum'] = re.search('<span class="n2">(.*?)</span>', eachclass, re.S).group(1)
    info['replyNum'] = re.search('<span class="n4">(.*?)</span>', eachclass, re.S).group(1)
    detail = re.search('<span class="n4">(.*)', eachclass, re.S).group(1)
    user = re.findall('bm_user_id=.*?>(.*?)</a>', detail, re.S)
    info['topicUser'] = user[0]
    info['replyUser'] = user[1]
    info['topicTime'] = re.search('<span class="n6">(.*?)</span>', detail, re.S).group(1)
    info['replyTime'] = re.search('<span class="n8">(.*?)</span>', detail, re.S).group(1)
    user_links = re.findall('href="(.*?)"', detail, re.S)
    info['topicUserLink'] = user_links[0]
    info['replyUserLink'] = user_links[1]

    return info


##
# 爬帖子内容
##
def __spide_topic_detail(eachclass, topic_id, page):
    info = {}
    auth_part = re.search('<!-- 作者信息 -->(.*?)<!-- /作者信息 --> ', eachclass, re.S).group(1)
    content_part = re.search('<!-- 正文 -->(.*?)<!-- /正文 -->', eachclass, re.S).group(1)
    status_part = re.search('<!-- 时间 -->(.*)</div> ', eachclass, re.S).group(1)

    info['topicId'] = topic_id
    info['pageIndex'] = page
    info['userLink'] = re.search('<a href="(.*?)" class=', auth_part, re.S).group(1)
    info['nickName'] = re.search('<strong style="line-hight:20px;">(.*?)</strong>', auth_part, re.S).group(1)
    info['userName'] = re.search('</strong>(.*?)</a>', auth_part, re.S).group(1)
    info['userAvatar'] = re.search('<img width="100" height="100" src="(.*?)"', auth_part, re.S).group(1)
    info['hp'] = re.search('<img src="http://jscss.kdslife.com/club/html/img/hp.gif" />(.*?)\n</span>', auth_part, re.S) \
        .group(1)
    info['pp'] = re.search('<img src="http://jscss.kdslife.com/club/html/img/pp.gif" border="0" />(.*?)</a>', auth_part
                           , re.S).group(1)

    info['content'] = re.search('<div class="clearfix" id=".*?" >(.*?)</div>', content_part, re.S).group(1)

    info['time'] = re.search('<div class="p_time">(.*?)</div>', status_part, re.S).group(1)
    info['floor'] = re.search('\.\.\.(.*?)\.\.\.', status_part, re.S).group(1)

    return info


##
# 获取帖子内容
##
def go_topic(topic_url, page):
    return_obj = {}
    topic_id = re.search('15_(.*?)__', topic_url, re.S).group(1)
    url = baseUrl + topic_url
    if int(page) > 1:
        url = baseUrl + topic_url[:-6] + page + topic_url[-6:]
    html = requests.get(url)
    if re.search('该贴的回复内容不存在，无法显示！', html.text, re.S):
        return_obj['topicItems'] = None
        return return_obj

    page_count = get_page_count(html.text)
    topic_item = re.findall('<!-- 作者信息 -->(.*?)<!-- /推荐 楼数 -->', html.text, re.S)
    topic_items = []
    for each in topic_item:
        topic_items.append(__spide_topic_detail(each, topic_id, page))
    return_obj['pageCount'] = page_count
    return_obj['topicItems'] = topic_items
    return return_obj


##
# 获取帖子页数
##
def get_page_count(html):
    inner_pager = re.search('<div class="inner_pager">(.*?)</div>', html, re.S).group(1)
    page_list = re.findall('_(.*?)__', inner_pager, re.S)
    return page_list[-2][-1]


##
# 获取帖子列表
##
def get_topic_list(page):
    url = '%s/forum_1_15_%s______.html' % (baseUrl, page)
    html = requests.get(url)
    title = re.findall('<li class="i2">(.*?)</li>', html.text, re.S)
    topic_list = []
    for each in title:
        topic_list.append(__spide_topic_list(each))
    return topic_list


##
# 解析用户信息
##
def get_user_info(html):
    info = {}
    user_info = re.search('<div id="info">(.*?)加为好友</a>', html, re.S).group(1)
    user_info = user_info.replace("\n", " ")
    info['avatarPic'] = re.search('<div id="li_2" class="center"><img width="100px" src="(.*?)"', user_info,
                                  re.S).group(1)
    re_username = re.search('<img alt="认证会员" src="http://images.pchome.net/global/img2/authUser.gif"/>认证会员</a><br />(.*?)<br />',
                 user_info, re.S)
    if re_username:
        info['userName'] = re_username.group(1)
    else:
        info['userName'] = re.search('<p>(.*?)<br />', user_info, re.S).group(1)

    info['hp'] = re.search('<img src="http://img.club.pchome.net/html/images/img/hp.gif" /> (.*?)<img', user_info,
                           re.S).group(1)
    info['pp'] = re.search('<img src="http://img.club.pchome.net/html/images/img/pp.gif" /> (.*?)<br />', user_info,
                           re.S).group(1)
    info['location'] = re.search('<br /> 来自：(.*?)<br />', user_info, re.S).group(1)
    info['register'] = re.search('<br />  注册：(.*?)<br />', user_info, re.S).group(1)
    info['actionCount'] = re.search('<br />  发帖：(.*?)<br />', user_info, re.S).group(1)
    print(info)
    return info


##
# 解析用户帖子列表
##
def get_user_topic(html):
    html = html.replace("\n", " ")
    topic_list = re.findall('<div id="topic_a">.*?<div id="topic">.*?</div> </div>', html, re.S)
    topic_items = []
    for each in topic_list:
        info = {}
        info['topicTitle'] = re.search('<div id="topic_a">(.*?)</div>', each, re.S).group(1)
        info['topicDate'] = re.search('<div id="li_1">发贴日期：(.*?)&nbsp', each, re.S).group(1)
        info['replyCount'] = re.search('\[ (.*?)人评论过 \]', each, re.S).group(1)
        info['topicContent'] = re.search('<div id="li_8" style="word-break:break-all;" align="left">(.*?)</div>', each,
                                         re.S).group(1)
        info['topicLink'] = re.search('href="(.*?)"', each, re.S).group(1)
        topic_items.append(info)
    return topic_items


html = requests.get("http://my.pchome.net/~sd1231131/")
get_user_info(html.text)
