# 无限层级留言项目计划

---
## 24/09/2021
1. [x] 分析需求
   1. （疑问）可以使用docker 安装、部署、进行最终成果展示吗？
   2. （疑问）登录时需要验证码吗？
   3. （疑问）需要使用https加强安全性避免明文传输吗？
   4. （疑问）注册时需要昵称字段吗？（通常都有)
   5. （疑问）为了方便最终演示，数据库可以选用像H2这类免安装的内存型关系数据库吗？
   6. （疑问）需求“如果未勾选”remember me”，则关闭浏览器后再次访问会提示注册或登录”和“查看留言时不需要登录” 感觉有点冲突，能否理解为仅当访问“发布留言”或“发布评论”等需要登录的接口时再提示未登录用户进行登录或注册？
   7. （疑问） 前端对留言及评论的展示可以使用懒加载的模式默认只展开部分留言及部分层级的评论吗？（需手动点击需要查看的层级）
   8. （疑问）用户登录后能主动选择 注销/退出登录 吗？
   9. （功能）登录（记住我）、注册、发布留言、发布评论、查看留言及评论、
   10. （注意）评论可以无限层级
   11. （注意）前后端分离 RESTFUL API
   12. （注意）多层级留言也不能有明显性能问题
4. [x] 准备&确认开发环境(git,idea,jdk，maven)

---
## 25/09/2021
1. [x] 深入分析功能点并设计出实现方案
   1. [x] 注册、登录
      1. 登录认证方案：user信息表+JWT认证（如果需要手动退出登录功能则需要增加Token黑名单）
      2. JWT自动延期方案：前端携token发起请求->后端检查token合法且即将过期->后端生成新token并放入http响应头->前端检测到token变化后替换缓存的旧token
      3. user表主要字段：id,username,email,password,gmt_create, gmt_modified
      4. 注册API：`POST /v1/user body:{"username":"string","email":"string","password":"string"} 响应：{"status":int,"message":"string","data":object}`
      5. 登录API：`POST /v1/session body:{"username":"string","password":"string"}    响应：{"status":int,"message":"string","data":object} `
      6. 
   2. [x] 无限树结构存储留言和回复的方案
      1. 方案：左右值编码预排序
      2. comment 表字段： id,content,lft,rgt,leve,parent_id,gmt_create, gmt_modified
   3. [x] 发表、查看留言和评论
      1. 发表API（parentNode为0：发布留言，其他：发布回复）：`POST /v1/comment 请求头带token:"authentication:string" body{"parentNode":int,"comment":"string"} 响应：{"status":int,"message":"string","data":object}` 
      2. 获取API（parentNode为0：获取留言，其他：获取回复）：`GET /v1/comment body{"parentNode":int,"size":int} 响应：{"status":int,"message":"string","data":object}`
2. [x] 对技术难点和算法难点进行调研&攻克
      1. [x] 无限树结构存储留言和回复的方案->(左右值编码预排序)
      2. [x] JWT自动延期->(后端到期自动刷新&前端替换)
3. [x] 设计前后端交互需要的API
4. [x] 技术选型
   1. 前端：vue
   2. 后端：springBoot、H2、Mybatis、JWT
5. [x] 细化&列出可量化、可执行的开发计划
6. [x] 搭建后端框架

---
## 26/09/2021
1. [x] 后端开发实现无限树结构插入和查询功能
2. [x] 验证插入和查询效率是否满足要求
---
## 27/09/2021
1. [x] 开发完成发布和获取留言接口及逻辑（含单元测试）
2. [x] 开发完成注册登录接口及逻辑（含单元测试）
3. [ ] 搭建前端框架
4. 
---
## 28/09/2021
1. [ ] 编写完成前端页面
2. [ ] 联调完成全部4个接口
3. 
---
## 29/09/2021
1. [ ] 编写完成一键部署&初始化脚本
2. [ ] 完成集成测试
3. [ ] 提交PR
4. 
---
## 30/09/2021
