package com.example.kokoko.libgdx;

import android.content.res.AssetManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.kokoko.Constant;
import com.example.kokoko.android.activities.GdxActivity;
import com.example.kokoko.libgdx.Screen.GameScreen;
import com.example.kokoko.libgdx.Screen.LoseScreen;
import com.example.kokoko.libgdx.Screen.MenuScreen;
import com.example.kokoko.libgdx.Screen.OptionScreen;
import com.example.kokoko.libgdx.Screen.WinScreen;

/** Classe per la creazione del game */
public class GameClass extends ApplicationAdapter {
    // Variable to call the user's stored preferences
    public static Preferences prefs;
    // Variables to implement Gdx inside the application and add the GUI
    public static SpriteBatch batch;
    public static AssetManager assetManager;
    private static GdxActivity gdxActivity;
    // User's variables in pref
    private static int nLvlMax;
    private static int punteggio;
    private static int numLvl;
    private static String nickName;
    private static boolean sound;
    // Variables to change the different type on Screen in LibGdx
    private AbstractScreens sActualScreen;
    private Constant.NumeroScreen sScreen;
    private boolean bSwitch;
    // Variable to define the delta time for the render method across the application
    private float deltaTime;

    // Constructor for the GameClass class which initializes the AssetManager and the GdxActivity
    public GameClass(AssetManager assetManager, GdxActivity gdxActivity) {
        super();
        this.assetManager = assetManager;
        this.gdxActivity = gdxActivity;
    }

    @Override
    public void create() {
        // Create preferences to save in our local memory user's data (number of level, points, nickname and sound options)
        prefs = Gdx.app.getPreferences("My preferences");
        nLvlMax = prefs.getInteger("nLvl", 0);
        punteggio = prefs.getInteger("punteggio", 0);
        nickName = prefs.getString("nickname", "Guest");
        sound = prefs.getBoolean("sound", true);
        numLvl = 0;
        
        // An instance of the MenuScreen is added to this to class as the starting screen and the screen options are initialized
        sActualScreen = new MenuScreen(this);
        batch = new SpriteBatch();
        sScreen = Constant.NumeroScreen.MENUSCREEN;
        bSwitch = false;
    }

    // Resizes the screen with the input width and height
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    // Defines the method render enabling his esecution if the variable bSwitch is true; the delta time for the render function is hardcoded
    @Override
    public void render() {
        if (bSwitch) {
            setScreen();
        }
        sActualScreen.render(deltaTime);
    }

    // Redefines the dispose method to only apply if the current screen is not null to avoid nullPointerExceptions
    @Override
    public void dispose() {
        if (sActualScreen != null) {
            sActualScreen.dispose();
        }

    }

    // Modifies the actual active screen by choosing based on the input value and updates the user data on load of the new screen
    public Constant.NumeroScreen setScreen() {
        Constant.NumeroScreen i = null;
        if (sScreen == Constant.NumeroScreen.MENUSCREEN) {
            sActualScreen = new MenuScreen(this);
            gdxActivity.updateUserInterface(nLvlMax, punteggio, nickName);
            i = Constant.NumeroScreen.MENUSCREEN;
        }
        else if (sScreen == Constant.NumeroScreen.GAMESCREEN) {
            sActualScreen = new GameScreen(this);
            gdxActivity.updateUserInterface(nLvlMax, punteggio, nickName);
            i = Constant.NumeroScreen.GAMESCREEN;
        }
        else if (sScreen == Constant.NumeroScreen.WINSCREEN) {
            sActualScreen = new WinScreen(this);
            i = Constant.NumeroScreen.WINSCREEN;
        }
        else if (sScreen == Constant.NumeroScreen.LOSESCREEN) {
            sActualScreen = new LoseScreen(this);
            i = Constant.NumeroScreen.LOSESCREEN;
        }
        else if (sScreen == Constant.NumeroScreen.OPTIONSCREEN) {
            sActualScreen = new OptionScreen(this);
            i = Constant.NumeroScreen.OPTIONSCREEN;
        }
        bSwitch = false;
        return i;
    }

    // Standard getter for the variable nLvlMax
    public static int getnLvlMax() {
        return nLvlMax;
    }

    // Standard getter for the variable nLvl
    public static int getNumLvl() {
        return numLvl;
    }

    public static void setNumLvl(int numLvl) {
        GameClass.numLvl = numLvl;
    }

    // Standard setter for the variable nLvl
    public static void setnLvlMax(int nLvlMax) {
        GameClass.nLvlMax = nLvlMax;
    }

    // Standard getter for the variable punteggio
    public static int getPunteggio() {
        return punteggio;
    }

    // Standard setter for the variable punteggio
    public static void setPunteggio(int punteggio) {
        GameClass.punteggio = punteggio;
    }

    // Standard getter for the variable nickName
    public static String getNickName() {
        return nickName;
    }

    // Standard setter for the variable nickName
    public static void setNickName(String nickName) {
        GameClass.nickName = nickName;
    }

    public void setsScreen(Constant.NumeroScreen sScreen) {
        this.sScreen = sScreen;
    }

    public void setbSwitch(boolean bSwitch) {
        this.bSwitch = bSwitch;
    }

}
