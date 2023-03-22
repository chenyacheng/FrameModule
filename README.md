# 模板项目
## 使用 Jetpack 库，MVVM 架构多模块进行开发

### 项目结构
    baselib       ->    基础模块
    networklib    ->    网络模块
    commonlib     ->    公共页面，常用的工具类，适配器类等
    home          ->    首页模块
    me            ->    我的模块
    wxannotaion   ->    微信支付注解
    wxcompiler    ->    微信支付注解处理器

## 应用签名
    keytool -list -v -keystore [签名的文件路径] (由于jdk版本过高，没有MD5)
    或者 gradle -> Tasks -> android 下，双击 signingReport (有MD5)
## 项目打包配置
    在 gradle.properties 文件里更改配置，在 buildSrc 文件夹下的 BuildConfig 更改版本号
## 数据加解密
    在 gradle.properties 文件里更改配置，可看到请求参数，和接口返回的数据
## 应用加固
    使用 360 加固助手进行加固
    在 app 模块下 /reinforce/reinforce.bat 里动态修改每次需要加固的apk安装包
## 查看依赖版本
    android {
        configurations.all {
            resolutionStrategy {
                failOnVersionConflict()
                // 全局强制指定依赖版本，例如如下
                force 'com.github.bumptech.glide:glide:4.11.0'
            }
        }
    }
    #gradlew 模块名:dependencies