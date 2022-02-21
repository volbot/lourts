package com.volbot.lourts.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.GUI.abstracts.GameWindow;
import com.volbot.lourts.Main;
import com.volbot.lourts.Map.GameMap;

public class NewGameMenu extends GameWindow {

    public Stage stage;
    public TextField[] fields;
    protected TextField.TextFieldStyle textFieldStyle;

    public NewGameMenu() {
        super();
        windowbg = new Texture("GUI/windows/menublank.png");

        textFieldStyle = new TextField.TextFieldStyle();
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbutton.png")));
        buttonStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbuttondown.png")));
        buttonStyle.font = new BitmapFont();
        textFieldStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/windows/buttons/eighthofwindowbuttondown.png")));
        textFieldStyle.font = new BitmapFont();
        textFieldStyle.fontColor = Color.WHITE;

        buttons = new Button[4];
        buttons[0] = new TextButton("Start", buttonStyle);
        buttons[1] = new TextButton("Back", buttonStyle);
        Button.ButtonStyle tempStyle = new Button.ButtonStyle();
        tempStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/menus/interact/talkbutton.png")));
        tempStyle.checked = new TextureRegionDrawable(new TextureRegion(new Texture("GUI/menus/interact/talkbuttondown.png")));
        buttons[2] = new Button(tempStyle);
        buttons[3] = new Button(tempStyle);

        genFields();

        buttons[2].setY(windowbg.getHeight() * 0.63f);
        buttons[3].setY(windowbg.getHeight() * 0.63f);
        fields[0].setY(windowbg.getHeight() * 0.55f);
        fields[1].setY(windowbg.getHeight() * 0.5f);
        fields[2].setY(windowbg.getHeight());
    }

    private int texID = 0;

    @Override
    public void drawMenu(SpriteBatch batch, OrthographicCamera cam) {
        buttons[2].setX(((cam.viewportWidth - windowbg.getWidth()) / 2)+20);
        buttons[3].setX(((cam.viewportWidth - windowbg.getWidth()) / 2)+200);

        fields[0].setX(((cam.viewportWidth - windowbg.getWidth()) / 2)+20);
        fields[1].setX(((cam.viewportWidth - windowbg.getWidth()) / 2)+20);
        fields[2].setX(((cam.viewportWidth - windowbg.getWidth() / 4f) / 2)+20);

        float temp = (cam.viewportWidth - buttons[0].getWidth()) / 2;
        for (int i = 0; i < 2; i++) {
            buttons[i].setX(temp);
        }
        temp = cam.viewportHeight / 2.6f;
        for (int i = 0; i < 2; i++) buttons[i].setY(temp -= buttons[i].getHeight());

        super.drawMenu(batch, cam);

        batch.draw(Main.texLoader.texUnits.get("base").heroes.get(this.texID),
                (cam.viewportWidth - windowbg.getWidth()) / 2 + windowbg.getWidth() * 0.05f,
                windowbg.getHeight() * 0.66f,
                200, 200);

        fields[0].draw(batch,1f);
        fields[1].draw(batch,1f);
        fields[2].draw(batch,1f);
    }

    private void genFields() {
        stage = new Stage();

        InputMultiplexer plex = new InputMultiplexer();
        plex.addProcessor(Main.inputs);
        plex.addProcessor(stage);
        Gdx.input.setInputProcessor(plex);

        fields = new TextField[3];
        for(int i = 0; i < 3; i ++){
            fields[i] = new TextField("",textFieldStyle);
            fields[i].setMaxLength(20);
            fields[i].setSize(200,15);
            stage.addActor(fields[i]);
        }

        fields[0].setName("NameBox");
        fields[0].setMessageText("Name");
        fields[1].setName("SeedBox");
        fields[1].setMessageText("Seed");
        fields[2].setName("WorldBox");
        fields[2].setMessageText("World Name");


    }

    @Override
    public void activateButton(int buttonDex) {
        switch (buttonDex) {
            case 0:
                Main.player = new Individual(fields[0].getText());
                Main.player.texID = this.texID;
                Main.worldmap=new GameMap(fields[2].getText(),fields[1].getText());

                double mapSize = Math.pow(2, Main.worldmap.chunks.depth);
                Main.player.position = new Vector3(
                        Main.random.nextInt((int)mapSize),
                        Main.random.nextInt((int)mapSize),0);
                while(!Main.worldmap.chunks.getTile(
                        (int)Main.player.position.x,(int)Main.player.position.y).walkable)
                    Main.player.position = new Vector3(
                            Main.random.nextInt((int)mapSize),
                            Main.random.nextInt((int)mapSize),0);

                Gdx.input.setInputProcessor(Main.inputs);
                Main.gui.currmenu = null;

                Main.entities.add(Main.player);
                Main.GAMEMODE = 0;

                break;
            case 1:
                Main.gui.currmenu = new MainMenu();
                break;
            case 2:
                texID -= (texID > 0 ? 1 : 0);
                break;
            case 3:
                int len = Main.texLoader.texUnits.get("base").heroes.size();
                texID += (texID < (len-1) ? 1 : 0);
                break;
        }
        buttons[buttonDex].setChecked(false);
    }
}
