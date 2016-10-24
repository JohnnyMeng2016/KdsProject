from flask import Flask,jsonify
from flask import request
from topic_manager import TopicManager
from reply_manager import ReplyManager
from user_manager import UserManager

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
    __topic_list = topic_manager.get_topic_list(page);
    __return_obj = {}
    __return_obj['statusCode'] = 200
    __return_obj['message'] = ''
    __return_obj['topicList'] = __topic_list
    return jsonify(__return_obj)

@app.route('/getTopicDetail', methods=['GET', 'POST'])
def get_topic_detail():
    topic_url = request.form['topicUrl']
    page = request.form['page']
    page_count = request.form['pageCount']
    reply_num = request.form['replyNum']
    reply_manager = ReplyManager()
    __replys = reply_manager.get_reply_list(topic_url, page, page_count, reply_num)
    __return_obj = {}
    __return_obj['statusCode'] = 200
    __return_obj['message'] = ''
    __return_obj['replyList'] = __replys
    return jsonify(__return_obj)

@app.route('/login', methods=['GET', 'POST'])
def login():
    _username = request.form['username']
    _password = request.form['password']
    _user_manager = UserManager();
    _user_manager.login(_username, _password)
    return '<h3>Cock.</h3>'



if __name__ == '__main__':
    app.run(host='0.0.0.0',port=8000)