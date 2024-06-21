# timber-android

## 项目介绍（Project Introduction）

项目取名timber——`木材`，象征人类从刀耕火种逐渐进化的源头。

项目所有技术文档都在 [0-1Learning](http://github.com/soonphe/0-1Learning) 中有完整介绍。

### 设备支持（Equipment Support）

设备支持：web管理后台、android（平板和phone）、ios、小程序

- server端：[timber](http://github.com/soonphe/timber)
- web端：[timber-web](http://github.com/soonphe/timber-web)
- android端：[timber-android](http://github.com/soonphe/timber-android)
- ios端：[timber-ios](http://github.com/soonphe/timber-ios)
- 小程序端：[timber-applet](http://github.com/soonphe/timber-applet)

### 业务支持（Business Support）

- 广告业务：支持广告智能投放，自动上架与下架，广告覆盖有效统计
- 电影业务：对接xx电影网
- 视频业务：小视频点播
- 音乐业务：音乐播放、下载
- 小说业务：文件上传和爬虫抓取
- 新闻业务：定期抓取
- 游戏业务：html游戏
- 城市特色业务：城市介绍，衣食住行、景点、广告引流

### 业务功能模块（Functional Module）

```
├── 系统模块
    ├── 用户统一管理后台系统UUM
    └── 权限系统模块
├── 业务模块
    ├── 广告模块
    ├── 电影模块
    ├── 视频模块
    ├── 音乐模块
    ├── 小说模块
    ├── 新闻模块
    ├── 游戏模块
    └── 城市模块
├── 搜索模块
├── 三方功能模块
└── 数据模块
    ├── 埋点模块
    └── 统计模块
```

### 高可用分布式系统架构（Highly available distributed system architecture）
![高可用分布式系统架构](document/static/architecture/highly_available_architecture.png "高可用分布式系统架构")


### 系统技术栈（System Technology Stack）
![系统技术栈](document/static/architecture/system_technology_stack.png "系统技术栈")

### 项目结构（Project Structure）
```
timber-android
├── app:
    ├── src:
        ├── assets：数据库配置
        ├── java：
            ├── adapter：recycleview适配器
            ├── api：http请求模块 *
                ├── api：接口请求定义
                └── serveice：接口封装
            ├── base：基类封装 *
            ├── components：封装网络请求
            ├── constants：静态类
            ├── di：依赖注入组件
            ├── entity：实体
            ├── pojo：持久化
            ├── receiver：广播监听
            ├── services：服务
            ├── ui：界面模块 *
                ├── ...：业务界面...
                ├── fragment：fragment模块
                    ├── center：商城
                    ├── home：首页
                    ├── mine：我的
                    └── msg：消息
                ├── main：主界面activity，包含多个fragment
                ├── splash：闪屏页activity
                └── unlock：解锁页activity
            ├── utils：工具类
            └── widget：组件
        ├── res：静态资源（布局、图片、文字、颜色等）
        └── AndroidManifes.xml：android清单
    ├── build.gradle: gradle构建    
    ├── debug.keystore: 签名打包
    ├── Key.jks: 签名
    └── proguard-rules.pro：混淆
├── timber-widget: 自定义组件模块
    ├── amount：自定义加减输入框（已完成）
    ├── banner：自定义banner
    ├── banner：自定义menu（已完成）
    ├── statusbar：自定义状态栏（已完成）
    ├── tablayout：自定义tablayout
    ├── titlebar：自定义apptitle(左侧返回，中间标题，右侧文字)（已完成）
    └── unlock：自定义解锁画面（已完成）
├── build.gradle: 基础gradle构建配置
├── config.gradle: 统一gradle版本配置
├── README.md -- 说明
└── settings.gradle: 项目配置

备注：标 * 的为重点模块
```

### 项目架构（System Architecture）

#### 1.基础项目架构（Base Framework）
+ 网络框架：okhttp3 + retrofit2
+ 异步框架：rxjava2
+ view依赖注入：butterknife
+ 组件依赖注入：Dagger
+ RPC数据解析：gson + fastjson
+ 日志：logger
+ 网络监控：fackbook stetho
+ 组件通信：EventBus
+ 数据库：Litepal
+ 网页加载：agentweb + 腾讯X5内核
+ 热修复+更新：bugly + tinker
+ JDK：1.8 + retrolambda表达式

#### 2.控件库
+ 图片加载：Glide + 头像circleimageview
+ 图片选择：知乎matisse
+ 控件徽章：BadgeView
+ 控件切换：SwitchButton
+ 列表加载：BaseRecyclerViewAdapterHelper + easyrecyclerview
+ 进度条：WangAvi
+ 弹出框：material-dialogs
+ 菜单列表：FlycoTabLayout_Lib + VerticalTabLayout
+ 轮播图：banner

#### 3.工具库
+ 上传下载：okgo + okserver
+ 图片压缩：Luban
+ 上拉刷新，下拉加载更多：SmartRefreshLayout
+ 小说阅读器：HwTxtReader
+ PDF阅读器：android-pdf-viewer
+ 表单验证：saripaar
+ 动态权限请求：rxpermissions
+ 动画库：nineoldandroids
+ 音视频播放器：jiaozivideoplayer
+ 中文转拼音：jpinyin
+ 定位：高德+citypicker
+ html解析：jsoup
+ 工具类：blankj
+ 一键换肤
+ 极光推送

### 开发进度
- gradle更新（官网）（当前gradle插件4.2.0，gradle 6.7.1）
  - gradle版本对应关系：https://developer.android.google.cn/studio/releases/gradle-plugin.html#updating-plugin
  - 去除jcenter，Jfrog，使用mavenCenter依赖更新（central仓库地址：central.sonatype.com）
    - google仓库：在gradle4.1之后，添加了新的语法google()，用于引用google自有的仓库。很方便，但是不便于研究被依赖的aar源文件。
    - jenter：属于jfrog，已停用，不能上传新包，不过可以下载（原有jcenter仅支持下载-已启动），jcenter是由 bintray.com维护的Maven仓库。可以在jcenter.bintray.com/看到整个仓库的内容。
    - Jfog已停用
    - mavenCentral：由sonatype.org维护的Maven仓库。可以在repo1.maven.org/maven2/看到整个仓库。
    * JitPack：那么这和 Maven Central，JCenter 有何不同呢？最大的区别就在于你不必完成 Maven Central 的一系列注册手续，乃至发布一个库之前的登记 Post 和等待管理员批准，也不必在 JCenter 上填写冗长的标签，找图做图做图标写说明，更不必每到发布时做一系列的准备工作，使用专用的工具完成最后一击。你只需要写好你的 GitHub Repo README就行了，其他的事情，JitPack 会全数包办。
    - build：推荐使用maven center和jitpack，参考：https://blog.csdn.net/2401_84104728/article/details/137532001
    - gradle更新：当前4.2.0（最新8.3，支持sdk34.0.0，参考文档：https://developer.android.google.cn/build/releases/past-releases/agp-8-3-0-release-notes?hl=en）
    - gradle plugin更新：当前6.7.1（最新版本8.8）
    - 新增maven仓库地址：maven { url "https://s01.oss.sonatype.org/content/groups/public" }

- 全面使用androidX✔️
  - androidX是针对android support的一次更新
  * 第一，包名。之前Android Support Library中的API，它们的包名都是在android.support.下面的，而AndroidX库中所有API的包名都变成了在androidx.下面。
  * 第二，命名规则。吸取了之前命名规则的弊端，AndroidX所有库的命名规则里都不会再包含具体操作系统API的版本号了。比如，像appcompat-v7库，在AndroidX中就变成了appcompat库。
- 项目极简化拆分，基础代码更新
  - baseApplication重写✔️
  - MyAppliation重写
    - 去除推送、bugly、tinker等配置✔️
    - okgo下载✔️
  - baseActivity重写✔️
  - BaseFragment重写✔️
  - dagger 依赖注入重写✔️
  - 基础适配器(基于RecyclerView.Adapter，暂缓)
  - 网络组件重写
    - 使用本地IP，重写convert✔️
    - okhttp，retrofit更新✔️
    - API参数变更✔️
    - json解析

- UI重写
  - unlock页，使用widget✔️
  - falsh页重写✔️
    - 网络请求获取闪屏页图片✔️
    - 倒计时、倒计时可跳过✔️
    - 判断网络是否可用、网络运营商名称✔️
  - main页面重写（使用viewpager嵌套fragment + CommonTabLayout联动切换）
    - 首页（带定位搜索apptitle、banner、munu、recycleView、swipeView下拉刷新）（已完成）
    - 商城(titlebar)✔️
    - 消息(titlebar)✔️
    - 我的（折叠toolbar，apptitle、list、login跳转）

- 自定义小组件：
  - 自定义解锁画面（已完成）
  - 自定义状态栏（已完成）
  - 自定义menu（已完成）
  - 自定义apptitle(左侧返回，中间标题，右侧文字)（已完成）
  - 自定义banner
  - 自定义加减输入框（已完成）
  - 自定义tablayout
  - 悬浮球(权限问题)

- 通用工具类：
  - 基础工具类：spUtil编写，logUtil编写
  - 数据库操作——参考Litepal
    - Android SQLiteOpenHelper Android SDK 默认提供的SQLite方案。 SQLite low-level API，Raw SQL queries 使用比较繁琐
    - Android Jetpack Room Jetpack 新组件。 SQLite 之上的 ORM 抽象层。
    - SQLDelight Square 开源的数据库框架。 使用 Kotlin DSL 生成 Java 代码。

- 调试debug：
  - 安装copilot✔️
  - android调试：在chrome的地址栏输入chrome://inspect, 可以看到当前连接的设备，然后点击inspect按钮✔️
  - android日志：Logcat✔️
  - android模拟器AVD访问本机开发环境(在在模拟器上可以用10.0.2.2代替127.0.0.1和localhost)✔️

- 其他问题
**Apps targeting Android12 and higher are required to specify...**
如果应用以 >=Android 12 或更高版本为目标平台，且包含使用 intent 过滤器的 activity、服务或广播接收器，您必须为这些应用组件显式声明 android:exported 属性。目的就是Android 12 提高了app和系统的安全性。
如果设为“true”，那么 Activity 可由任何应用访问，并且可通过其确切类名称启动。
如果设为“false”，则 Activity 只能由同一应用的组件、使用同一用户 ID 的不同应用或具有特权的系统组件启动。 没有 intent 过滤器时，这是默认值。

**Android解决设备ID获取异常 java.lang.SecurityException: getDeviceId: The user 10612 does not meet the require**
受此影响，在Android10( Target API 提升到了 30 之后)以及之后的版本想通上述代码获取设备ID或者IMEI号都无法实现，这里我们总结一下具体有哪些API无法调用
```
Build
getSerial()
TelephonyManager
getImei()
getDeviceId()
getMeid()
getSimSerialNumber()
getSubscriberId()
```

**android:screenOrientation="portrait" 不推荐**
解决方案通常是让Activity根据用户的屏幕旋转做出相应的调整。可以通过在Activity中重写onConfigurationChanged()方法来处理屏幕旋转的情况：
```
java
@Override
public void onConfigurationChanged(Configuration newConfig) {
super.onConfigurationChanged(newConfig);
// 根据newConfig中的信息做出相应的处理，如重新加载布局或者数据
}
同时，在AndroidManifest.xml中为该Activity添加屏幕方向改变的权限：

xml
<activity
android:name=".YourActivity"
android:configChanges="orientation|screenSize"
android:screenOrientation="sensor">
</activity>
```
也可以在application添加标签
```
解决方法：
application标签下加入：

xmlns:tool="http://schemas.android.com/tools"
tool:ignore="LockedOrientationActivity"
```

**java.lang.IllegalArgumentException: No Retrofit annotation found. (parameter**
问题定位：Retrofit方法参数错误
```
interface ApiService {
    @GET("users/{id}")
    Call<User> getUser(@Path("id") int userId);
 
    @POST("users/new")
    Call<User> createUser(@Body User user);
 
    @GET("users/search")
    Call<List<User>> searchUsers(@Query("name") String name);
}
```

**定位信息问题**
java.lang.UnsupportedOperationException: GpsStatus APIs not supported, please use GnssStatus APIs instead

**自定义menu加载问题**
java.lang.IllegalArgumentException: You cannot start a load for a destroyed






