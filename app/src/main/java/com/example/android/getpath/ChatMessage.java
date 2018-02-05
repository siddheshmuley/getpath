package com.example.android.getpath;

/**
 * Created by Siddhesh on 11/23/2017.
 */

public class ChatMessage {
    int id;
    String message;
    String translatedMessage;

    ChatMessage(int id, String message, String translatedMessage){
        this.id=id;
        this.message=message;
        this.translatedMessage=translatedMessage;
    }

    int getId(){
        return id;
    }

    String getMessage(){
        return message;
    }

    String getTranslatedMessage(){
        return translatedMessage;
    }
}
