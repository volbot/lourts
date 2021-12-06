package com.volbot.lourts.Agents;

import com.volbot.lourts.Data.Stats;

public class Individual extends Agent{
    private final Stats stats; //INT, WIS, STR, DEX, CHA, PSI
    public Individual(String name){
        super(name);
        stats = new Stats();
    }
}