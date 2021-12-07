package com.volbot.lourts.Agents;

import com.volbot.lourts.Data.Background;
import com.volbot.lourts.Data.Location;
import com.volbot.lourts.Data.Personality;

public class Hero extends Individual {
    private final Personality personality;
    private final Background background;

    public Hero(String name) {
        super(name);
        personality = new Personality(0, 0, 0, 0);
        background = new Background(new Location("Nowhere"), 0);
    }

    public Hero(String name, Location origin) {
        super(name);
        personality = new Personality(0, 0, 0, 0);
        background = new Background(origin, 0);
        x=origin.x;
        y=origin.y;
    }

    public Personality getPersonality() {
        return personality;
    }

    public Background getBackground() {
        return background;
    }
}
