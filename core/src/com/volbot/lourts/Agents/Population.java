package com.volbot.lourts.Agents;

import java.util.ArrayList;

public class Population {
    public ArrayList<Demographic> pop = new ArrayList<>();

    public void add(Demographic demographic){
        for(Demographic demo : pop){
            if(demographic.getOrigin()==demo.getOrigin()){
                if(demographic.getLevel()==demo.getLevel()){
                    demo.setPopulation(demo.getPopulation()+demographic.getPopulation());
                    return;
                }
            }
        }
        pop.add(demographic);
    }
}
