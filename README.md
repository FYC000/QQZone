# QQZone
尚硅谷JavaWeb项目QQZone个人笔记整理和归纳
本项目是一个构建小型的JavaWeb项目，所涵盖的语言包括HTML+CSS+JAVASCRIPT+JAVA
本项目涵盖的重要知识点包括:
A.XML配置文件的使用、Session、Request、Application的作用域
B.Thymeleaf的语法使用
C.事务管理
D.MVC结构:模型层、视图层、控制层
E.IOC结构
F.过滤器Filter
G.多线程中互不干扰的单线程-ThreadLocal


实现的功能有：
-实现了访问好友空间的
-在好友空间的时候可以返回到自己的空间
-可以删去自己的日志列表
-可以删去自己的回复和其他人的回复
-可以添加空间主人的回复
-可以对主人的日志进行回复

本次项目的结构类似于MVC结构:
视图层-html网页
模型层-Controller组件、Service层、DAO层、POJO（数据库实体的类）
控制器(类似于SSM框架)-包含过滤器、事务管理、IOC控制反转、Disaptcher（中央处理器）、监听器（ServletContext）、连接数据库的API（Base DAO）、专门处理事务的工具(ConnUtil)

本次项目通过配置WEB-INF文件来存储IOC容器的信息以及包装thymeleaf的前缀和后缀，并通过IOC容器存储的信息来控制applicationContext.xml文件来控制模型层中Service层、DAO层、Controller组件的依赖关系，当服务器收到客户端发来的信息时，Disaptcher（中央处理器）通过反射调用Controller组件并且查阅applicationContext.xml文件来找到对应的Service方法，Serivice层调用DAO层方法，DAO层调用BaseDAO的方法，来调用数据库的数据，从而一路返回到Disaptcher（中央处理器）,Disaptcher（中央处理器）在进行视图处理，返回到ViewBase的组件，从而返回html有关信息给客户端，从而完成一个操作。
