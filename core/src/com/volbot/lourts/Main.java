package com.volbot.lourts;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.volbot.lourts.Agents.Agent;
import com.volbot.lourts.Agents.Hero;
import com.volbot.lourts.Agents.Individual;
import com.volbot.lourts.Data.Location;
import com.volbot.lourts.Input.InputManager;
import com.volbot.lourts.Map.GameMap;
import com.volbot.lourts.Render.Display;
import com.volbot.lourts.Render.TexLoader;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {

	private OrthographicCamera cam;
	private InputManager inputs;

	TexLoader texLoader;
	Display display;
	Individual crabwizard;
	Hero skeletrex;
	Location boneland;

	public static ArrayList<Agent> entities = new ArrayList<>();
	public static GameMap map;

	@Override
	public void create () {
		map = new GameMap();
		texLoader = new TexLoader();
		crabwizard = new Individual("crabwizard");
		entities.add(crabwizard);
		crabwizard.x=0;
		crabwizard.y=0;
		boneland = new Location("boneland",200,200);
		entities.add(boneland);
		skeletrex = boneland.getFigurehead();
		entities.add(skeletrex);
		skeletrex.setGoalPos(new int[]{-300, 100});
		cam = new OrthographicCamera();
		cam.setToOrtho(false,480,480);
		cam.position.x=320;
		cam.position.y=240;
		display = new Display(cam);
		display.displayEntity(crabwizard);
		display.displayEntity(skeletrex);
		display.displayEntity(boneland);
		inputs = new InputManager(cam);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		crabwizard.think();
		skeletrex.think();

		Agent clicked = inputs.checkEntityClicks();
		if(clicked!=null){
			for(String s : clicked.getInteractOptions(crabwizard)){
				System.out.println(s);
			}
		} else {
			inputs.parseCameraMovement(300);
			inputs.parsePlayerMovement(crabwizard);
		}
		display.loop();
	}
	
	@Override
	public void dispose () {
		display.dispose();
		texLoader.dispose();
	}
}
