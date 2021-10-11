package com.example.kokoko.libgdx.Screen;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.example.kokoko.libgdx.Background;
import com.example.kokoko.libgdx.GameClass;

/**Classe per selezionare il livello*/
public class SelectScreen extends AbstractScreens implements Screen {

    public GameClass gameClass;
    private final Skin skin;
    private final OrthographicCamera camera;
    private final TextButton onlineButton;
    private final TextButton offlineButton;
    private final TextButton backButton;
    private final TextButton returnButton;
    private final TextButton prevButton;
    private final TextButton nextButton;
    private final TextButton continueButton;
    private final TextButton[] numberLevel;
    private final Label onlineLabel;
    private final Stage stage;
    private int moltiplicatore;
    private int numLvl;
    private boolean end;
    private final Background rectBK;

    public SelectScreen(final GameClass gameClass) {
        this.gameClass = gameClass;
        camera = new OrthographicCamera();

        skin = new Skin(Gdx.files.internal("skin.json"));

        onlineButton = new TextButton("Online", skin);
        offlineButton = new TextButton("Offline", skin);
        backButton = new TextButton(Constant.BACK_TEXT, skin);
        continueButton = new TextButton("Continue", skin);
        returnButton = new TextButton(Constant.BACK_TEXT, skin);
        onlineLabel = new Label("Stiamo lavorando per voi", skin);
        prevButton = new TextButton(Constant.PREVIOUS_TEXT, skin);
        nextButton = new TextButton(Constant.NEXT_TEXT, skin);
        numberLevel = new TextButton[Constant.N_OF_LEVELS];

        rectBK = new Background();

        for (int i = 0; i < Constant.N_OF_LEVELS; i++) {
            numberLevel[i] = new TextButton("" + (i + 1), skin);
            numberLevel[i].getLabel().setFontScale(3);
            numberLevel[i].setBounds(200 + (i * 400), Gdx.graphics.getHeight() / 2f - 200, 200, 200);
        }

        moltiplicatore = 0;
        end = false;
        numLvl = Constant.N_OF_LEVELS;

        stage = new Stage();
        stage.clear();

        setButtonPosition();
        setButton();

        stage.addActor(onlineButton);
        stage.addActor(offlineButton);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(this.stage);
    }

    private void setButton() {
        onlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeView(1);
            }
        });

        offlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeView(2);
            }
        });

        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeView(0);
            }
        });

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameClass.setNumLvl(GameClass.getnLvlMax() + 1);
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
        });

        for (int i = 0; i < Constant.N_OF_LEVELS; i++) {
            final int finalI = i;
            numberLevel[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (GameClass.getnLvlMax() >= finalI) {
                        GameClass.setNumLvl(finalI + 1);
                        gameClass.setsScreen(Constant.NumeroScreen.GAMESCREEN);
                        gameClass.setbSwitch(true);
                    }
                }
            });

        }

        prevButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (moltiplicatore >= Constant.LVLS_PER_PAGE) {
                    end = false;
                    numLvl += Constant.LVLS_PER_PAGE;
                    moltiplicatore -= Constant.LVLS_PER_PAGE;
                    stage.clear();
                    changeButton(moltiplicatore);
                }
                else {
                    Log.i("PREV", "PRIMA PAGINA");
                }

            }
        });

        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!end) {
                    numLvl -= Constant.LVLS_PER_PAGE;
                    moltiplicatore += Constant.LVLS_PER_PAGE;
                    stage.clear();
                    changeButton(moltiplicatore);
                }
                else {
                    Log.i("NEXT", "ULTIMA PAGINA");
                }

            }
        });
    }

    private void setButtonPosition() {
        // Grandezza font
        onlineButton.getLabel().setFontScale(3);
        offlineButton.getLabel().setFontScale(3);
        backButton.getLabel().setFontScale(3);
        continueButton.getLabel().setFontScale(3);
        returnButton.getLabel().setFontScale(3);
        onlineLabel.setFontScale(6);
        // posizione tasti
        onlineButton.setBounds(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f + 100, 400, 200);
        offlineButton.setBounds(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f - 200, 400, 200);
        backButton.setBounds(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f - 500, 400, 200);
        onlineLabel.setBounds(Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f - 200, 400, 200);
        continueButton.setBounds(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f + 200, 400, 200);
        returnButton.setBounds(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f - 425, 400, 150);
        prevButton.setBounds(Gdx.graphics.getWidth() / 2f - 400, Gdx.graphics.getHeight() / 2f - 400, 100, 100);
        nextButton.setBounds(Gdx.graphics.getWidth() / 2f + 300, Gdx.graphics.getHeight() / 2f - 400, 100, 100);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.65f, 0.65f, 0.65f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GameClass.batch.setProjectionMatrix(camera.combined);

        GameClass.batch.begin();
        GameClass.batch.setColor(StringToColor(GameClass.getRectColor()));
        rectBK.render(GameClass.batch);
        GameClass.batch.setColor(Color.WHITE);

        GameClass.batch.end();

        if (GameClass.isDynamicBkgrd()) {
            rectBK.moveRect();
            rectBK.resetRect();
        } else
            Log.i("MenuScreen SCREEN:" , "No movement");

        if (GameClass.getBkgrndType()) {
            rectBK.rotateYRect();
            rectBK.rotateXRect();
        } else
            Log.i("MenuScreen SCREEN:" , "No rotation");


        stage.draw();
        stage.act();
    }

    private Color StringToColor(String s) {
        switch (s) {
            case "WHITE":
                return Color.WHITE;
            case "GOLD":
                return Color.GOLD;
            case "RED":
                return Color.RED;
            case "BLUE":
                return Color.BLUE;
            case "BLACK":
                return Color.BLACK;
            default:
                return Color.WHITE;
        }

    }

    private void changeView(int facciata) {
        stage.clear();
        if (facciata == 0) {
            stage.addActor(onlineButton);
            stage.addActor(offlineButton);
            stage.addActor(backButton);
        }
        else if (facciata == 1) {
            stage.addActor(onlineLabel);
            stage.addActor(returnButton);
        }
        else if (facciata == 2) {
            stage.addActor(continueButton);
            for (int i = 0; i < Math.min(numLvl, Constant.LVLS_PER_PAGE); i++) {
                stage.addActor(numberLevel[i]);
            }
            stage.addActor(prevButton);
            stage.addActor(nextButton);
            stage.addActor(returnButton);
        }

    }

    private void changeButton(int numberButton) {
        for (int i = 0; i < Math.min(numLvl, Constant.LVLS_PER_PAGE); i++) {
            numberLevel[numberButton + i].setBounds(200 + (i * 400), Gdx.graphics.getHeight() / 2f - 200, 200, 200);

            if (numLvl <= Constant.NUM_FOR_PAGES) {
                end = true;
            }
            stage.addActor(numberLevel[numberButton + i]);
        }

        stage.addActor(continueButton);
        stage.addActor(prevButton);
        stage.addActor(nextButton);
        stage.addActor(returnButton);
    }

}