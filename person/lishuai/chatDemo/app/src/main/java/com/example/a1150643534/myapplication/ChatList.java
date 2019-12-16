package com.example.a1150643534.myapplication;

public class ChatList {
    private int type;//1,玩家说话;0,npc说话;
    private String chatList;




    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChatList() {
        return chatList;
    }

    public void setChatList(String chatList) {
        this.chatList = chatList;
    }
}
