package com.andack.mymoocproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.util.ShareUtil;
import com.andack.mymoocproject.util.StaticClass;
import com.andack.mymoocproject.util.UtilTools;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/19
 * 邮箱：    1160083806@qq.com
 * 描述：    闪屏页面
 */

public class SplashActivity extends AppCompatActivity{
    /**
     * 1.延时2000ms
     * 2.判断程序是否第一次运行（全屏主题的编写）
     * 3.自定义字体
     * 4.Acitivity全屏主题
     *
     *
     */
    private TextView tv_splash;
    //用来延迟
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case StaticClass.HANDER_SPLASH:
                    //判断程序是否第一次运行
                    if(isFirst())
                    {

                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                        finish();

                    }else {
//                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
//                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                        finish();
                    }
                    break;

            }
        }
    };

    private boolean isFirst() {
        boolean isFirst=ShareUtil.GetBool(this,StaticClass.SHARE_IS_FIRST,true);
        if (isFirst) {
            ShareUtil.putBool(SplashActivity.this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        initView();
    }

    private void initView() {
        handler.sendEmptyMessageDelayed(StaticClass.HANDER_SPLASH,2000);
        tv_splash= (TextView) findViewById(R.id.tv_splash);
        UtilTools.setFont(this,tv_splash);
    }

    //禁止返回键

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

    }
}
