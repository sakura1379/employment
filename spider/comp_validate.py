import json
import requests
from flask import Flask, request
from get_comp_info import get_comp_info

app = Flask(__name__)


headers = {
    'Accept': 'application/json, text/plain, */*',
    'Connection': 'keep-alive',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36'
}


@app.route('/validate', methods=['POST'])
def extract_keyword():
    # 企业验证的输入 {"type": "company", "compName": 公司名, "creditcode": 统一社会信用代码}
    # 学生邮箱验证的输入 {"type": "student", "email": 待验证邮箱}
    # 输出 {"status": "fail"}，通过时为"success"
    status = "fail"
    if request.data != '':
        data = json.loads(request.data)
        if data.get('type') == 'company':
            real_data = get_comp_info(data['compName'])
            if data['creditcode'] == real_data['creditcode']:
                status = "success"
        elif data.get('type') == 'student':
            url = 'https://verify-email.org/home/verify-as-guest/' + data['email']
            try:
                r = requests.get(url, headers=headers)
                r.raise_for_status()
                data = json.loads(r.text)
                if data['response']['status'] == 1:
                    status = "success"
            except Exception:
                return json.dumps({'status': status})

    return json.dumps({'status': status})


if __name__ == '__main__':
    app.run(host='0.0.0.0', port='7788')
