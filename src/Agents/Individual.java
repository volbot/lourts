package Agents;

import Data.Stats;

public class Individual extends Agent{
    private final Stats stats; //INT, WIS, STR, DEX, CHA, PSI
    public Individual(){
        super();
        stats = new Stats();
    }
}
