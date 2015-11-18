# jZoomer is a SWT/JFace Desktop Appilication #

## Features ##
  * Support Mouse Drag Widgets
  * Support I18N
  * Extendibility framework

## Functions ##
  * Dynamic magnifier
  * Static mage zoom in/out
  * Chalk function
  * Color picker & Save to clipboard automatically
  * Measuring with mouse detecting zoom rate intelligently

## Others ##
### How To Use Patch To Upgrade JZoomer ###
  * Unpack jZoomer\_patch_(version).zip
  * Copy all the files unpacked into jZoomer_(version).jar and overwrite the original files
  * Run jZoomer_(version).jar with new features
  * Enjoy it! :)_




## History(Chinese) ##
```
20080628
1.	Bug修正：
	·修正取色辅助信息描述不正确的bug
	·修正鼠标图标显示不正常的bug
2.	功能添加：
	·实现自定义鼠标图标的添加和管理
	·实现粉笔功能：可以在jZoomer主面板上涂写做标记
	·对粉笔功能提供多个功能快捷键：增加/减小粉笔大小、改变粉笔颜色、画直线、擦除等
	·设置取色、粉笔、测量为单选菜单互相排斥
	·添加取色和粉笔功能的自定义鼠标图标
3.	核心调整：
	·添加CursorsManager类用于管理加载的鼠标图标
	·将鼠标拖动组件/窗口的处理从BasicWindow中提取出来放到MouseAction类中，方便日后重用
	·添加ChalkAction类用来实现粉笔功能
	·在JZoomerConstant中添加有关粉笔和鼠标图标的一些常量
	·添加HotKeyManager用于对热键的管理和解析（未完全实现）
4.	版本变更：
	版本升级为1.3.0

20080626
1.	Bug修正：
	·修正对测量功能描述上的错误
	·修正取色信息面板频繁显示，影响其他功能使用的Bug
	·用了一种不太好的方法修正鼠标图标显示不正常的Bug(临时)
2.	功能添加：
	·build.xml中添加package.patch工程，用于打补丁包
3.	核心调整：
4.	版本变更：
         版本升级为1.2.1

20080623
1.	Bug修正：
	·修正对取色功能描述上的错误
2.	功能添加：
3.	核心调整：
4.	版本变更：

20080621
1.	Bug修正：
	·修正退出时报异常的Bug
2.	功能添加：
	·实现测量功能，可测量选中区域大小
3.	核心调整：
	·在system.properties中添加“tray.message.show”字段用以控制是否在托盘区显示提示信息
	·实现TrackerAction用以实现测量功能
4.	版本变更：
	版本升级为1.2.0

20080620
1.	Bug修正：
	·修正程序退出时会报异常的Bug
2.	功能添加：
	·实现左键单击托盘区图标，主窗口开启/隐藏功能
3.	核心调整：
4.	版本变更：
	版本升级为1.1.4

20080619 2nd
1.	Bug修正：
	·修正开始/暂停监视切换时有时会报空指针异常的错误
2.	功能添加：
	·实现静止时图片的缩放
3.	核心调整：
	·动态\静止开关中对图片处理的逻辑改变，以实现静止时图片的缩放功能
	·ZoomAction中zoomIn、zoomOut方法更新，支持对静止图片的缩放
4.	版本变更：
	版本升级为1.1.3
	
20080619
1.	Bug修正：
	·修正取色开启时鼠标双击取色后，色彩信息显示不正常的bug
2.	功能添加：
	·隐藏/取色后会在系统托盘区有信息提示
3.	核心调整：
	·在trayItem中加入tooltip用于辅助信息提示
4.	版本变更：
	版本升级为1.1.2

20080618 2nd
1.	Bug修正：
2.	功能添加：
	·添加用户手册中文版V1.1
	·添加用户手册英文版V1.1
	·添加ant编译文件，使用“ant package”即可轻松打包发布文件
	·调整system.properties的排版，方便阅读
3.	核心调整：
	·加入fatjar.jar架包，用于打包编译
4.	版本变更：
	版本升级为1.1.1

20080618
1.	Bux修正：
	·修正工程中加载lib路径不正确的bug
	·修正鼠标滚轮在停止取图监视时使用上的Bug
	·修正鼠标进入窗口后，还可以开启监视的Bug
2.	功能添加：
	·添加色彩信息面板
	·实现取色功能，用Ctrl+C开启，双击可以直接取色至剪贴板，单击暂停/开始取色
3.	核心调整：
	·在Google Code上添加了SVN地址用于维护，地址为：https://jzoomer.googlecode.com/svn/trunk/
	·重构鼠标拖拽Widget和面板的方法，增强其扩展性
	·添加ColorInfoPanel用于显示和拾取颜色
	·在ColorAction添加ColorInfoPanel相关的资源、逻辑和方法
	·添加TrackerAction用以实现对图片大小的测量（未实现）
4.	版本变更：
	版本升级为1.1
5.	待做事项：
	·停止监控时图片的缩放
	·对图片大小的测量(width=xxxpx,height=xxxpx)
	·色彩信息面板样式的美化
	·build.xml文件

20080617
1.	Bug修正：
	·修正进出窗口时会报异常的Bug
2.	功能添加：
	·将开始/暂停监控的快捷键更改为Ctrl+Z
	·支持容器内部控件的拖拽（按住Ctrl键）
	·支持拖拽容器时移动整个Shell（在空白处）
	·拖拽时鼠标变成小手形状
	·添加exit.confirm属性用以判断是否在退出时弹出提示框
3.	核心调整：
	·添加canvas用于对静止截图的处理
	·添加Tracker用于测量
	·添加Label用于信息提示
	·添加ColorAction用于对静止截图的处理
4.	版本变更：
	版本升级为1.03

20080616 2nd
1.	Bug修正：
	·修正暂停监控时调整窗口大小，显示图像错乱的Bug
2.	功能添加：
	·添加设定背景色属性background.color
3.	核心调整：
	·去掉canvas，直接将采样图片放于container上，便于以后进一步在container上添加组件及操作
4.	版本变更:
	·版本升级为1.02

20080616
1.	Bug修正：
	·暂未修正无法通过修改配置文件切换语言的Bug
2.	功能添加：
	·添加使用手册英文版v1.0
	·如需软件显示语言自动随系统语言切换，只需将System.properties中“locale”属性置空即可
	·调整缩放范围，使其既可放大屏幕也可缩小屏幕
3.	核心调整：
	·添加SWT/JFace的jar包到工程lib目录下
	·BasicWindow添加对鼠标移动点击的事件监听，以实现组件可用鼠标拖拽功能
	·BasicWindow添加在屏幕中央显示/随鼠标位置显示方法
	·更改屏幕采样并缩放的核心算法，使其缩放质量可以调整，支持缩小屏幕，且采样速度更快
4.	版本变更：
	·版本升级为1.0.1
5.	待做事项：
	·使用手册英文版
	·采样图片截取：只截取可见部分
	·对基类添加：随鼠标拖动功能，自动在屏幕中央显示功能，自动随鼠标显示功能
	·打包后无法通过修改配置文件切换语言（打包之前可以）
	·整理需要到的jar包，从eclipse中提取出来放到lib目录下
	·设置滚轮放大缩小可以开启/取消

20080615
1. Bug修正：
	·解决内存溢出Bug
	·解决字符串乱码Bug
	`解决拖拽位置不正常Bug
2. 功能添加：
	·添加SystemPropertiesReader类，用于解析系统配置文件system.properties
	·完成软件的国际化，可根据需要切换英文/中文（暂时只能在启动前配置）
	·添加快捷键和部分图标
	·实现关闭到系统托盘效果
3. JZoomer v1.0 发布

20080614
1. 重新设计框架，提高其重用性
2. 实现功能：
	·字符串国际化
	·系统文件可编辑
	·系统托盘显示
	·支持滚轮缩放功能
	·控件拖拽功能
	
20071227
1. JExplorer实现，初步了解JFace框架的设计思想，了解Action的使用方法

20070730
1. ColorPicker实现，其中含有部分放大镜功能，但有内存溢出Bug
```