# -*- coding: utf-8 -*-
import re
import csv
import json
import time
import logging
import requests
import traceback

# 设置日志
logging.basicConfig(filename='job.log',
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


def get_job_info(ord, comp_name):
    url = 'https://nowpick.nowcoder.com/u/job/list?token='
    pagesize = 35 if ord <= 10 else 5
    params = {
        'recruitType': '2',
        'page': 1,
        'pageSize': pagesize,
        'query': comp_name
    }
    try:
        r = requests.post(url, data=params, headers=headers)
        r.raise_for_status()
        r.encoding = r.apparent_encoding
        content = json.loads(r.text)
        # 判断岗位数
        if content['data']['totalCount'] < 1:
            return None
        # 获取岗位信息
        info_dicts = []
        for data in content['data']['datas']:
            info_dict = parse_singe_job(ord, comp_name, data)
            if info_dict:
                info_dicts.append(info_dict)
        return info_dicts
    except Exception:
        traceback.print_exc()


def parse_singe_job(ord, comp_name, data):
    # 解析岗位描述
    job_content = json.loads(data['ext'])['infos']
    if '我' in job_content or '内推' in job_content:
        return None
    # 解析岗位类别
    job_name = data['jobName']
    type_pattern = '前端|后端|算法|客户端|测试|数据分析|大数据|android|ios|移动端'
    match = re.search(type_pattern, job_name.lower())
    if match:
        job_type = match.group(0)
        if job_type in ['android', 'ios']:
            job_type = '移动端'
    else:
        job_type = '开发'
    # 解析岗位性质
    job_kind = 1 if '实习' in job_name else 2
    # 解析工作地点
    job_city = data['jobCity'] if data['jobCity'] else data['jobAddress']
    # 解析薪资
    if data['salaryMin'] > 0:
        salary = str(data['salaryMin']) + '-' + str(data['salaryMax']) + '元/天'
    else:
        salary = '薪资面议'
    # 输出info
    info_dict = {
            'compName': comp_name,
            'compId': ord,
            'jobName': job_name,
            'jobType': job_type,
            'jobKind': job_kind,
            'status': 1,
            'relDate': parse_time(data['deliverBegin']),
            'jobAddress': job_city,
            'jobCon': job_content,
            'jobDeadline': parse_time(data['deliverEnd']),
            'deliverNum': 0,
            'salary': salary,
            'approveStatus': 2
        }
    return info_dict


def parse_time(time_stamp):
    if time_stamp:
        timeArray = time.localtime(time_stamp // 1000)
        return time.strftime("%Y-%m-%d", timeArray)
    else:
        return 'null'


if __name__ == '__main__':
    # 获取待爬取岗位的企业列表
    with open('comp_list.json', 'r', encoding='utf-8') as f:
        comp_list = json.load(f)

    # 定义输出字段
    header = ['compName', 'compId', 'jobName', 'jobType', 'jobKind', 'status',
              'relDate', 'jobAddress', 'jobCon', 'jobDeadline', 'deliverNum',
              'salary', 'approveStatus']

    # 遍历获取岗位并输出到csv文件
    with open('job_info.csv', 'w', encoding='utf-8', newline='') as f:
        csv_writer = csv.DictWriter(f, header)
        csv_writer.writeheader()
        total_info = 0
        logger.info('Start crawling ...')
        for ord, comp in enumerate(comp_list):
            info_dicts = get_job_info(ord + 1, comp)
            if info_dicts:
                csv_writer.writerows(info_dicts)
                # 判断终止
                total_info += len(info_dicts)
                logging.info(f'Crawling ord: {str(ord)} comp: {comp} dict_len: {str(len(info_dicts))} total: {str(total_info)}')
            if total_info > 500:
                logger.info(f'Finish! Current ord: {str(ord)} comp: {comp}')
                break
