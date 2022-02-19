package com.volbot.lourts.Map;

import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.math.MathUtils.*;

public class Perlin {

    float seed;
    public Perlin(String seed) {
        this.seed = seed != null
                ? seed.hashCode() % 100000
                : 53754.5453123f;
    }

    public float rand(Vector3 v) {
        float hash = sinDeg(40*sinDeg(v.cpy().dot(new Vector3(76.45256f,35.89564f, 0f))+33)+80)*seed;
        hash = hash - floor(hash);
        return hash;
    }

    public float noise(Vector3 v) {
        Vector3 i = v.cpy();
        i.x=floor(i.x);
        i.y=floor(i.y);
        Vector3 f = v.cpy();
        f.x = v.x-i.x;
        f.y = v.y-i.y;

        float a = rand(i.cpy());
        float b = rand(i.cpy().add(new Vector3(1.0f, 0.0f, 0f)));
        float c = rand(i.cpy().add(new Vector3(0.0f, 1.0f, 0f)));
        float d = rand(i.cpy().add(new Vector3(1.0f, 1.0f, 0f)));

        //CUBIC HERMINE
        Vector3 u = f.scl(f).scl(f.scl(-2.0f).add(3.0f));
        //Vector3 u = v.cpy().add((float) (((Math.sqrt(3)-1)/2)*(v.x+v.y)));

        return clamp(a,b,u.x) + (c-a) * u.y * (1.0f - u.x) + (d-b) * u.x * u.y;
    }

}
