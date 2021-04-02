package com.example.kokoko.libgdx;

import android.content.res.AssetManager;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.kokoko.android.activities.GdxActivity;
import com.example.kokoko.libgdx.Screen.GameScreen;
import com.example.kokoko.libgdx.Screen.LoseScreen;
import com.example.kokoko.libgdx.Screen.MenuScreen;
import com.example.kokoko.libgdx.Screen.WinScreen;

public class GameClass extends ApplicationAdapter {


    // Variables to change the different type on Screen in LibGdx
    public Screens sActualScreen;
    public short sScreen;
    public boolean bSwitch;
    
    // Variable to define the delta time for the render method across the application
    public float dt;
    
    // Variable to call the user's stored preferences
    public static Preferences prefs;
    
    // Variables to implement Gdx inside the application and add the GUI
    public static SpriteBatch batch;
    public static AssetManager as;
    public static GdxActivity ga;

    // User's variables in pref
    private static int nLvlMax,punteggio,nLvl;
    private static String nickName;
    private static boolean sound;

    // Constructor for the GameClass class which initializes the AssetManager and the GdxActivity
    public GameClass(AssetManager as, GdxActivity ga){
        super();
        this.as = as;
        this.ga = ga;
    }

    @Override
    public void create() {
        // Create preferences to save in our local memory user's data (number of level, points, nickname and sound options)
        prefs = Gdx.app.getPreferences("My preferences");
        nLvlMax = prefs.getInteger("nLvl", 0);
        punteggio = prefs.getInteger("punteggio", 0);
        nickName = prefs.getString("nickname", "Guest"); //TODO aggiungere identificativo?
        sound = prefs.getBoolean("sound", true);
        nLvl = 0;
        
        // An instance of the MenuScreen is added to this to class as the starting screen and the screen options are initialized
        sActualScreen = new MenuScreen(this);
        batch = new SpriteBatch();
        sScreen = 0;
        bSwitch =false;
    }

    // Resizes the screen with the input width and height
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    // Defines the method render enabling his esecution if the variable bSwitch is true; the delta time for the render function is hardcoded
    @Override
    public void render() {
        if(bSwitch) setScreen();
            sActualScreen.render(dt);
    }

    // Redefines the dispose method to only apply if the current screen is not null to avoid nullPointerExceptions
    @Override
    public void dispose() {
        if(sActualScreen!=null)
            sActualScreen.dispose();
    }

    // Modifies the actual active screen by choosing based on the input value and updates the user data on load of the new screen
    public void setScreen(){
        if(sScreen == 0) {
            sActualScreen = new MenuScreen(this);
            ga.updateUI(nLvlMax, punteggio,nickName);
        }
        else if(sScreen == 1) {
            sActualScreen = new GameScreen(this);
            ga.updateUI(nLvlMax, punteggio,nickName);
        }
        else if(sScreen == 2) {
            sActualScreen = new WinScreen(this);
        }
        else if(sScreen == 4){
            sActualScreen = new LoseScreen(this);
        }
        bSwitch = false;
    }

    // Standard getter for the variable nLvlMax
    public static int getnLvlMax() {
        return nLvlMax;
    }

    // Standard getter for the variable nLvl
    public static int getnLvl() {
        return nLvl;
    }

    public static void setnLvl(int nLvl) {
        GameClass.nLvl = nLvl;
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



}
