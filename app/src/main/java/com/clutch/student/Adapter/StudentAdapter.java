package com.clutch.student.Adapter;

/**
 * Created by clutchyu on 2018/3/19.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clutch.student.Entity.Student;
import com.clutch.student.Entity.Student_info;
import com.clutch.student.R;

import java.util.List;

/**
 * 学生信息类适配器，将查找到的信息展示在ThirdFragment上
 * Student_info类，属性为图片Id，和一个字符串代表student中的各个属性，即每获取一个student对象将其转化成5个该对象，添加到List中。
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{

    private List<Student_info> mStudentList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View StudentView;
        ImageView StudentImage;
        TextView StudentText;

        public ViewHolder(View view) {
            super(view);
            StudentView = view;
            StudentImage = (ImageView) view.findViewById(R.id.Student_image);
            StudentText = (TextView) view.findViewById(R.id.Student_text);
        }
    }

    public StudentAdapter(List<Student_info> StudentList) {
        mStudentList=StudentList;
    }

    @Override
    /**
     * 点击对应部分显示toast
     */
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.StudentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Student_info Student = mStudentList.get(position);
            }
        });
        holder.StudentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Student_info Student = mStudentList.get(position);
            }
        });
        return holder;
    }

    @Override
    /**
     * 将课程信息显示在textView上
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student_info Student = mStudentList.get(position);
        holder.StudentImage.setImageResource(Student.getImage());
        holder.StudentText.setText(Student.getInfo());
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

}
