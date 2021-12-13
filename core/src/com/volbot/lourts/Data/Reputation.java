package com.volbot.lourts.Data;

import com.volbot.lourts.Agents.Agent;

import java.util.HashMap;

public class Reputation {
    private final HashMap<String, Integer> repmap = new HashMap<>();

    public Reputation() {
    }

    public boolean knows(Agent key){
        return repmap.containsKey(key.getName());
    }

    public int get(Agent key) {
        return repmap.get(key.getName());
    }

    public void meet(Agent a) {
        repmap.put(a.getName(), 0);
    }

    public void impress(Agent a, int mag) {
        String name = a.getName();
        int newrep = repmap.get(name)+mag;
        if(Math.abs(newrep)<=100){
            repmap.put(name, newrep);
        }
    }
}
