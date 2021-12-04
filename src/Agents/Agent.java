package Agents;

public class Agent {
    private final Reputation facmap;
    private final Reputation charmap;
    private final Reputation locmap;

    public Agent() {
        super();
        facmap = new Reputation();
        charmap = new Reputation();
        locmap = new Reputation();
    }
}
