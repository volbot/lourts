package Agents;

import Data.Reputation;

public class Agent {
    private final Reputation rep;
    private String name;

    public Agent(String name) {
        super();
        rep = new Reputation();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getReputation(Agent agent) {
        if(rep.isset(agent.getName())){
            return rep.get(agent.getName());
        }
        return rep.get(agent.getName());
    }
}
