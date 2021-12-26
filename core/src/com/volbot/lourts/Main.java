package com.volbot.lourts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.Agents.Location;
import com.volbot.lourts.GUI.GUIManager;
import com.volbot.lourts.Input.InputManager;
import com.volbot.lourts.Map.GameMap;
import com.volbot.lourts.Render.Display;
import com.volbot.lourts.Render.TexLoader;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {

	private OrthographicCamera cam;

	public static GUIManager gui;
	public static Display display;
	public static ArrayList<Agent> entities = new ArrayList<>();
	public static GameMap map;
	public static TexLoader texLoader;
	public static InputManager inputs;

	public static Individual player;
	public static int GAMETIME;
	public static boolean PAUSED = false;

	@Override
	public void create () {
		GAMETIME=0;
		map = new GameMap();
		texLoader = new TexLoader();
		Individual crabwizard = new Individual("Crabwizard");
		player = crabwizard;
		entities.add(crabwizard);
		gui = new GUIManager();
		crabwizard.position.x=400;
		crabwizard.position.y=400;
		crabwizard.texID=1;
		Location boneland = new Location("Boneland","Skeletrex",200,200, 100);
		entities.add(boneland);
		boneland.texID=0;
		Location bonetown = new Location("Bonetown", "Anthony Hopkins",800,440,233);
		entities.add(bonetown);
		bonetown.texID=0;
		cam = new OrthographicCamera();
		cam.setToOrtho(false,1024,576);
		cam.position.x=0;
		cam.position.y=0;
		display = new Display(cam);
		inputs = new InputManager(cam);
		Gdx.input.setInputProcessor(inputs);
	}

	@Override
	public void render () {
		GAMETIME++;
		ScreenUtils.clear(1, 0, 0, 1);
		massThink();
		if(gui.currmenu!=null){
			gui.loop();
		}
		inputs.parseCameraMovement();
		display.loop();
	}

	public void massThink () {
		int len1 = entities.size();
		Agent entity;
		for(int j = 0; j<len1; j++){
			entity = entities.get(j);
			if(entity instanceof Individual){
				entity.think();
			} else if(entity instanceof Location){
				ArrayList<Individual> heroes = ((Location) entity).heroes;
				if(!heroes.isEmpty()) {
					int len2 = heroes.size();
					for (int i = 0; i < len2; i++) {
						heroes.get(i).think();
						if (heroes.size() < len2) {
							len2--;
							i--;
						}
					}
				}
			}
			if(entities.size()<len1){
				len1--;
				j--;
			}
			if(entities.size()>len1){
				len1++;
			}
		}
	}
	
	@Override
	public void dispose () {
		display.dispose();
		texLoader.dispose();
	}

	public static void setPaused(boolean val){
		PAUSED=val;
	}
}
