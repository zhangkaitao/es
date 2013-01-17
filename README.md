项目的基础设施：
1、通用的web、service、dao层
2、通用分页和排序基础设施和逻辑、分页标签
3、……

CI status : <img src="https://travis-ci.org/zhangkaitao/es.png"/>


Maven常用命令

一、生成站点报告
可以生成如站点整体信息、javadoc、checkstyle、findbugs等信息的汇总
1.1、修改es\pom.xml
distributionManagement\site\{id=local}  将url改成你自己的站点位置
1.2、在es目录下执行如下命令，生成站点报告并发布到【1.1】指定的位置
mvn clean site:site site:deploy

二、数据库
1、测试库
2、开发库

四、运行web应用


