package com.andack.mymoocproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.adapter.WeChatAdapter;
import com.andack.mymoocproject.entity.WeChatEntity;
import com.andack.mymoocproject.ui.WeChatActivity;
import com.andack.mymoocproject.util.L;
import com.andack.mymoocproject.util.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/15
 * 邮箱：    1160083806@qq.com
 * 描述：    管家助手的Fragment
 */

public class WechatFragment extends Fragment {
    @Nullable
    private ListView mListView;
    private WeChatAdapter adapter;
    private List<WeChatEntity> weChatEntityList;
    private WeChatEntity weChatEntity;
    private ArrayList<String> mTitle;
    private ArrayList<String> mUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.wechat_layout,null);
        mTitle=new ArrayList<>();
        mUrl=new ArrayList<>();
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        //http://v.juhe.cn/weixin/query?key=您申请的KEY
        String Url="http://v.juhe.cn/weixin/query?key="+ StaticClass.WECHAT_KEY;
        weChatEntityList=new ArrayList<>();

        RxVolley.get(Url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
                L.i(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                L.i("v="+errorNo+"\n strMsg="+strMsg);
                super.onFailure(errorNo, strMsg);
            }
        });

    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonRes=jsonObject.getJSONObject("result");
            JSONArray jsonArray=jsonRes.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                weChatEntity=new WeChatEntity();
                JSONObject json= (JSONObject) jsonArray.get(i);
                String title=json.getString("title");
                String source=json.getString("source");
                String imageUrl=json.getString("firstImg");
                String Url=json.getString("url");
                mUrl.add(Url);
                mTitle.add(title);
                L.i(title);
                weChatEntity.setImagrUrl(imageUrl);
                weChatEntity.setSource(source);
                weChatEntity.setTitle(title);
                weChatEntityList.add(weChatEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initView(View view) {
        mListView= (ListView) view.findViewById(R.id.weChatListView);

        adapter=new WeChatAdapter(getActivity(),weChatEntityList);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), WeChatActivity.class);
                intent.putExtra("title",mTitle.get(position));
                intent.putExtra("url",mUrl.get(position));
                startActivity(intent);
            }
        });
    }


}
