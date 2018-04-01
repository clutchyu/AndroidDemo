package com.clutch.student.Fragment;

/**
 * Created by clutchyu on 2018/3/17.
 * BottomNavigationView对应的第一个Fragment
 * 显示学生已选课程信息，整个页面布局为一个RecyclerView
 */


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.clutch.student.Adapter.CourseSecAdapter;
import com.clutch.student.Dao.ScoreDao;
import com.clutch.student.Entity.CourseSec;
import com.clutch.student.MainActivity;
import com.clutch.student.MyApplication;
import com.clutch.student.R;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import java.util.List;


public class FirstFragment extends Fragment {
    static Context context = MyApplication.getInstance();
    private static ScoreDao course = new ScoreDao(context);
    private static RecyclerView recyclerView;
    private static TextView emptyText;
    private static List<CourseSec> CourseList;
    public FirstFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        // initCourse();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        emptyText = (TextView) view.findViewById(R.id.empty_text);      //用于在无数据时进行提示
        CourseList = course.getScore(MainActivity.getStudentId());      //获取已选课程的List
        if(CourseList==null){
            //若数据为空，则显示emptyText,隐藏recyclerView
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);       //线性布局
            recyclerView.setLayoutManager(layoutManager);                               //RecyclerView加载线性布局
            CourseSecAdapter adapter = new CourseSecAdapter(CourseList);                //创建课程类适配器，参数为刚查询到的存有课程信息的CourseList
            recyclerView.setAdapter(adapter);                                           //将适配器加载入recyclerView中
            //为RecyclerView中的每一项加分割线
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
        }
        // Inflate the layout for this fragment
        return view;
    }
    /**
     * 刷新数据，点击对应课程在Adapter中调用该函数实现页面刷新
     * 内容为重新查询数据库中的数据放入recyclerView
     */
    public static void update() {
        CourseList = course.getScore(MainActivity.getStudentId());
        if(CourseList==null){
            emptyText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            CourseSecAdapter adapter = new CourseSecAdapter(CourseList);
            recyclerView.setAdapter(adapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);
        }
    }





}
