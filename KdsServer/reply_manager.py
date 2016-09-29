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

    def get_reply_list(self,topic_url,page):
        #从数据库里获取回复集
        reply_dao = data_access.ReplyDao()
        __topic_id = re.search('15_(.*?)__', topic_url, re.S).group(1)
        __replys = reply_dao.get_replys_by_condition(__topic_id, page)
        if len(__replys)!=0:
            return __replys;
        #数据库里没有，爬+存
        __result_dict = spider.go_topic(topic_url, page)
        __replys = __result_dict["topicItems"]
        reply_dao.save_replys(__replys)
        return __replys;

