package com.example.kokoko.libgdx.Screen;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.AbstractScreens;
import com.example.kokoko.libgdx.GameClass;
import com.example.kokoko.libgdx.AbstractScreens;

public class SelectScreen extends AbstractScreens implements Screen {

    public GameClass gameClass;
    private Skin skin;
    private OrthographicCamera camera;
    private TextButton onlineButton;
    private TextButton offlineButton;
    private TextButton backButton;
    private TextButton returnButton;
    private TextButton prevButton;
    private TextButton nextButton;
    private TextButton continueButton;
    private TextButton[] numberLevel;
    private Label onlineLabel;
    private Stage stage;
    private int moltiplicatore, nLvl;
    private boolean end;
    private final Preferences prefs;

    public SelectScreen(final GameClass gameClass){
        this.gameClass = gameClass;
        camera = new OrthographicCamera();

        skin = new Skin(Gdx.files.internal("skin.json"));
        prefs = Gdx.app.getPreferences("Zawardo");

        onlineButton = new TextButton("Online",skin);
        offlineButton = new TextButton("Offline", skin);
        backButton = new TextButton(Constant.BACK_TEXT, skin);
        continueButton = new TextButton("Continue",skin);
        returnButton = new TextButton(Constant.BACK_TEXT,skin);
        onlineLabel = new Label("Stiamo lavorando per voi", skin);
        prevButton = new TextButton(Constant.PREVIOUS_TEXT,skin);
        nextButton = new TextButton(Constant.NEXT_TEXT,skin);
        numberLevel = new TextButton[Constant.N_OF_LEVELS];

        for(int i = 0; i < Constant.N_OF_LEVELS; i++){
            numberLevel[i] = new TextButton(""+ (i+1), skin);
            numberLevel[i].getLabel().setFontScale(3);
            numberLevel[i].setBounds( 200 + (i*400), Gdx.graphics.getHeight()/2 - 200,200,200);
        }

        moltiplicatore = 0;
        end = false;
        nLvl = Constant.N_OF_LEVELS;
        // Grandezza font
        onlineButton.getLabel().setFontScale(3);
        offlineButton.getLabel().setFontScale(3);
        backButton.getLabel().setFontScale(3);
        continueButton.getLabel().setFontScale(3);
        returnButton.getLabel().setFontScale(3);
        onlineLabel.setFontScale(6);

        stage = new Stage();
        stage.clear();
        // posizione tasti
        onlineButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 + 100, 400, 200);
        offlineButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 200, 400, 200);
        backButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 500, 400, 200);
        onlineLabel.setBounds(Gdx.graphics.getWidth() / 2 - 400, Gdx.graphics.getHeight() / 2 - 200 , 400, 200);
        continueButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 + 200, 400, 200);
        returnButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 425, 400, 150);
        prevButton.setBounds(Gdx.graphics.getWidth() / 2 - 400, Gdx.graphics.getHeight() / 2 - 400, 100, 100);
        nextButton.setBounds(Gdx.graphics.getWidth() / 2 + 300, Gdx.graphics.getHeight() / 2 - 400, 100, 100);


        onlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeView(1);
            }
        } );

        offlineButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeView(2);
            }
        });

        returnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeView(0);
            }
        });

        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setNumLvl(gameClass.getnLvlMax() + 1);
                gameClass.setsScreen(Constant.NumeroScreen.GAMESCREEN);
                gameClass.setbSwitch(true);
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setsScreen(Constant.NumeroScreen.MENUSCREEN);
                gameClass.setbSwitch(true);
            }
        } );

        for(int i = 0; i < Constant.N_OF_LEVELS; i++){
            final int finalI = i;
            numberLevel[i].addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(gameClass.getnLvlMax() >= finalI) {
                        gameClass.setNumLvl(finalI + 1);
                        gameClass.setsScreen(Constant.NumeroScreen.GAMESCREEN);
                        gameClass.setbSwitch(true);
                    }
                }
            });

        }

        prevButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(moltiplicatore >= Constant.LVLS_PER_PAGE){
                    end = false;
                    nLvl += Constant.LVLS_PER_PAGE;
                    moltiplicatore -= Constant.LVLS_PER_PAGE;
                    stage.clear();
                    changeButton(moltiplicatore);
                }
                else{
                    Log.i("TAG", "ERRORE SONO ALLA PRIMA PAGINA  Messaggio maggico : " + nLvl + " moltplicatore : " + moltiplicatore);
                }

            }
        } );

        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!end){
                    nLvl -= Constant.LVLS_PER_PAGE;
                    moltiplicatore += Constant.LVLS_PER_PAGE;
                    stage.clear();
                    changeButton(moltiplicatore);
                }else{
                    Log.i("TAG", "ULTIMA PAGINA    Messaggio maggico : " + nLvl + " moltplicatore : " + moltiplicatore );
                }

            }
        } );


        stage.addActor(onlineButton);
        stage.addActor(offlineButton);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f,0.2f,0.1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameClass.batch.setProjectionMatrix(camera.combined);

        gameClass.batch.begin();

        gameClass.batch.end();

        stage.draw();
        stage.act();
    }

    private void changeView(int facciata){
        stage.clear();
        if(facciata == 0){
            stage.addActor(onlineButton);
            stage.addActor(offlineButton);
            stage.addActor(backButton);
        }
        else if(facciata == 1){
            stage.addActor(onlineLabel);
            stage.addActor(returnButton);
        }
        else if(facciata == 2){
            stage.addActor(continueButton);
            for(int i = 0; i < Math.min(nLvl ,Constant.LVLS_PER_PAGE); i++){
                stage.addActor(numberLevel[i]);
            }
            stage.addActor(prevButton);
            stage.addActor(nextButton);
            stage.addActor(returnButton);
        }


    }

    private void changeButton(int n){
        for(int i = 0; i < Math.min(nLvl,Constant.LVLS_PER_PAGE); i++){
            numberLevel[n+i].setBounds(200 + (i*400), Gdx.graphics.getHeight() / 2 - 200, 200, 200);

            if(nLvl <= Constant.NUM_FOR_PAGES) {
                end = true;
            }
            stage.addActor(numberLevel[n+i]);
        }


        stage.addActor(continueButton);
        stage.addActor(prevButton);
        stage.addActor(nextButton);
        stage.addActor(returnButton);
    }

}