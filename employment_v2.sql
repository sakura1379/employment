/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : employment

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 11/06/2021 21:18:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement` (
  `announceId` int NOT NULL AUTO_INCREMENT COMMENT '公告编号 [no]',
  `announceTitle` varchar(50) DEFAULT NULL COMMENT '公告标题 [text]',
  `announceContent` text COMMENT '公告内容 [f]',
  `announceType` int DEFAULT NULL COMMENT '公告类型 (1=系统公告, 2=经验分享) [enum]',
  `announceTime` date DEFAULT NULL COMMENT '发布时间 [date-create]',
  `adminId` int NOT NULL COMMENT '管理员编号 [no]',
  PRIMARY KEY (`announceId`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of announcement
-- ----------------------------
BEGIN;
INSERT INTO `announcement` VALUES (1001, '系统公告', '第一条测试公告，你好，我是帅哥。', 1, '2021-05-11', 10001);
COMMIT;

-- ----------------------------
-- Table structure for apply_info
-- ----------------------------
DROP TABLE IF EXISTS `apply_info`;
CREATE TABLE `apply_info` (
  `stuNum` int NOT NULL COMMENT '学生编号 [no]',
  `jobId` int NOT NULL COMMENT '职位信息编号 [no]',
  `internshipTime` int DEFAULT NULL COMMENT '一周可实习时间 (1=一天, 2=两天, 3=三天, 4=四天, 5=五天, 6=六天) [enum]',
  `dutyTime` int DEFAULT NULL COMMENT '最快到岗时间 (1=一周内, 2=两周内, 3=一个月内, 4=三个月内) [enum]',
  `ApplyStatus` int DEFAULT '1' COMMENT '历史申请状态 (1=简历待筛选, 2=未通过, 3=一面, 4=二面, 5=HR面, 6=录用评估中, 7=录用意向, 8=已录用) [enum]',
  `newApplyStatus` int DEFAULT '1' COMMENT '最近申请状态 (1=简历待筛选, 2=未通过, 3=一面, 4=二面, 5=HR面, 6=录用评估中, 7=录用意向, 8=已录用) [enum]',
  PRIMARY KEY (`stuNum`,`jobId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职位申请表';

-- ----------------------------
-- Records of apply_info
-- ----------------------------
BEGIN;
INSERT INTO `apply_info` VALUES (10002, 17, 3, 1, 1, 1);
INSERT INTO `apply_info` VALUES (10002, 18, 3, 1, 3, 2);
COMMIT;

-- ----------------------------
-- Table structure for comp_user
-- ----------------------------
DROP TABLE IF EXISTS `comp_user`;
CREATE TABLE `comp_user` (
  `hrId` int NOT NULL COMMENT 'hr编号 [no]',
  `compId` int DEFAULT NULL COMMENT '企业编号 [no]',
  PRIMARY KEY (`hrId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comp_user
-- ----------------------------
BEGIN;
INSERT INTO `comp_user` VALUES (10003, 20005);
INSERT INTO `comp_user` VALUES (10004, 20004);
INSERT INTO `comp_user` VALUES (10009, 20005);
INSERT INTO `comp_user` VALUES (10011, 20005);
INSERT INTO `comp_user` VALUES (10013, 20004);
INSERT INTO `comp_user` VALUES (10014, 20006);
COMMIT;

-- ----------------------------
-- Table structure for company_info
-- ----------------------------
DROP TABLE IF EXISTS `company_info`;
CREATE TABLE `company_info` (
  `compId` int NOT NULL AUTO_INCREMENT COMMENT '公司编号 [no]',
  `compName` varchar(50) DEFAULT NULL COMMENT '企业名称 [text]',
  `compIndustry` varchar(30) DEFAULT NULL COMMENT '企业所在行业 [text]',
  `compSize` int DEFAULT NULL COMMENT '企业规模 (1=100人以下, 2=999人以下, 3=9999人以下, 4=9999人以上) [enum]',
  `compAddress` varchar(50) DEFAULT NULL COMMENT '企业地址 [text]',
  `complink` varchar(50) DEFAULT NULL COMMENT '企业官网链接 [link]',
  `creditcode` varchar(30) DEFAULT NULL COMMENT '统一社会信用代码 [text]',
  `compEsDate` date DEFAULT NULL COMMENT '企业成立日期 [date]',
  `compIntro` text COMMENT '企业介绍 [textarea]',
  `approveStatus` int DEFAULT '1' COMMENT '审核状态 (1=未审核, 2=审核通过, 3=审核不通过) [enum]',
  PRIMARY KEY (`compId`)
) ENGINE=InnoDB AUTO_INCREMENT=20007 DEFAULT CHARSET=utf8 COMMENT='企业信息表';

-- ----------------------------
-- Records of company_info
-- ----------------------------
BEGIN;
INSERT INTO `company_info` VALUES (20003, '腾讯', '互联网', 4, '中山大学信息管理学院', 'www.baidu.com', '1234567890', '2021-05-11', '是中山大学信息管理学院2018级MIS第六组成员自创', 2);
INSERT INTO `company_info` VALUES (20004, '百度', '互联网', 4, '地球', 'www.baidu.com', '2343267912', '2021-05-12', 'xxxxxxxxxxxxxcompIntro', 2);
INSERT INTO `company_info` VALUES (20005, '中山大学', '教育', 3, '广州大学城', 'www.xxxxxx.com', '1235675481739', '2001-10-09', '无敌', 2);
INSERT INTO `company_info` VALUES (20006, '房地产大佬企业', '房地产', 4, '地球', 'www.baidu.com', '123447139471039', '2000-01-14', '房地产企业介绍测试，房地产企业介绍测试，房地产企业介绍测试，房地产企业介绍测试，房地产企业介绍测试', 1);
COMMIT;

-- ----------------------------
-- Table structure for ep_role
-- ----------------------------
DROP TABLE IF EXISTS `ep_role`;
CREATE TABLE `ep_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色id，--主键、自增',
  `name` varchar(20) NOT NULL COMMENT '角色名称, 唯一约束',
  `info` varchar(200) DEFAULT NULL COMMENT '角色详细描述',
  `is_lock` int NOT NULL DEFAULT '1' COMMENT '是否锁定(1=是,2=否), 锁定之后不可随意删除, 防止用户误操作',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1213 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统角色表';

-- ----------------------------
-- Records of ep_role
-- ----------------------------
BEGIN;
INSERT INTO `ep_role` VALUES (1, '超级管理员', '最高权限', 1, '2021-05-11 23:11:33');
INSERT INTO `ep_role` VALUES (2, '普通管理员', '普通系统管理员', 2, '2021-05-13 09:47:35');
INSERT INTO `ep_role` VALUES (11, '学生用户', '普通账号', 2, '2021-05-11 23:11:33');
INSERT INTO `ep_role` VALUES (111, '测试角色', '测试用例', 2, '2021-05-12 11:31:56');
INSERT INTO `ep_role` VALUES (121, '公司普通管理员hr', '普通管理账号', 2, '2021-05-11 23:11:33');
INSERT INTO `ep_role` VALUES (1212, '公司超级管理员', '超级管理账号', 2, '2021-05-11 23:11:33');
COMMIT;

-- ----------------------------
-- Table structure for ep_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `ep_role_permission`;
CREATE TABLE `ep_role_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id号',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID ',
  `permission_code` varchar(50) DEFAULT NULL COMMENT '权限码',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色权限中间表';

-- ----------------------------
-- Records of ep_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `ep_role_permission` VALUES (18, 1, 'bas', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (19, 1, '1', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (20, 1, '11', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (21, 1, '99', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (22, 1, 'console', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (23, 1, 'sql-console', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (24, 1, 'redis-console', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (25, 1, 'apilog-list', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (26, 1, 'form-generator', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (27, 1, 'auth', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (28, 1, 'role-list', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (29, 1, 'menu-list', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (30, 1, 'admin-list', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (31, 1, 'admin-add', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (32, 1, 'sp-cfg', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (33, 1, 'sp-cfg-app', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (34, 1, 'sp-cfg-server', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (35, 11, 'user_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (36, 11, 'stu_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (37, 121, 'user_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (38, 1212, 'user_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (39, 1, 'user_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (40, 121, 'job_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (41, 121, 'seminar_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (42, 121, 'mail_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (43, 1212, 'job_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (44, 1212, 'seminar_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (45, 1212, 'mail_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (46, 1212, 'company_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (47, 1, 'announcement', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (48, 1212, 'comp_user', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (49, 11, 'favor_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (50, 121, 'favor_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (51, 1212, 'favor_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (52, 11, 'apply_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (53, 121, 'apply_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (54, 1212, 'apply_info', '2021-05-11 23:09:10');
INSERT INTO `ep_role_permission` VALUES (55, 111, 'test', '2021-05-12 11:12:47');
INSERT INTO `ep_role_permission` VALUES (56, 121, 'company_info', '2021-05-15 16:40:44');
INSERT INTO `ep_role_permission` VALUES (58, 1212, 'company_info_update', '2021-05-15 16:42:06');
INSERT INTO `ep_role_permission` VALUES (59, 1, 'review', '2021-05-16 10:41:21');
INSERT INTO `ep_role_permission` VALUES (60, 2, 'review', '2021-05-16 10:41:53');
COMMIT;

-- ----------------------------
-- Table structure for favor_info
-- ----------------------------
DROP TABLE IF EXISTS `favor_info`;
CREATE TABLE `favor_info` (
  `favorNum` int NOT NULL AUTO_INCREMENT COMMENT '收藏编号 [no]',
  `stuNum` int NOT NULL COMMENT '学生编号 [no]',
  `compId` int DEFAULT NULL COMMENT '企业编号 [no]',
  `jobId` int DEFAULT NULL COMMENT '职位信息编号 [no]',
  PRIMARY KEY (`favorNum`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of favor_info
-- ----------------------------
BEGIN;
INSERT INTO `favor_info` VALUES (25, 10002, 13, 17);
INSERT INTO `favor_info` VALUES (26, 10002, 14, NULL);
COMMIT;

-- ----------------------------
-- Table structure for job_info
-- ----------------------------
DROP TABLE IF EXISTS `job_info`;
CREATE TABLE `job_info` (
  `jobId` int NOT NULL AUTO_INCREMENT COMMENT '职位信息编号 [no]',
  `compId` int NOT NULL COMMENT '公司编号 [no click=comp_info.compId]',
  `jobName` varchar(30) DEFAULT NULL COMMENT '职位名称 [text j=like]',
  `jobType` varchar(30) DEFAULT NULL COMMENT '岗位类别 [text j=like]',
  `jobKind` int DEFAULT NULL COMMENT '招聘性质 (1=实习, 2=校招, 3=实习和校招) [enum]',
  `status` int DEFAULT NULL COMMENT '招聘状态 (1=招聘中, 2=已结束) [enum]',
  `relDate` date DEFAULT NULL COMMENT '发布日期 [date-create]',
  `jobAddress` varchar(50) DEFAULT NULL COMMENT '工作地点 [text]',
  `jobCon` text COMMENT '职位描述 [f]',
  `jobDeadline` date DEFAULT NULL COMMENT '截止日期 [date]',
  `deliverNum` int DEFAULT NULL COMMENT '已投递人数 [num]',
  `salary` varchar(50) DEFAULT NULL COMMENT '薪资 [text]',
  `approveStatus` int DEFAULT '1' COMMENT '审核状态 (1=未审核, 2=审核通过, 3=审核不通过) [enum]',
  PRIMARY KEY (`jobId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='职位信息表';

-- ----------------------------
-- Records of job_info
-- ----------------------------
BEGIN;
INSERT INTO `job_info` VALUES (17, 13, '产品经理', '产品', 1, 1, '2021-05-11', '广州', '是由中山大学信息管理学院2018级MIS第六组成员自创', '2021-05-29', 0, '100/天', 2);
INSERT INTO `job_info` VALUES (18, 13, '产品运营', '运营', 1, 1, '2021-05-11', '深圳', '乱填的', '2021-05-30', 0, '50/天', 2);
COMMIT;

-- ----------------------------
-- Table structure for mail_info
-- ----------------------------
DROP TABLE IF EXISTS `mail_info`;
CREATE TABLE `mail_info` (
  `mailNum` int NOT NULL AUTO_INCREMENT COMMENT '信箱编号 [no]',
  `stuNum` int NOT NULL COMMENT '学生编号 [no]',
  `infoId` int NOT NULL COMMENT '信息编号 [no]',
  `infoType` int NOT NULL COMMENT '信息类型 (1=宣讲会信息, 2=职位信息)[enum]',
  PRIMARY KEY (`mailNum`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mail_info
-- ----------------------------
BEGIN;
INSERT INTO `mail_info` VALUES (21, 10002, 17, 2);
COMMIT;

-- ----------------------------
-- Table structure for seminar_info
-- ----------------------------
DROP TABLE IF EXISTS `seminar_info`;
CREATE TABLE `seminar_info` (
  `seminarId` int NOT NULL AUTO_INCREMENT COMMENT '宣讲会信息编号 [no]',
  `seminarTitle` varchar(50) DEFAULT NULL COMMENT '宣讲会标题 [text]',
  `seminarContent` text COMMENT '宣讲会内容 [f]',
  `hrId` int DEFAULT NULL COMMENT 'hr编号 [no]',
  `approveStatus` int DEFAULT '1' COMMENT '审核状态 (1=未审核, 2=审核通过, 3=审核不通过) [enum]',
  `seminarTime` date DEFAULT NULL COMMENT '发布时间 [date-create]',
  PRIMARY KEY (`seminarId`)
) ENGINE=InnoDB AUTO_INCREMENT=1208 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seminar_info
-- ----------------------------
BEGIN;
INSERT INTO `seminar_info` VALUES (1202, '曾琳荣最帅标题', '曾琳荣最帅内容', 10003, 2, '2021-05-15');
INSERT INTO `seminar_info` VALUES (1203, '标题修改测试', '内容修改测试测试', 10003, 1, '2021-05-16');
INSERT INTO `seminar_info` VALUES (1204, '垃圾中山大学', '宣讲会内容中山大学垃圾985211 争创双一流 曾琳荣最帅 测试各种索引', 10009, 1, '2021-05-17');
INSERT INTO `seminar_info` VALUES (1206, '宣讲会标题添加测试', '宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，宣讲会内容添加测试，', 10004, 2, '2021-06-10');
INSERT INTO `seminar_info` VALUES (1207, '宣讲会标题测试3', '宣讲会内容测试333333333333333333333333', 10004, 2, '2021-06-10');
COMMIT;

-- ----------------------------
-- Table structure for stu_info
-- ----------------------------
DROP TABLE IF EXISTS `stu_info`;
CREATE TABLE `stu_info` (
  `stuNum` int NOT NULL COMMENT '学生id [no click=sp_admin.id]',
  `stuName` varchar(30) DEFAULT NULL COMMENT '学生姓名 [text]',
  `stuGraUniversity` varchar(30) DEFAULT NULL COMMENT '学生学校 [text]',
  `stuMajor` varchar(30) DEFAULT NULL COMMENT '学生专业 [text]',
  `stuEducation` int DEFAULT NULL COMMENT '学生学历 (1=本科, 2=研究生) [enum]',
  `stJodKind` int DEFAULT NULL COMMENT '求职性质 (1=实习, 2=校招, 3=实习和校招) [enum]',
  `stuGraduateTime` date DEFAULT NULL COMMENT '学生毕业年份 [date]',
  `stuTelephone` varchar(20) DEFAULT NULL COMMENT '学生电话号码 [num]',
  `dreamAddress` varchar(50) DEFAULT NULL COMMENT '期望城市 [text]',
  `dreamPosition` varchar(50) DEFAULT NULL COMMENT '期望职位类别 [text]',
  `resume` varchar(100) DEFAULT NULL COMMENT '简历信息 [file]',
  PRIMARY KEY (`stuNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生信息表';

-- ----------------------------
-- Records of stu_info
-- ----------------------------
BEGIN;
INSERT INTO `stu_info` VALUES (10002, '陈楽骅', '中山大学', '信管', 1, 1, '2022-06-30', '16293212345', '土耳其', '产品', 'http://127.0.0.1/upload/file/2021/06-10/1623328894536502015341.pdf');
INSERT INTO `stu_info` VALUES (10015, '诸侯', 'sysu', '管理', 2, 1, '2022-06-29', '12345678911', '广州', '后端', NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id，--主键、自增',
  `name` varchar(100) NOT NULL COMMENT '账号名称',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `mail` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `role_id` int DEFAULT '11' COMMENT '所属角色id',
  `status` int DEFAULT '1' COMMENT '账号状态 (1=正常, 2=禁用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '上次登陆时间',
  `login_ip` varchar(50) DEFAULT NULL COMMENT '上次登陆IP',
  `login_count` int DEFAULT '0' COMMENT '登陆次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10019 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户表';

-- ----------------------------
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES (10001, '曾琳荣', '99140C1B8A8400747346571F9212BA0B', '18023893551@163.com', 1, 1, '2021-05-11 21:58:11', '2021-05-18 20:35:05', '127.0.0.1', 17);
INSERT INTO `user_info` VALUES (10002, 'clh', '5C41A87C54D63B760D81B039A2BAB3FF', '992456536@qq.com', 11, 1, '2021-05-11 21:59:11', '2021-06-10 20:37:14', '127.0.0.1', 11);
INSERT INTO `user_info` VALUES (10003, 'cjy', '7B51C5CF2E531C27B12672662BEC2C4C', '1234567@qq.com', 121, 1, '2021-05-11 22:01:07', '2021-06-10 19:26:48', '127.0.0.1', 8);
INSERT INTO `user_info` VALUES (10004, 'zy', '0CF04C6807910DEC54FA44104147E669', '123456@qq.com', 1212, 1, '2021-05-11 23:14:16', '2021-06-10 19:30:39', '127.0.0.1', 2);
INSERT INTO `user_info` VALUES (10005, '曾琳荣帅', 'A47C07368978296D58C8BFBC76DEDA87', '992456@qq.com', 111, 1, '2021-05-12 15:33:49', '2021-05-13 10:27:48', '127.0.0.1', 1);
INSERT INTO `user_info` VALUES (10007, '曾琳荣最帅3', '6CF6B12BF8E6727020978BEC4675F26B', '992456@qq.com', 111, 1, '2021-05-12 18:16:15', '2021-05-14 23:42:53', '127.0.0.1', 1);
INSERT INTO `user_info` VALUES (10009, '曾琳荣公司', 'BEA4557A3BDF5E53E9FE64A544709080', 'zenglr9@mail2.sysu.edu.cn', 1212, 1, '2021-05-14 21:48:42', '2021-05-17 20:17:31', '127.0.0.1', 21);
INSERT INTO `user_info` VALUES (10011, '曾麟榮', '66ECE1B180B294BDBA3D01E45CF4B329', 'zenglr8@mail2.sysu.edu.com', 121, 1, '2021-05-14 23:28:37', NULL, NULL, 0);
INSERT INTO `user_info` VALUES (10013, '企业管理员测试用户', 'D66EE4518C00870976093C579617DCF7', '43256532314@qq.com', 121, 1, '2021-06-10 19:32:05', NULL, NULL, 0);
INSERT INTO `user_info` VALUES (10014, '房地产企业超级管理员', '7718242B39E835A6366059B05066A1ED', '12358129723@163.com', 1212, 1, '2021-06-10 19:43:58', NULL, NULL, 0);
INSERT INTO `user_info` VALUES (10015, 'zh', 'D3FD48692A3972A8FF5CF3EB4CF4ED01', '135879641@163.com', 11, 1, '2021-06-10 19:57:23', NULL, NULL, 0);
INSERT INTO `user_info` VALUES (10016, '就业管理员1111', '190FB22A0F24670B183DC2199E64204E', '1236847193@123.com', 1, 1, '2021-06-10 20:08:58', '2021-06-10 20:16:12', '127.0.0.1', 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
