package com.andack.mymoocproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/9
 * 邮箱：    1160083806@qq.com
 * 描述：    事件分发LinearLayout
 */

public class DispatchLinayout extends LinearLayout {
    private DispatchKeyListener dispatchKeyListener;
    public DispatchLinayout(Context context) {
        super(context);
    }

    public DispatchLinayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DispatchKeyListener getDispatchKeyListener() {
        return dispatchKeyListener;
    }

    public void setDispatchKeyListener(DispatchKeyListener dispatchKeyListener) {
        this.dispatchKeyListener = dispatchKeyListener;
    }

    //定义一个接口用来处理按键事件
    public static interface  DispatchKeyListener{
        boolean dispatchKeyEvent(KeyEvent keyEvent);
    }


    //接口就是沟通两个类之间的桥梁

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //如果不为空说明调用了,去获取事件
        if (dispatchKeyListener!=null) {
            return dispatchKeyListener.dispatchKeyEvent(event);
        }

        return super.dispatchKeyEvent(event);
    }
}
