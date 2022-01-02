package com.volbot.lourts.Data;

public class TalkResponse {

    public String response;
    public TalkOption[] options;

    public TalkResponse(String response, TalkOption[] options) {
        this.response=response;
        this.options=options;
    }

    public TalkResponse(String response){
        this.response=response;
        this.options=new TalkOption[]{new TalkOption("  Leave",null)};
    }
}
