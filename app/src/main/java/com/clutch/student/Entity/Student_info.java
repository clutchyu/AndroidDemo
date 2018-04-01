package com.clutch.student.Entity;


/**
 * Created by clutchyu on 2018/3/19.
 * 拆分学生信息的内部类，用于单项展示学生属性在RecycleView上
 */

public class Student_info{
    private int image;
    private String info;
    public Student_info(){

    }
    public Student_info(int image,String info){
        this.image = image;
        this.info = info;
    }
    public int getImage(){return this.image;}
    public void setImage(int image){this.image = image;}
    public String getInfo(){return this.info;}
    public void setInfo(String info){this.info = info;}
}
