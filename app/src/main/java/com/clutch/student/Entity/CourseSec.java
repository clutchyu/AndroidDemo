package com.clutch.student.Entity;

/**
 * Created by clutchyu on 2018/3/21.
 * 展示学生选课及对应课程分数的类
 */

public class CourseSec {
    private int course_id;
    private  String course_name;
    private int credit;
    private int grade;
    private int imageId;
    public CourseSec(){

    }
    public CourseSec(int course_id,String course_name,int credit,int grade,int imageId){
        this.course_id = course_id;
        this.course_name = course_name;
        this.credit = credit;
        this.grade = grade;
        this.imageId = imageId;
    }
    public int getCourse_id(){return this.course_id;}
    public String getCourse_name(){return this.course_name;}
    public int getCredit(){return this.credit;}
    public int getGrade(){return this.grade;}
    public int getImageId(){return this.imageId;}
    public void setCourse_id(int course_id){this.course_id = course_id;}
    public void setCourse_name(String course_name){this.course_name = course_name;}
    public void setCredit(int credit){this.credit = credit;}
    public void setGrade(int grade){this.grade = grade;}
    public void setImageId(int imageId){this.imageId = imageId;}
}
