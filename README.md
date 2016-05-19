# RetrofitAndRxjava

这是一个Demo，通过代码演示并解答了大家在使用Retrofit和Rxjava的过程中经常会问的一些问题。

我热爱分享，但也不愿意误导别人，有不正确的地方**欢迎在issue里拍砖**。

**下面是TodoList ：**(已完成的会标记出)


## [Retrofit](http://square.github.io/retrofit/)

[okhttp wiki](https://github.com/square/okhttp/wiki)

[retrofit wiki](https://github.com/square/retrofit/wiki)

1. **网络请求打log**(用到[`okhttp-logging-interceptor`](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor))

1. **给http请求加header**（先猜一下`@Headers`和`@Header`有什么区别，在写这个README的时候我也不知道，哈哈哈）

1. 几种数据请求方式(get还是post，post body是form data还是json，这些知识请自已科普)

	- `@GET`的使用（见[GetActivity](app/src/main/java/com/dwgg/retrofitandrxjava/GetActivity.java)）
	- `@POST`（这个用得最多了，特别是做andrid开发，基本上我们会把多数的接口都定义成**post json**的方式）
	- `@Path`的使用（见[GetActivity](app/src/main/java/com/dwgg/retrofitandrxjava/GetActivity.java)）（比如`https://api.github.com/users/whatever`和`https://api.github.com/users/JakeWharton`只有`whatever`和`JakeWharton`不同，retrofit要怎么定义这个接口。好学的小朋友这个时候肯定会再科普一下`restful api`的知识）
	- `@FormUrlEncoded`的使用（用来**提交form表单**数据）
	- `@Multipart`的使用（用来**上传文件**）

1. 怎么样低碳环保地显示**加载中...**？（见[GetActivity](app/src/main/java/com/dwgg/retrofitandrxjava/GetActivity.java)中

	`.compose(RxUtils.<GitHubUser>showLoading(GetActivity.this))`

	此Demo中在GetActivity里实现了接口类`ILoading`）

1. 网络加载的数据在显示之后有一些公用的**数据正确性检查**，这个要怎么写？

1. **Interceptor拦截器**都能干什么，哪些事情用它做会很低碳环保？（直接说答案吧。比如，给每个请求加自定义header） 

1. **Converter转换器**的使用

1. **CallAdapter**（这里我主要只是会说说`RxJavaCallAdapterFactory`）

1. **Auth**

1. **https**



## [RxJava](https://github.com/ReactiveX/RxJava/wiki)

1. **创建操作(`create`、`from`、`just`、`empty`、`throw` )**

1. **map**(改变数据类型或修改数据，用得最多 )

1. **flatmap(`map`做不到的`flatmap`可以搞定，基本上能够熟练使用两个操作符，rxjava就算入门了 )**

1. **`toList()`和`toSortedList()`**(专门说一下，虽然不重要，但是我经常用)

1. **过滤、去重(`filter`、`distinct` )**

1. **concat的使用场景**

1. **rxBinding**(以rxjava的姿势操作Android UI)

1. **RxLifecycle**(rxjava用不好就更容易内存泄漏)