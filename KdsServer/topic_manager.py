import spider
from datetime import datetime,timedelta

##
# 帖子管理
##
class TopicManager(object):

    #缓存的帖子列表，以页码作为key
    cache_topic_list = {}

    def __new__(cls, *args, **kw):
        if not hasattr(cls, '_instance'):
            orig = super(TopicManager, cls)
            cls._instance = orig.__new__(cls, *args, **kw)
        return cls._instance

    ##
    # 指定页码，获取该页的帖子列表
    ##
    def get_topic_list(self, page):
        if page in self.cache_topic_list:
            if None != self.cache_topic_list[page]:
                list = self.cache_topic_list[page]
                list_time = list[0];
                if list_time+timedelta(seconds=60)>datetime.now():
                    return list[1:]
        topic_list = spider.get_topic_list(page)
        list = []
        list.append(datetime.now())
        list.extend(topic_list)
        self.cache_topic_list[page] = list
        return list[1:]
