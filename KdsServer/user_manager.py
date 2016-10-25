import requests
import json
import spider

class UserManager(object):

    ##
    # 用户登录
    ##
    def login(self, username, password):
        _result = {}
        # 登录获取SessionId
        url = 'https://passport.pchome.net/index.php'
        param = {'c': 'login', 'm': 'do_login', 'user_name': username, 'password': password, 'vcode': ''}
        html = requests.post(url, param, verify=True)
        hjson = json.loads(html.text)
        msg = hjson['msg']
        _result['msg'] = msg
        if msg == 'ok':
            sid = hjson['data']
            url = 'http://my.pchome.net/~'+username+'/'
            html = requests.get(url)
            _result['userInfo'] = spider.get_user_info(html.text)
            _result['isSuccess'] = 1
            _result['sid'] = sid
            return _result
        else:
            _result['isSuccess'] = 0
            return msg

    ##
    # 获取指定用户发帖列表
    ##
    def get_user_topic_list(self, username, page):
        _result = {}
        url = 'http://my.pchome.net/~'+username+'/art_'+page+'_.html'
        html = requests.get(url)
        _result['topicList'] = spider.get_user_topic(html.text)
        return _result

    ##
    # 获取用户数据+首页发帖数据
    ##
    def get_user_detail(self,url):
        _result = {}
        html = requests.get(url)
        _result['userInfo'] = spider.get_user_info(html.text)
        _result['topicList'] = spider.get_user_topic(html.text)
        return _result

