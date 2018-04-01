package com.clutch.student.Entity;

/**
 * Created by clutchyu on 2018/3/19.
 * 学生实体类，id,name,sex,age,phone
 */

public class Student {
    private int id;
    private String name;
    private String sex;
    private int age;
    private String phone;
    private int imageId;
    public Student(){

    }
    public Student(int id,String name,String sex,int age,String phone,int imageId){
        this.id = id;
        this.name = name;
        this.sex = sex;
        this. age = age;
        this. phone = phone;
        this.imageId = imageId;
    }
    public int getId(){return  id;}
    public void setId(int id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getSex(){return this.sex;}
    public void setSex(String sex){this.sex = sex;}
    public int getAge(){return this.age;}
    public void setAge(int age){this.age=age;}
    public String getPhone(){return this.phone;}
    public void setPhone(String phone){this.phone=phone;}
    public int getImageId(){return imageId;}
    public void setImageId(int id){this.imageId = id;}
}
