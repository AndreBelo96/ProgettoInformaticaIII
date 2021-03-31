package com.example.kokoko;

import com.badlogic.gdx.Gdx;


public class Constant {
    //WORLD
    public static final int WIDTH = Gdx.graphics.getWidth();
    public static final int HEIGHT = Gdx.graphics.getHeight();
    public static final int NUMERO_PIXEL_X = 288;
    public static final int NUMERO_PIXEL_Y = 96;

    //TILE
    public static final int TILE_WIDHT = 64;
    public static final int TILE_HEIGHT = 32;
    public static final float REAL_WIDTH = WIDTH / NUMERO_PIXEL_X;
    public static final float REAL_HEIGHT = HEIGHT / NUMERO_PIXEL_Y;
    public static final int BORDER_HEIGHT = 6;
    public static final float TILE_SPOS_INIT_X = TILE_WIDHT/2;
    public static final float TILE_SPOS_INIT_Y = TILE_HEIGHT/2 - BORDER_HEIGHT/2;

    //CAMERA
    public static final int CAMERA_MOVEMENT_X = TILE_WIDHT/2;
    public static final int CAMERA_MOVEMENT_Y = TILE_HEIGHT/2-BORDER_HEIGHT/2;

    //PLAYER
    public static final int PLAYER_WIDHT = 20;
    public static final int PLAYER_HEIGHT = 21;
    public static final float PLAYER_POS_INIT_X = TILE_WIDHT/2 - PLAYER_WIDHT/2;
    public static final float PLAYER_POS_INIT_Y = TILE_HEIGHT/2 - PLAYER_HEIGHT/2 + BORDER_HEIGHT;
    public static final float PLAYER_SPOS_INIT_X = TILE_WIDHT/2;
    public static final float PLAYER_SPOS_INIT_Y = TILE_HEIGHT/2 -  BORDER_HEIGHT/2;

    //TIMER
    public static final float IDLE_TIME =  10f * 0.2f;
    public static final float MOVE_TIME =  17f * 0.1f;
    public static final float FLY_TIME =  .86f;//17f * 0.1f;

    /*public static final float MOVE_VEL_PER_PIXEL_X = (TILE_WIDHT/2) / 15f;
    public static final float MOVE_VEL_PER_PIXEL_Y = (PLAYER_MOVEMENT_Y) / 15f;*/

    //TEXT CONSTANT
    public static final String WIN_TEXT = "LEVEL CLEARED";
    public static final String LOSE_TEXT = "LEVEL FAILED";
    public static final String RELOAD_LVL_TEXT = "Reload Level";
    public static final String NEXT_LVL_TEXT = "Next Level";
    public static final String EXIT_TEXT = "EXIT GAME";
    public static final String NEXT_TEXT = ">";
    public static final String PREVIOUS_TEXT = "<";
    public static final String BACK_TEXT = "Back";

    //SCOREBOARD/POINTS
    public static final int NUM_FOR_PAGES = 5;
    public static final int PUNTEGGIO_PER_TILE = 20;

    //BUTTONS DIMENSIONS


    //LEVELS
    public static final int N_OF_LEVELS = 6,FIRST_LEVEL = 0;
    public static final int LVLS_PER_PAGE = 4;

    //ENUM
    public enum direzioni {
        TOPLEFT,
        TOPRIGHT,
        BOTTOMLEFT,
        BOTTOMRIGHT;
    }
}
