import pymysql

config = {
          'host':'127.0.0.1',
          'port':3306,
          'user':'root',
          'password':'1231131',
          'db':'garbage_heap',
          'charset':'utf8mb4',
          'cursorclass':pymysql.cursors.DictCursor,
          }

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