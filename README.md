# olympos_Microservice

# olympos

#### 介绍
1.12版本

基本可用（已添加redis支持，后续会添加mq支持）

#### 软件架构
软件架构说明

快速部署型微服务架构，只需要先部署第三方服务后，简单修改配置文件，然后部署网关和业务服务器，即可运行。

此架构基于 java19，shenyu gateway，springcloud alibaba，springboot 3，mybatis 3，mysql 8，Redis 等。

本架构支持多数据源配置，对 shenyu gateway 进行深度定制，以适配 springboot 3 以及方便再网关编写 API共通处理 逻辑。

#### 特点

1.  支持 spring boot 3
2.  支持 Java19
3.  支持 多数据源简易配置，并且不限制数据源数量
4.  基于 mysql8
5.  基于 springcloud alibaba nacos2
6.  加入自主研发的 shenyu gateway 插件 TaiHe，可以更方便的再网关对 API接口 进行统一处理或者共通处理，
   并且其 处理配置 放在业务模块内，让网关共通处理与业务模块可以完全解耦。
7.  Redis封装使用更加接近于java集合，更加方便易用，并且支持集群与哨兵模式，可以更加方便的添加集群服务器。
8.  Redis模块支持缓存分页功能，此功能基于Redis的zset数据结构，可以更加方便的实现缓存分页，用于页面列表的排序，分页功能。

#### 安装教程

1.  部署 mysql8 服务器
2.  部署 nacos2 服务器
3.  部署 shenyu-admin 服务器（2.5.1版本，再gaihub下载源码后，在shenyu-admin中修改配置，再打包部署即可），并且使用db初始化sql对数据库进行初始化（详见shenyu官方教程）
4.  对 shenyu-admin 进行配置。打开 shenyu-admin 页面，添加 TaiHe 插件（在使用说明中有详细说明）
5.  修改项目内 网关（zeus） 和 业务模块（hades） 内配置，并且打包部署（如果本地执行，则不需要部署）

#### 使用说明

1.  shenyu-admin 配置模板（仅为参考，可以根据自身需求进行配置）

   1.基础配置 - 插件管理

     插件名          角色名     排序   状态
     BeforePlugin   Enhance   305    开
     AfterPlugin    Enhance   350    开

   2.插件列表 - Enhance

     1）BeforePlugin

        [1] 选择器

          名称          类型   执行顺序  继续后续选择器  打印日志  是否开启
          BeforePlugin 全流量    1      开            开       开

        [2] 规则器

          名称          匹配方式 执行顺序 条件                 打印日志 是否开启
          BeforePlugin  and      1    [uri]  + [=]  + [*]   开      开

     2）AfterPlugin

        [1] 选择器

          名称          类型   执行顺序  继续后续选择器  打印日志  是否开启
          AfterPlugin  全流量     1    开           开       开

        [2] 规则器

          名称          匹配方式 执行顺序 条件                 打印日志 是否开启
          AfterPlugin   and      1    [uri]  + [=]  + [*]   开      开
