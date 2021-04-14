package com.example.kokoko;

import com.badlogic.gdx.Gdx;

/** classi costanti */
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
    public static final float TILE_SPOS_INIT_X = TILE_WIDHT / 2;
    public static final float TILE_SPOS_INIT_Y = TILE_HEIGHT / 2 - BORDER_HEIGHT / 2;

    //CAMERA
    public static final int CAMERA_MOVEMENT_X = TILE_WIDHT / 2;
    public static final int CAMERA_MOVEMENT_Y = TILE_HEIGHT / 2 - BORDER_HEIGHT / 2;

    //PLAYER
    public static final int PLAYER_WIDHT = 20;
    public static final int PLAYER_HEIGHT = 21;
    public static final float PLAYER_POS_INIT_X = TILE_WIDHT / 2 - PLAYER_WIDHT / 2;
    public static final float PLAYER_POS_INIT_Y = TILE_HEIGHT / 2 - PLAYER_HEIGHT / 2 + BORDER_HEIGHT;
    public static final float PLAYER_SPOS_INIT_X = TILE_WIDHT / 2;
    public static final float PLAYER_SPOS_INIT_Y = TILE_HEIGHT / 2 - BORDER_HEIGHT / 2;

    //ARROWS
    public static final int ARROW_WIDHT = 13;
    public static final int ARROW_HEIGHT = 10;
    public static final float ARROW_SPOS_INIT_X = ARROW_WIDHT / 2;
    public static final float ARROW_SPOS_INIT_Y = ARROW_HEIGHT / 2 - BORDER_HEIGHT / 2;

    //TIMER
    public static final float IDLE_TIME = 10f * 0.2f;
    public static final float MOVE_TIME = 17f * 0.1f;
    public static final float FLY_TIME = .86f;

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
    public static final int N_OF_LEVELS = 6;
    public static final int FIRST_LEVEL = 0;
    public static final int LVLS_PER_PAGE = 4;
    public static final String TILE_IN_START_POS = "1";

    /** ENUM DIREZIONI PLAYER*/
    public enum Direzioni {
        TOPLEFT,
        TOPRIGHT,
        BOTTOMLEFT,
        BOTTOMRIGHT
    }

    /** ENUM NUMERO DI SCREEN*/
    public enum NumeroScreen {
        MENUSCREEN,
        GAMESCREEN,
        WINSCREEN,
        LOSESCREEN,
        OPTIONSCREEN,
        SCOREBOARDSCREEN,
        SELECTSCREEN
    }

    //ACTIVITY CONSTANT STRING
    public static final String NAME = "Name";
    public static final String DATA = "Data";
    public static final String TOTAL_POINTS = "Total Points";
    public static final String E_MAIL = "eMail";

    //ACTIVITY CONSTANT INT
    public static final int MIN_PASS_CHAR = 6;

    //ATLAS REGION STRING
    public static final String SLIME_REGION = "Slime";

    //ATLAS REGION INT
    public static final int IDLE_SLIME_REGION = 11;
    public static final int MOVE_SLIME_REGION = 28;
    public static final float SLIME_FRAME_DURATION = 5f;
}
