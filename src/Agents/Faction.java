package Agents;

import Data.Personality;

public class Faction extends Agent{
    private Hero leader;
    private int wealth;
    private Personality personality;

    public Faction(String name, Hero leader, int wealth){
        super(name);
        this.leader = leader;
        this.wealth = wealth;
        personality = leader.getPersonality();
    }
}
