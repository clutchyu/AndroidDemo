package com.clutch.student.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clutch.student.Entity.Student_info;
import com.clutch.student.Entity.Student;
import com.clutch.student.R;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clutchyu on 2018/3/19.
 * 对student表进行数据库操作
 */

public class StudentDao {
    private static final String TAG = "StudentDao";

    // 列定义
    private final String[] ORDER_COLUMNS = new String[] {"id","name","sex","age","phone"};

    private Context context;
    private MyDatabaseHelper dbHelper;

    public StudentDao(Context context) {
        this.context = context;
        dbHelper = new MyDatabaseHelper(context);
    }
    /**
     * 判断表中是否有数据
     */
    public boolean isDataExist(){
        int count = 0;

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            // select count(Id) from Orders
            cursor = db.query("student", new String[]{"COUNT(Id)"}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) return true;
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }



    /**
     * 根据学生Id查询学生信息
     */

    public List<Student_info> getStudent(int id){
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = dbHelper.getReadableDatabase();
            String sql = "select * from student where id = ?";
            cursor = db.rawQuery(sql,new String[]{String.valueOf(id)});
            if (cursor.getCount() > 0) {
                Student student = new Student();
                while(cursor.moveToNext()){
                     student = parseStudent(cursor);
                }

                List<Student_info> StudentList = new ArrayList<Student_info>(5);
                Student_info sid = new Student_info(R.drawable.id,Integer.toString(student.getId()));
                StudentList.add(sid);
                Student_info sname = new Student_info(R.drawable.name,student.getName());
                StudentList.add(sname);
                Student_info sex = new Student_info(R.drawable.sex,student.getSex());
                StudentList.add(sex);
                Student_info age = new Student_info(R.drawable.age,Integer.toString(student.getAge()));
                StudentList.add(age);
                Student_info phone = new Student_info(R.drawable.phone,student.getPhone());
                StudentList.add(phone);

                return StudentList;
            }
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
}
    /**
     * 更新学生信息,学生用户自己修改个人信息,根据Id进行更新，无法更改Id
     */
    public Student updateStudent(Student student){
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues value = new ContentValues();
            value.put("name",student.getName());
            value.put("sex",student.getSex());
            value.put("age",student.getAge());
            value.put("phone",student.getPhone());
            db.update("student",value,"id = ?",new String[]{String.valueOf(student.getId())});
            db.setTransactionSuccessful();
            return student;             //直接返回了参数中的Student类对象，并没有重新查询数据库中刚进行更新的对象，结果是一样的
        }catch (Exception e) {
            Log.e(TAG, "", e);
        }
        finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return null;
    }

    /**
     * 插入学生信息，用户注册
     */
    public boolean insertStudent(Student student){
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("id",student.getId());
            values.put("name",student.getName());
            values.put("sex",student.getSex());
            values.put("age",student.getAge());
            values.put("phone",student.getPhone());
            db.insertOrThrow("student",null,values);
            db.setTransactionSuccessful();
            return true;
        }catch (SQLiteConstraintException e){
            //Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false;
        }catch (Exception e){
            Log.e(TAG, "", e);
        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }
    /**
      * 转化为Student类
    * */

    private Student parseStudent(Cursor cursor) {
        Student student = new Student();
        student.setId(cursor.getInt(cursor.getColumnIndex("id")));
        student.setName(cursor.getString(cursor.getColumnIndex("name")));
        student.setSex(cursor.getString(cursor.getColumnIndex("sex")));
        student.setAge(cursor.getInt(cursor.getColumnIndex("age")));
        student.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        student.setImageId(R.drawable.health);
        return student;
    }
    }
