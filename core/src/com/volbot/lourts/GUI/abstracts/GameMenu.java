package com.volbot.lourts.GUI.abstracts;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public abstract class GameMenu {

    public Button[] buttons;

    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        if(buttons!=null){
            for(Button button : buttons){
                button.draw(batch,1);
            }
        }
    }
}
