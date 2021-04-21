# employment-backend

## 数据库结构

### 1.1 学生表

==学生编号==+学生姓名+学生账号+学生密码+学生邮箱+学生电话+学生学校+毕业年份+学生学历+学生专业+求职性质+预期岗位+预期地点+简历信息

==如果想要多个期望职位的话就另开一个表==

stu_info

```sql
CREATE TABLE `stu_info` (
  `stuNum` int(11) NOT NULL COMMENT '学生id [no click=sp_admin.id]',  /*唯一主键【用户信息表插入？】*/
  `stuName` varchar(30) DEFAULT NULL COMMENT '学生姓名 [text]', /*学生姓名*/
  `avatar` varchar(200) DEFAULT NULL COMMENT '学生头像 [img]',
  `stuGraUniversity` varchar(30) DEFAULT NULL COMMENT '学生学校 [text]', /*学生学校*/
  `stuMajor` varchar(30) DEFAULT NULL COMMENT '学生专业 [text]', /*学生专业*/
  `stuEducation` int(20) DEFAULT NULL COMMENT '学生学历 (1=本科, 2=研究生) [enum]', /*学生学历*/  
  `stJodKind` int(20) DEFAULT NULL COMMENT '学生学历 (1=实习, 2=校招, 3=实习和校招) [enum]', /*求职性质：实习or校招or实习和校招*/
  `stuGraduateTime` date DEFAULT NULL COMMENT '学生毕业年份 [date]', /*学生毕业年份*/
  `stuEmail` varchar(30) DEFAULT NULL COMMENT '学生邮箱 [text]', /*学生邮箱*/
  `stuTelephone` varchar(20) DEFAULT NULL COMMENT '学生电话号码 [num]', /*学生电话号码*/
  `dreamAddress` varchar(50) DEFAULT NULL COMMENT '期望城市 [text]',/*期望城市*/
  `dreamPosition` varchar(50) DEFAULT NULL COMMENT '期望职位类别 [text]', /*期望职位类别*/
   `resume` varchar(100) DEFAULT NULL COMMENT '简历信息 [file]', /*简历信息*/
  PRIMARY KEY (`stuNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生信息表';
```



### 1.2 企业招聘时间安排表

企业编号+企业名称+时间安排（批数号+岗位类型+网申截止时间+笔试时间）

MongoDB吧？

schedule

compId+compName+schedule（batchNum: +jobType：+deadline： + writeTime



### 1.3 职位信息表

==招聘信息编号==+岗位名称+岗位类别+招聘类别+招聘状态+发布时间+工作地点+职位描述+截止日期+已投递人数+薪资+其他待遇+招聘信息审核状态+==企业编号==

job_info

```sql
CREATE TABLE `job_info` (
  `jobId` int(11) NOT NULL AUTO_INCREMENT COMMENT '职位信息编号 [no]',  /*唯一主键*/
  `compId` varchar(30) NOT NULL COMMENT '公司编号 [no click=comp_info.compId]', /*公司编号*/
  `jobName` varchar(30) DEFAULT NULL COMMENT '职位名称 [text j=like]', /*岗位名称*/
  `jobType` varchar(30) DEFAULT NULL COMMENT '岗位类别 [text j=like]', /*岗位类别*/
  `jobKind` int(11) DEFAULT NULL COMMENT '招聘性质 (1=实习, 2=校招, 3=实习和校招) [enum]', /*招聘性质：实习or校招*/
  `status` int(11) DEFAULT NULL COMMENT '招聘状态 (1=招聘中, 2=已结束) [enum]', /*招聘状态*/
  `relDate` date DEFAULT NULL COMMENT '发布日期 [date-create]', /*发布时间*/
  `jobAddress` varchar(50) DEFAULT NULL COMMENT '工作地点 [text]', /*工作地点*/
  `jobCon` text COMMENT '职位描述 [f]', /*职位描述*/
  `jobDeadline` date DEFAULT NULL COMMENT '截止日期 [date]', /*投递截止日期*/
  `deliverNum` int(11) DEFAULT NULL COMMENT '已投递人数 [num]',/*已投递人数*/
  `salary` varchar(50) DEFAULT NULL COMMENT '薪资 [text]', /*薪资*/
  `approveStatus` int(11) NOT NULL COMMENT '审核状态 (1=未审核, 2=审核通过, 3=审核不通过) [enum]', /*审核状态*/
  PRIMARY KEY (`jobId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='职位信息表';
```



### 1.4 企业信息表

==企业编号==+企业负责人+统一社会代码+企业密码+企业名称+企业logo+企业地址+企业所在行业+企业简介+企业官网链接+企业成立日期+企业融资轮次+企业注册资本+企业联系电话+企业信息审核状态

comp_info

```sql
CREATE TABLE `company_info`(
  `compId` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司编号 [no]',/*唯一主键*/
  `compName` varchar(50) DEFAULT NULL COMMENT '企业名称 [text]',
  `compIndustry` varchar(30) DEFAULT NULL COMMENT '企业所在行业 [text]',
  `compSize` varchar(30) DEFAULT NULL COMMENT '企业规模 [text]',
  `compAddress` varchar(50) DEFAULT NULL COMMENT '企业地址 [text]',
  `complink` varchar(50) DEFAULT NULL COMMENT '企业官网链接 [link]',
  `creditcode` varchar(30) DEFAULT NULL COMMENT '统一社会信用代码 [text]',
  `compEsDate` date DEFAULT NULL COMMENT '企业成立日期 [date]',
  `compIntro` text COMMENT '企业介绍 [textarea]',
  `approveStatus` varchar(20) NOT NULL COMMENT '审核状态 (1=未审核, 2=审核通过, 3=审核不通过) [enum]', 
  PRIMARY KEY (`compId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='企业信息表';
```



### 1.5 职位申请表【修改】

==学生编号==+可实习时间（新插入）+到岗时间（新插入）+==招聘信息编号==+申请状态【终止前状态、最近状态】 逻辑：更新申请状态时存入上一个状态

apply_info

```sql
CREATE TABLE `apply_info` (
  `stuNum` int(11) NOT NULL COMMENT '学生编号 [no]',   /*学生编号*/
  `jobId` int(11) NOT NULL COMMENT '职位信息编号 [no]',     /*招聘信息编号*/
  `internshipTime` int(11) DEFAULT NULL COMMENT '一周可实习时间 (1=一天, 2=两天, 3=三天, 4=四天, 5=五天, 6=六天) [enum]', /*一周可实习时间*/
  `dutyTime` int(11) DEFAULT NULL COMMENT '最快到岗时间 (1=一周内, 2=两周内, 3=一个月内, 4=三个月内) [enum]',/*最快到岗时间*/
  `ApplyStatus` int(11) NOT NULL COMMENT '历史申请状态 (1=简历待筛选, 2=未通过, 3=一面, 4=二面, 5=HR面, 6=录用评估中, 7=录用意向, 8=已录用) [enum]',/*历史申请状态*/
  `newApplyStatus` int(11) NOT NULL COMMENT '最近申请状态 (1=简历待筛选, 2=未通过, 3=一面, 4=二面, 5=HR面, 6=录用评估中, 7=录用意向, 8=已录用) [enum]',/*最近申请状态*/
  PRIMARY KEY (`stuNum`,`JobId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职位申请表';

```



### 1.6 管理员信息表【感觉不太需要】【删除】

管理员编号+管理员名称+密码

administrator

```sql
CREATE TABLE `administrator` (
  `adminId` int(11) NOT NULL AUTO_INCREMENT,   /*管理员编号*/
  `adminName` varchar(20) DEFAULT NULL,/*管理员姓名*/
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
```



### 1.7 公告信息表【公告类型未确定】

公告信息编号+公告标题+公告内容+公告类型+发布时间+管理员编号

announcement

```sql
CREATE TABLE `announcement` (
  `announceId` int(11) NOT NULL AUTO_INCREMENT  COMMENT '公告编号 [no]',   /*公告编号*/
  `announceTitle` varchar(50) DEFAULT NULL COMMENT '公告标题 [text]',/*公告标题*/
  `announceContent` text COMMENT '公告内容 [f]',/*公告内容*/
  `announceType` int(11) DEFAULT NULL COMMENT '公告类型 (1=系统公告, 2=经验分享, 3=宣讲会信息) [enum]',/*公告类型*/
  `announceTime` date DEFAULT NULL COMMENT '发布时间 [date-create]',/*发布时间*/
  `adminId` int(11) NOT NULL COMMENT '管理员编号 [no]',   /*管理员编号*/
  PRIMARY KEY (`announceId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
```



### 1.8 学生信箱表【不确定这样设计可不可以】

学生编号+公告编号or职位信息编号+==学生邮箱内容链接（不知道这个怎么获得）==  【编号、类型】

mail_info

```sql
CREATE TABLE `mail_info` (
  `stuNum` int(11) NOT NULL COMMENT '学生编号 [no]',   /*学生编号*/
  `announceId` int(11) DEFAULT NULL COMMENT '公告编号 [no]', /*公告编号*/
  `jobId` int(11) DEFAULT NULL COMMENT '职位信息编号 [no]',/*职位信息编号*/
  PRIMARY KEY (`stuNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```



### 1.9 学生收藏表

学生编号+职位信息编号+企业编号

favor_info

```sql
CREATE TABLE `favor_info` (
  `stuNum` int(11) NOT NULL COMMENT '学生编号 [no]',   /*学生编号*/
  `compId` int(11) DEFAULT NULL COMMENT '企业编号 [no]', /*企业编号*/
  `jobId` int(11) DEFAULT NULL COMMENT '职位信息编号 [no]',/*职位信息编号*/
  PRIMARY KEY (`stuNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```



### 1.10 登录用户表【修改】

不知道如何处理与其他三个角色信息表的关系

==新增信息的时候首先新增登录用户表，然后根据赋予的id再插入其他三个角色信息表？（这样就不会主键冲突了==

==手机号重复了或许可以统一采用邮箱或账号名称登录，不用手机号==

user

暂时

```sql
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id，--主键、自增',
  `name` varchar(100) NOT NULL COMMENT '账号名称',
  /*`avatar` varchar(500) DEFAULT NULL COMMENT '头像地址'可不要,*/
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  /*`pw` varchar(50) DEFAULT NULL COMMENT '明文密码'打算不要,*/
  `mail` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `role_id` int(11) DEFAULT '11' COMMENT '所属角色id',
  `status` int(11) DEFAULT '1' COMMENT '账号状态(1=正常, 2=禁用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '上次登陆时间',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '上次登陆IP',
  `login_count` int(11) DEFAULT '0' COMMENT '登陆次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';
```



### 1.11 用户角色表

类似学生、管理员、企业用户

![image-20210417154125833](/Users/sskura/Library/Application Support/typora-user-images/image-20210417154125833.png)

```sql
CREATE TABLE `ep_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id，--主键、自增',
  `name` varchar(20) NOT NULL COMMENT '角色名称, 唯一约束',
  `info` varchar(200) DEFAULT NULL COMMENT '角色详细描述',
  `is_lock` int(11) NOT NULL DEFAULT '1' COMMENT '是否锁定(1=是,2=否), 锁定之后不可随意删除, 防止用户误操作',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统角色表';
```



### 1.12 角色权限对应表

```sql
CREATE TABLE `ep_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID ',
  `permission_code` varchar(50) DEFAULT NULL COMMENT '菜单项ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色权限中间表';
```



### 1.13 企业用户对应表

企业编号（企业信息表）+企业用户编号（用户表）

一个企业可以有很多个HR

```sql
CREATE TABLE `comp_user` (
  `hrId` int(11) NOT NULL COMMENT 'hr编号 [no]',   /*hr编号*/
  `compId` int(11) DEFAULT NULL COMMENT '企业编号 [no]', /*企业编号*/
  PRIMARY KEY (`hrId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```



