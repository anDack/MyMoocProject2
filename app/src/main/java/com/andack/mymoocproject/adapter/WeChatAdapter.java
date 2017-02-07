package com.andack.mymoocproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.entity.WeChatEntity;
import com.andack.mymoocproject.util.PicassoUtils;

import java.util.List;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/7
 * 邮箱：    1160083806@qq.com
 * 描述：    微信精选的Adapter
 */

public class WeChatAdapter extends BaseAdapter {
    private Context mContext;
    private List<WeChatEntity>list;
    private LayoutInflater inflater;
    private int Width;
    public WeChatAdapter(Context context,List<WeChatEntity> list)
    {
        this.mContext=context;
        this.list=list;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Width = wm.getDefaultDisplay().getWidth();//屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
         Width = dm.widthPixels;    //得到宽度
//        int height = dm.heightPixels;  //得到高度

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null) {
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.wechat_item,null);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.weChatImage);

            viewHolder.titleTv= (TextView) convertView.findViewById(R.id.titleTv);
            viewHolder.sourceTv= (TextView) convertView.findViewById(R.id.sourceTv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.sourceTv.setText(list.get(position).getSource());
        viewHolder.titleTv.setText(list.get(position).getTitle());
        PicassoUtils.HaveSizeLoadImg(mContext,list.get(position).getImagrUrl(),viewHolder.imageView,Width/3,200);
        return convertView;
    }
    class ViewHolder{
        private TextView titleTv;
        private TextView sourceTv;
        private ImageView imageView;
    }
}
