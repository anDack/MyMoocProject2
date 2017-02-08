package com.andack.mymoocproject.application;

import android.app.Application;

import com.andack.mymoocproject.util.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/16
 * 邮箱：    1160083806@qq.com
 * 描述：    Application
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //对bugly进行初始化
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_ID,true);
        //对bmob进行初始化
        Bmob.initialize(this,StaticClass.BMOB_ID);
        //对科大讯飞的语言合成进行集成
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"="+StaticClass.SPEECH_KEY);
    }
}
