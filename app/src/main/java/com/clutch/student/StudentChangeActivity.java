package com.clutch.student;
/**
 * 修改个人信息界面
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.clutch.student.Adapter.*;
import com.clutch.student.Dao.StudentDao;
import com.clutch.student.Entity.Student;


public class StudentChangeActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText name;
    private RadioButton mMaleRb;
    private RadioButton mFamaleRb;
    private EditText age;
    private EditText phone;
    private  int studentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_change);
        Intent intent = getIntent();

        studentId = intent.getIntExtra("id",-1);
        Button save = (Button)findViewById(R.id.save);
        Button cancel = (Button)findViewById(R.id.cancel);
        name = (EditText)findViewById(R.id.name);
        //sex = (EditText)findViewById(R.id.sex);
        mMaleRb = (RadioButton)findViewById(R.id.male_rb);
        mFamaleRb = (RadioButton)findViewById(R.id.famale_rb);
        age = (EditText)findViewById(R.id.age);
        phone = (EditText)findViewById(R.id.phone);
        save.setOnClickListener(this);
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }

        });
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.save:
                String sname = name.getText().toString();
                String ssex ="";
                if (mMaleRb.isChecked()){
                    ssex="男";
                }
                else if (mFamaleRb.isChecked()){
                    ssex="女";
                }
                String sage = age.getText().toString();
                String sphone = phone.getText().toString();
                if(!EditCheck.CheckString(sname,"名字",6)){
                    Toast.makeText(v.getContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                    break;
                }
                else if(!mMaleRb.isChecked()&&!mFamaleRb.isChecked()){
                    Toast.makeText(v.getContext(),"性别不能为空！",Toast.LENGTH_SHORT).show();
                    break;
                }
                else if(!EditCheck.CheckInt(sage,"年龄",10,60)){
                    Toast.makeText(v.getContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                    break;
                }
                else if(!EditCheck.CheckString(sphone,"电话",15)){
                    Toast.makeText(v.getContext(),EditCheck.getWarning(),Toast.LENGTH_SHORT).show();
                    break;
                }
                Student student = new Student(studentId,sname,ssex,Integer.parseInt(sage),sphone,R.drawable.health);
                writeDatabase(student);
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 写入数据库
     */
    private void writeDatabase(Student student){
        Context context = MyApplication.getInstance();
        StudentDao stuDao = new StudentDao(context);
        stuDao.updateStudent(student);

    }
}
