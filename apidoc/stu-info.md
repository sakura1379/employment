# 学生信息表


---
### 1、增加
- 接口
``` api
	/StuInfo/add
```
- 参数
``` p
	{Integer}	stuNum			学生id 
	{String}	stuName			学生姓名 
	{String}	avatar			学生头像 
	{String}	stuGraUniversity			学生学校 
	{String}	stuMajor			学生专业 
	{Integer}	stuEducation			学生学历(1=本科,2=研究生) 
	{Integer}	stJodKind			学生学历(1=实习,2=校招,3=实习和校招) 
	{Date}	stuGraduateTime			学生毕业年份 
	{String}	stuEmail			学生邮箱 
	{String}	stuTelephone			学生电话号码 
	{String}	dreamAddress			期望城市 
	{String}	dreamPosition			期望职位类别 
	{String}	resume			简历信息 
```
- 返回 
@import(res)


--- 
### 2、删除
- 接口
``` api
	/StuInfo/delete
```
- 参数
``` p
	{Integer}	stuNum			要删除的记录stuNum
```
- 返回
@import(res)


---
### 3、批量删除
- 接口
``` api
	/StuInfo/deleteByIds
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
	/StuInfo/update
```
- 参数
``` p
	{Integer}	stuNum			学生id  (修改条件)
	{String}	stuName			学生姓名 
	{String}	avatar			学生头像 
	{String}	stuGraUniversity			学生学校 
	{String}	stuMajor			学生专业 
	{Integer}	stuEducation			学生学历(1=本科,2=研究生) 
	{Integer}	stJodKind			学生学历(1=实习,2=校招,3=实习和校招) 
	{Date}	stuGraduateTime			学生毕业年份 
	{String}	stuEmail			学生邮箱 
	{String}	stuTelephone			学生电话号码 
	{String}	dreamAddress			期望城市 
	{String}	dreamPosition			期望职位类别 
	{String}	resume			简历信息 
```
- 返回
@import(res)


---
### 5、查 - 根据id
- 接口
```  api 
	/StuInfo/getById
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
			"stuNum": 0,		// 学生id
			"stuName": "",		// 学生姓名
			"avatar": "",		// 学生头像
			"stuGraUniversity": "",		// 学生学校
			"stuMajor": "",		// 学生专业
			"stuEducation": 0,		// 学生学历(1=本科,2=研究生)
			"stJodKind": 0,		// 学生学历(1=实习,2=校招,3=实习和校招)
			"stuGraduateTime": new Date(),		// 学生毕业年份
			"stuEmail": "",		// 学生邮箱
			"stuTelephone": "",		// 学生电话号码
			"dreamAddress": "",		// 期望城市
			"dreamPosition": "",		// 期望职位类别
			"resume": "",		// 简历信息
		},
		"dataCount": -1
	}
```


---
### 6、查集合 - 根据条件
- 接口
``` api
	/StuInfo/getList
```
- 参数 （参数为空时代表忽略指定条件）
``` p
	{int}	pageNo = 1			当前页
	{int}	pageSize = 10		页大小 
	{Integer}	stuNum			学生id 
	{String}	stuName			学生姓名 
	{String}	avatar			学生头像 
	{String}	stuGraUniversity			学生学校 
	{String}	stuMajor			学生专业 
	{Integer}	stuEducation			学生学历(1=本科,2=研究生) 
	{Integer}	stJodKind			学生学历(1=实习,2=校招,3=实习和校招) 
	{Date}	stuGraduateTime			学生毕业年份 
	{String}	stuEmail			学生邮箱 
	{String}	stuTelephone			学生电话号码 
	{String}	dreamAddress			期望城市 
	{String}	dreamPosition			期望职位类别 
	{String}	resume			简历信息 
	{int}	sortType = 0		排序方式 (0 = 默认, 1 = 学生id, 2 = 学生姓名, 3 = 学生学校, 4 = 学生专业, 5 = 学生学历(1=本科,2=研究生), 6 = 学生学历(1=实习,2=校招,3=实习和校招), 7 = 学生毕业年份, 8 = 学生邮箱, 9 = 学生电话号码, 10 = 期望城市, 11 = 期望职位类别)
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
	/StuInfo/updateByNotNull
```
- 参数
``` p
	{Integer}	stuNum			学生id  (修改条件)
	{String}	stuName			学生姓名 
	{String}	avatar			学生头像 
	{String}	stuGraUniversity			学生学校 
	{String}	stuMajor			学生专业 
	{Integer}	stuEducation			学生学历(1=本科,2=研究生) 
	{Integer}	stJodKind			学生学历(1=实习,2=校招,3=实习和校招) 
	{Date}	stuGraduateTime			学生毕业年份 
	{String}	stuEmail			学生邮箱 
	{String}	stuTelephone			学生电话号码 
	{String}	dreamAddress			期望城市 
	{String}	dreamPosition			期望职位类别 
	{String}	resume			简历信息 
```
- 返回
@import(res)







