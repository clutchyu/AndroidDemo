package com.clutch.student.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clutch.student.Entity.User;

/**
 * Created by clutchyu on 2018/3/26.
 * 对log表进行数据库操作
 */

public class UserDao {
    private Context context;
    private MyDatabaseHelper dbHelper;

    public UserDao(Context context) {
        this.context = context;
        dbHelper = new MyDatabaseHelper(context);
    }

    /**
     * 检查登录表中是否有对应的账号
     */
    public boolean check(int id, String password) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "select password from log where student_id = ?";
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    User user = new User();
                    String passwd = cursor.getString(cursor.getColumnIndex("password"));
                    if(passwd.equals(password)){
                        return true;
                    }

                }

            }


        } catch (Exception e) {

        } /*finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }*/

        return false;
    }
    /**
     * 向表中插入数据
     */
    public boolean insertLog(int studentId,String password){
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("student_id",studentId);
            values.put("password",password);
            db.insertOrThrow("log",null,values);
            db.setTransactionSuccessful();
            return true;
        }catch (SQLiteConstraintException e){
            //Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false;
        }catch (Exception e){

        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }
    /**
     * 修改密码
     */
    public boolean modifyPasswd(User user,String newPasswd){
        SQLiteDatabase db = null;
        String sql = "update log set password = ? where student_id = ?";
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            if(check(user.getId(),user.getPassword())){
                db.execSQL(sql,new Object[]{newPasswd,user.getId()});
                db.setTransactionSuccessful();
                return true;
            }
            else {
                return false;
            }
        }catch (SQLiteConstraintException e){
            //Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
            return false;
        }catch (Exception e){

        }finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }
}