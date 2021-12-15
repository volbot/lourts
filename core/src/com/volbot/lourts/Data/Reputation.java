package com.volbot.lourts.Data;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Individual;

import java.util.HashMap;

public class Reputation {
    private final HashMap<String, Float> repmap = new HashMap<>();

    public Reputation() {
    }

    public boolean knows(Agent key){
        return repmap.containsKey(key.getName());
    }

    public int get(Agent key) {
        return Math.round(repmap.get(key.getName()));
    }

    public void meet(Agent a) {
        repmap.put(a.getName(), 0f);
    }

    public void impress(Agent a, double mag) {
        String name = a.getName();
        float newrep = repmap.get(name)+(float)mag;
        if(Math.abs(newrep)<=100){
            repmap.put(name, newrep);
        }
        if(a instanceof Individual){
            if(((Individual) a).location!=null){
                ((Individual) a).location.rep.impress(a,mag*0.2);
            }
        }
    }
}
