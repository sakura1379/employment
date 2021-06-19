import re
import csv
import json
import time
import requests
import traceback
from lxml import etree

headers = {
    'Accept': 'application/json, text/plain, */*',
    'Connection': 'keep-alive',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36'
}

# 获取待爬取企业列表
with open('comp_list.json', 'r', encoding='utf-8') as f:
    comp_list = json.load(f)
    comp_pattern = re.compile('|'.join(comp_list).lower())


def get_seminar_info():
    info_dicts, page = [], 1
    while len(info_dicts) < 100:
        if page == 1:
            url = 'https://xjh.haitou.cc/gz/uni-32'
        else:
            url = f'https://xjh.haitou.cc/gz/uni-32/page-{str(page)}'
        try:
            time.sleep(1)
            r = requests.get(url, headers=headers)
            r.raise_for_status()
        except Exception:
            traceback.print_exc()

        html = etree.HTML(r.text)
        info_lists = html.xpath('//tr[@data-last="1"]')
        for info in info_lists:
            # 解析题目
            title = info.xpath('./td[@class="cxxt-title"]/a/@title')[0]
            title = re.sub('\n学校.*\n地点.*$', '', title)
            title = re.sub(r'\(.*\)', '', title)
            if comp_pattern.search(title):
                # 解析时间
                stime = info.xpath('./td[@class="text-left cxxt-holdtime"]/span[1]/text()')[0]
                # 解析地址
                addr = info.xpath('./td[@class="text-ellipsis"]/span[1]/text()')[0]
                # 解析发布时间
                rtime = info.xpath('./td[@class="cxxt-time"]/text()')[0]
                info_dict = {
                    'seminarTitle': title,
                    'seminarTime': stime,
                    'seminarAddress': addr,
                    'approveStatus': 2,
                    'releaseTime': rtime
                }
                info_dicts.append(info_dict)
        page += 1

    return info_dicts


if __name__ == '__main__':
    # 定义输出字段
    header = ['seminarTitle', 'seminarTime', 'seminarAddress', 'approveStatus', 'releaseTime']

    # 遍历获取岗位并输出到csv文件
    with open('seminar_info.csv', 'w', encoding='utf-8', newline='') as f:
        csv_writer = csv.DictWriter(f, header)
        csv_writer.writeheader()
        info_dicts = get_seminar_info()
        csv_writer.writerows(info_dicts)
