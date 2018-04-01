package com.clutch.student.Adapter;

/**
 * Created by clutchyu on 2018/3/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.clutch.student.Entity.Course;
import java.util.List;
import com.clutch.student.Dao.CourseDao;
import com.clutch.student.R;

/**
 *课程类适配器，将课程信息展示在CourseFragment上
 * 管理员查看所有课程信息
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{

    private List<Course> mCourseList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View CourseView;
        ImageView CourseImage;
        TextView CourseName;

        public ViewHolder(View view) {
            super(view);
            CourseView = view;
            CourseImage = (ImageView) view.findViewById(R.id.Course_image);
            CourseName = (TextView) view.findViewById(R.id.Course_name);
        }
    }

    public CourseAdapter(List<Course> courseList) {
        mCourseList=courseList;
    }

    @Override
    /**
     * 点击对应部分显示toast
     */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.CourseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Course course = mCourseList.get(position);
                Toast.makeText(v.getContext(), "you clicked view " + course.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.CourseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Course course = mCourseList.get(position);
                Toast.makeText(v.getContext(), "you clicked image " + course.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    /**
     * 将课程信息显示在textView上
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = mCourseList.get(position);
        holder.CourseImage.setImageResource(course.getImageId());
        holder.CourseName.setText("课程号："+course.getId()+"          "+course.getName()+"\n学分："+course.getCredit());
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

}