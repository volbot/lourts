package Agents;

public class Hero extends Individual {
    private final Personality personality;
    private final Background background;
    public Hero() {
        super();
        personality = new Personality(0,0,0,0);
        background = new Background(0);
    }
}
