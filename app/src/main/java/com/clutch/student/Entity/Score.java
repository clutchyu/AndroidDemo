package com.clutch.student.Entity;

/**
 * Created by clutchyu on 2018/3/21.
 * 选课信息类，课程Id,学生Id,分别对应各自表中的属性，有外键约束
 * 成绩grade为可空属性，即学生选课并未结课，结课后由管理员录入成绩
 * 学生无权限修改该表中的数据
 */

public class Score {
    private int course_id;
    private int student_id;
    private int grade;
    public Score(){

    }
    public Score(int course,int student,int grade){
        course_id = course;
        student_id = student;
        this.grade = grade;
    }
    public int getCourseId(){return this.course_id;}
    public void setCourseId(int course_id){this.course_id = course_id;}
    public int getStudentId(){return this.student_id;}
    public void setStudentId(int student_id){this.student_id = student_id;}
    public int getGrade(){return this.grade;}
    public void setGrade(int grade){this.grade = grade;}
}
