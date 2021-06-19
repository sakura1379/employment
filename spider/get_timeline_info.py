# -*- coding: utf-8 -*-
import json
import logging
import random
import requests
import traceback

# 设置日志
logging.basicConfig(filename='timeline.log',
                    filemode='w',
                    level=logging.INFO,
                    format='[%(asctime)s]  %(levelname)-12s | %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S')
logger = logging.getLogger()

headers = {
    'Accept': 'text/plain, */*; q=0.01',
    'Connection': 'keep-alive',
    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36'
}

num2key = {0: 'referral', 1: 'resume', 2: 'written', 3: 'audition', 4: 'offer'}


def get_timeline(comp_list):
    time_infos, page, crawled = [], 1, []
    while len(time_infos) < 450 and page < 100:
        url = f'https://www.nowcoder.com/school/schedule/data?token=&query=&typeId=0&propertyId=0&onlyFollow=false&page={str(page)}&_=1624067070071'
        try:
            r = requests.get(url, headers=headers)
            r.raise_for_status()
            r.encoding = r.apparent_encoding
            content = json.loads(r.text)
            curr_list = content['data']['companyList']
            for data in curr_list:
                if data['name'] not in comp_list or data['name'] in crawled:   # 判断是否为目标公司
                    continue
                # 解析所需字段
                logging.info(f'Crawling comp: ' + data['name'] + ' total: ' + str(len(time_infos)))
                time_info = {}
                time_info['compName'] = data['name']
                time_info['compId'] = comp_list.index(data['name']) + 1
                temp1, temp2, temp3 = random.randint(1, 30), random.randint(1, 29), random.randint(8, 9)
                random_time = [
                    f'6月{str(random.randint(1, 30))}日-7月{str(temp1)}日',
                    f'6月{str(random.randint(1, 30))}日-7月{str(temp1)}日',
                    f'7月{str(temp1 + 1)}日',
                    f'7月{str(temp1 + 2)}日-{str(temp3)}月{str(temp2)}日',
                    f'{str(temp3)}月{str(temp2 + 1)}日起'
                ]
                for num in range(5):
                    key = num2key[num]
                    if data['schedules']:
                        if num < len(data['schedules']):
                            time_info[key] = data['schedules'][num]['time']
                        else:
                            time_info[key] = random_time[num]
                    else:
                        time_info[key] = random_time[num]
                time_infos.append(time_info)
                crawled.append(data['name'])
            page += 1
        except KeyError:
            traceback.print_exc()
            print(data['name'])

    return time_infos


if __name__ == '__main__':
    # 获取待爬取岗位的企业列表
    with open('comp_list.json', 'r', encoding='utf-8') as f:
        comp_list = json.load(f)

    time_info = get_timeline(comp_list)
    time_info = sorted(time_info, key=lambda item: item['compId'])
    with open('timeline.json', 'w', encoding='utf-8') as f:
        json.dump(time_info, f, indent=4, ensure_ascii=False)