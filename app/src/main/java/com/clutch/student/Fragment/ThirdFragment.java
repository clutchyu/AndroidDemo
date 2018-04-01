package com.clutch.student.Fragment;

/**
 * Created by asus on 2018/3/17.
 * BottomNavigationView对应的第三个Fragment
 * 显示个人信息
 * 从修改信息的界面返回ThirdFragment时，状态为onResume,重写onResume方法实现刷新数据。
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.clutch.student.Adapter.StudentAdapter;
import com.clutch.student.Dao.StudentDao;
import com.clutch.student.Entity.Student_info;
import com.clutch.student.MainActivity;
import com.clutch.student.MyApplication;
import com.clutch.student.R;
import com.clutch.student.StudentChangeActivity;

import java.util.List;



public class ThirdFragment extends Fragment {
    Context context = MyApplication.getInstance();
    private StudentDao student = new StudentDao(context);
    private List<Student_info> StudentList = student.getStudent(MainActivity.getStudentId());
    private RecyclerView recyclerView;
    private int studentId = MainActivity.getStudentId();
    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        //initCourse();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        StudentAdapter adapter = new StudentAdapter(StudentList);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        Button button = (Button) view.findViewById (R.id.click);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(context, StudentChangeActivity.class);
                intent.putExtra("id",studentId);
                startActivity(intent);

            }

        });

        // Inflate the layout for this fragment
        return view;
    }
    public void onResume(){
        super.onResume();
        StudentList = student.getStudent(MainActivity.getStudentId());
        StudentAdapter adapter = new StudentAdapter(StudentList);
        recyclerView.setAdapter(adapter);

    }

}