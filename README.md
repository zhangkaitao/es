<a href="http://zhangkaitao.github.io/es/" target="_blank">项目首页</a>
|
<a href="http://jinnianshilongnian.iteye.com/" target="_blank">我的博客</a> | <a href="http://url.cn/MViZN0">点击加134755960 QQ群讨论</a> | <a href="http://demo.kaifazhe.me/es/admin/index" target="_blank">演示地址（感谢134755960群友Panda支持）</a>(admin/123456 请不要执行增删改操作 谢谢)


##介绍
* ES是一个JavaEE企业级项目的快速开发的脚手架，提供了底层抽象和通用功能，拿来即用。

### 核心功能
* 通用的DAO、Service、Controller抽象，从CRUD中解放
* 简单的分页、排序、查询抽象，更快的开发速度
* 借助spring实现简单的数据绑定、类型转换、验证、格式化
* 提供基础的用户、组织机构、职务等管理
* 提供基于资源的细粒度授权管理，灵活的授权模型
* 提供常用功能的用例，直接拿来用即可，比如树、可移动列表（即如排序的分类）、父子表、编辑器、文件上传、大数据量Excel导入导出等等
* 监控功能，比如缓存管理（命中率、缓存控制等）、二级缓存管理、JVM状态、数据库（druid做的很好了，直接用它的）等等
* 维护功能，比如图标管理、强大的在线编辑功能（直接在线编辑JSP，无需来回切服务器，上传下载等等）、静态资源管理（自动的压缩、版本化js/css）、动态增删任务调度
* 站内信功能，支持嵌套展示等
* 通知系统：耗时功能异步化，完成后使用通知系统通知
* 通用的文件上传/下载、通用的验证模型等等
* ……

### 技术选型

#### 管理
* maven依赖和项目管理
* git版本控制

#### 后端
* IoC容器 spring
* web框架 springmvc
* orm框架 hibernate + spring data jpa
* 安全框架 shiro
* 验证框架 hibernate validator
* 任务调度框架 quartz
* 缓存 ehcache
* 数据源 druid
* 日志 slf4j+logback
* Json fastjson
* 日期美化 prettytime
* servlet 3.0(需要支持servlet3的servlet容器，如tomcat7)
* jcaptcha 验证码
* jsp 模板视图

#### 前端
* jquery js框架
* jquery-ui-bootstrap界面框架
* font-wesome 字体/图标框架
* jquery Validation Engine 验证框架（配合spring的验证框架，页面验证简化不少）
* kindeditor 编辑器
* nicescroll 漂亮的滚动条
* zTree 树框架
* jquery blockUI 弹出框/遮罩框架
* jquery-fileupload 文件上传
* bootstrap-datatimepicker 日历选择

#### 数据库
 * 目前只支持mysql，建议mysql5.5及以上

####
 * 本脚手架会选型技术的最新版本

###支持的浏览器
 * chrome
 * firefox（目前使用firefox 19.0.2测试）
 * opera 12
 * ie7及以上（建议ie9以上，获取更好的体验）
 * 其他浏览器暂时未测试

###系统界面截图
* <a href="http://demo.kaifazhe.me/es/admin/index" target="_blank">演示地址（感谢134755960群友Panda支持）</a>
* <a href="https://github.com/zhangkaitao/es/blob/master/src/support/img/1.PNG?raw=true" target="_blank">点击查看1</a>
* <a href="https://github.com/zhangkaitao/es/blob/master/src/support/img/2.PNG?raw=true" target="_blank">点击查看2</a>
* <a href="https://github.com/zhangkaitao/es/blob/master/src/support/img/3.PNG?raw=true" target="_blank">点击查看3</a>


##CI status
<a href="https://travis-ci.org/zhangkaitao/es"><img src="https://travis-ci.org/zhangkaitao/es.png"/></a>


##如何运行

####1、到es/web/pom.xml修改数据库配置：
*  默认修改：profiles/profile/development下的
*  connection.admin.url
*  connection.username
*  connection.password

####2、到项目的根下(es)
* cd bin
* install.bat 安装jar包到本地仓库（jdk6即可）
* create-db.bat 创建数据库（mysql需要5.5及以上 编码为utf-8）
* refresh-db.bat 创建schema和初始化data
* jetty.bat 启动web应用 默认端口9080 可以到es/web/pom.xml下修改（servlet 2.5即可）
* 系统默认帐户是admin/123456

####3、注意
如果你是用mvn jetty:run启动项目，默认会执行speed-up 应用，不过可以到src/main/resources/spring-speed-up.xml中把profile="development"改成任意其他的即可，或者删除<br/>
请参考<a href='http://jinnianshilongnian.iteye.com/blog/1883013'>加速spring/hibernate应用调试时启动速度</a>
