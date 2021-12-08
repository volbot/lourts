package com.volbot.lourts.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.Main;

public class InputManager {

    Camera cam;

    public boolean camLockedToMap = false;

    public InputManager(Camera camera) {
        cam = camera;
    }

    private Vector3 positionClick(Vector3 touchPos) {
        return new Vector3(
                touchPos.x - cam.position.x,
                touchPos.y - cam.position.y,
                0
        );
    }

    public Agent checkEntityClicks() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);
            touchPos = positionClick(touchPos);
            for (Agent e : Main.entities) {
                if (touchPos.x < e.x + 12 && touchPos.x > e.x - 12) {
                    if (touchPos.y < e.y + 12 && touchPos.y > e.y - 12) {
                        return e;
                    }
                }
            }
        }
        return null;
    }

    public void parsePlayerMovement(Individual player) {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);
            touchPos = positionClick(touchPos);
            player.setGoalPos(new int[]{
                    (int) (touchPos.x),
                    (int) (touchPos.y)}
            );
        }
    }

    public void parseCameraMovement(int moveSpeed) {
        boolean[] inputs = new boolean[]{false, false, false, false};
        int dirs = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            inputs[0] = true;
            dirs++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            inputs[1] = true;
            dirs++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            inputs[2] = true;
            dirs++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            inputs[3] = true;
            dirs++;
        }

        camLockedToMap=false;
        if(camLockedToMap) {
            if (cam.position.y < 0 && inputs[0]) {
                inputs[0] = false;
            }
            if (cam.position.y + cam.viewportHeight >= Main.map.chunks.get(0).size() * 480 && inputs[1]) {
                inputs[1] = false;
            }
            if (cam.position.x + cam.viewportWidth >= Main.map.chunks.size() * 480 && inputs[2]) {
                inputs[2] = false;
            }
            if (cam.position.x < 0 && inputs[3]) {
                inputs[3] = false;
            }
        }

        if (inputs[0] && inputs[1]) {
            inputs[0] = false;
            inputs[1] = false;
        } else if (inputs[2] && inputs[3]) {
            inputs[2] = false;
            inputs[3] = false;
        }
        if (dirs == 2) {
            double temp = Math.pow(moveSpeed, 2);
            temp /= 2;
            temp = Math.sqrt(temp);
            moveSpeed = (int) Math.ceil(temp);
        }
        moveSpeed *= Gdx.graphics.getDeltaTime();
        if (inputs[0]) {
            cam.position.y -= moveSpeed;
        }
        if (inputs[1]) {
            cam.position.y += moveSpeed;
        }
        if (inputs[2]) {
            cam.position.x += moveSpeed;
        }
        if (inputs[3]) {
            cam.position.x -= moveSpeed;
        }
    }
}
