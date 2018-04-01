package com.clutch.student.Adapter;

/**
 * Created by clutchyu on 2018/3/22.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.clutch.student.Entity.CourseSec;
import java.util.List;
import com.clutch.student.R;

/**
 *课程类适配器，将课程信息展示在CourseSecFragment上
 * 管理员查看所有课程信息
 */
public class CourseSecAdapter extends RecyclerView.Adapter<CourseSecAdapter.ViewHolder>{

    private List<CourseSec> mCourseSecList;


    static class ViewHolder extends RecyclerView.ViewHolder {
        View CourseSecView;
        ImageView CourseSecImage;
        TextView CourseSecName;

        public ViewHolder(View view) {
            super(view);
            CourseSecView = view;
            CourseSecImage = (ImageView) view.findViewById(R.id.Course_image);
            CourseSecName = (TextView) view.findViewById(R.id.Course_name);
        }
    }

    public CourseSecAdapter(List<CourseSec> CourseSecList) {
        mCourseSecList=CourseSecList;
    }
    public void changeAdapter(List<CourseSec> CourseSecList){
        mCourseSecList=CourseSecList;
    }

    @Override
    /**
     * 点击对应部分显示toast
     */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.CourseSecView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                CourseSec CourseSec = mCourseSecList.get(position);
            }
        });
        holder.CourseSecImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                CourseSec CourseSec = mCourseSecList.get(position);
            }
        });
        return holder;
    }

    @Override
    /**
     * 将课程信息显示在textView上
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
        CourseSec CourseSec = mCourseSecList.get(position);
        holder.CourseSecImage.setImageResource(CourseSec.getImageId());
        holder.CourseSecName.setText("学号： "+CourseSec.getCourse_id()+"         "+CourseSec.getCourse_name()+"\n学分："+CourseSec.getCredit()+"\n成绩："+CourseSec.getGrade());
    }

    @Override
    public int getItemCount() {
        return mCourseSecList.size();
    }

}