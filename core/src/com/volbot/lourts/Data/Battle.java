package com.volbot.lourts.Data;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.QuadTreeFloat;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Combatant;
import com.volbot.lourts.Agents.Demographic;
import com.volbot.lourts.Agents.Individual;

import java.util.ArrayList;
import java.util.HashMap;

public class Battle {
    public Agent aggressor;
    public Agent defender;
    public ArrayList<Combatant> combatants;

    public Battle(Agent aggressor, Agent defender) {
        this.aggressor = aggressor;
        this.defender = defender;
        combatants = new ArrayList<>();
        combatants.add(new Combatant(aggressor).setPosition(new Vector3(100,100,0)));
        combatants.add(new Combatant(defender).setPosition(new Vector3(300,100,0)));
        if(aggressor instanceof Individual){
            int j = 1;
            for(Demographic d : ((Individual) aggressor).getParty().pop){
                int i = 0;
                while(i < d.getPopulation()) {
                    combatants.add(new Combatant(d,aggressor).setPosition(combatants.get(0).position.cpy().add(-j*10,10*i,0)));
                    i++;
                }
                j++;
            }
        }
        if(defender instanceof Individual){
            int j = 1;
            for(Demographic d : ((Individual) defender).getParty().pop){
                System.out.println("deez");
                int i = 0;
                while(i < d.getPopulation()) {
                    combatants.add(new Combatant(d,defender).setPosition(combatants.get(0).position.cpy().add(j*10,10*i,0)));
                    i++;
                }
                j++;
            }
        }
    }

}
