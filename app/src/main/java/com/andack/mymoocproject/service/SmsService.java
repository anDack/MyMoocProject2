package com.andack.mymoocproject.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;

import com.andack.mymoocproject.util.L;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/8
 * 邮箱：    1160083806@qq.com
 * 描述：    短信接收服务
 */

public class SmsService extends Service {
    @Nullable
    private SmsReceiver mReceiver;
    private String originatingAddress;
    private String messageBody;
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
        L.i("服务运行");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mReceiver=null;
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
                }

        }
    }
}
