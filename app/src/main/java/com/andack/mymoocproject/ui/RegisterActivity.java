package com.andack.mymoocproject.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.andack.mymoocproject.R;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/22
 * 邮箱：    1160083806@qq.com
 * 描述：    注册界面
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText userNameEd;
    private EditText passWdEd;
    private EditText passEd;
    private EditText emailEd;
    private EditText descEd;
    private Button registerSureBtn;
    private RadioGroup sexRG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlayout);
        initView();
    }

    private void initView() {
        userNameEd= (EditText) findViewById(R.id.userNameEt);
        passWdEd= (EditText) findViewById(R.id.passwdEt);
        passEd= (EditText) findViewById(R.id.passEt);
        emailEd= (EditText) findViewById(R.id.emallEt);
        descEd= (EditText) findViewById(R.id.descEt);
        sexRG= (RadioGroup) findViewById(R.id.sexRg);
        registerSureBtn= (Button) findViewById(R.id.registerSureBtn);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.registerSureBtn:
                sexRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.boyR:
                                break;
                            case R.id.girlR:
                                break;
                        }
                    }
                });
                break;
        }
    }
}
