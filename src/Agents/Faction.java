package Agents;

import Data.Personality;
import Data.Reputation;

public class Faction extends Agent{
    private String name;
    private Hero leader;
    private int wealth;
    private Personality personality;
    private Reputation rep;

    public Faction(String name, Hero leader, int wealth){
        super(name);
        this.leader = leader;
        this.wealth = wealth;
        personality = leader.getPersonality();
        rep = new Reputation();
    }
}
