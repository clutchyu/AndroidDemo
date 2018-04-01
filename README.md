# Android 学生选课系统
## 概述
&emsp;&emsp;此程序是功能为学生选课系统的Android App,功能涵盖了：  
&emsp;&emsp;学生和管理员以账号密码的形式登录系统，进入系统分别对应各自的功能。  
&emsp;&emsp;学生查看课表，课程成绩，进行选课，修改个人信息。  
&emsp;&emsp;管理员添加删除课程，修改学生成绩，添加学生信息。
## 数据库
![ER图](https://raw.githubusercontent.com/clutchyu/MarkDownPhotos/master/ER.png)  
&emsp;&emsp;共四个表：学生信息表 student ,课程信息表 course ,成绩表 score ,登录表 log。
score表中course_id和student_id为联合主键，分别与对应course表和student表中的course_id和id组成外键。
## 程序功能
### 登录
&emsp;&emsp;实现在数据库中存入了一些数据，其中，登陆表log中管理员所对应的账号为0，密码为000000。只有此账号能登入管理员对应的界面。学生以学号为账号，初始密码默认为123456，可登入学生对应界面。  
![登录](https://raw.githubusercontent.com/clutchyu/MarkDownPhotos/master/%E7%99%BB%E5%BD%95.jpg)
### 学生界面
&emsp;&emsp;以账号10001，密码123456登入系统，进入此学生界面。
主界面可滑动，三个滑动界面对应三个功能，查看课表，选课，修改个人信息。  
![学生](https://raw.githubusercontent.com/clutchyu/MarkDownPhotos/master/%E8%AF%BE%E8%A1%A8.jpg)
### 管理员界面
![管理](https://raw.githubusercontent.com/clutchyu/MarkDownPhotos/master/%E7%AE%A1%E7%90%86%E5%91%98.jpg)
### 程序特色
&emsp;&emsp;界面采用 BottomNavigationView + ViewPager + Fragment 实现功能界面的左右滑动，用RecylerView 组件展示课程信息，其中在界面中刷新数据成为技术难点。程序中自己在要更新的界面写了更新方法，在提交界面点击按钮提交信息后，调用该方法，重新查询数据显示到相应的界面中。     
&emsp;&emsp;程序中的主要思路为界面与数据操控逻辑分离，显示信息时将调用Dao 中对应的类查询到所需信息传入 Adapter 包中对应的适配器中，设置好适配器后，将适配器用于对应组件中，在传入对应界面实现显示。  
&emsp;&emsp;输入信息时将采集到的信息传入 Dao 包中的对应的类，进行数据库操作，将操作是否成功以 boolean 类型返回到相应适配器，再传入对应界面给出相应提示，并实现数据刷新
