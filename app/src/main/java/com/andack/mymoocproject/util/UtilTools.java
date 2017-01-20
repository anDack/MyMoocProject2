package com.andack.mymoocproject.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/19
 * 邮箱：    1160083806@qq.com
 * 描述：    常用的工具类
 */

public class UtilTools {
    //设置字体
    public static void setFont(Context context, TextView textView){

        Typeface fontType=Typeface.createFromAsset(context.getAssets(),"fonts/YYG.TTF");
        textView.setTypeface(fontType);

    }
}
