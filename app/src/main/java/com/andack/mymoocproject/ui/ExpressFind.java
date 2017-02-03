package com.andack.mymoocproject.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.util.L;
import com.andack.mymoocproject.util.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/3
 * 邮箱：    1160083806@qq.com
 * 描述：    快递查询页面
 * 步骤：
 *   1.获取输入框的内容
 *   2.判断是否为空
 *   3.请求数据
 *   4.得到Json
 *   5.ListView适配器
 *   6.实体类（item）
 *   7.设置数据/显示数据
 *
 */

public class ExpressFind extends BaseActivity implements View.OnClickListener{
    private EditText expressCoEt;
    private EditText expressNumEt;
    private Button expressFindBtn;
    private String url="http://v.juhe.cn/exp/index?key=";
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
                String ExpressNum=expressNumEt.getText().toString().trim();
                String ExpressCo=expressCoEt.getText().toString().trim();
                String getUrl=url+ StaticClass.EXPRESSFIND_KEY+"&com="+ExpressCo+"&no="+ExpressNum;
                if (!TextUtils.isEmpty(ExpressCo)&& !TextUtils.isEmpty(ExpressNum)) {
                    //请求数据
                    RxVolley.get(getUrl, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(ExpressFind.this, ""+t, Toast.LENGTH_SHORT).show();
                            L.i("JSON"+t);
                        }

                    });
                }else {
                    Toast.makeText(this, "公司简称或单号没填,厉害了大兄弟！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
