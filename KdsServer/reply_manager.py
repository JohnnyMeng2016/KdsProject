
##
# 回复管理
##
class ReplyManager(object):

    def __new__(cls, *args, **kw):
        if not hasattr(cls, '_instance'):
            orig = super(ReplyManager, cls)
            cls._instance = orig.__new__(cls, *args, **kw)
        return cls._instance

    #def get_reply_list(self,topic_id,page):

