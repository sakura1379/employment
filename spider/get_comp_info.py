import csv
import json
import random
import logging
import requests
import traceback
import time
from bs4 import BeautifulSoup
from jsonpath import jsonpath

logging.basicConfig(filename='comp.log',
                    filemode='w',
                    level=logging.INFO,
                    format='[%(asctime)s]  %(levelname)-12s | %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S')
logger = logging.getLogger()

headers = {
    'Accept': 'application/json, text/plain, */*',
    'Connection': 'keep-alive',
    'Cookie': 'Hm_lvt_baca6fe3dceaf818f5f835b0ae97e4cc=1615210678,1615210693; log_guid=63ae6d7f9c9af11eb2ca488e30384164; PSTM=1615262889; BIDUPSID=10B6324F518787EA35103DC53F03922A; __yjs_duid=1_1364451e2d642632cce4bbe7f91974731618059383372; H_WISE_SIDS=107312_110085_127969_128699_131423_131862_154212_164071_165135_167296_168308_168489_170142_170797_170817_170872_170935_171235_171632_171930_172644_172827_172897_172995_173017_173033_173124_173127_173130_173243_173369_173414_173563_173576_173602_173610_173916_174198_174618_174639_174643_174682_174787_8000078_8000130_8000140; BDPPN=082321f1e011a1c9307ca7970c3b5cac; _j54_6ae_=xlTM-TogKuTwjQ7dZxRY1rSVfJk*x1nGcwmd; MCITY=-257:; BAIDUID=59199475F8A113FBBDFE7466217CB15D:FG=1; BDUSS=BDRnk3SllSUEkzeTNseG1aSXlweW04b3VRWFgwOGM2TDZLWUpKSzRqZHNSLTFnRVFBQUFBJCQAAAAAAAAAAAEAAAB-hMIXyc-52dSl7PcAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGy6xWBsusVgcF; BDUSS_BFESS=BDRnk3SllSUEkzeTNseG1aSXlweW04b3VRWFgwOGM2TDZLWUpKSzRqZHNSLTFnRVFBQUFBJCQAAAAAAAAAAAEAAAB-hMIXyc-52dSl7PcAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGy6xWBsusVgcF; BDORZ=FFFB88E999055A3F8A630C64834BD6D0; H_PS_PSSID=; BDSFRCVID=Cq8OJeC62xSiHUrezKktboxjFe099ObTH6ao1ktnCY2htGo3HKCMEG0Psx8g0KubmWgPogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0Ptf8g0f5; H_BDCLCKID_SF=tbCOoD--JD03fP36q4jo2-F3KUnh-nbK-DIX3buQ0-DW8pcNLTDK2httyboHKRTi25naW-n_3JK2bDTO-lO1j4_eDfRJ2fJktDbJKbAbWpvT8q5jDh3525ksD-Rt5trU5aRy0hvctb3cShPmQMjrDRLbXU6BK5vPbNcZ0l8K3l02V-bIe-t2b6Qh-p52f6LqtnRP; BDSFRCVID_BFESS=Cq8OJeC62xSiHUrezKktboxjFe099ObTH6ao1ktnCY2htGo3HKCMEG0Psx8g0KubmWgPogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0Ptf8g0f5; H_BDCLCKID_SF_BFESS=tbCOoD--JD03fP36q4jo2-F3KUnh-nbK-DIX3buQ0-DW8pcNLTDK2httyboHKRTi25naW-n_3JK2bDTO-lO1j4_eDfRJ2fJktDbJKbAbWpvT8q5jDh3525ksD-Rt5trU5aRy0hvctb3cShPmQMjrDRLbXU6BK5vPbNcZ0l8K3l02V-bIe-t2b6Qh-p52f6LqtnRP; _j47_ka8_=57; _fb537_=xlTM-TogKuTwOJalvnCIdpBillpF-phhl35-WXG13k2*Y5hJsoGwD6Mmd; BA_HECTOR=a08585050l2h04aha21gcr0r30r; delPer=0; PSINO=7; Hm_lvt_ad52b306e1ae4557f5d3534cce8f8bbf=1624041651,1624083171; __yjs_st=2_ODY4ZDU5MmIwNGJjYzgwMTcwYTE5ZGIyMzhlMDYxMGFlNTFkZGZmN2NlOTliOGYyODc3Y2UzMTk0MjQ1MTFkOTQxYjBkYzIzNTM2ZWQ5YzZiNDI3YWMzYWQxYzZmNDI2Y2QxNjE3NWVmZWVkZWMxYjQzNjNiMjc4YTljODkwNGI2OTc1NTQ1OTNmNjk5Zjg0NTA1Mzk2NGY4MGRkMjQ0YzRhODc1ZTQwOTlhMzljZjJhM2FiMjgxOTIxYWIzYWQ0YjA2ZTI0Mzg2NDNkOGEzNTFiNjc0OTkyODdiOWM4MmZjNjEwOTcwMDdiYzM3ZDgxOTExNDkzOGNlM2I4N2RkMGUwMmY0MzhiMTAzNTU2ODMwZmJhM2ExYzA4ODMxMDk3XzdfOWVlODAzMDQ=; ab_sr=1.0.1_YTY3NTdlY2EzYzJjNDY4OGQyNmJmN2Q3NDNkYTU2MjVmODBmZmUxZjRjZmJjZTRlYTBjMjVhMGYzY2UyN2ZmMzc4YzU3MDQwODA0Y2FjN2FlNGI4NDYxMjBlOWMzODcwNDhlZDgwMTc1ZTUzMDRiNTM1NmIxY2FmZWRhOGFjMDMxNWY0ZmFjZjBkNjNiZTU1YTNkNjExNTllOWRhMGUwMQ==; _s53_d91_=056ddb149824a8d208ac8ae78877123ef0f6696a0c1fdba54566ebc6004a57e33e56a43840742855c635cd2ad1ac1ed03d0a1c1880a893738436d4015250bba21aca27270f51517ffd881dc05aab15a59b6758eabae537811a63d8c12fb2ae2f34e084016ebc1b5ad7f60081780270983f2f4fbb1952b3f59305ffbe14f416f02fa8980289aae34773be1fff34ba3601661263bd849f0f7732ee4e0658afee82acc8a7a1e973b1e9eeaf538b2a299abf7897de80e2080d8bb12d1dbfd12e02d20318c8c046d87487b09d5a5fa943604dffc7ac8408131e5769293852bee82a2e; _y18_s21_=2a0c1e51; Hm_lpvt_ad52b306e1ae4557f5d3534cce8f8bbf=1624083182',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36'
}


def get_comp_list():
    url = 'https://www.nowcoder.com/community/page?token=&page=1&pageSize=500&_=1624034476971'
    try:
        r = requests.get(url, headers=headers)
        r.raise_for_status()
        r.encoding = r.apparent_encoding
        content = json.loads(r.text)
        comp_list = jsonpath(content, '$..community..name')
        with open('comp_list.json', 'w', encoding='utf-8') as f:
            json.dump(comp_list, f, indent=4, ensure_ascii=False)
    except Exception:
        traceback.print_exc()


def get_comp_pid(comp_name):
    url = f'https://aiqicha.baidu.com/s?q={str(comp_name)}&t=0'
    try:
        r = requests.get(url, headers=headers)
        r.raise_for_status()
        r.encoding = r.apparent_encoding
        soup = BeautifulSoup(r.text, 'html.parser')
        text = soup.body.script.string
        del_str1 = '/* eslint-disable */  window.loginStatus = null;  window.pageData = '
        del_str2 = ';\n        window.isSpider = null;\n        window.updateTime = null;\n\n        /* eslint-enable */'
        data = json.loads(text.replace(del_str1, '').replace(del_str2, ''))
        pid = data['result']['resultList'][0]['pid']
        return pid
    except Exception:
        traceback.print_exc()


def get_comp_info(ord, comp_name):
    time.sleep(0.5)
    pid = get_comp_pid(comp_name)
    time.sleep(0.5)
    url = 'https://aiqicha.baidu.com/detail/basicAllDataAjax?pid=' + str(pid)
    try:
        r = requests.get(url, headers=headers)
        r.raise_for_status()
        r.encoding = r.apparent_encoding
        data = json.loads(r.text)['data']['basicData']
        info_dict = {
            'compId': ord,
            'compName': comp_name,
            'compIndustry': data['industry'],
            'compSize': random.randint(2, 4),
            'compAddress': data['regAddr'],
            'complink': data['website'],
            'creditcode': data['taxNo'],
            'compEsDate': data['startDate'],
            'compIntro': data['scope'],
            'approveStatus': 2
        }
        return info_dict
    except Exception:
        traceback.print_exc()


if __name__ == '__main__':
    # 获取待爬取岗位的企业列表
    with open('comp_list.json', 'r', encoding='utf-8') as f:
        comp_list = json.load(f)

    # 定义输出字段
    header = ['compId', 'compName', 'compIndustry', 'compSize', 'compAddress',
              'complink', 'creditcode', 'compEsDate', 'compIntro', 'approveStatus']

    # 遍历获取岗位并输出到csv文件
    with open('comp_info.csv', 'w', encoding='utf-8', newline='') as f:
        csv_writer = csv.DictWriter(f, header)
        csv_writer.writeheader()
        logger.info('Start crawling ...')
        for ord, comp in enumerate(comp_list):
            logging.info(f'Crawling ord: {str(ord)} comp: {comp}')
            info_dict = get_comp_info(ord + 1, comp)
            if info_dict:
                csv_writer.writerow(info_dict)
