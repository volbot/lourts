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

    public void sub (ArrayList<Demographic> list) {
        for(Demographic d : list){
            for(Demographic d2 : pop) {
                if(d.getOrigin().equals(d2.getOrigin())){
                    if(d.getLevel()==d2.getLevel()){
                        d2.setPopulation(d2.getPopulation()-d.getPopulation());
                    }
                }
            }
        }
    }

    public void sub (Demographic d) {
        ArrayList<Demographic> list = new ArrayList<>();
        list.add(d);
        sub(list);
    }
}
