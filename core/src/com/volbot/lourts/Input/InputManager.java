package com.volbot.lourts.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Combatant;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.GUI.GameMenu;
import com.volbot.lourts.GUI.GameWindow;
import com.volbot.lourts.Main;
import com.volbot.lourts.Map.Tile;

public class InputManager implements InputProcessor {

    OrthographicCamera cam;

    private final int camSpeed;

    private Vector3 camHold = null;

    public boolean camLockedToMap = true;
    private static final int camBound = 300;

    public InputManager(OrthographicCamera camera) {
        cam = camera;
        camSpeed = 300;
    }

    public Vector3 boundClick(Vector3 touchPos) {
        Vector3 touchLoc = touchPos.cpy();
        touchLoc.x = Math.max(0, touchLoc.x);
        touchLoc.y = Math.max(0, touchLoc.y);
        touchLoc.x = Math.min(Main.map.size(), touchLoc.x);
        touchLoc.y = Math.min(Main.map.size(), touchLoc.y);
        return touchLoc;
    }

    private Vector3 positionClick(Vector3 clickLoc) {
        Vector3 touchPos = clickLoc.cpy();
        touchPos = cam.unproject(touchPos);

        Vector3 camPos = cam.position.cpy();
        touchPos.sub(camPos);
        camPos.scl(cam.zoom);
        touchPos.sub(camPos);

        touchPos.scl(1 / cam.zoom);

        Vector3 camSize = new Vector3(cam.viewportWidth, cam.viewportHeight, 0);
        touchPos.mulAdd(camSize, 0.5f);
        camSize.scl(1 / cam.zoom);
        touchPos.x *= camSize.x / cam.viewportWidth;
        touchPos.y *= camSize.y / cam.viewportHeight;


        return touchPos;
    }

    public Vector3 getTouchPos() {
        Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        vec = positionClick(vec);
        return vec;
    }

    public Agent entityHovered(Vector3 touchLoc) {
        float thresh = 0;
        float width;
        if (Main.GAMEMODE == 0) {
            for (Agent e : Main.entities) {
                width = e instanceof Location ? 20 * cam.zoom : 10 * cam.zoom;
                if (touchLoc.x < e.position.x + width + thresh && touchLoc.x > e.position.x - width - thresh) {
                    if (touchLoc.y < e.position.y + width + thresh && touchLoc.y > e.position.y - width - thresh) {
                        return e;
                    }
                }
            }
        } else if (Main.GAMEMODE == 1) {
            width = 10 * cam.zoom;
            for (Combatant c : Main.battle.combatants) {
                if (touchLoc.x < c.position.x + width + thresh && touchLoc.x > c.position.x - width - thresh) {
                    if (touchLoc.y < c.position.y + width + thresh && touchLoc.y > c.position.y - width - thresh) {
                        return c.getAgent();
                    }
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
        moveSpeed *= Gdx.graphics.getDeltaTime() * cam.zoom;
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
/*
        if(camLockedToMap){
            if(cam.position.x>camBound){
                cam.position.x=camBound;
            } else if(cam.position.x-cam.viewportWidth<-camBound-(Main.map.size())){
                cam.position.x=-camBound+cam.viewportWidth-(Main.map.size());
            }
            if(cam.position.y>camBound){
                cam.position.y=camBound;
            } else if(cam.position.y-cam.viewportHeight<-camBound-(Main.map.size())){
                cam.position.y=-camBound+cam.viewportHeight-(Main.map.size());
            }
        }

 */
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Main.GAMEMODE == 1) { // battle mode
            if (keycode == Input.Keys.TAB) {
                Main.gui.drawNotificationWindow("retreat");
            }
        }
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
                    if (tempMenu.buttons != null) {
                        for (Button b : tempMenu.buttons) {
                            Vector3 bpos = new Vector3(
                                    b.getX() - cam.position.x,
                                    b.getY() - cam.position.y, 0);
                            Vector3 bsize = new Vector3(
                                    b.getWidth(),
                                    b.getHeight(), 0);
                            bpos.scl(1 / cam.zoom);
                            if (tempMenu instanceof GameWindow) {
                                returnval = true;
                                bsize.scl(1 / cam.zoom);
                            }
                            if (touchLoc.x > bpos.x && touchLoc.x < bpos.x + bsize.x) {
                                if (touchLoc.y > bpos.y && touchLoc.y < bpos.y + bsize.y) {
                                    b.setChecked(true);
                                    for (Button b2 : tempMenu.buttons) if (!b.equals(b2)) b2.setChecked(false);
                                    returnval = true;
                                }
                            }
                        }
                    }
                }
                if (!returnval) {
                    Main.gui.clearMenu();
                    if (Main.GAMEMODE == 0) {
                        if (hoverAgent != null && hoverAgent != Main.player) {
                            Main.gui.drawInteractMenu(hoverAgent);
                            Main.player.setDestination(hoverAgent);
                            returnval = true;
                        } else {
                            Tile tile = Main.map.chunks.getTile((int) touchLoc.x / 20, (int) touchLoc.y / 20);
                            if (tile.walkable) {
                                Main.player.setDestination(touchLoc);
                            }
                        }
                    }
                }
                return returnval;
            case 1:
                camHold = touchLoc;
                return false;
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
        if (camHold != null && cam.zoom>0.2) {
            Vector3 temp = touchLoc.cpy().sub(camHold);
            cam.position.add(temp.scl(cam.zoom));
            cam.update();
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        Vector3 camSize = new Vector3(cam.viewportWidth, cam.viewportHeight, 0);
        camSize.scl(0.5f);
        Vector3 camPos = cam.position.cpy();
        camPos.sub(camSize);
        if (amountY < 0) {
            cam.zoom += 0.02f;
            if (cam.zoom > 2.3f) {
                cam.zoom = 2.3f;
            } else {
                cam.position.mulAdd(camPos, 0.02f / cam.zoom);
            }
        } else {
            cam.zoom -= 0.02f;
            if (cam.zoom < 0.05) {
                cam.zoom = 0.05f;
            } else {
                cam.position.mulAdd(camPos, -0.02f / cam.zoom);
            }
        }
        return true;
    }
}
