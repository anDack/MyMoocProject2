package com.andack.mymoocproject.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andack.mymoocproject.R;
/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/3
 * 邮箱：    1160083806@qq.com
 * 描述：    快递查询页面
 */

public class ExpressFind extends BaseActivity implements View.OnClickListener{
    private EditText expressCoEt;
    private EditText expressNumEt;
    private Button expressFindBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.express_find_ui);
        initView();
    }

    private void initView() {
        expressCoEt= (EditText) findViewById(R.id.expressCoEt);
        expressNumEt= (EditText) findViewById(R.id.expressNumEt);
        expressFindBtn= (Button) findViewById(R.id.expressFindByCoBtn);
        expressFindBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.expressFindByCoBtn:
                break;
        }
    }
}
