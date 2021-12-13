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

    public ArrayList<String> getTalkOptions(Individual a) {
        ArrayList<String> options = new ArrayList<>();
        if(rep.knows(a)) {
            options.add("+EGood to see you again, "+getName()+".");
            options.add("  ");
            options.add("  ");
            options.add(" EI must take my leave. <exit>");
        } else {
            rep.meet(a);
            options.add("+EPleased to make your acquaintance - I am "+a.getName());
            options.add("UEYour reputation precedes you, "+getName()+".");
            options.add("-EYour vibes disgust me.");
            options.add("-EI must take my leave. <exit>");
        }
        return options;
    }

    public String getName() {
        return name;
    }
}
