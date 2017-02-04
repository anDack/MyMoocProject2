package com.andack.mymoocproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.entity.ExpressEntity;

import java.util.List;
/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/4
 * 邮箱：    1160083806@qq.com
 * 描述：    快递时间轴的Adapter
 */

public class TimeLineAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExpressEntity>list;
    private LayoutInflater inflater;
    private ExpressEntity expressEntity;
    public TimeLineAdapter(Context context,List<ExpressEntity>list)
    {
        this.mContext=context;
        this.list=list;
        this.inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (viewHolder==null) {
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.express_item,null);
            viewHolder.remarkTv= (TextView) convertView.findViewById(R.id.remarkTv);
            viewHolder.zoneTv= (TextView) convertView.findViewById(R.id.zoneTv);
            viewHolder.dateTimeTv= (TextView) convertView.findViewById(R.id.datetimeTv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //设置Data
        expressEntity=list.get(position);
        viewHolder.remarkTv.setText(expressEntity.getRemark());
        viewHolder.zoneTv.setText(expressEntity.getZone());
        viewHolder.dateTimeTv.setText(expressEntity.getDatetime());
        return convertView;
    }
    class ViewHolder{
        private TextView remarkTv;
        private TextView zoneTv;
        private TextView dateTimeTv;
    }
}
