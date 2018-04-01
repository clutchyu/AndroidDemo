package com.clutch.student.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.clutch.student.Entity.Course;
import com.clutch.student.Entity.CourseSec;
import com.clutch.student.Entity.Score;
import com.clutch.student.R;
import com.facebook.stetho.inspector.database.SqliteDatabaseDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clutchyu on 2018/3/21.
 * 对Score表进行数据库操作
 */

public class ScoreDao {
    private static final String TAG = "ScoreDao";
    private Context context;
    private MyDatabaseHelper dbHelper;
    public ScoreDao(Context context) {
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
            cursor = db.query("score", new String[]{"COUNT(Id)"}, null, null, null, null, null);

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
     * 录入选课信息及成绩
     */
    public boolean setScore(Score score){
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("student_id",score.getStudentId());
            values.put("course_id",score.getCourseId());
            values.put("grade",score.getGrade());           //此项数据可能为空
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
     * 查询选课情况及课程分数,根据学生Id
     */
    public List<CourseSec> getScore(int student_id){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "select c.course_id,c.course_name,credit,grade " +
                "from course as c,score as s " +
                "where s.student_id= ? and s.course_id=c.course_id";
        try{
            db = dbHelper.getWritableDatabase();
            cursor = db.rawQuery(sql,new String[]{String.valueOf(student_id)});
            if (cursor.getCount() > 0) {
                List<CourseSec> scoreList = new ArrayList<CourseSec>(cursor.getCount());
                while (cursor.moveToNext()) {
                    CourseSec course = parseCourseSec(cursor);
                    scoreList.add(course);
                }

                return scoreList;
            }


        }catch (Exception e) {
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
     * 查询学生已选课程，Course类
     */
    public List<Course> getCourse(int id)
    {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "select c.course_id,c.course_name,credit " +
                "from course as c,score as s " +
                "where s.student_id= ? and s.course_id=c.course_id";                //出去socre表中已选的课程
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(sql,new String[]{String.valueOf(id)});
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
     * 查询学生未选课程信息
     */
    public List<Course> getUnCourse(int id){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "select * from course where course_id not in" +
                "(select course_id from score where student_id = ?)";                //除去socre表中已选的课程
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(sql,new String[]{String.valueOf(id)});
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
     * 学生选课
     */
    public boolean chooseCourse(int student_id,int course_id){
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("student_id",student_id);
            values.put("course_id",course_id);
            db.insertOrThrow("score",null,values);
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
     * 录入学生成绩，需要先判断学生是否选了该门课程，没选提示错误
     * 进行update操作即便是表中数据不符合要更新的条件，无法进行更行，也不会产生异常
     * 先对要进行更新的学生id和课程id进行查询，看表中是否有对应的项，如果有则进行update操作，没有则返回false
     */
    public boolean writeScore(Score score){
        SQLiteDatabase db = null;
        String sql = "update score set grade = ? where student_id = ? and course_id = ?";
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            if(checkCourse(score.getStudentId(),score.getCourseId())){
                db.execSQL(sql,new Object[]{score.getGrade(),score.getStudentId(),score.getCourseId()});
                db.setTransactionSuccessful();
                 return true;
            }else{
                return false;
            }
        }catch (SQLiteConstraintException e){
            return false;
        }catch (Exception e) {
            Log.e(TAG, "", e);

        }
        finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }
    /**
     * 根据学生id,和课程Id，去Score中查询是否有对应项，有返回true,无返回false
     */
    private boolean checkCourse(int student_id,int course_id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        String sql = "select * from score where student_id = ? and course_id = ?";
        cursor = db.rawQuery(sql,new String[]{String.valueOf(student_id),String.valueOf(course_id)});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }


    }
    /**
     * 将查询到的数据转换为CourseSec类对象
     * @param cursor
     * @return
     */
    private CourseSec parseCourseSec(Cursor cursor) {
        CourseSec course = new CourseSec();
        int id = cursor.getInt(cursor.getColumnIndex("course_id"));
        course.setCourse_id(cursor.getInt(cursor.getColumnIndex("course_id")));
        course.setCourse_name(cursor.getString(cursor.getColumnIndex("course_name")));
        course.setCredit(cursor.getInt(cursor.getColumnIndex("credit")));
        course.setGrade(cursor.getInt(cursor.getColumnIndex("grade")));
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
