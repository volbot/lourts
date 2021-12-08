package com.volbot.lourts.Data;

import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Hero;
import com.volbot.lourts.Agents.Individual;

import java.util.ArrayList;
import java.util.Random;

public class Location extends Agent {
    private Hero figurehead;
    private int population;
    private int wealth;
    private Personality personality;

    ArrayList<Individual> heroes = new ArrayList<>();

    public Location(String name){
        super(name);
        population=1;
        wealth=0;
        figurehead=new Hero("Skeletrex",this);
        heroes.add(figurehead);
        personality=figurehead.getPersonality();
    }

    public Location(String name, int xpos, int ypos){
        super(name);
        population=1;
        wealth=0;
        x=xpos;
        y=ypos;
        figurehead=new Hero("Skeletrex",this);
        heroes.add(figurehead);
        personality=figurehead.getPersonality();
    }

    @Override
    public void think() {
        population+=new Random().nextInt((2*population) + 1) - population;
        if(population==0){
            //towndies
        }
    }

    public Personality getPersonality() {
        return personality;
    }

    public int getPopulation() {
        return population;
    }

    public Hero getFigurehead() {
        return figurehead;
    }

    public int getWealth() {
        return wealth;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setFigurehead(Hero figurehead) {
        this.figurehead = figurehead;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }
}
