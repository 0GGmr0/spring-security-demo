#### 此项目为springboot整合spring security的一个简单的demo
>项目环境为springboot、mysql、mybatis（使用了反向工程，自动生成mapper文件为/src/main/resources/generator下的文件，修改一些参
数即可使用）  
>jdk版本为11  
##### 运行此项目需要如下步骤：
1.创建名字为security_demo的数据库  
2.运行init.sql文件(authority为用户权限，1是普通用户，2是管理员)  
3.运行项目
##### demo介绍
未登录状态下访问除了login之外的任何url均会返回401，如果权限有问题会返回403，
权限无问题会正常执行

