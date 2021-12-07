package com.volbot.lourts.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.volbot.lourts.Agents.Individual;

public class InputManager {

    Camera cam;

    public InputManager(Camera camera){
        cam = camera;
    }

    private Vector3 positionClick(Vector3 touchPos){
        return new Vector3(
                touchPos.x-cam.position.x,
                touchPos.y-cam.position.y,
                0
        );
    }

    public void parsePlayerMovement(Individual player){
        if(Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(touchPos);
            touchPos = positionClick(touchPos);
            player.setGoalPos(new int[]{
                    (int)(touchPos.x),
                    (int)(touchPos.y)}
            );
        }
    }

    public void parseCameraMovement(int moveSpeed){
        boolean[] inputs = new boolean[]{false,false,false,false};
        int dirs = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            inputs[0]=true;
            dirs++;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            inputs[1]=true;
            dirs++;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            inputs[2]=true;
            dirs++;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            inputs[3]=true;
            dirs++;
        }
        if (inputs[0]&&inputs[1]) {
            inputs[0]=false;
            inputs[1]=false;
        }else if (inputs[2]&&inputs[3]) {
            inputs[2]=false;
            inputs[3]=false;
        }
        if(dirs==2){
            double temp = Math.pow(moveSpeed,2);
            temp /= 2;
            temp=Math.sqrt(temp);
            moveSpeed=(int)Math.ceil(temp);
        }
        moveSpeed*=Gdx.graphics.getDeltaTime();
        if(inputs[0]){
            cam.position.y-=moveSpeed;
        }
        if(inputs[1]){
            cam.position.y+=moveSpeed;
        }
        if(inputs[2]){
            cam.position.x+=moveSpeed;
        }
        if(inputs[3]){
            cam.position.x-=moveSpeed;
        }
    }
}
