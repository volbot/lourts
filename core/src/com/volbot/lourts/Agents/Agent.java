package com.volbot.lourts.Agents;

import com.volbot.lourts.Data.Reputation;

import java.util.ArrayList;

public class Agent {

    public int texID;

    public int x;
    public int y;

    public final Reputation rep;
    private final String name;

    protected Agent(String name) {
        rep = new Reputation();
        this.name = name;
    }

    public void think() { }

    public ArrayList<String>[] getTalkOptions(Individual a) {
        ArrayList<String> options = new ArrayList<>();
        ArrayList<String> responses = new ArrayList<>();
        if(rep.knows(a)) {
            options.add("+EGood to see you again, "+getName()+".");
            responses.add("Good to see you as well, "+a.getName()+".");
            options.add("  ");
            responses.add("  ");
            options.add("  ");
            responses.add("  ");
            options.add(" EI must take my leave. <exit>");
            responses.add("Good day, "+a.getName()+".");
        } else {
            rep.meet(a);
            options.add("+EPleased to make your acquaintance - I am "+a.getName());
            responses.add("Great to meet you, "+a.getName()+". I am "+getName()+". I hope to see you again on the field.");
            options.add("UEYour reputation precedes you, "+getName()+".");
            responses.add("I am afraid yours does not...?");
            options.add("-EYour vibes disgust me.");
            responses.add("You are a scoundrel.");
            options.add("-EI must take my leave. <exit>");
            responses.add("Ok... Good day... to you, then?");
        }
        ArrayList<String>[] returnval = new ArrayList[2];
        returnval[0]=options;
        returnval[1]=responses;
        return returnval;
    }

    public String getName() {
        return name;
    }
}
