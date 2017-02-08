package com.andack.mymoocproject.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.andack.mymoocproject.R;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_layout);
        initView();
    }

    private void initView() {
        speechSw= (Switch) findViewById(R.id.speechSw);
        L.i("speechSw is "+ShareUtil.GetBool(this,"speechselect",false));
        speechSw.setChecked(ShareUtil.GetBool(this,"speechselect",false));

        speechSw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speechSw:
                speechSw.setChecked(!speechSw.isSelected());
                ShareUtil.putBool(this,"speechselect",!speechSw.isSelected());

                break;
        }
    }
}
