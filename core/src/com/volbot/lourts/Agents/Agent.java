package com.volbot.lourts.Agents;

import com.badlogic.gdx.Gdx;
import com.volbot.lourts.Data.Reputation;

import java.util.ArrayList;

public class Agent {

    public int texID;

    public int x;
    public int y;

    private final Reputation rep;
    private String name;

    protected Agent(String name) {
        rep = new Reputation();
        this.name = name;
    }

    public void think() { }

    public ArrayList<String> getInteractOptions(Individual a) {
        ArrayList<String> options = new ArrayList<>();
        options.add("Talk");
        options.add("Follow");
        options.add("Attack");
        return options;
    }

    public String getName() {
        return name;
    }

    public int getReputation(Agent agent) {
        if (rep.isset(agent.getName())) {
            return rep.get(agent.getName());
        }
        return rep.get(agent.getName());
    }
}
