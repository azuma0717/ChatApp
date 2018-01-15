package com.gmail.yuki.chatapp;

/**
 * Created by yuki on 2018/01/10.
 */

public class Message {


    private String content;

    public Message(){
    }

    public Message(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
