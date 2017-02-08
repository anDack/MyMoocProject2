package com.andack.mymoocproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.service.SmsService;
import com.andack.mymoocproject.util.L;
import com.andack.mymoocproject.util.ShareUtil;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/19
 * 邮箱：    1160083806@qq.com
 * 描述：    设置页面
 */

public class Setting extends BaseActivity implements View.OnClickListener {
    private Switch speechSw;
    private Switch smsSw;
    private Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_layout);
        initView();
    }

    private void initView() {
        speechSw= (Switch) findViewById(R.id.speechSw);
        smsSw= (Switch) findViewById(R.id.smsLiSw);
        L.i("speechSw is "+ShareUtil.GetBool(this,"speechselect",false));
        speechSw.setChecked(ShareUtil.GetBool(this,"speechselect",false));
        smsSw.setChecked(ShareUtil.GetBool(this,"smsselect",false));
        speechSw.setOnClickListener(this);
        smsSw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speechSw:
                speechSw.setSelected(!speechSw.isSelected());

                ShareUtil.putBool(this,"speechselect",speechSw.isSelected());
                break;
            case R.id.smsLiSw:
                smsSw.setSelected(!smsSw.isSelected());
                ShareUtil.putBool(this,"smsselect",smsSw.isSelected());
                if (smsSw.isSelected()) {
                    L.i("开启服务");
                    intent=new Intent(this, SmsService.class);
                    startService(intent);
                }else {
                    L.i("关闭服务");
                    stopService(intent);
                }
                break;
        }
    }
}
