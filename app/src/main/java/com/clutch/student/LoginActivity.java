package com.clutch.student;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.clutch.student.Dao.MyDatabaseHelper;
import com.clutch.student.Dao.UserDao;

public class LoginActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private EditText account;
    private EditText password;
    private Button log;
    private Button sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new MyDatabaseHelper(this);
        dbHelper.getWritableDatabase();
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        log = findViewById(R.id.login);
        sign = findViewById(R.id.signin);
        final UserDao user = new UserDao(this);
        log.setOnClickListener(new View.OnClickListener() {         //点击登录，跳转到MainActivity,传入对应id
            @Override
            public void onClick(View view) {
                String id_str = account.getText().toString();
               // int id = Integer.parseInt(id_str);
                String passwd = password.getText().toString();
                if(!EditCheck.CheckInt(id_str,"账号",0,99999)){
                    showNormalDialog(EditCheck.getWarning());
                }
                else if(!EditCheck.CheckString(passwd,"密码",20)){
                    showNormalDialog(EditCheck.getWarning());
                }
                else if(user.check(Integer.parseInt(id_str),passwd)){              //账号匹配成功,进入MainActivity
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("id",Integer.parseInt(id_str));
                    startActivity(intent);
                    finish();
                }else{                              //弹出错误提示框
                    String word = "账号或密码错误！";
                    showNormalDialog(word);
                }

            }
        });
        sign.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                    Intent intent = new Intent(LoginActivity.this,ModifyPasswdActivity.class);
                    startActivity(intent);
            }
        });
    }

    private void showNormalDialog(String word){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(LoginActivity.this);
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
}

