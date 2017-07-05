# FlashSale - 高并发秒杀系统

## GitHub源码地址
https://github.com/lddahz789

## 项目使用
### 1. 下载
`Download Zip`或者 `git clone`

> git clone https://github.com/lddahz789/FlashSale.git

### 2. 修改配置文件
修改resources下`jdbc.propertites`为你的数据库配置
修改resources/spring下`spring-dao.xml`中
```
<!-- 注入RedisDAO -->
    <bean id="redisDAO" class="com.flashsale.dao.cache.RedisDAO">
    	<constructor-arg index="0" value="http://106.14.173.104"/>
    	<constructor-arg index="1" value="6379"/>
    </bean>
```
为你的redis服务器地址或者localhost

### 3. 部署
部署项目到tomcat之类的web容器
> 注: 如图在web目录下中的js文件中请将此更改为你的项目地址(server.xml中配置的) 或者更改为获取到的contextPath
![mark](http://os3e5ayd1.bkt.clouddn.com/blog/170705/g06dc1BhmD.png?imageslim)

### 4. Enjoy
访问http://你的域名/你的项目名

## 项目在线演示:
http://example.bestzhuo.top/miaosha (近期备案中,可能会影响访问)
or 
http://106.14.173.104/miaosha


## 项目截图
![mark](http://os3e5ayd1.bkt.clouddn.com/blog/170705/HL644h0bI0.png?imageslim)
![mark](http://os3e5ayd1.bkt.clouddn.com/blog/170705/1mm4iAbfC7.png?imageslim)
![mark](http://os3e5ayd1.bkt.clouddn.com/blog/170705/2e3gIIjD4a.png?imageslim)
![mark](http://os3e5ayd1.bkt.clouddn.com/blog/170705/9Lj0K1mLA1.png?imageslim)
![mark](http://os3e5ayd1.bkt.clouddn.com/blog/170705/mH8hklmj8i.png?imageslim)

## 项目设计思路

### 1. 数据库设计
* product: 存放商品信息,包含商品ID,库存,秒杀相关的时间
* order: 存放订单信息,由于没有做登录模块,所以从cookie中读取用户手机,手机+商品ID为订单的主键
### 2. DAO层设计
* 整合MyBatis与Spring实现DAO层,这里用的是接口+MAPPER.XML的方式
* 编写DAO相关的JUnit测试
### 3. SERVICE层设计
* 站在使用者的角度设计SERVICE层的接口
* 实现接口,完成业务逻辑,包括加密等操作都在这执行
* service: 存放服务，即为一系列逻辑
* exception:	存放service接口所需要的异常，如重复秒杀，秒杀与关闭等
* dto: 数据传输层，与entity类似，存放一些表示数据的类型，web与service间的数据传递
* enums: 封装枚举类，表述常量字段-状态值（“秒杀成功”，“秒杀结束”等等）
* web接口设计,基于RESTful
```
GET	/flashsale/list	（秒杀列表）
GET	/flashsale/{id}/detail	（详情页）
GET	/flashsale/time/now	（系统时间）
POST	/flashsale/{id}/exposer	（暴露秒杀）
POST /flashsale/{id}/{md5}/execution	（执行秒杀
```
* 完成JUnit单元测试
### 4. Web层设计
#### 页面逻辑
![mark](http://os3e5ayd1.bkt.clouddn.com/blog/170705/D5828e6101.png?imageslim)
* jsp页面模块化设计
* js模块化设计
* Ajax的应用
### 5. 并发优化
![mark](http://os3e5ayd1.bkt.clouddn.com/blog/170705/G7cgJdd2if.png?imageslim)
![mark](http://os3e5ayd1.bkt.clouddn.com/blog/170705/BccED716lm.png?imageslim)
![mark](http://os3e5ayd1.bkt.clouddn.com/blog/170705/5a6j99JDbC.png?imageslim)
* 对于静态资源,使用CDN服务
* 对于秒杀地址暴露的接口这种情况,我们可以用redis缓存.
* 对于获取时间操作,不必优化,本身执行就很快,可以达到10WQPS.
* 为了减少MYSQL的行级锁持有时间,将更新库存操作与增加订单操作顺序对调.
* 进一步优化: 为了减少网络延迟跟GC带来的影响,我们使用数据库存储过程来取代JAVA中的事务
### 进一步优化 //TODO
* 使用分布式数据库 分表
* Nginx与WEB容器配合(反向代理) 集群化部署
* Redis 集群化部署
![mark](http://os3e5ayd1.bkt.clouddn.com/blog/170705/J9K518hCeF.png?imageslim)
