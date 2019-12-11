# luis-cloud
springcloud Greenwich SR4 整合alibaba毕业版
undertow替代tomcat
特别说明：
从shiro到security再到oauth2.0+security+jwt实践之后发现一般应用工程没必要为了技术而技术，只有适合自己的才是最好的，so，我这里只用了security在gateway
上做了一层认证和鉴权，在gateway后面的微服务全放到k8s容器内网管理，简单直接。
先这样吧，当前工程只是本人闲时手痒搭的简单框架，反正也没什么人看。
