package com.andack.mymoocproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.entity.GirlEntity;
import com.andack.mymoocproject.util.PicassoUtils;

import java.util.List;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/7
 * 邮箱：    1160083806@qq.com
 * 描述：    gank妹子Adapter
 */

public class GirlsAdapter extends BaseAdapter {
    private Context mContext;
    private List<GirlEntity>girlEntityList;
    private GirlEntity girlEntity;
    private int Width;
    private LayoutInflater inflater;
    public GirlsAdapter(Context mContext,List<GirlEntity> list)
    {
        this.mContext=mContext;
        this.girlEntityList=list;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        Width = dm.widthPixels;    //得到宽度
    }
    @Override
    public int getCount() {
        return girlEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return girlEntityList.get(position);

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
            convertView=inflater.inflate(R.layout.meizi_item,null);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.GirlIv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        PicassoUtils.HaveSizeLoadImg(mContext,girlEntityList.get(position).getImageUrl(),viewHolder.imageView,Width/2,500);
        return convertView;

    }
    class ViewHolder{
        private ImageView imageView;
    }
}
