package com.volbot.lourts.Agents;

public class Demographic {

    public Demographic(Location origin, int population) {
        this.level=1;
        this.origin=origin;
        this.population=population;
    }

    public Location getOrigin() {
        return origin;
    }

    private Location origin;
    private int level;
    public int population;

    public int getLevel() {
        return level;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getName() {
        String originString = origin.getName()+"ian";
        String levelString = null;
        switch(level){
            case 1:
                levelString = "Recruit";
                break;
            case 2:
                levelString = "Militia";
                break;
            case 3:
                levelString = "Infantry";
                break;
            case 4:
                levelString = "Gun Priest";
                break;
            case 5:
                levelString = "Warlord";
                break;
        }
        if(levelString==null){
            levelString = "Legend";
        }
        return originString+" "+levelString;
    }
}
