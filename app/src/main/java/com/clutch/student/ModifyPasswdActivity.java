package com.clutch.student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.renderscript.Script;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.clutch.student.Dao.UserDao;
import com.clutch.student.Entity.User;

public class ModifyPasswdActivity extends AppCompatActivity {
    private EditText account;
    private EditText passwdOld;
    private EditText passwdNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_passwd);
         account = (EditText)findViewById(R.id.account_m);
         passwdOld = (EditText)findViewById(R.id.passwd_o);
        passwdNew = (EditText)findViewById(R.id.passwd_n);
        Button  commit = (Button)findViewById(R.id.commit_m);
        Button cancel = (Button)findViewById(R.id.cancel_m);
        commit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String word;
                if(!EditCheck.CheckInt(account.getText().toString(),"账号",10000,99999)){
                    showNormalDialog(EditCheck.getWarning());
                }
                else if(!EditCheck.CheckString(passwdOld.getText().toString(),"旧密码",20)) {
                    showNormalDialog(EditCheck.getWarning());
                }
                else if(!EditCheck.CheckString(passwdNew.getText().toString(),"新密码",20)){
                    showNormalDialog(EditCheck.getWarning());
                }
                else {
                    User user = new User(Integer.parseInt(account.getText().toString()),passwdOld.getText().toString());
                    String password = passwdNew.getText().toString();
                    if(writeDatabase(user,password)){
                        word = "修改密码成功！";
                        showNormalDialog(word);
                        //延时1s
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1000);
                    }
                    else{
                        word="修改失败！账号与原密码不匹配！请重试：";
                        showNormalDialog(word);
                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
    }
    private void showNormalDialog(String word){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(ModifyPasswdActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage(word);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        normalDialog.setCancelable(true);
                        //...To-do
                    }
                });

        // 显示
        normalDialog.show();
    }
    private boolean writeDatabase(User user,String passwd){
        UserDao userDao = new UserDao(MyApplication.getInstance());
        if(userDao.modifyPasswd(user,passwd))
            return true;
        else
            return false;
    }
}
