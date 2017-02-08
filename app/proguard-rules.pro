# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Develop\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn u.aly.**
-verbose
-dontoptimize
-dontpreverify
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

-keep class com.umeng.update.** {*;}
-keep class u.upd.** {*;}
-keep class com.umeng.analytics.** {*;}
-keep class u.aly.** {*;}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

-keep public class com.shenhua.nandagy.R$*{
   public static final int *;
}

-ignorewarnings
# 这里根据具体的SDK版本修改
-libraryjars libs/BmobSDK_V3.4.5_1111.jar
-keepattributes Signature
-keep class cn.bmob.v3.** {*;}

# 保证继承自BmobObject、BmobUser类的JavaBean不被混淆
-keep class com.shenhua.nandagy.bean.bmobbean.MyUser{*;}

-dontwarn android.support.**
-keep class org.jsoup.**

-dontskipnonpubliclibraryclassmembers
-libraryjars libs/jsoup-1.8.3.jar
-libraryjars libs/mta-sdk-1.6.2.jar
-libraryjars libs/open_sdk_r5043.jar
-libraryjars libs/universal-image-loader-1.8.6-with-sources.jar
-libraryjars libs/httpcore-4.2.4.jar
-libraryjars libs/httpclient-4.2.5.jar

-dontwarn com.tencent.**
-keep class com.tencent.** {*;}

-dontwarn android.support.**

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}