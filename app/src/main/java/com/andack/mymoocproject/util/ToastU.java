package com.andack.mymoocproject.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/22
 * 邮箱：    1160083806@qq.com
 * 描述：    toast工具类
 */

public class ToastU {
    public static void m(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
