package com.andack.mymoocproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.andack.mymoocproject.R;
import com.andack.mymoocproject.entity.ChatEntity;

import java.util.List;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/6
 * 邮箱：    1160083806@qq.com
 * 描述：    聊天的Adapter
 */

public class ChatAdapter extends BaseAdapter {
    public static final int VALUE_LEFT_TEXT=1;
    public static final int VALUE_RIGHT_TEXT=2;


    private Context mContext;
    private LayoutInflater inflater;
    private List<ChatEntity>mlist;
    private ChatEntity chatEntity;

    public ChatAdapter(Context mContext,List<ChatEntity>mlist)
    {
        this.mContext=mContext;
        this.mlist=mlist;
        inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeftViewHolder leftViewHolder=null;
        RightViewHolder rightViewHolder=null;
        if (convertView==null) {
            switch (getItemViewType(position)) {
                case VALUE_LEFT_TEXT:
                    leftViewHolder=new LeftViewHolder();
                    convertView=inflater.inflate(R.layout.left_item,null);
                    leftViewHolder.tv_left_text= (TextView) convertView.findViewById(R.id.leftChatTv);
                    convertView.setTag(leftViewHolder);
                    break;
                case VALUE_RIGHT_TEXT:
                    rightViewHolder=new RightViewHolder();
                    convertView=inflater.inflate(R.layout.right_item,null);
                    rightViewHolder.tv_right_text= (TextView) convertView.findViewById(R.id.rightChatTv);
                    convertView.setTag(rightViewHolder);
                    break;
            }
        }else {
            switch (getItemViewType(position)){
                case VALUE_LEFT_TEXT:
                    leftViewHolder= (LeftViewHolder) convertView.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    rightViewHolder= (RightViewHolder) convertView.getTag();
                    break;
            }
        }
        chatEntity=mlist.get(position);
        switch (getItemViewType(position)){
            case VALUE_LEFT_TEXT:
                leftViewHolder.tv_left_text.setText(chatEntity.getContent());
                break;
            case VALUE_RIGHT_TEXT:
                rightViewHolder.tv_right_text.setText(chatEntity.getContent());
                break;
        }
        return convertView;
    }

    //根据数据的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatEntity chatEntity=mlist.get(position);
        int Type=chatEntity.getType();
        return Type;
    }
    //返回所有layout数据
    @Override
    public int getViewTypeCount() {
        return 3;//mlist.size+1
    }
    //写两个ViewHolder
    //左边的ViewHolder
    class LeftViewHolder{
        private TextView tv_left_text;
    }
    class RightViewHolder{
        private TextView tv_right_text;
    }
}
