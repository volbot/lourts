package com.volbot.lourts.Data;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.QuadTreeFloat;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Combatant;
import com.volbot.lourts.Agents.Demographic;
import com.volbot.lourts.Agents.Individual;

import java.util.HashMap;

public class Battle {
    public Agent aggressor;
    public Agent defender;
    public HashMap<Combatant, Vector3> combatants;

    public Battle(Agent aggressor, Agent defender) {
        this.aggressor = aggressor;
        this.defender = defender;
        combatants = new HashMap<>();
        combatants.put(new Combatant(aggressor),new Vector3(0,0,0));
        combatants.put(new Combatant(defender),new Vector3(0,0,0));
        if(aggressor instanceof Individual){
            for(Demographic d : ((Individual) aggressor).getParty().pop){
                int i = 0;
                while(i < d.getPopulation()) {
                    combatants.put(new Combatant(d),new Vector3(0,0,0));
                }
            }
        }
    }

}
