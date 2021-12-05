package Data;

import Agents.Agent;

public class Location extends Agent {
    private int population;
    private int wealth;

    public Location(String name){
        super(name);
        population=0;
        wealth=0;
    }

}
