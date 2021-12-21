package com.volbot.lourts.Data;

public class Personality {
    private float[] statProclivity;
    private float[] sympatheticProclivity;

    public Personality(float[] biology, float[] amygdala) {
        statProclivity = biology;
        sympatheticProclivity = amygdala;
    }
}
