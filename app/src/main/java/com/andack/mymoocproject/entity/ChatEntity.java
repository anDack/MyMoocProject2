package com.andack.mymoocproject.entity;

/**
 * 项目名称：MyMoocProject2
 * 项目作者：anDack
 * 项目时间：2017/2/6
 * 邮箱：    1160083806@qq.com
 * 描述：    聊天的实体类
 */

public class ChatEntity {
    //文本
    private String content;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
