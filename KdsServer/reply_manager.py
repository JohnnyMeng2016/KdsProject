import spider
import data_access
import re

##
# 回复管理
##
class ReplyManager(object):

    def __new__(cls, *args, **kw):
        if not hasattr(cls, '_instance'):
            orig = super(ReplyManager, cls)
            cls._instance = orig.__new__(cls, *args, **kw)
        return cls._instance

    def get_reply_list(self, topic_url, page, page_count, reply_num):
        __topic_id = re.search('15_(.*?)__', topic_url, re.S).group(1)#解析TopicID
        reply_dao = data_access.ReplyDao()

        if page==page_count:#最后一页回复
            if page!=1:
                __result_dict = spider.go_topic(topic_url, page)
                __replys = __result_dict["topicItems"]
                return __replys
            else:
                __replys = reply_dao.get_replys_by_condition(__topic_id, page)
                if len(__replys)==reply_num:
                    return __replys
                else:
                    #更新数据库中改页信息
                    reply_dao.delete_replys_by_condition(__topic_id, page)
        #从数据库里获取回复集
        __replys = reply_dao.get_replys_by_condition(__topic_id, page)
        if len(__replys)!=0:
            return __replys;
        #数据库里没有，爬+存
        __result_dict = spider.go_topic(topic_url, page)
        __replys = __result_dict["topicItems"]
        if None!=__replys:
            reply_dao.save_replys(__replys)
        return __replys;

