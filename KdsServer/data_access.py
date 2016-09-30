import pymysql
import threading

config = {
    'host': '127.0.0.1',
    'port': 3306,
    'user': 'root',
    'password': '1231131',
    'db': 'garbage_heap',
    'charset': 'utf8mb4',
    'cursorclass': pymysql.cursors.DictCursor,
}


class ConnectPool:
    connection_pool = []
    lock = threading.Lock()

    def __new__(cls, *args, **kw):
        if not hasattr(cls, '_instance'):
            orig = super(ConnectPool, cls)
            cls._instance = orig.__new__(cls, *args, **kw)
            cls.__create_connection_pool(cls)
        return cls._instance

    def __create_connection_pool(self):
        for i in range(10):
            __connection = pymysql.connect(**config)
            self.connection_pool.append(__connection)

    ##
    # 获取连接
    ##
    def get_connection(self):
        self.lock.acquire()
        __connection = self.connection_pool.pop(0)
        self.lock.release()
        return __connection;

    ##
    # 回收连接
    ##
    def release_connection(self, __connection):
        self.lock.acquire()
        self.connection_pool.append(__connection)
        self.lock.release()


class ReplyDao:
    def __save_one_line(self, params, values):
        __connection = ConnectPool().get_connection();
        try:
            with __connection.cursor() as cursor:
                # 执行sql语句，插入记录
                __sql = 'INSERT INTO reply ('
                for param in params:
                    __sql += (param + ',')
                __sql = __sql[:-1]
                __sql = __sql + ') VALUES ('
                for value in values:
                    __sql += (value + ',')
                __sql = __sql[:-1]
                __sql = __sql + ')'
                cursor.execute(__sql)
            # 没有设置默认自动提交，需要主动提交，以保存所执行的语句
            __connection.commit()
        finally:
            ConnectPool().release_connection(__connection)

    ##
    # 保存回复集
    ##
    def save_replys(self, replys):
        for __reply in replys:
            params = []
            values = []
            for k in __reply:
                params.append(k)
                if k == "userLink" or k == "nickName" or k == "userName" or k == "content" or k == "time" \
                        or k == "floor":
                    values.append('\'' + __reply[k] + '\'')
                else:
                    values.append(__reply[k])
            self.__save_one_line(params, values)

    ##
    # 根据topicId、page获取回复集
    ##
    def get_replys_by_condition(self, topicId, page):
        __replys = []
        __connection = ConnectPool().get_connection();
        try:
            with __connection.cursor() as cursor:
                # 执行sql语句，查询数据
                __sql = 'SELECT * FROM REPLY WHERE topicId=%s AND pageIndex=%s'
                cursor.execute(__sql, (topicId, page))
                for item in cursor:
                    __replys.append(item)
            return __replys
        finally:
            ConnectPool().release_connection(__connection)

    ##
    # 根据topicId、page删除对应回复集
    ##
    def delete_replys_by_condition(self, topicId, page):
        __connection = ConnectPool().get_connection();
        try:
            with __connection.cursor() as cursor:
                # 执行sql语句，查询数据
                __sql = 'DELETE FROM REPLY WHERE topicId=%s AND pageIndex=%s'
                cursor.execute(__sql, (topicId, page))
        finally:
            ConnectPool().release_connection(__connection)


if __name__ == '__main__':
    replyDao = ReplyDao()
    replyDao.get_replys_by_condition(8844621, 1)
