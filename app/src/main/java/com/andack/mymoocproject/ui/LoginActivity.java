package com.andack.mymoocproject.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.andack.mymoocproject.R;
/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/22
 * 邮箱：    1160083806@qq.com
 * 描述：    登录界面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button loginBtn;
    private Button registerBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();
    }

    private void initView() {
        loginBtn= (Button) findViewById(R.id.loginBtn);
        registerBtn= (Button) findViewById(R.id.registerBtn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                break;
            case R.id.registerBtn:

                break;
        }
    }
}
