package com.volbot.lourts.Agents;

import com.badlogic.gdx.Gdx;
import com.volbot.lourts.Data.Reputation;

public class Agent {

    public int x;
    public int y;

    private final Reputation rep;
    private String name;

    public Agent(String name) {
        rep = new Reputation();
        this.name = name;
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
