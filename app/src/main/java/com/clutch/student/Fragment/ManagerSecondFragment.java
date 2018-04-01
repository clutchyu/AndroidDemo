package com.clutch.student.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clutch.student.AddCourseActivity;
import com.clutch.student.Dao.ScoreDao;
import com.clutch.student.EditCheck;
import com.clutch.student.Entity.Score;
import com.clutch.student.MainActivity;
import com.clutch.student.MyApplication;
import com.clutch.student.R;

/**
 * Created by clutchyu on 2018/3/27.
 * 以管理员身份登入系统的第二个界面，修改学生选课成绩
 */

public class ManagerSecondFragment extends Fragment {
    static Context context = MyApplication.getInstance();
    private  Score score = new Score();
    private EditText studentText;
    private EditText courseText;
    private EditText creditText;
    public void ManagerSecondFragment(){}
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_second, container, false);
        studentText = (EditText)view.findViewById(R.id.editText);
        courseText = (EditText)view.findViewById(R.id.editText2);
        creditText = (EditText)view.findViewById(R.id.editText3);
        Button commit = (Button)view.findViewById(R.id.commit);
        Button cancel = (Button)view.findViewById(R.id.cancel_s);
        commit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(!EditCheck.CheckInt(studentText.getText().toString(),"学生学号",10000,99999)){
                    Toast.makeText(v.getContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else if(!EditCheck.CheckInt(courseText.getText().toString(),"课程号",10000,99999)){
                    Toast.makeText(v.getContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else if(!EditCheck.CheckInt(creditText.getText().toString(),"成绩",0,100)){
                    Toast.makeText(v.getContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                }
                else if(writeDatabase()){
                    String word = "录入成功！";
                    studentText.setText("");
                    courseText.setText("");
                    creditText.setText("");
                    Toast.makeText(v.getContext(),word, Toast.LENGTH_SHORT).show();
                }else{
                    String word = "录入失败\n不存在此学生或此学生未选课";
                    Toast.makeText(v.getContext(),word, Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //清空输入框
                studentText.setText("");
                courseText.setText("");
                creditText.setText("");

            }
        });


        return view;
    }

    public boolean writeDatabase(){
        ScoreDao scoreDao = new ScoreDao(MyApplication.getInstance());
        int studentId = Integer.parseInt(studentText.getText().toString());
        int courseId = Integer.parseInt(courseText.getText().toString());
        int grade = Integer.parseInt(creditText.getText().toString());
        score.setStudentId(studentId);
        score.setCourseId(courseId);
        score.setGrade(grade);
        boolean flag = scoreDao.writeScore(score);
        if(flag){
            return true;
        }
        else {
            return false;
        }
    }
}
