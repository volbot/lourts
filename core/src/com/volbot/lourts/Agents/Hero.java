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

    public Personality getPersonality() {
        return personality;
    }

    public Background getBackground() {
        return background;
    }
}
