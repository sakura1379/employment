CREATE DATABASE employment;
use employment;

CREATE TABLE `stu_info` (
  `stuNum` int(11) NOT NULL COMMENT '学生id [no click=sp_admin.id]',  /*唯一主键【用户信息表插入？】*/
  `stuName` varchar(30) DEFAULT NULL COMMENT '学生姓名 [text]', /*学生姓名*/
  /*`avatar` varchar(200) DEFAULT NULL COMMENT '学生头像 [img]',*/
  `stuGraUniversity` varchar(30) DEFAULT NULL COMMENT '学生学校 [text]', /*学生学校*/
  `stuMajor` varchar(30) DEFAULT NULL COMMENT '学生专业 [text]', /*学生专业*/
  `stuEducation` int(20) DEFAULT NULL COMMENT '学生学历 (1=本科, 2=研究生) [enum]', /*学生学历*/  
  `stJodKind` int(20) DEFAULT NULL COMMENT '求职性质 (1=实习, 2=校招, 3=实习和校招) [enum]', /*求职性质：实习or校招or实习和校招*/
  `stuGraduateTime` date DEFAULT NULL COMMENT '学生毕业年份 [date]', /*学生毕业年份*/
  /*`stuEmail` varchar(30) DEFAULT NULL COMMENT '学生邮箱 [text]', 学生邮箱*/
  `stuTelephone` varchar(20) DEFAULT NULL COMMENT '学生电话号码 [num]', /*学生电话号码*/
  `dreamAddress` varchar(50) DEFAULT NULL COMMENT '期望城市 [text]',/*期望城市*/
  `dreamPosition` varchar(50) DEFAULT NULL COMMENT '期望职位类别 [text]', /*期望职位类别*/
   `resume` varchar(100) DEFAULT NULL COMMENT '简历信息 [file]', /*简历信息*/
  PRIMARY KEY (`stuNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生信息表';


CREATE TABLE `job_info` (
  `jobId` int(11) NOT NULL AUTO_INCREMENT COMMENT '职位信息编号 [no]',  /*唯一主键*/
  `compId` int(11) NOT NULL COMMENT '公司编号 [no click=comp_info.compId]', /*公司编号*/
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
  `approveStatus` int(11) DEFAULT '1' COMMENT '审核状态 (1=未审核, 2=审核通过, 3=审核不通过) [enum]', /*审核状态*/
  PRIMARY KEY (`jobId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='职位信息表';


CREATE TABLE `company_info`(
  `compId` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司编号 [no]',/*唯一主键*/
  `compName` varchar(50) DEFAULT NULL COMMENT '企业名称 [text]',
  `compIndustry` varchar(30) DEFAULT NULL COMMENT '企业所在行业 [text]',
  `compSize` int(11) DEFAULT NULL COMMENT '企业规模 (1=100人以下, 2=999人以下, 3=9999人以下, 4=9999人以上) [enum]',
  `compAddress` varchar(50) DEFAULT NULL COMMENT '企业地址 [text]',
  `complink` varchar(50) DEFAULT NULL COMMENT '企业官网链接 [link]',
  `creditcode` varchar(30) DEFAULT NULL COMMENT '统一社会信用代码 [text]',
  `compEsDate` date DEFAULT NULL COMMENT '企业成立日期 [date]',
  `compIntro` text COMMENT '企业介绍 [textarea]',
  `approveStatus` int(11) DEFAULT '1' COMMENT '审核状态 (1=未审核, 2=审核通过, 3=审核不通过) [enum]', 
  PRIMARY KEY (`compId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='企业信息表';


CREATE TABLE `apply_info` (
  `stuNum` int(11) NOT NULL COMMENT '学生编号 [no]',   /*学生编号*/
  `jobId` int(11) NOT NULL COMMENT '职位信息编号 [no]',     /*招聘信息编号*/
  `internshipTime` int(11) DEFAULT NULL COMMENT '一周可实习时间 (1=一天, 2=两天, 3=三天, 4=四天, 5=五天, 6=六天) [enum]', /*一周可实习时间*/
  `dutyTime` int(11) DEFAULT NULL COMMENT '最快到岗时间 (1=一周内, 2=两周内, 3=一个月内, 4=三个月内) [enum]',/*最快到岗时间*/
  `ApplyStatus` int(11) DEFAULT '1' COMMENT '历史申请状态 (1=简历待筛选, 2=未通过, 3=一面, 4=二面, 5=HR面, 6=录用评估中, 7=录用意向, 8=已录用) [enum]',/*历史申请状态*/
  `newApplyStatus` int(11) DEFAULT '1' COMMENT '最近申请状态 (1=简历待筛选, 2=未通过, 3=一面, 4=二面, 5=HR面, 6=录用评估中, 7=录用意向, 8=已录用) [enum]',/*最近申请状态*/
  PRIMARY KEY (`stuNum`,`JobId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职位申请表';


CREATE TABLE `announcement` (
  `announceId` int(11) NOT NULL AUTO_INCREMENT  COMMENT '公告编号 [no]',   /*公告编号*/
  `announceTitle` varchar(50) DEFAULT NULL COMMENT '公告标题 [text]',/*公告标题*/
  `announceContent` text COMMENT '公告内容 [f]',/*公告内容*/
  `announceType` int(11) DEFAULT NULL COMMENT '公告类型 (1=系统公告, 2=经验分享) [enum]',/*公告类型*/
  `announceTime` date DEFAULT NULL COMMENT '发布时间 [date-create]',/*发布时间*/
  `adminId` int(11) NOT NULL COMMENT '管理员编号 [no]',   /*管理员编号*/
  PRIMARY KEY (`announceId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;


CREATE TABLE `mail_info` (
  `mailNum` int(11) NOT NULL AUTO_INCREMENT COMMENT '信箱编号 [no]',   /*信箱编号*/
  `stuNum` int(11) NOT NULL COMMENT '学生编号 [no]',   /*学生编号*/
  `infoId` int(11) DEFAULT NULL COMMENT '信息编号 [no]', /*信息编号*/
  `infoType` int(11) DEFAULT NULL COMMENT '信息类型 (1=宣讲会信息, 2=职位信息)[enum]',/*信息类型*/
  PRIMARY KEY (`mailNum`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;


CREATE TABLE `favor_info` (
  `favorNum` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏编号 [no]',   /*收藏编号*/
  `stuNum` int(11) NOT NULL COMMENT '学生编号 [no]',   /*学生编号*/
  `compId` int(11) DEFAULT NULL COMMENT '企业编号 [no]', /*企业编号*/
  `jobId` int(11) DEFAULT NULL COMMENT '职位信息编号 [no]',/*职位信息编号*/
  PRIMARY KEY (`favorNum`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;


CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id，--主键、自增',
  `name` varchar(100) NOT NULL COMMENT '账号名称',
  /*`avatar` varchar(500) DEFAULT NULL COMMENT '头像地址'可不要,*/
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  /*`pw` varchar(50) DEFAULT NULL COMMENT '明文密码'打算不要,*/
  `mail` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `role_id` int(11) DEFAULT '11' COMMENT '所属角色id',
  `status` int(11) DEFAULT '1' COMMENT '账号状态 (1=正常, 2=禁用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '上次登陆时间',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '上次登陆IP',
  `login_count` int(11) DEFAULT '0' COMMENT '登陆次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';


CREATE TABLE `ep_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id，--主键、自增',
  `name` varchar(20) NOT NULL COMMENT '角色名称, 唯一约束',
  `info` varchar(200) DEFAULT NULL COMMENT '角色详细描述',
  `is_lock` int(11) NOT NULL DEFAULT '1' COMMENT '是否锁定(1=是,2=否), 锁定之后不可随意删除, 防止用户误操作',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统角色表';


CREATE TABLE `ep_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID ',
  `permission_code` varchar(50) DEFAULT NULL COMMENT '权限码',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色权限中间表';


CREATE TABLE `comp_user` (
  `hrId` int(11) NOT NULL COMMENT 'hr编号 [no]',   /*hr编号*/
  `compId` int(11) DEFAULT NULL COMMENT '企业编号 [no]', /*企业编号*/
  PRIMARY KEY (`hrId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `seminar_info` (
  `seminarId` int(11) NOT NULL AUTO_INCREMENT  COMMENT '宣讲会信息编号 [no]',
  `seminarTitle` varchar(50) DEFAULT NULL COMMENT '宣讲会标题 [text]',/*宣讲会标题*/
  `seminarContent` text COMMENT '宣讲会内容 [f]',/*宣讲会内容*/
  `hrId` int(11) DEFAULT NULL COMMENT 'hr编号 [no]', /*hr编号*/
  `approveStatus` int(11) DEFAULT '1' COMMENT '审核状态 (1=未审核, 2=审核通过, 3=审核不通过) [enum]', /*审核状态*/
  `seminarTime` date DEFAULT NULL COMMENT '发布时间 [date-create]',/*发布时间*/
  PRIMARY KEY (`seminarId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 示例数据懒得写了，大家自己在本地添加吧 本地测试的时候不碍事的

insert into ep_role_permission() values (0, '1', 'bas', now());
insert into ep_role_permission() values (0, '1', '1', now());
insert into ep_role_permission() values (0, '1', '11', now());
insert into ep_role_permission() values (0, '1', '99', now());

insert into ep_role_permission() values (0, '1', 'console', now());
insert into ep_role_permission() values (0, '1', 'sql-console', now());
insert into ep_role_permission() values (0, '1', 'redis-console', now());
insert into ep_role_permission() values (0, '1', 'apilog-list', now());
insert into ep_role_permission() values (0, '1', 'form-generator', now());

insert into ep_role_permission() values (0, '1', 'auth', now());
insert into ep_role_permission() values (0, '1', 'role-list', now());
insert into ep_role_permission() values (0, '1', 'menu-list', now());
insert into ep_role_permission() values (0, '1', 'admin-list', now());
insert into ep_role_permission() values (0, '1', 'admin-add', now());

insert into ep_role_permission() values (0, '1', 'sp-cfg', now());
insert into ep_role_permission() values (0, '1', 'sp-cfg-app', now());
insert into ep_role_permission() values (0, '1', 'sp-cfg-server', now());

insert into ep_role_permission() values (0, '11', 'user_info', now());
insert into ep_role_permission() values (0, '11', 'stu_info', now());
insert into ep_role_permission() values (0, '121', 'user_info', now());
insert into ep_role_permission() values (0, '1212', 'user_info', now());
insert into ep_role_permission() values (0, '1', 'user_info', now());

insert into ep_role_permission() values (0, '121', 'job_info', now());
insert into ep_role_permission() values (0, '121', 'seminar_info', now());
insert into ep_role_permission() values (0, '121', 'mail_info', now());
insert into ep_role_permission() values (0, '121', 'company_info', now());
insert into ep_role_permission() values (0, '1212', 'job_info', now());
insert into ep_role_permission() values (0, '1212', 'seminar_info', now());
insert into ep_role_permission() values (0, '1212', 'mail_info', now());
insert into ep_role_permission() values (0, '1212', 'company_info', now());
insert into ep_role_permission() values (0, '1212', 'company_info_update', now());

insert into ep_role_permission() values (0, '1', 'announcement', now());
insert into ep_role_permission() values (0, '1212', 'comp_user', now());


insert into ep_role_permission() values (0, '11', 'favor_info', now());
insert into ep_role_permission() values (0, '121', 'favor_info', now());
insert into ep_role_permission() values (0, '1212', 'favor_info', now());
insert into ep_role_permission() values (0, '11', 'apply_info', now());
insert into ep_role_permission() values (0, '121', 'apply_info', now());
insert into ep_role_permission() values (0, '1212', 'apply_info', now());


INSERT INTO `ep_role`(`role_id`, `name`, `info`, `is_lock`) VALUES (1, '超级管理员', '最高权限', 1);
INSERT INTO `ep_role`(`role_id`, `name`, `info`, `is_lock`) VALUES (11, '学生用户', '普通账号', 2);
INSERT INTO `ep_role`(`role_id`, `name`, `info`, `is_lock`) VALUES (121, '公司普通管理员hr', '普通管理账号', 2);
INSERT INTO `ep_role`(`role_id`, `name`, `info`, `is_lock`) VALUES (1212, '公司超级管理员', '超级管理账号', 2);

--密码实际上都是1234567
INSERT INTO `user_info` VALUES (10001, 'zlr', '99140C1B8A8400747346571F9212BA0B', '18023893551@163.com', 1, 1, '2021-05-11 21:58:11', NULL, NULL, 0);
INSERT INTO `user_info` VALUES (10002, 'clh', '5C41A87C54D63B760D81B039A2BAB3FF', '992456536@qq.com', 11, 1, '2021-05-11 21:59:11', NULL, NULL, 0);
INSERT INTO `user_info` VALUES (10003, 'cjy', '7B51C5CF2E531C27B12672662BEC2C4C', '1234567@qq.com', 121, 1, '2021-05-11 22:01:07', NULL, NULL, 0);
INSERT INTO `user_info` VALUES (10004, 'zy', '0CF04C6807910DEC54FA44104147E669', '123456@qq.com', 1212, 1, '2021-05-11 23:14:16', NULL, NULL, 0);