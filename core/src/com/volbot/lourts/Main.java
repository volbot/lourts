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

	public static TexLoader texLoader;
	Individual crabwizard;
	Location boneland;

	public static Individual player;
	public static GUIManager gui;
	public static Display display;
	public static ArrayList<Agent> entities = new ArrayList<>();
	public static GameMap map;
	public static InputManager inputs;

	public static int GAMETIME;

	@Override
	public void create () {
		GAMETIME=0;
		map = new GameMap();
		texLoader = new TexLoader();
		crabwizard = new Individual("Crabwizard");
		player = crabwizard;
		entities.add(crabwizard);
		gui = new GUIManager();
		crabwizard.position.x=0;
		crabwizard.position.y=0;
		crabwizard.texID=1;
		boneland = new Location("Boneland",200,200, 100);
		entities.add(boneland);
		boneland.texID=0;
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
		if(GAMETIME==300){
			//boneland.getFigurehead().setDestination(new Vector3(400,400,0));
		}
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
				int len2 = heroes.size();
				for(int i = 0; i<len2; i++){
					heroes.get(i).think();
					if(heroes.size()<len2){
						len2--;
						i--;
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
}
