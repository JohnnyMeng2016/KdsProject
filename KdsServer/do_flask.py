from flask import Flask,jsonify
from flask import request
from topic_manager import TopicManager
import data_access

import spider

app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def home():
    return '<h1>Home</h1>'

@app.route('/signin', methods=['GET'])
def signin_form():
    return '''<form action="/signin" method="post">
              <p><input name="username"></p>
              <p><input name="password" type="password"></p>
              <p><button type="submit">Sign In</button></p>
              </form>'''

@app.route('/signin', methods=['POST'])
def signin():
    # 需要从request对象读取表单内容：
    if request.form['username']=='admin' and request.form['password']=='password':
        return '<h3>Hello, admin!</h3>'
    return '<h3>Bad username or password.</h3>'

@app.route('/getTopicList', methods=['GET', 'POST'])
def get_topic_list():
    page = request.form['page']
    topic_manager = TopicManager()
    return jsonify(topic_manager.get_topic_list(page))

@app.route('/getTopicDetail', methods=['GET', 'POST'])
def get_topic_detail():
    topic_url = request.form['topicUrl']
    page = request.form['page']
    return jsonify(spider.go_topic(topic_url, page))


if __name__ == '__main__':
    app.run(host='0.0.0.0',port=8000)