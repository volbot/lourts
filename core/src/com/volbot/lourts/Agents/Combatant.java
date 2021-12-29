package com.volbot.lourts.Agents;

public class Combatant {
    public Object entity;
    public int texID;
    public String theme;
    public Combatant(Agent a) {
        entity = a;
        texID = a.texID;
        theme = a.theme;
    }
    public Combatant(Demographic d) {
        entity = d;
        texID = d.texID;
        theme = d.theme;
    }
}