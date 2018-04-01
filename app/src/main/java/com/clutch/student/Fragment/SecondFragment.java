package com.clutch.student.Fragment;

/**
 * Created by clutch on 2018/3/17.
 * BottomNavigationView对应的第二个Fragment
 * 用于学生选课，学生点击对应课程即可提示选课成功
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clutch.student.Adapter.ScoreAdapter;
import com.clutch.student.Dao.ScoreDao;
import com.clutch.student.Entity.Course;
import com.clutch.student.MainActivity;
import com.clutch.student.MyApplication;
import com.clutch.student.R;
import java.util.List;




public class SecondFragment extends Fragment {
    static Context context = MyApplication.getInstance();
    private static ScoreDao  uncourse = new ScoreDao(context);   //未选的课程
    private static RecyclerView recyclerView_undo;
    private static List<Course> undoList;
    private static ScoreAdapter adapter;
    private static TextView emptyText;
    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        recyclerView_undo = (RecyclerView) view.findViewById(R.id.recycler_undo);
        emptyText = (TextView) view.findViewById(R.id.empty_text) ;
        undoList = uncourse.getUnCourse(MainActivity.getStudentId());
        if(undoList==null){
            emptyText.setVisibility(View.VISIBLE);
            recyclerView_undo.setVisibility(View.GONE);
        }else {
            //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView_undo.setLayoutManager(layoutManager);
            ScoreAdapter adapter = new ScoreAdapter(undoList);
            recyclerView_undo.setAdapter(adapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView_undo.getContext(),layoutManager.getOrientation());
            recyclerView_undo.addItemDecoration(mDividerItemDecoration);
        }
        return view;
    }

    /**
     * 刷新数据，点击对应课程在Adapter中调用该函数实现页面刷新
     */
    public static void updata() {
        undoList = uncourse.getUnCourse(MainActivity.getStudentId());
        if(undoList==null){
            emptyText.setVisibility(View.VISIBLE);
            recyclerView_undo.setVisibility(View.GONE);
        }else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView_undo.setLayoutManager(layoutManager);
            ScoreAdapter adapter = new ScoreAdapter(undoList);
            recyclerView_undo.setAdapter(adapter);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView_undo.getContext(),layoutManager.getOrientation());
            recyclerView_undo.addItemDecoration(mDividerItemDecoration);

        }
    }
}
