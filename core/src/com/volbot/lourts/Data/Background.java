package com.volbot.lourts.Data;

import com.volbot.lourts.Agents.Location;

public class Background {
    public final Location origin;
    final int wealth;
    public Background(Location origin, int wealth){
        this.wealth=wealth;
        this.origin=origin;
    }
}
