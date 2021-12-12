package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public abstract class GameMenu {

    public Button[] buttons;

    public abstract void drawMenu(SpriteBatch batch, Camera cam);
}