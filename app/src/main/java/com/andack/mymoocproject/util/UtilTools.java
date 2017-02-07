package com.andack.mymoocproject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

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
    public static void putImg2Share(Context context, CircleImageView circleImageView)
    {
        BitmapDrawable drawable= (BitmapDrawable) circleImageView.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
        //将bitmap压缩成字节输出流
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byteArrayOutputStream);
        //利用Based64将我们的字节数组输出流转换为String
        byte[] bytes= byteArrayOutputStream.toByteArray();
        String imgStr=new String(Base64.encodeToString(bytes,Base64.DEFAULT));
        //第三步：将String保存为ShareP
        ShareUtil.putString(context,"image_title",imgStr);
    }
    public static void getImg4Share(Context context,CircleImageView circleImageView)
    {
        String content=ShareUtil.GetString(context,"image_title","");
        if (!content.equals("")) {
            //利用Base64转化为图像
            byte[] bytes=Base64.decode(content,Base64.DEFAULT);
            ByteArrayInputStream byInputStream=new ByteArrayInputStream(bytes);
            Bitmap bitmap= BitmapFactory.decodeStream(byInputStream);
            circleImageView.setImageBitmap(bitmap);
        }

    }


}
