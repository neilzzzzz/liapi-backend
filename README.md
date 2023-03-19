## 项目背景

项目背景：

1. 前端开发需要用到后端接口
2. 使用现成的系统的功能（http://api.btstu.cn/）

API 接口平台：

1. 防止攻击（安全性）
2. 不能随便调用（限制、开通）
3. **统计调用次数**
4. 计费
5. 流量保护
6. API 接口

## 项目介绍

做一个提供 API 接口调用的平台，用户可以注册登录，开通接口调用权限，用户可以使用接口，并且每次调用会进行统计。管理员可以发布接口、下线接口、接入接口，以及可视化接口的调用情况、数据。

### 业务架构图

![业务架构](https://github.com/neilzzzzz/liapi-backend/blob/master/img/41ccda886725f220b04c499b07f0ba0.png)



## 技术选型

### 后端

- Java Spring Boot
- Sring Boot Starter （SDK 开发）
- Spring Cloud Gateway(网关、限流、日志)
- Dubbo(RPC)
