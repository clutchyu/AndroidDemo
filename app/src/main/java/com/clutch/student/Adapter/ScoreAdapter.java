package com.clutch.student.Adapter;

/**
 * Created by clutchyu on 2018/3/26.
 * 显示学生未选课的信息，显示到SecondFragment中
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clutch.student.Dao.ScoreDao;
import com.clutch.student.Entity.Course;
import java.util.List;

import com.clutch.student.Fragment.FirstFragment;
import com.clutch.student.Fragment.SecondFragment;
import com.clutch.student.MainActivity;
import com.clutch.student.MyApplication;
import com.clutch.student.R;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder>{
    Context context = MyApplication.getInstance();

    private List<Course> mCourseList;
    private ScoreDao score = new ScoreDao(context);
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
    public ScoreAdapter(List<Course> courseList) {
        mCourseList=courseList;
    }
    public ScoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        final ScoreAdapter.ViewHolder holder = new ScoreAdapter.ViewHolder(view);
        holder.CourseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Course course = mCourseList.get(position);
                score.chooseCourse(MainActivity.getStudentId(),course.getId());
                Toast.makeText(v.getContext(), "选课成功！" , Toast.LENGTH_SHORT).show();
                SecondFragment.updata();
                FirstFragment.update();

            }
        });
        holder.CourseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Course course = mCourseList.get(position);
                score.chooseCourse(MainActivity.getStudentId(),course.getId());
                Toast.makeText(v.getContext(), "选课成功！ " , Toast.LENGTH_SHORT).show();
                SecondFragment.updata();
                FirstFragment.update();

            }
        });
        return holder;
    }


    /**
     * 将课程信息显示在textView上
     */
    public void onBindViewHolder(ScoreAdapter.ViewHolder holder, int position) {
        Course course = mCourseList.get(position);
        holder.CourseImage.setImageResource(course.getImageId());
        holder.CourseName.setText("课程号："+course.getId()+"          "+course.getName()+"\n学分："+course.getCredit());
    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }


}
