package com.volbot.lourts.Agents;

import com.volbot.lourts.Data.Personality;
import com.volbot.lourts.Data.Reputation;

import java.util.ArrayList;

public class Faction extends Agent{
    private Hero leader;
    private int wealth;
    private Personality personality;
    private Reputation rep;
    public ArrayList<Individual> heroes = new ArrayList<>();

    public Faction(String name, Hero leader, int wealth){
        super(name);
        this.leader = leader;
        leader.faction=this;
        this.wealth = wealth;
        personality = leader.getPersonality();
    }
}
