package com.example.kokoko.libgdx;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.Screen.GameScreen;

/** Classe del player del giocatore */
public class Player implements Entity {

    /** Public enum for the possible player motion states */
    public enum State {
        IDLE,
        JUMPING
    }
    
    // Variables to save the value of player motion state
    private State currentState;
    private State previousState;

    // Variables for the position 
    private Vector2 pos;
    private Vector2 worldPos;

    // Variables for the animation and rendering
    private Array<TextureRegion> frames;
    private Animation<TextureRegion> move;
    private Animation<TextureRegion> idle;
    private GameScreen screen;
    private float time;
    private float animationMoveTime;
    private float timeControlVel;
    private boolean bool_move;
    private boolean startAnimMove;
    private boolean canJump;
    
    // Variable for the player movement direction state
    private int wasd;

    // Constructor for the player class where the player position is initialized and the world position is set as origin, all the other variables to control the player movement are set at their default starting value
    public Player(GameScreen screen) {
        this.pos = new Vector2(Constant.PLAYER_POS_INIT_X, Constant.PLAYER_POS_INIT_Y);
        this.worldPos = new Vector2(0, 0);
        this.screen = screen;
        time = 0;
        wasd = 0;
        canJump = true;
        timeControlVel = 0;
        animationMoveTime = 0;
        currentState = State.IDLE;
        previousState = State.IDLE;
        startAnimMove = false;
        bool_move = false;
        // The animation frames are parsed from the sprite which cointains all the frames based on the x position
        frames = new Array<TextureRegion>();
        for (int i = 0; i < Constant.IDLE_SLIME_REGION; i++)
            frames.add(new TextureRegion(this.screen.getAtlas().findRegion(Constant.SLIME_REGION), i * Constant.PLAYER_WIDHT, 0, Constant.PLAYER_WIDHT, Constant.PLAYER_HEIGHT));
        idle = new Animation(Constant.SLIME_FRAME_DURATION, frames, Animation.PlayMode.LOOP);
        frames.clear();
        for (int i = Constant.IDLE_SLIME_REGION; i < Constant.MOVE_SLIME_REGION; i++)
            frames.add(new TextureRegion(this.screen.getAtlas().findRegion(Constant.SLIME_REGION), i * Constant.PLAYER_WIDHT, 0, Constant.PLAYER_WIDHT, Constant.PLAYER_HEIGHT));
        move = new Animation(Constant.SLIME_FRAME_DURATION, frames, Animation.PlayMode.NORMAL);
        frames.clear();
    }

    // The batch draws a rectangle based on the setRegion and the position given to the player
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(setRegion(time), pos.x, pos.y);
    }

    // Moves the player sprite based on the movement input (the wasd variable) and checks if the movement is possible or if the animation is still playing
    @Override
    public void update(float delta) {

        if (currentState == previousState)
            time += delta;
        else
            time = 0;

        timeControlVel += delta;

        if (startAnimMove) {
            animationMoveTime += delta;
        }

        if (timeControlVel <= (Constant.MOVE_TIME - 0.3f) && timeControlVel >= 0.3f && bool_move) {

            switch (wasd) {
                case 1:
                    pos.x -= Constant.PLAYER_SPOS_INIT_X;
                    pos.y += Constant.PLAYER_SPOS_INIT_Y;
                    break;
                case 2:
                    pos.x += Constant.PLAYER_SPOS_INIT_X;
                    pos.y -= Constant.PLAYER_SPOS_INIT_Y;
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
    public void move(Constant.Direzioni dir) {
        if (canJump && !currentState.equals(State.JUMPING)) {
            switch (dir) {
                case TOPLEFT:
                    bool_move = true;
                    canJump = false;
                    timeControlVel = 0;
                    wasd = 1;
                    worldPos.x += 1;
                    currentState = State.JUMPING;
                    break;
                case BOTTOMRIGHT:
                    bool_move = true;
                    canJump = false;
                    timeControlVel = 0;
                    wasd = 2;
                    worldPos.x -= 1;
                    currentState = State.JUMPING;
                    break;
                case BOTTOMLEFT:
                    bool_move = true;
                    canJump = false;
                    timeControlVel = 0;
                    wasd = 3;
                    worldPos.y -= 1;
                    currentState = State.JUMPING;
                    break;
                case TOPRIGHT:
                    bool_move = true;
                    canJump = false;
                    timeControlVel = 0;
                    worldPos.y += 1;
                    wasd = 4;
                    currentState = State.JUMPING;
                    break;
                default:
            }

        }
    }

    // Standard getter for the variable worldPos
    public Vector2 getPos() {
        return this.worldPos;
    }

    // Returns the region of the texture to use based on the kind of animation (idle or jumping) and the time
    public TextureRegion setRegion(float delta_time) {
        final TextureRegion textureRegion;
        if (bool_move) {
            if (animationMoveTime >= Constant.MOVE_TIME) {
                bool_move = false;
                canJump = true;
                screen.setBoolSwitch(true);
                previousState = State.JUMPING;
                currentState = State.IDLE;
            }
            previousState = State.JUMPING;
            startAnimMove = true;
            textureRegion = move.getKeyFrame(delta_time, true);
        }
        else {
            animationMoveTime = 0;
            startAnimMove = false;
            previousState = State.IDLE;
            textureRegion = idle.getKeyFrame(delta_time, true);
        }
        return textureRegion;
    }

    // Getter for the boolean variable CanIJump
    public boolean isCanJump() {
        return canJump;
    }

}
