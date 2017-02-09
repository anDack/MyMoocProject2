package com.andack.mymoocproject.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.util.L;
import com.andack.mymoocproject.view.DispatchLinayout;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/8
 * 邮箱：    1160083806@qq.com
 * 描述：    短信接收服务
 */

public class SmsService extends Service implements View.OnClickListener {
    @Nullable
    public static final String SYSTEM_DIALOG_RESON_KEY="reason";
    public static final String SYSTEM_HOME_KEY="homekey";
    private SmsReceiver mReceiver;
    private String originatingAddress;
    private String messageBody;
    private HomeKeyReceiver homeKeyReceiver;
    private WindowManager wm;
    private DispatchLinayout view;
    private WindowManager.LayoutParams layoutParams;
    private TextView originatingAddressTv;
    private TextView contentTv;
    private Button sendSmsBtn;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 服务创建函数
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mReceiver=new SmsReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(Integer.MAX_VALUE);
        registerReceiver(mReceiver,filter);
        homeKeyReceiver=new HomeKeyReceiver();
        IntentFilter filter1=new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(homeKeyReceiver,filter1);
        L.i("服务运行");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mReceiver=null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendSmsBtn:
                sendSms();
                if (view.getParent()!=null) {
                    wm.removeView(view);
                }
                break;
        }
    }

    private void sendSms() {
        Uri uri=Uri.parse("smsto:"+originatingAddress);
        Intent intent=new Intent(Intent.ACTION_SENDTO,uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_baby","");
        startActivity(intent);
    }

    public class SmsReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {


                L.i("监听到短信");
                Object[] objects = (Object[]) intent.getExtras().get("pdus");
                for (Object object : objects) {
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
                    originatingAddress = sms.getOriginatingAddress();
                    messageBody = sms.getMessageBody();
                    L.i("发送人：" + originatingAddress + "信息内容:" + messageBody);
                    showSmsWindows();
                }

        }
    }

    private void showSmsWindows() {
        //弹出一个Window
        //初始化一个WindowManager
        wm= (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //初始化LayoutParams
        layoutParams=new WindowManager.LayoutParams();
        //定义宽高
        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height=WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutParams.flags=//WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | 不能触摸
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | 沒有焦点
        //定义格式
        layoutParams.format= PixelFormat.TRANSLUCENT;
        //定义类型
        layoutParams.type=WindowManager.LayoutParams.TYPE_PHONE;
        view= (DispatchLinayout) View.inflate(getApplicationContext(),R.layout.smsshow_layout,null);
        view.setDispatchKeyListener(mlistener);
        initView(view);
        wm.addView(view,layoutParams);


    }
    private DispatchLinayout.DispatchKeyListener mlistener=new DispatchLinayout.DispatchKeyListener() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode()==keyEvent.KEYCODE_BACK) {
                if (view!=null)
                {
                    wm.removeView(view);
                }
                return true;
            }
            return false;
        }
    };

    private void initView(View view) {
        originatingAddressTv= (TextView) view.findViewById(R.id.originatingAddressTv);
        contentTv= (TextView) view.findViewById(R.id.smsContentTv);
        sendSmsBtn= (Button) view.findViewById(R.id.sendSmsBtn);
        originatingAddressTv.setText(originatingAddress);
        contentTv.setText(messageBody);
        sendSmsBtn.setOnClickListener(this);
    }

    private class HomeKeyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
            {
                String reason=intent.getStringExtra(SYSTEM_DIALOG_RESON_KEY);
                if (SYSTEM_HOME_KEY.equals(reason))
                {
                    if (view.getParent()!=null)
                    {
                        wm.removeView(view);
                    }
                }


            }
        }
    }
}
