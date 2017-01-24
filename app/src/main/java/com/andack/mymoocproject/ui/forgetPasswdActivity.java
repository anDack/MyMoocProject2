package com.andack.mymoocproject.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.entity.UserMode;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/22
 * 邮箱：    1160083806@qq.com
 * 描述：    忘记密码界面重置密码
 */

public class forgetPasswdActivity extends BaseActivity implements View.OnClickListener{
    private EditText passwdBeforeEt;
    private EditText passwdAfterEt;
    private EditText passwdAfterSureEt;
    private Button changePsswdBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_layout);
        initView();
    }

    private void initView() {
        passwdBeforeEt= (EditText) findViewById(R.id.passwdBefore);
        passwdAfterEt= (EditText) findViewById(R.id.passwdAfter);
        passwdAfterSureEt= (EditText) findViewById(R.id.passwdAfterSure);
        changePsswdBtn= (Button) findViewById(R.id.resetPasswd);
        changePsswdBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetPasswd:
                String passwdBeforeStr=passwdBeforeEt.getText().toString().trim();
                String passwdAfterStr=passwdAfterEt.getText().toString().trim();
                String passwdAfterSureStr=passwdAfterSureEt.getText().toString().trim();
                if (!TextUtils.isEmpty(passwdBeforeStr)
                        &&!TextUtils.isEmpty(passwdAfterStr)
                        &&! TextUtils.isEmpty(passwdAfterSureStr)) {
                    if (passwdAfterStr.equals(passwdAfterSureStr)){
                        UserMode.updateCurrentUserPassword(passwdBeforeStr, passwdAfterStr, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null)
                                {
                                    Toast.makeText(forgetPasswdActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(forgetPasswdActivity.this, "更换密码失败"+e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(this, "新密码与确认密码错误", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "请完整填写信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
