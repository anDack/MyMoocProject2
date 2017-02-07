package com.andack.mymoocproject.ui;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.andack.mymoocproject.R;
/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/7
 * 邮箱：    1160083806@qq.com
 * 描述：    微信精选的UI
 */

public class WeChatActivity extends BaseActivity {
    private ProgressBar progressBar;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wechat_show_layout);
        initView();
    }

    private void initView() {
        Intent intent=getIntent();
        final String title=intent.getExtras().getString("title");
        final String url=intent.getExtras().getString("url");
        getSupportActionBar().setTitle(title);
        progressBar= (ProgressBar) findViewById(R.id.weChatProgressBar);
        webView= (WebView) findViewById(R.id.weChatWebView);
        webView.getSettings().setJavaScriptEnabled(true);//设置容许使用JavaScript
        webView.getSettings().setSupportZoom(true);//容许缩放
        webView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        webView.setWebChromeClient(new WebViewClient());

        webView.loadUrl(url);
        //本地显示
        webView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                //接受这个事件
                return true;
            }
        });


    }
    public class WebViewClient extends WebChromeClient{
        //监听进度条的变化

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress==100) {
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
