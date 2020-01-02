# luis-cloud
springcloud Greenwich SR4 整合alibaba毕业版
undertow替代tomcat
springcloud+oauth2.0+security+jwt+redis+nacos+Sentinel
己实现如下功能：
1、oauth2.0单点登陆，用jwt替换UUID今牌
2、gateway整合sentinel实现服务熔断限流降级
3、注册中心和配置中心整合nacos
4、gateway路由动态刷新
5、gateway通过全局过滤方式认证鉴权，后端微服务放到网关后面用K8S内网管理不对外提供访问权限
6、gateway整合swagger文档集合
7、gateway添加用户操作日志

代码只是一些基本框架，拿来改改就能用，欢迎沟通！

