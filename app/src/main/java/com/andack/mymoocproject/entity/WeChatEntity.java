package com.andack.mymoocproject.entity;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/7
 * 邮箱：    1160083806@qq.com
 * 描述：    微信精选的实体类
 */

public class WeChatEntity {
    private String Title;
    private String Source;
    private String ImagrUrl;

    public String getImagrUrl() {
        return ImagrUrl;
    }

    public void setImagrUrl(String imagrUrl) {
        ImagrUrl = imagrUrl;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
