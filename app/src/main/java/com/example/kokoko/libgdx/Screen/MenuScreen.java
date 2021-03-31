package com.example.kokoko.libgdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.GameClass;
import com.example.kokoko.libgdx.Screens;
import com.google.firebase.auth.FirebaseAuth;

public class MenuScreen extends Screens implements Screen {

    public GameClass gameClass;
    private Skin skin;
    private OrthographicCamera camera;
    private TextButton playButton,exitButton;
    private Stage stage;
    private final Preferences prefs;
    private Texture title;

    //TODO inserire un'immagine per il titolo
    private BitmapFont font;


    public MenuScreen(final GameClass gameClass){

        this.gameClass = gameClass;
        camera = new OrthographicCamera();

        skin = new Skin(Gdx.files.internal("skin.json"));
        prefs = Gdx.app.getPreferences("Zawardo");

        title = new Texture(Gdx.files.internal("Titolo.png"));


        playButton = new TextButton("Play",skin);

        exitButton = new TextButton(Constant.EXIT_TEXT,skin);

        playButton.getLabel().setFontScale(3);

        exitButton.getLabel().setFontScale(3);

        stage = new Stage();
        stage.clear();

        playButton.setBounds(Gdx.graphics.getWidth()/2 - 200,Gdx.graphics.getHeight()/2 - 100,400,150); //TODO modifica questi numeri con costanti

        exitButton.setBounds(Gdx.graphics.getWidth()/2 + 500,Gdx.graphics.getHeight()/2 - 500,400,150);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                gameClass.sScreen = 1;
                gameClass.bSwitch = true;
            }
        } );



        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }

        } );


        stage.addActor(playButton);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(this.stage);

    }


    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f,0.2f,0.1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameClass.batch.setProjectionMatrix(camera.combined);

        gameClass.batch.begin();
        gameClass.batch.draw(title, -0.48f, .5f, 0.96f, 0.38f);
        gameClass.batch.end();

        stage.draw();
        stage.act();
    }

    public void dispose(){
        gameClass.batch.dispose();
        stage.dispose();

    }




}

/** Il batch nel render va a chiedermi le posizioni a livello locale, dell'immagine stessa, quindi devo scriverle con un esponenziale di ^-2 (il punto di origine qui è il centro dello schermo )
 * Quando invece vado a lavorare sui tasti presenti nell'activity parlo di posizioni globali rispetto quini allo schermo, dove lo (0;0) corrisponde al centro dello schermo
 */