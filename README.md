##### 项目简介：

​	这里使用XXX的素材，当时主要关注后台的Java代码的编写，并且跟着视频做，好多功能也都没有实现，写的比较水。

​	需要注意的是注册功能需要使用邮箱验证...暂时无法使用

##### 效果图：

1、前台

![](/images/TIM截图20191101150023.png)

2、后台

![](/images/TIM截图20191101150219.png)

##### 项目展示：

1、前台页面

 http://47.102.120.49:8080/store/ 

2、后台页面

 http://47.102.120.49:8080/store/admin/ 

##### 如果要使用本项目，需要修改一下文件

1、src/main/resources/c3p0-config.xml

![](/images/TIM截图20191101144241.png)



将`jdbcUrl`、`user`、`password`替换成自己数据库的相关信息

2、src/main/java/com/hrious/store/utils/JedisUtils.java

![](/images/TIM截图20191101144422.png)

修改redis连接信息

3、src/main/java/com/hrious/store/config/AlipayConfig

![](/images/TIM截图20191101144704.png)

##### 总结：

​	纪念自己当年学习jsp+Servlet后写的一个小Demo，希望能够帮助更多的人。考虑到可玩性，邮件验证的尽量会实现吧

