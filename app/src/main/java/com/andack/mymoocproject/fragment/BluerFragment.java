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
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
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
    private SpeechSynthesizer mTts;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bluer_layout,null);
        initSpeech();
        initView(view);
        return view;

    }

    private void initSpeech() {
        mTts= SpeechSynthesizer.createSynthesizer(getActivity(), null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
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
//        if (ShareUtil.GetBool(getActivity(),"speechselect",false)) {
//            L.i("speech_select is true");
            Speech(text);
//        }else {
//            L.i("speech_select is false");
//        }
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
    private void Speech(String text)
    {
        L.i("Speech 调用成功"+text);
        mTts.startSpeaking(text, mSynListener);
    }
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
            if (error==null) {
                L.i("onCompleted 接口执行");
            }else {
                L.i(error.toString());
            }
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            L.i("进度："+percent);
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };
    }
