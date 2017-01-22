package com.andack.mymoocproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.entity.UserMode;
import com.andack.mymoocproject.util.ShareUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/22
 * 邮箱：    1160083806@qq.com
 * 描述：    登录界面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText passwdEt_Login;
    private EditText accentEt_Login;
    private Button loginBtn;
    private Button registerBtn;
    private CheckBox rembPasswd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();
        boolean isRember=ShareUtil.GetBool(this,"rember",false);
        rembPasswd.setChecked(isRember);
        if (isRember) {
            accentEt_Login.setText(ShareUtil.GetString(this,"name",""));
            passwdEt_Login.setText(ShareUtil.GetString(this,"passwd",""));
        }
    }

    private void initView() {
        passwdEt_Login= (EditText) findViewById(R.id.passwdEt_login);
        accentEt_Login= (EditText) findViewById(R.id.userNameEt_login);
        loginBtn= (Button) findViewById(R.id.loginBtn);
        registerBtn= (Button) findViewById(R.id.registerBtn);
        rembPasswd= (CheckBox) findViewById(R.id.rembPasswd);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                String name=accentEt_Login.getText().toString().trim();
                String passwd=passwdEt_Login.getText().toString().trim();
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(passwd)) {
                    final BmobUser bmob=new BmobUser();
                    bmob.setUsername(name);
                    bmob.setPassword(passwd);
                    bmob.login(new SaveListener<UserMode>() {
                        @Override
                        public void done(UserMode userMode, BmobException e) {
                            if (e==null)
                            {
                                if (bmob.getEmailVerified()) {
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "这个邮箱没有验证，无法登录！", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "登录错误"+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, "账号，或者密码空缺！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.registerBtn:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareUtil.putBool(this,"rember",rembPasswd.isChecked());
        if (rembPasswd.isChecked()) {
            ShareUtil.putString(this,"name",accentEt_Login.getText().toString().trim());
            ShareUtil.putString(this,"passwd",passwdEt_Login.getText().toString().trim());
        }
    }
}
