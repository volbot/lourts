package com.volbot.lourts.Data;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Individual;

import java.util.HashMap;

public class Reputation {
    private final HashMap<Agent, Float> repmap = new HashMap<>();

    public Reputation() {
    }

    public boolean knows(Agent key){
        return repmap.containsKey(key);
    }

    public int get(Agent key) {
        return Math.round(repmap.get(key));
    }

    public void meet(Agent a) {
        repmap.put(a, 0f);
    }

    public void impress(Agent a, double mag) {
        float newrep = repmap.get(a)+(float)mag;
        if(Math.abs(newrep)<=100){
            repmap.put(a, newrep);
        }
    }
}
