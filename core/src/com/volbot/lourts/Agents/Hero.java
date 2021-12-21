package com.volbot.lourts.Agents;

import com.volbot.lourts.Data.Background;
import com.volbot.lourts.Data.Personality;

public class Hero extends Individual {
    private final Personality personality;
    private final Background background;

    public Hero(String name) {
        super(name);
        personality = new Personality(new float[]{0, 0, 0, 0, 0, 0}, new float[]{0, 0, 0, 0});
        background = new Background(new Location("Nowhere"), 0);
    }

    public Hero(String name, Location origin) {
        super(name);
        personality = origin.getPersonality();
        background = new Background(origin, 0);
        this.location=origin;
        origin.heroes.add(this);
    }

    @Override
    public void think() {
        super.think();

    }

    public Personality getPersonality() {
        return personality;
    }

    public Background getBackground() {
        return background;
    }
}
