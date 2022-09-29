## 尚好房项目

###  1.项目介绍

尚好房项目是我在复习中间件学习的一个项目，主要是一个二手房管理服务平台，开放优质资源和线上能力，收集线上线下房产资源，为消费者提供优质的房源

### 2. 核心技术

| 基础框架：ssm                       |
| ----------------------------------- |
| 分布式框架：ssm + Dubbo + zk        |
| spring session redis实现session共享 |
| 图片服务器：七牛云                  |
| 后台管理权限控制：spring-security   |
| 前端用户登录判断：拦截器            |
| 后台管理模板：Thymeleaf             |
| 前端技术:Vue+Axios                  |

### 3.项目模块

最终分布式架构模块

shf-parent：根目录，管理子模块：

​	common-util：公共类模块

​	model：实体类模块

​	service：dubbo服务父节点

​		service-acl：权限服务模块

​		service-house：房源服务模块

​		service-user：用户服务模块

​	service-api：dubbo服务api接口

​	web：前端（dubbo服务消费者）

​		web-admin：后台管理系统

​		web-front：网站前端

数据库：

![image-20220215092734848](https://xingqiu-tuchuang-1256524210.cos.ap-shanghai.myqcloud.com/6837/image-20220215092734848.png)

项目运行环境：

1.idea开发环境,例如jdk环境,maven环境等

2.数据库，mysql环境

3.redis环境，这里我使用的是linux系统的redis

4.使用dubbo，zk作为分布式开发，我这里使用的zk是在本地运行的zk。
