package com.volbot.lourts.Map;

import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.math.MathUtils.*;

public class Perlin {

    public float rand(Vector3 v) {
        float hash = sin(v.cpy().dot(new Vector3(12.9898f,78.233f, 0f)))*48758.5453123f;
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

        return clamp(a,b,u.x) + (c-a) * u.y * (1.0f - u.x) + (d-b) * u.x * u.y;
    }

}
