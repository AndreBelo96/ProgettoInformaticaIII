package com.example.kokoko.libgdx;

import android.util.Log;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.Screen.GameScreen;

public class Player implements Entity{

    // Public enum for the possible player motion states
    public enum State { IDLE , JUMPING};
    
    // Variables to save the value of player motion state
    private State currentState, previousState;
    
    // Variables for the position 
    private Vector2 pos, worldPos;
    
    // Variables for the animation and rendering
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> move,idle;
    private GameScreen screen;
    private float time,  animationMove_Time, Time_Control_Vel;
    private boolean bool_move, startAnimMove, canIJump;
    
    // Variable for the player movement direction state
    private int wasd;

    // Constructor for the player class where the player position is initialized and the world position is set as origin, all the other variables to control the player movement are set at their default starting value
    public Player(GameScreen screen){
        this.pos = new Vector2(Constant.PLAYER_POS_INIT_X,Constant.PLAYER_POS_INIT_Y);
        this.worldPos = new Vector2(0,0);
        this.screen = screen;
        time = 0;
        wasd = 0;
        canIJump = true;
        Time_Control_Vel = 0;
        animationMove_Time = 0;
        currentState = State.IDLE;
        previousState = State.IDLE;
        startAnimMove = false;
        bool_move = false;
        // The animation frames are parsed from the sprite which cointains all the frames based on the x position
        frames = new Array<TextureRegion>();
        for(int i = 0 ; i < 11; i++)
            frames.add(new TextureRegion(this.screen.getAtlas().findRegion("Slime"), i * 20, 0, 20 ,21));
        idle = new Animation(5f, frames, Animation.PlayMode.LOOP);
        frames.clear();
        for(int i = 11 ; i < 28; i++)
            frames.add(new TextureRegion(this.screen.getAtlas().findRegion("Slime"), i * 20, 0, 20 ,21));
        move = new Animation(5f, frames, Animation.PlayMode.NORMAL);
        frames.clear();
    }

    // The batch draws a rectangle based on the setRegion and the position given to the player
    @Override
    public void render(SpriteBatch batch) {batch.draw(setRegion(time),pos.x,pos.y);}

    // Moves the player sprite based on the movement input (the wasd variable) and checks if the movement is possible or if the animation is still playing
    @Override
    public void update(float delta) {
        time = currentState == previousState ? time + delta : 0;

        Time_Control_Vel += delta;

        if(startAnimMove){
            animationMove_Time += delta;
        }

        //System.out.println("TIMECONTROL: " + Time_Control_Vel + " millisecondi: ");

        //Log.i("TAG: " + (Time_Control_Vel <= (Constant.MOVE_TIME - 0.3f))," time control vel >= 0.3: " + (Time_Control_Vel >= 0.3f) + " bool move : " + bool_move);
        //TODO controllo prima variabile bool qua sotto
        if( Time_Control_Vel <= (Constant.MOVE_TIME - 0.3f) && Time_Control_Vel >= 0.3f && bool_move){

            switch(wasd){
                case 1:
                    pos.x -= Constant.PLAYER_SPOS_INIT_X;
                    pos.y += Constant.PLAYER_SPOS_INIT_Y;
                    Log.i("TAG: " ," SWITCH   Player POSX: " + pos.x + "Player POS.y : " + pos.y);
                    break;
                case 2:
                    pos.x += Constant.PLAYER_SPOS_INIT_X;
                    pos.y -= Constant.PLAYER_SPOS_INIT_Y;
                    Log.i("TAG: " ," SWITCH   Player POSX: " + pos.x + "Player POS.y : " + pos.y);
                    break;
                case 3:
                    pos.x -= Constant.PLAYER_SPOS_INIT_X;
                    pos.y -= Constant.PLAYER_SPOS_INIT_Y;
                    break;
                case 4:
                    pos.x += Constant.PLAYER_SPOS_INIT_X;
                    pos.y += Constant.PLAYER_SPOS_INIT_Y;
                    break;
                default:
            }
        }

    }

    // Activates the state to enable movement and defines the movement direction in world axis
    public void move(Constant.direzioni dir){
        if(canIJump && !currentState.equals(State.JUMPING)){
            switch(dir){
                case TOPLEFT:
                    bool_move = true;
                    canIJump = false;
                    Time_Control_Vel = 0;
                    wasd = 1;
                    worldPos.x += 1;
                    currentState = State.JUMPING;
                    break;
                case BOTTOMRIGHT:
                    bool_move = true;
                    canIJump = false;
                    Time_Control_Vel = 0;
                    wasd = 2;
                    worldPos.x -= 1;
                    currentState = State.JUMPING;
                    break;
                case BOTTOMLEFT:
                    bool_move = true;
                    canIJump = false;
                    Time_Control_Vel = 0;
                    wasd = 3;
                    worldPos.y -= 1;
                    currentState = State.JUMPING;
                    break;
                case TOPRIGHT:
                    bool_move = true;
                    canIJump = false;
                    Time_Control_Vel = 0;
                    worldPos.y += 1;
                    wasd = 4;
                    currentState = State.JUMPING;
                    break;
                default:
            }

        }
    }

    // Standard getter for the variable worldPos
    public Vector2 getPos(){
        return this.worldPos;
    }

    // Returns the region of the texture to use based on the kind of animation (idle or jumping) and the time
    public TextureRegion setRegion(float dt){
        if(bool_move){
            if(animationMove_Time >= Constant.MOVE_TIME){
                bool_move = false;
                canIJump = true;
                screen.setBool_switch(true);
                previousState = State.JUMPING;
                currentState = State.IDLE;
            }
            previousState = State.JUMPING;
            startAnimMove = true;
            return move.getKeyFrame( dt,true );
        }
        else {
            animationMove_Time = 0;
            startAnimMove = false;
            previousState = State.IDLE;
            return idle.getKeyFrame( dt ,true);
        }

    }

    // Getter for the boolean variable CanIJump
    public boolean isCanIJump() {
        return canIJump;
    }

}
