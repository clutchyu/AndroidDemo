package com.clutch.student;

/**
 * Created by clutchyu on 2018/3/31.
 * 用于数据输入检验
 */

public class EditCheck {
    private static String warning;
    public EditCheck(){

    }
    public static String getWarning(){return warning;}
    public static  boolean CheckInt(String input,String label,int min,int max){
        if(input.equals("")) {
            warning = label + "不能为空！";
            return false;
        }
        else {
            try{
                int value = Integer.parseInt(input);
                if(value<min||value>max) {
                    warning = label + "应在" + min + "至" + max + "区间";
                    return false;
                }
            }catch(RuntimeException e){
                warning =label+"格式错误！";
                return false;
            }
        }
        return true;
    }
    public static boolean CheckString(String input,String label,int size){
        if(input.equals("")) {
            warning = label + "不能为空！";
            return false;
        }
        else if(input.length()>size){
            warning = label + "长度错误！";
        }
        return true;
    }
}
