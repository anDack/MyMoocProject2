package com.andack.mymoocproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.andack.mymoocproject.R;
import com.andack.mymoocproject.adapter.ChatAdapter;
import com.andack.mymoocproject.entity.ChatEntity;
import com.andack.mymoocproject.util.L;
import com.andack.mymoocproject.util.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

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

public class BluerFragment extends Fragment implements View.OnClickListener {
    @Nullable
//    private Button leftBtn;
//    private Button rightBtn;
    private Button sendBtn;
    private EditText inputEt;
    private ListView ChatLV;
    private List<ChatEntity> list= new ArrayList<>();
    private ChatAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bluer_layout,null);
        initView(view);
        return view;

    }

    private void initView(View view) {
        sendBtn= (Button) view.findViewById(R.id.sendBtn);
//        leftBtn= (Button) view.findViewById(R.id.leftChatBtn);
//        rightBtn= (Button) view.findViewById(R.id.rightChatBtn);
        ChatLV= (ListView) view.findViewById(R.id.chatListView);
        inputEt= (EditText) view.findViewById(R.id.input_chatEt);
        sendBtn.setOnClickListener(this);
//        leftBtn.setOnClickListener(this);
//        rightBtn.setOnClickListener(this);

        adapter=new ChatAdapter(getActivity(),list);
        ChatLV.setAdapter(adapter);
        AddLeftItem("你好，我是机器人");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.leftChatBtn:
//                AddLeftItem("你好");
//                break;
//            case R.id.rightChatBtn:
//                AddRightItem("不好");
//                break;
            case R.id.sendBtn:
                String content=inputEt.getText().toString().trim();

                if (!TextUtils.isEmpty(content)&&content.length()<=30) {
                    inputEt.setText("");
                    AddRightItem(content);
                    String Url="http://op.juhe.cn/robot/index?info="+content+"&key="+ StaticClass.TULINROBOT_KEY;
                    RxVolley.get(Url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            parsingJson(t);
                            L.i(t);
                        }

                        @Override
                        public void onFailure(int errorNo, String strMsg) {
                            Toast.makeText(getActivity(), "错误码："+errorNo+" 错误信息："+strMsg, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "把你想说的说出来!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONObject jsonRes=jsonObject.getJSONObject("result");
            String text=jsonRes.getString("text");
            AddLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void AddLeftItem(String text)
    {
        ChatEntity chatEntity=new ChatEntity();
        chatEntity.setType(ChatAdapter.VALUE_LEFT_TEXT);
        chatEntity.setContent(text);
        list.add(chatEntity);
        //通知Adapter变化
        adapter.notifyDataSetChanged();
        //将listView滚动到最后一行
        ChatLV.setSelection(ChatLV.getBottom());
    }
    private void AddRightItem(String text)
    {
        ChatEntity chatEntity=new ChatEntity();
        chatEntity.setType(ChatAdapter.VALUE_RIGHT_TEXT);
        chatEntity.setContent(text);
        list.add(chatEntity);
        //通知Adapter变化
        adapter.notifyDataSetChanged();
        //将listView滚动到最后一行
        ChatLV.setSelection(ChatLV.getBottom());
    }
}
