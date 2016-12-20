# 一句代码进行登录

```java
  LoginUtil.doActionNeedLogin(this, () -> {
         //do something need login
        });
        
```

## 使用方式

###  doActionNeedLogin

```
检查是否登录
登录直接执行操作，未登录跳转登录，登录后继续执行操作，放弃登录则什么都不做
```

```java
  LoginUtil.doActionNeedLogin(this, () -> {
         //do something need login
        });
        
```

### doActionJustAfterLogin

```
检查是否登录
已登录什么都不做
未登录跳转登录，登录后继续执行操作，放弃登录则什么都不做
```

```java
  LoginUtil.doActionJustAfterLogin(this, () -> {
         //do something need login
        });
        
```

### doActionAlreadyLogin

```
需要登录才能执行的操作
登录直接执行操作，未登录跳转登录，登录后什么都不做
```

```java
  LoginUtil.doActionAlreadyLogin(this, () -> {
         //do something need login
        });
```

### doLogin

```
跳转登录，什么都不做
```

```java
  LoginUtil.doLogin(this);
```

### isLogin

```
判断是否登录，已登录返回true
```

```java
  LoginUtil.isLogin();
```

##  初始化默认配置

### 设置登录activity

```java
 LoginUtil.setLoginActivity(LoginActivity.class);
```

### 添加自定义是否登录判断拦截器

在应用入口调用

```java
   if (!Util.checkNULL(LoginUtil.USER_POHONE)) {
            return true;
        }
        LoginUtil.USER_POHONE = SharedUtil.getString(context, "username");
        if (!Util.checkNULL(LoginUtil.USER_POHONE)) {
            return true;
        }
```

## 登录配置

在loginAtivity登录成功后和在应用启动时从本地缓存中取出登录凭证赋值给 LoginUtil.USER_TOKEN

```java
  LoginUtil.USER_TOKEN = token;
```

token为登录凭证，如果你自定义了登录拦截器，记得为你的判断变量赋值

在loginAtivity的onfinish()方法中调用

```java
  if (LoginUtil.CALLBACK != null)
            LoginUtil.CALLBACK.postExec();
```

## 依赖添加

## maven

```
<dependency>
  <groupId>com.gengqiquan.login-util</groupId>
  <artifactId>library</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

## gralde

```
compile 'com.gengqiquan.login-util:app:1.0.1'
```

## lvy

```
<dependency org='com.gengqiquan.login-util' name='app' rev='1.0.1'>
  <artifact name='app' ext='pom' ></artifact>
</dependency>
```