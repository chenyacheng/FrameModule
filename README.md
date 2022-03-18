# 模板项目
## 使用 Jetpack 库，MVVM 架构多模块进行开发

## 应用签名
    keytool -list -v -keystore [签名的文件路径] (由于jdk版本过高，没有MD5)
    或者 gradle -> Tasks -> android 下，双击 signingReport (有MD5)
## 项目打包配置
    在 gradle.properties 文件里更改配置，在 buildSrc 文件夹下的 BuildConfig 更改版本号
## 数据加解密
    在 gradle.properties 文件里更改配置，可看到请求参数，和接口返回的数据