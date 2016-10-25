from flask import Flask,jsonify
from flask import request
from topic_manager import TopicManager
from reply_manager import ReplyManager
from user_manager import UserManager

app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def home():
    return '<h1>Home</h1>'

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
    _return_obj = _user_manager.login(_username, _password)
    return jsonify(_return_obj)

@app.route('/goUser',methods=['GET', 'POST'])
def goUser():
    _user_url = request.form['userUrl']
    _user_manager = UserManager();
    _return_obj = _user_manager.get_user_detail(_user_url)
    return jsonify(_return_obj)

@app.route('/getUserTopicList', methods=['GET', 'POST'])
def get_my_topic():
    _username = request.form['username']
    _page = request.form['page']
    _user_manager = UserManager()
    _return_obj = _user_manager.get_user_topic_list(_username, _page)
    return jsonify(_return_obj)

@app.route('/getMyReply', methods=['GET', 'POST'])
def get_my_reply():
    _sid = request.form['sid']
    _user_manager = UserManager()
    return '<h3>Cock.</h3>'

@app.route('/getMyFavorite', methods=['GET', 'POST'])
def get_my_favorite():
    _sid = request.form['sid']
    _user_manager = UserManager()
    return '<h3>Cock.</h3>'

if __name__ == '__main__':
    app.run(host='0.0.0.0',port=8000)