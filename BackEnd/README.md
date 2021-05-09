# employment
就业服务网站



## 项目技术栈

### 后端技术栈

1. Spring Boot
2. MyBatis-Plus
3. Redis
4. MongoDB
5. Swagger2



## 服务端环境搭建

### 1.maven

自行配置

### 2.数据库

#### mysql

- 账号：root 
- 密码：201379
- 如需根据本机配置修改，请修改application.yml文件中的mysql配置
- 初始数据库搭建脚本为employment.sql，直接执行即可

#### redis

- 端口号：6379
- 无密码

#### MongoDB

- 无密码
- 暂时未使用，可提前安装



## 项目运行步骤

1. mvn clean
2. mvn install
3. 运行EmploymentApplication.java

注：idea中点击⚡️标志可跳过单元测试



## 接口管理

本项目使用Swagger2管理接口文档，项目运行后可通过访问http://localhost:8299/swagger-ui.html来查看接口。

#### 接口规范

- 在每一个controller类上添加注解@Api

- 每一个接口方法上添加注解@ApiOperation("说明方法的用途、作用")

- @ApiImplicitParams：用在请求的方法上，包含一组参数说明

  - 例如：![image-20210509112724064](https://tva1.sinaimg.cn/large/008i3skNgy1gqc8rjauunj314m0cmtbe.jpg)

  - 展示效果：![image-20210509115338447](https://tva1.sinaimg.cn/large/008i3skNgy1gqc8re8mbfj30u00ycdjy.jpg)

  - 点击上图中的”Try it out“即可进行接口测试。![image-20210509115414815](https://tva1.sinaimg.cn/large/008i3skNgy1gqc8rbkey1j313o0u0whw.jpg)

    





## 单元测试

在本地编写方法时，请务必进行单元测试通过后再提交。

#### 测试步骤：

1. 在test包下面新建包以及测试类、测试方法。
2. 在类上添加注解@SpringbootTest(class = xxxxx)  【可自行百度一下或复制别人的
3. 在方法上加上注解@Test
4. 编写方法进行测试



## 其他规范

1. 在类上加上注解@Slf4j即可开启日志输出，最好每一个输入和输出或者方法启动时加上日志输出，方便日后日志查看debug。例如：![image-20210509115004705](https://tva1.sinaimg.cn/large/008i3skNgy1gqc8r5eu33j314m0g4ae1.jpg)
2. 每一个接口及类请添加注释说明。
3. 接口返回格式统一使用AjaxJson类中的格式。
4. 每次修改项目前先git pull再进行修改，commit时请尽量写出修改的内容，push可以直接push到main。