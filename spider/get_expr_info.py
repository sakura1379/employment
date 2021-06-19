import re
import csv
import json
import time
import logging
import requests
import traceback
from lxml import etree
from jsonpath import jsonpath

headers = {
    'Accept': 'application/json, text/plain, */*',
    'Connection': 'keep-alive',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36'
}


def get_comp_list():
    url = 'https://www.nowcoder.com/community/page?token=&page=1&pageSize=500&_=1624034476971'
    try:
        r = requests.get(url, headers=headers)
        r.raise_for_status()
        r.encoding = r.apparent_encoding
        content = json.loads(r.text)
        comp_names = jsonpath(content, '$..community..name')
        comp_ids = jsonpath(content, '$..community..id')
        comp_list = zip(comp_names, comp_ids)
        return comp_list
    except Exception:
        traceback.print_exc()


def get_expr_info(comp_name, comp_id):
    url = f'https://www.nowcoder.com/discuss/tag/{str(comp_id)}?type=2'
    info_dicts = []
    try:
        r = requests.get(url, headers=headers)
        r.raise_for_status()
        html = etree.HTML(r.text)
        info_list = html.xpath('//div[@class="discuss-detail"]')
        for info in info_list:
            parent1 = info.xpath('./div[@class="discuss-main clearfix"][1]/a[@rel="prefetch"]')[0]
            title = parent1.text.strip('\n')
            link = 'https://www.nowcoder.com' + parent1.attrib['href']
            parent2 = info.xpath('./div[@class="feed-foot"][1]/div[@class="feed-origin"]/p/a')[0]
            match = re.search('\d{4}-\d{2}-\d{2}', parent2.tail)
            retime = match.group(0) if match else time.strftime('%Y-%m-%d')
            info_dict = {
                'period': 1,
                'title': title,
                'time': retime,
                'link': link 
            }
            info_dicts.append(info_dict)
        return info_dicts
    except Exception:
        traceback.print_exc()    


if __name__ == '__main__':
    # 获取待爬取岗位的企业列表
    comp_list = get_comp_list()

    # 定义输出字段
    header = ['period', 'title', 'time', 'link']

    # 遍历获取岗位并输出到csv文件
    with open('expr_notice.csv', 'w', encoding='utf-8', newline='') as f:
        csv_writer = csv.DictWriter(f, header)
        csv_writer.writeheader()
        total_info = 0
        logger.info('Start crawling ...')
        for comp, id in comp_list:
            info_dicts = get_expr_info(comp, id)
            if info_dicts:
                csv_writer.writerows(info_dicts)
                # 判断终止
                total_info += len(info_dicts)
                logging.info(f'Crawling ord: {str(ord)} comp: {comp} dict_len: {str(len(info_dicts))} total: {str(total_info)}')
            if total_info > 500:
                logger.info(f'Finish! Current ord: {str(ord)} comp: {comp}')
                break
