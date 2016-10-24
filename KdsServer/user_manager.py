import requests
import json

class UserManager(object):

    def login(self, username, password):
        # 登录获取SessionId
        url = 'https://passport.pchome.net/index.php'
        param = {'c': 'login', 'm': 'do_login', 'user_name': username, 'password': password, 'vcode': ''}
        html = requests.post(url, param, verify=False)
        hjson = json.loads(html.text)
        sid = hjson['data']
        print(sid)

        url = 'http://my.pchome.net/self/'
        cookies = dict(sessionID=sid,
                       sid=sid)
        html = requests.post(url, cookies=cookies)
        print(html.text)



