package com.volbot.lourts.Agents;

import com.volbot.lourts.Data.Reputation;
import com.volbot.lourts.Data.TalkOption;
import com.volbot.lourts.Data.TalkResponse;

public class Agent {

    public int texID;

    public String theme;

    public int x;
    public int y;

    public final Reputation rep;
    private final String name;

    protected Agent(String name) {
        rep = new Reputation();
        this.name = name;
        this.theme = "base";
    }

    public void think() { }

    public TalkResponse startConversation(Individual a) {
        if(rep.knows(a)) {
            return new TalkResponse("Good to see you, "+a.getName()+".", new TalkOption[]{
                    new TalkOption("  Good to see you too, "+getName()+".",    new TalkResponse("Safe travels.")),
                    new TalkOption("  Good to see you too, "+getName()+".",    new TalkResponse("Safe travels.")),
                    new TalkOption("  Good to see you too, "+getName()+".",    new TalkResponse("Safe travels.")),
                    new TalkOption("  I must take my leave. <exit>",            new TalkResponse("Safe travels.")),
            });
        } else {
            rep.meet(a);
            a.rep.meet(this);
            return new TalkResponse("I don't believe we've met. I am "+getName(), new TalkOption[]{
                    new TalkOption("+ Pleased to meet you, "+getName()+". I'm "+a.getName(),    new TalkResponse("Great to meet you too, "+a.getName()+". It's always good to see a friendly face out on the field.")),
                    new TalkOption("U Your reputation precedes you, "+getName()+".",            new TalkResponse("I am afraid yours does not...?")),
                    new TalkOption("- Your vibes disgust me.",                                  new TalkResponse("You are a scoundrel.")),
                    new TalkOption("- I must take my leave. <exit>",                            new TalkResponse("Ok... Good day... to you, then?")),
            });
        }
    }

    public String getName() {
        return name;
    }
}
