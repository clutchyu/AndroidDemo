package com.clutch.student.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import com.clutch.student.R;
import com.clutch.student.Entity.Course;
/**
 * Created by clutchyu on 2018/3/17.
 * 对course表进行数据库操作
 */

public class CourseDao {
    private static final String TAG = "CourseDao";

    // 列定义
    private final String[] ORDER_COLUMNS = new String[] {"course_id","course_name","credit"};

    private Context context;
    private MyDatabaseHelper dbHelper;

    public CourseDao(Context context) {
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
            cursor = db.query("course", new String[]{"COUNT(Id)"}, null, null, null, null, null);

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
     * 查询课程信息
     */
    public List<Course> getCourse(){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "select * from course";
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(sql,null);
            //cursor = db.rawQuery("select * from course",null);
           /* cursor = db.query(dbHelper.TABLE_NAME,
                    ORDER_COLUMNS,
                    "course_name = ?",
                    new String[] {"math"},
                    null, null, null);*/
            if (cursor.getCount() > 0) {
                List<Course> courseList = new ArrayList<Course>(cursor.getCount());
                while (cursor.moveToNext()) {
                    Course course = parseCourse(cursor);
                    courseList.add(course);
                }
                return courseList;
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
     * 添加课程
     */
    public boolean addCourse(Course course) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("course_id",course.getId());
            values.put("course_name",course.getName());
            values.put("credit",course.getCredit());
            db.insertOrThrow("course",null,values);
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
     * 删除课程,以课程id
     */
    public boolean deleteCourse(int courseId) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            db.delete("course","course_id = ?",new String[]{String.valueOf(courseId)});
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
     * 将找到的数据转化为Course类
     */
    private Course parseCourse(Cursor cursor){
        Course course = new Course();
        int id = cursor.getInt(cursor.getColumnIndex("course_id"));
        course.setId(id);
        course.setName(cursor.getString(cursor.getColumnIndex("course_name")));
        course.setCredit(cursor.getInt(cursor.getColumnIndex("credit")));
        switch (id){
            case 10001:
                course.setImageId(R.drawable.math);
                break;
            case 10002:
                course.setImageId(R.drawable.chinese);
                break;
            case 10003:
                course.setImageId(R.drawable.english);
                break;
            case 10004:
                course.setImageId(R.drawable.physic);
                break;
            case 10005:
                course.setImageId(R.drawable.sport);
                break;
            default:
                course.setImageId(R.drawable.health);
        }

        return course;
    }


}
