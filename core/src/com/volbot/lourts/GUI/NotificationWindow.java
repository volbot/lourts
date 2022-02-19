package com.volbot.lourts.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.volbot.lourts.GUI.abstracts.GameWindow;
import com.volbot.lourts.Main;

public class NotificationWindow extends GameWindow {

    public static float width;
    public static float height;
    private final BitmapFont font = new BitmapFont();
    private final String type;
    private String notification;

    public NotificationWindow(String type){
        this.type=type;
        windowbg = new Texture("GUI/windows/menublank.png");
        font.getData().setScale(1f);
        buttons = new TextButton[2];
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbuttondown.png")));
        buttonStyle.font = font;
        if(type.compareTo("retreat")==0){
            buttons[0]=new TextButton("Retreat",buttonStyle);
            buttons[1]=new TextButton("Stay",buttonStyle);
            notification = "Are you sure you want to retreat?";
        }
        if(type.compareTo("battlewon")==0){
            buttons[0]=new TextButton("Continue",buttonStyle);
            buttons[1]=new TextButton("Stay",buttonStyle);
            notification = "You have won!";
        }
        if(type.compareTo("battlelost")==0){
            buttons[0]=new TextButton("Continue",buttonStyle);
            buttons[1]=new TextButton("Stay",buttonStyle);
            notification = "You have lost.";
        }
    }

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        width = cam.viewportWidth * 0.3f;
        height = cam.viewportHeight* 0.1f;
        GlyphLayout layout = new GlyphLayout(font,
                notification, Color.WHITE,
                windowbg.getWidth()*0.8f, Align.topLeft, false
        );
        batch.draw(windowbg,(cam.viewportWidth-width)/2, (cam.viewportHeight-height)/2, width, height);

        buttons[0].setX((cam.viewportWidth-width*0.95f)/2);
        buttons[1].setX((cam.viewportWidth)/2);
        for(Button b : buttons) {
            b.setWidth(width * 0.45f);
            b.setHeight(height*0.45f);
            b.setY((cam.viewportHeight-height*0.95f)/2);
            b.draw(batch,1);
        }
        font.draw(batch, notification, (cam.viewportWidth-width)/2, (cam.viewportHeight)/2+(height/4));
    }

    @Override
    public void activateButton(int buttonDex) {
        switch(buttonDex) {
            case 0:
                switch (type) {
                    case "retreat":
                        Main.endBattle(null);
                        Main.gui.clearMenu();
                        break;
                    case "battlewon":
                        Main.endBattle(Main.player);
                        break;
                    case "battlelost":
                        if (Main.battle.aggressor.equals(Main.player)) {
                            Main.endBattle(Main.battle.defender);
                        } else {
                            Main.endBattle(Main.battle.aggressor);
                        }
                        break;
                }
                break;
            case 1:
                if(type.equals("retreat")){
                    Main.gui.clearMenu();
                }
                break;
        }
    }
}
