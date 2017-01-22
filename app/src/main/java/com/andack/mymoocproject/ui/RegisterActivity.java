package com.andack.mymoocproject.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.entity.UserMode;
import com.andack.mymoocproject.util.ToastU;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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
    private EditText ageEd;
    private EditText emailEd;
    private EditText descEd;
    private Button registerSureBtn;
    private RadioGroup sexRG;
    private boolean isGender=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlayout);
        initView();
    }

    private void initView() {
        ageEd= (EditText) findViewById(R.id.ageEt);
        userNameEd= (EditText) findViewById(R.id.userNameEt);
        passWdEd= (EditText) findViewById(R.id.passwdEt);
        passEd= (EditText) findViewById(R.id.passEt);
        emailEd= (EditText) findViewById(R.id.emallEt);
        descEd= (EditText) findViewById(R.id.descEt);
        sexRG= (RadioGroup) findViewById(R.id.sexRg);
        registerSureBtn= (Button) findViewById(R.id.registerSureBtn);
        registerSureBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.registerSureBtn:
                String age=ageEd.getText().toString().trim();
                String name=userNameEd.getText().toString().trim();
                String passwd=passWdEd.getText().toString().trim();
                final String email=emailEd.getText().toString().trim();
                String pass=passEd.getText().toString().trim();
                String dec=descEd.getText().toString().trim();
                //判断是否为空
                if ((!TextUtils.isEmpty(name)) &&
                        (!TextUtils.isEmpty(passwd))&&
                        (!TextUtils.isEmpty(pass))&&
                        (!TextUtils.isEmpty(email))) {
                    //判断确认密码和密码是否相同
                    if (passwd.equals(pass)) {
                        //先把性别判断一下
                        sexRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                switch (checkedId) {
                                    case R.id.boyR:
                                        isGender=true;
                                        break;
                                    case R.id.girlR:
                                        isGender=false;
                                        break;
                                }
                            }
                        });
                        //判断简介是否为空
                        if (TextUtils.isEmpty(dec)) {
                            dec="这个人很懒，什么都没留下！";
                        }
                        //注册
                        UserMode userMode=new UserMode();
                        userMode.setUsername(name);
                        userMode.setPassword(passwd);
                        userMode.setDesc(dec);
                        userMode.setEmail(email);
                        userMode.setAge(Integer.parseInt(age));

                        userMode.setSex(isGender);

                        userMode.signUp(new SaveListener<UserMode>() {

                            @Override
                            public void done(UserMode userMode, BmobException e) {
                                if (e==null)
                                {
                                    ToastU.m(RegisterActivity.this,"注册成功!");
                                    userMode.requestEmailVerify(email, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e==null)
                                            {
                                                Toast.makeText(RegisterActivity.this, "验证信息已经发送到邮箱，请确认", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(RegisterActivity.this, "发送邮件错误请重置邮箱"+e.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    finish();
                                }else {
                                    ToastU.m(RegisterActivity.this,"注册失败："+e.toString());
                                }
                            }
                        });

                    }else {
                        ToastU.m(this,"两次输入的密码不相同");
                    }

                }else {
                    ToastU.m(this,"缺失相关信息无法注册");
                }


                break;
        }
    }
}
