package com.andack.mymoocproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.adapter.GirlsAdapter;
import com.andack.mymoocproject.entity.GirlEntity;
import com.andack.mymoocproject.util.L;
import com.andack.mymoocproject.util.PicassoUtils;
import com.andack.mymoocproject.view.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/1/15
 * 邮箱：    1160083806@qq.com
 * 描述：    管家助手的Fragment
 */

public class GirlsFragment extends Fragment {
    @Nullable
    private GridView gridView;
    private GirlEntity girlEntity;
    private List<GirlEntity>list=new ArrayList<>();
    private GirlsAdapter adapter;
    private CustomDialog customDialog;
    private ImageView showImage;
    private Button Reflash;
    private List<String>Urls=new ArrayList<>();
    //确定URL
    String Url;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.girls_layout,null);
        Url="http://gank.io/api/search/query/listview/category/"+changeFormat()+"/count/50/page/1";
        initData();
        initView(view);
       changeFormat();
        return view;
    }

    private String changeFormat() {
        String welfare = null;
        try {
            //Gank升級 需要转码
            welfare = URLEncoder.encode(getString(R.string.text_welfare), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return welfare;
    }

    private void initData() {
        //http://gank.io/api/search/query/listview/category/Android/count/10/page/1

        RxVolley.get(Url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.e(t);
                parsingJson(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONArray jsonArray=jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                girlEntity=new GirlEntity();
                JSONObject json= (JSONObject) jsonArray.get(i);
                String url=json.getString("url");
                Urls.add(url);
                girlEntity.setImageUrl(url);
                L.i("url="+url);
                list.add(girlEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initView(View view) {
        Reflash= (Button) view.findViewById(R.id.refreshBtn);
        gridView= (GridView) view.findViewById(R.id.gridView);
        customDialog=new CustomDialog(getActivity(),
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
                ,R.layout.show_girl_dialog,R.style.Theme_Dialog, Gravity.CENTER,R.style.pop_anim_style);
        showImage= (ImageView) customDialog.findViewById(R.id.girlShowIv);
        Reflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxVolley.get(Url, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.e(t);
                    }


                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                    }
                });
            }
        });
        adapter=new GirlsAdapter(getActivity(),list);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PicassoUtils.DefultLoadImg(getActivity(),Urls.get(position),showImage);


                PhotoViewAttacher  attacher=new PhotoViewAttacher(showImage);
                attacher.update();
                customDialog.show();

            }
        });
    }
}
