package com.andack.mymoocproject.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.andack.mymoocproject.R;
/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/24
 * 邮箱：    1160083806@qq.com
 * 描述：    自定义一个Dialog
 */

public class CustomDialog extends Dialog {
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity,int anim)
    {
        super(context,style);
        //设置属性
        setContentView(layout);
        Window window=getWindow();
        WindowManager.LayoutParams layoutParams= window.getAttributes();
        layoutParams.width=width;
        layoutParams.height=height;
        layoutParams.gravity=gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity)
    {
        this(context,width,height,layout,style,gravity,R.style.pop_anim_style);
    }
    public CustomDialog(Context context,int layout,int style) {
        this(context,WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT
        ,layout,style, Gravity.CENTER);
    }
}
