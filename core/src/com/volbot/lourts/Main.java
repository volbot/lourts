package com.volbot.lourts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Hero;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.Data.Location;
import com.volbot.lourts.GUI.GUIManager;
import com.volbot.lourts.Input.InputManager;
import com.volbot.lourts.Map.GameMap;
import com.volbot.lourts.Render.Display;
import com.volbot.lourts.Render.TexLoader;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {

	private OrthographicCamera cam;

	TexLoader texLoader;
	Individual crabwizard;
	Hero skeletrex;
	Location boneland;

	public static Individual player;
	public static GUIManager gui;
	public static Display display;
	public static ArrayList<Agent> entities = new ArrayList<>();
	public static GameMap map;
	public static InputManager inputs;

	@Override
	public void create () {
		map = new GameMap();
		texLoader = new TexLoader();
		crabwizard = new Individual("crabwizard");
		player = crabwizard;
		entities.add(crabwizard);
		gui = new GUIManager();
		crabwizard.x=0;
		crabwizard.y=0;
		boneland = new Location("boneland",200,200);
		entities.add(boneland);
		skeletrex = boneland.getFigurehead();
		entities.add(skeletrex);
		skeletrex.setDestination(new Vector3(-300, 100,0));
		cam = new OrthographicCamera();
		cam.setToOrtho(false,1024,576);
		cam.position.x=320;
		cam.position.y=240;
		display = new Display(cam);
		display.displayEntity(crabwizard);
		display.displayEntity(skeletrex);
		display.displayEntity(boneland);
		inputs = new InputManager(cam);
		Gdx.input.setInputProcessor(inputs);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		crabwizard.think();
		skeletrex.think();
		inputs.parseCameraMovement();
		display.loop();
	}
	
	@Override
	public void dispose () {
		display.dispose();
		texLoader.dispose();
	}
}
