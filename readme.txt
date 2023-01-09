前端ui  jquery

异常处理：自定义错误页面
日志处理（aop）：请求url 访问者ip 调用方法classMethod 参数args 返回内容

实体类：博客blog 博客专栏Type 博客标签Tag 博客评论Comment 用户User

blog type     多对一
blog tag      多对多
blog user     多对一
blog comment  一对多

parentComment  replayComment 一对多

blog 有分类 标签 评论 用户
有 标题 内容 首图 标记 浏览次数 赞赏开启 版权开启 评论开启 发布状态 创建时间 更新时间

type id 名称

Tag id 名称

Comment 昵称 邮箱 头像 评论内容 创建时间

User 昵称 用户名 密码 邮箱 类型 头像 创建时间 更新时间

三层架构
终端显示层
请求处理层 Web
业务逻辑层 Service
持久层 Dao
数据源

后台管理
登录页面 MD5加密 登录拦截器
专栏页面 添加专栏 分页 前端校验 后端校验
标签页面 类似
博客管理 博客新增 博客修改 博客删除 分页查询

图片340 140  50 50

