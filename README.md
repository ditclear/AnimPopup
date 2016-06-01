# AnimPopup
可以自动消失的popupwindow，实现 + 1 效果，自动淡出


###SreenShot:

![](https://github.com/vienan/AnimPopup/blob/master/screenshot.gif)

###Installation
gradle:
```java
  dependencies {
    compile 'com.vienan:animpopup:1.0.0'
  }
```
maven:
```java
  <dependency>
    <groupId>com.vienan</groupId>
    <artifactId>animpopup</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
  </dependency>
```

###Usage
```java
  AnimPopup.getInstance(context)
                .setMsg("啊哒哒哒哒。。。")
                .setTextColor(Color.RED)
                .setTextSize(20) //dp
                .showOnBottom(v)  //or top
                .show();
```
###Contact me

>E-mail:1940692836@qq.com

>Location:成都软件园

Welcome to star && fork if it has any help.
