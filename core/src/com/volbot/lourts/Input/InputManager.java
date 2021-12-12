package com.volbot.lourts.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.GUI.GameMenu;
import com.volbot.lourts.GUI.InteractMenu;
import com.volbot.lourts.Main;

public class InputManager implements InputProcessor {

    Camera cam;

    private final int camSpeed;

    private Vector3 camHold = null;

    public boolean camLockedToMap = false;

    public InputManager(Camera camera) {
        cam = camera;
        camSpeed = 300;
    }

    private Vector3 positionClick(Vector3 touchPos) {
        return new Vector3(
                -cam.position.x + touchPos.x,
                -cam.position.y + touchPos.y,
                0
        );
    }

    public Agent entityHovered(Vector3 clickLoc) {
        int thresh = 17;
        for (Agent e : Main.entities) {
            if (clickLoc.x < e.x + thresh && clickLoc.x > e.x - thresh) {
                if (clickLoc.y < e.y + thresh && clickLoc.y > e.y - thresh) {
                    if(Main.player!=e) return e;
                }
            }
        }
        return null;
    }

    public Agent entityHovered() {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPos);
        Vector3 touchLoc = positionClick(touchPos);
        int thresh = 17;
        for (Agent e : Main.entities) {
            if (touchLoc.x < e.x + thresh && touchLoc.x > e.x - thresh) {
                if (touchLoc.y < e.y + thresh && touchLoc.y > e.y - thresh) {
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

        camLockedToMap = false;
        if (camLockedToMap) {
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
        Vector3 temp = new Vector3(screenX, screenY, 0);
        cam.unproject(temp);
        Vector3 touchLoc = positionClick(temp);
        Agent hoverAgent = entityHovered(touchLoc);
        switch (button) {
            case 0:
                if (Main.gui.currmenu != null) {
                    GameMenu tempmenu = Main.gui.currmenu;
                    if(tempmenu instanceof InteractMenu) {
                        InteractMenu menu = (InteractMenu)tempmenu;
                        Agent menuAgent = menu.getAgent();
                        if (hoverAgent == null) {
                            if (touchLoc.y > menuAgent.y - 15 && touchLoc.y < menuAgent.y + 15) {
                                if (touchLoc.x > menuAgent.x - 25 - 15 && touchLoc.x < menuAgent.x - 25 + 15) {
                                    menu.buttons[0].setChecked(!menu.buttons[0].isChecked());
                                    menu.buttons[1].setChecked(false);
                                    returnval = true;
                                }
                                if (touchLoc.x > menuAgent.x + 25 - 15 && touchLoc.x < menuAgent.x + 25 + 15) {
                                    menu.buttons[1].setChecked(!menu.buttons[1].isChecked());
                                    menu.buttons[0].setChecked(false);
                                    returnval = true;
                                }
                            }
                        }
                    }
                }
                if (!returnval) {
                    Main.gui.clearInteractMenu();
                    if (hoverAgent != null && hoverAgent != Main.player) {
                        Main.gui.drawInteractMenu(hoverAgent);
                        Main.player.setDestination(hoverAgent);
                        returnval = true;
                    } else {
                        Main.player.setDestination(touchLoc);
                    }
                }
                break;
            case 1:
                if (hoverAgent != null) {
                    Main.player.setDestination(touchLoc);
                }
                camHold = touchLoc;
                break;
        }
        return returnval;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        camHold = null;
        return Gdx.input.justTouched() && Gdx.input.getX(pointer) == screenX || Gdx.input.getY(pointer) == screenY;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(camHold!=null) {
            Vector3 touchLoc = new Vector3(screenX, screenY, 0);
            touchLoc = cam.unproject(touchLoc);
            touchLoc.sub(camHold);
            cam.position.set(touchLoc);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
