##项目的基础设施：
1、通用的web、service、dao层
2、通用分页和排序基础设施和逻辑、分页标签
3、……

###CI status : 
<img src="https://travis-ci.org/zhangkaitao/es.png"/>


##如何运行

####1、到es/web/pom.xml修改数据库配置：
*  默认修改：profiles/profile/development下的
*  connection.admin.url
*  connection.username
*  connection.password

####2、到项目的根下(es)
* cd bin
* install.bat 安装jar包到本地仓库
* create-db.bat 创建数据库
* refresh-db.bat 创建schema和初始化data
* jetty启动web应用 默认端口9080 可以到es/web/pom.xml下修改

