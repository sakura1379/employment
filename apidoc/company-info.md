# 企业信息表


---
### 1、增加
- 接口
``` api
	/CompanyInfo/add
```
- 参数
``` p
	{Integer}	compId			公司编号 
	{String}	compName			企业名称 
	{String}	compIndustry			企业所在行业 
	{String}	compSize			企业规模 
	{String}	compAddress			企业地址 
	{String}	complink			企业官网链接 
	{String}	creditcode			统一社会信用代码 
	{Date}	compEsDate			企业成立日期 
	{String}	compIntro			企业介绍 
	{String}	approveStatus			审核状态(1=未审核,2=审核通过,3=审核不通过) 
```
- 返回 
@import(res)


--- 
### 2、删除
- 接口
``` api
	/CompanyInfo/delete
```
- 参数
``` p
	{Integer}	compId			要删除的记录compId
```
- 返回
@import(res)


---
### 3、批量删除
- 接口
``` api
	/CompanyInfo/deleteByIds
```
- 参数
``` p
	{数组}	ids			要删除的记录id数组，逗号隔开，例：ids=1,2,3,4
```
- 返回
@import(res)


---
### 4、修改
- 接口
``` api
	/CompanyInfo/update
```
- 参数
``` p
	{Integer}	compId			公司编号  (修改条件)
	{String}	compName			企业名称 
	{String}	compIndustry			企业所在行业 
	{String}	compSize			企业规模 
	{String}	compAddress			企业地址 
	{String}	complink			企业官网链接 
	{String}	creditcode			统一社会信用代码 
	{Date}	compEsDate			企业成立日期 
	{String}	compIntro			企业介绍 
	{String}	approveStatus			审核状态(1=未审核,2=审核通过,3=审核不通过) 
```
- 返回
@import(res)


---
### 5、查 - 根据id
- 接口
```  api 
	/CompanyInfo/getById
```
- 参数
``` p
	{Integer}	id			要查询的记录id
```
- 返回示例
``` js
	{
		"code": 200,
		"msg": "ok",
		"data": {
			"compId": 0,		// 公司编号
			"compName": "",		// 企业名称
			"compIndustry": "",		// 企业所在行业
			"compSize": "",		// 企业规模
			"compAddress": "",		// 企业地址
			"complink": "",		// 企业官网链接
			"creditcode": "",		// 统一社会信用代码
			"compEsDate": new Date(),		// 企业成立日期
			"compIntro": "",		// 企业介绍
			"approveStatus": "",		// 审核状态(1=未审核,2=审核通过,3=审核不通过)
		},
		"dataCount": -1
	}
```


---
### 6、查集合 - 根据条件
- 接口
``` api
	/CompanyInfo/getList
```
- 参数 （参数为空时代表忽略指定条件）
``` p
	{int}	pageNo = 1			当前页
	{int}	pageSize = 10		页大小 
	{Integer}	compId			公司编号 
	{String}	compName			企业名称 
	{String}	compIndustry			企业所在行业 
	{String}	compSize			企业规模 
	{String}	compAddress			企业地址 
	{String}	complink			企业官网链接 
	{String}	creditcode			统一社会信用代码 
	{Date}	compEsDate			企业成立日期 
	{String}	compIntro			企业介绍 
	{String}	approveStatus			审核状态(1=未审核,2=审核通过,3=审核不通过) 
	{int}	sortType = 0		排序方式 (0 = 默认, 1 = 公司编号, 2 = 企业名称, 3 = 企业所在行业, 4 = 企业规模, 5 = 企业地址, 6 = 统一社会信用代码, 7 = 企业成立日期, 8 = 审核状态(1=未审核,2=审核通过,3=审核不通过))
```
- 返回 
``` js
	{
		"code": 200,
		"msg": "ok",
		"data": [
			// 数据列表，格式参考getById 
		],
		"dataCount": 100	// 数据总数
	}
```




---
### 7、修改 - 空值不改
- 接口
``` api
	/CompanyInfo/updateByNotNull
```
- 参数
``` p
	{Integer}	compId			公司编号  (修改条件)
	{String}	compName			企业名称 
	{String}	compIndustry			企业所在行业 
	{String}	compSize			企业规模 
	{String}	compAddress			企业地址 
	{String}	complink			企业官网链接 
	{String}	creditcode			统一社会信用代码 
	{Date}	compEsDate			企业成立日期 
	{String}	compIntro			企业介绍 
	{String}	approveStatus			审核状态(1=未审核,2=审核通过,3=审核不通过) 
```
- 返回
@import(res)







