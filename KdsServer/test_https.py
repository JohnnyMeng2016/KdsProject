import requests
import json

'''
url = 'https://passport.pchome.net/index.php'
payload = {'c': 'login', 'm': 'do_login', 'user_name': 'sd1231131', 'password': 'eddie1231131', 'vcode': ''}
html = requests.post(url, payload, verify=False);
hjson = json.loads(html.text)
sid = hjson['data']

url = 'https://passport.pchome.net/index.php?c=login&m=login_success&sid=' + sid
html = requests.get(url, verify=False)

url = 'https://passport.pchome.net/index.php?c=login&m=set_oauth&sid=' + sid
html = requests.get(url, verify=False)

url = 'http://club.pchome.net/forum_1_15.html'
html = requests.get(url)
print(html.text)
'''

url = 'http://club.pchome.net/ajaxSendReply.php'
cookies = dict(__userId='5304002', sessionID='b9655ca5b7bc4aa199ed62c33e6850d1', sid='b9655ca5b7bc4aa199ed62c33e6850d1')
payload = {'areaid': '1', 'topicid': '15', 'bbsid': '8866951', 'randPicId': '1477294269', 'message': '月薪3000的我表示“帮我搭卵噶”', 'propId':''}
html = requests.post(url, cookies=cookies, params=payload)
print(html.text)
