package com.volbot.lourts.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Data.Location;
import com.volbot.lourts.GUI.GameMenu;
import com.volbot.lourts.GUI.GameWindow;
import com.volbot.lourts.Main;

public class InputManager implements InputProcessor {

    OrthographicCamera cam;

    private final int camSpeed;

    private Vector3 camHold = null;

    public boolean camLockedToMap = false;

    public InputManager(OrthographicCamera camera) {
        cam = camera;
        camSpeed = 300;
    }

    private Vector3 positionClick(Vector3 clickLoc) {
        Vector3 touchPos = clickLoc.cpy();
        touchPos=cam.unproject(touchPos);
        touchPos.x+=((cam.viewportWidth)/2);
        touchPos.y+=((cam.viewportHeight)/2);
        touchPos.x-=((cam.position.x*2));
        touchPos.y-=((cam.position.y*2));
        return touchPos;
    }

    public Vector3 getTouchPos(){
        Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        vec = positionClick(vec);
        return vec;
    }

    public Agent entityHovered(Vector3 touchLoc) {
        float thresh = 0;
        float width;
        for (Agent e : Main.entities) {
            width = e instanceof Location ? 20*cam.zoom : 10*cam.zoom;
            if (touchLoc.x < e.x + width + thresh && touchLoc.x > e.x - width - thresh) {
                if (touchLoc.y < e.y + width + thresh && touchLoc.y > e.y - width - thresh) {
                    return e;
                }
            }
        }
        return null;
    }

    public void parseCameraMovement() {
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

        if (inputs[0] && inputs[1]) {
            inputs[0] = false;
            inputs[1] = false;
        } else if (inputs[2] && inputs[3]) {
            inputs[2] = false;
            inputs[3] = false;
        }
        int moveSpeed = camSpeed;
        if (dirs == 2) {
            double temp = Math.pow(camSpeed, 2);
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

    @Override
    public boolean keyDown(int keycode) {
        return Gdx.input.isKeyPressed(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return Gdx.input.isKeyJustPressed(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return Gdx.input.isKeyPressed(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean returnval = false;
        Vector3 clickPos = new Vector3(screenX, screenY, 0);
        Vector3 touchLoc = positionClick(clickPos);
        Agent hoverAgent = entityHovered(touchLoc);
        switch (button) {
            case 0:
                if (Main.gui.currmenu != null) {
                    GameMenu tempMenu = Main.gui.currmenu;
                    if(tempMenu.buttons!=null){
                        for(Button b : tempMenu.buttons){
                            Vector3 bpos = new Vector3(b.getX(),b.getY(),0);
                            bpos.scl(1/cam.zoom);
                            if(touchLoc.x>bpos.x&&touchLoc.x<bpos.y+b.getWidth()/2){
                                if(touchLoc.y>bpos.y&&touchLoc.y<bpos.y+b.getHeight()/2){
                                    b.setChecked(true);
                                    for(Button b2 : tempMenu.buttons) if(b!=b2) b2.setChecked(false);
                                    returnval=true;
                                }
                            }
                        }
                    }
                }
                if (!returnval) {
                    Main.gui.clearMenu();
                    if (hoverAgent != null && hoverAgent != Main.player) {
                        Main.gui.drawInteractMenu(hoverAgent);
                        Main.player.setDestination(hoverAgent);
                        returnval = true;
                    } else {
                        Main.player.setDestination(touchLoc);
                    }
                }
                return returnval;
            case 1:
                if (camHold == null && hoverAgent != null) {
                    Main.player.setDestination(touchLoc);
                }
                if(Main.gui.currmenu instanceof GameWindow){
                    Main.gui.clearMenu();
                }
                camHold = touchLoc;
                return returnval;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        camHold = null;
        return Gdx.input.justTouched() && Gdx.input.getX(pointer) == screenX || Gdx.input.getY(pointer) == screenY;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 clickPos = new Vector3(screenX, screenY, 0);
        Vector3 touchLoc = positionClick(clickPos);
        if(camHold!=null) {
            Vector3 temp = touchLoc.cpy().sub(camHold);
            cam.position.add(temp);
            cam.update();
        } else {
            if(Main.player.getDestination()==null) {
                Main.player.setDestination(touchLoc);
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if(amountY<0) {
            cam.zoom += 0.02f;
        } else {
            cam.zoom -= 0.02f;
        }
        System.out.println(cam.zoom);
        return true;
    }
}
