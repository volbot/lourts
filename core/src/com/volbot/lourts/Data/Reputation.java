package com.volbot.lourts.Data;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Individual;

import java.util.ArrayList;
import java.util.HashMap;

public class Reputation {
    private final HashMap<Agent, Float> repmap = new HashMap<>();

    public Reputation() {
    }

    public boolean knows(Agent key){
        return repmap.containsKey(key);
    }

    public ArrayList<Individual> known() {
        ArrayList<Individual> searchList = new ArrayList<>();
        for(Agent a : repmap.keySet()){
            if(a instanceof Individual){
                searchList.add((Individual)a);
            }
        }
        return searchList;
    }

    public int get(Agent key) {
        return Math.round(repmap.get(key));
    }

    public void meet(Agent a) {
        repmap.put(a, 0f);
    }

    public void impress(Agent a, double mag) {
        if(!knows(a)){
            meet(a);
        }
        float newrep = repmap.get(a)+(float)mag;
        if(Math.abs(newrep)<=100){
            repmap.put(a, newrep);
        }
    }
}
