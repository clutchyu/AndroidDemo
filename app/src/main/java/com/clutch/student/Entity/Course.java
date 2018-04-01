package com.clutch.student.Entity;

/**
 * Created by clutchyu on 2018/3/17.
 */
/**
 * 课程类，课程号，课程名，学分，图片id
 */
public class Course {
    private int courseId;
    private String courseName;
    private int credit;
    private int imageId;
    public Course(){

    }
    public Course(int courseId,String courseName,int credit,int imageId){
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.imageId = imageId;
    }

    public int getId(){
        return courseId;
    }
    public void setId(int id){this.courseId = id;}
    public String getName(){
        return courseName;
    }
    public void setName(String name){this.courseName = name;}
    public int getCredit(){
        return credit;
    }
    public void setCredit(int credit){this.credit = credit;}
    public int getImageId(){
        return imageId;
    }
    public void setImageId(int id){this.imageId = id;}
}
