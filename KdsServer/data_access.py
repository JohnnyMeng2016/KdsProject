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

    def __init__(self):
        for i in range(10):
            __connection = pymysql.connect(**config)
            self.connection_pool.append(__connection)

    ##
    # 获取连接
    ##
    def get_connection(self):
        self.lock.acquire()
        self.connection_pool.pop(0)
        self.lock.release()

    ##
    # 回收连接
    ##
    def release_connection(self, __connection):
        self.lock.acquire()
        self.connection_pool.append(__connection)
        self.lock.release()

class Reply:


connection = pymysql.connect(**config)
try:
    with connection.cursor() as cursor:
        # 执行sql语句，插入记录
        sql = 'INSERT INTO topic (title) VALUES (%s)'
        cursor.execute(sql, ('Robin'));
    # 没有设置默认自动提交，需要主动提交，以保存所执行的语句
    connection.commit()

finally:
    connection.close();