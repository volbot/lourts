package com.volbot.lourts.Data;

import com.volbot.lourts.Agents.Agent;

public class Location extends Agent {
    private int population;
    private int wealth;
    private Personality personality;

    public Location(String name){
        super(name);
        population=0;
        wealth=0;
        personality=new Personality(0,0,0,0);
    }

}
