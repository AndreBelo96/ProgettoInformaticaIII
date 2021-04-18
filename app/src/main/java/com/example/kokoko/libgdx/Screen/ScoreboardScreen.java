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

/** Classe per lo scoreboard */
public class ScoreboardScreen extends AbstractScreens implements Screen {

    private static final int TEXT_WIDTH = 800;

    public GameClass gameClass;
    private Skin skin;
    private OrthographicCamera camera;
    private TextButton backButton;
    private TextButton prevButton;
    private TextButton nextButton;
    private Label[] classifica;
    private Stage stage;
    private final Preferences prefs;
    private int nPlayer;
    private int moltiplicatore;
    private boolean end;
    private String[] nomi;
    private int[] punti;

    public ScoreboardScreen(final GameClass gameClass) {

        this.gameClass = gameClass;
        camera = new OrthographicCamera();

        //dati per la scoreboard
        nomi = GameClass.gdxActivity.getNomi();
        punti = GameClass.gdxActivity.getPunti();

        skin = new Skin(Gdx.files.internal("skin.json"));
        prefs = Gdx.app.getPreferences("Zawardo");

        backButton = new TextButton(Constant.BACK_TEXT, skin);
        prevButton = new TextButton(Constant.PREVIOUS_TEXT, skin);
        nextButton = new TextButton(Constant.NEXT_TEXT, skin);
        classifica = new Label[punti.length];
        nPlayer = classifica.length;
        end = false;

        backButton.getLabel().setFontScale(3);
        prevButton.getLabel().setFontScale(3);
        nextButton.getLabel().setFontScale(3);

        mergeSort(punti, nomi, 0, classifica.length - 1);

        for (int i = 0; i < classifica.length; i++) {
            classifica[i] = new Label("Posizione " + (i + 1) + ": " + nomi[i] + " punti: " + " " + punti[i], skin);
            classifica[i].getStyle().font.getData().setScale(3);
        }

        stage = new Stage();
        stage.clear();

        moltiplicatore = 0;
        createScoreboard(moltiplicatore, classifica);

        setButton();

        stage.addActor(backButton);
        stage.addActor(prevButton);
        stage.addActor(nextButton);

        for (int i = 0; i < Math.min(Constant.NUM_FOR_PAGES, nPlayer); i++) {
            stage.addActor(classifica[i]);
        }

        Gdx.input.setInputProcessor(this.stage);
    }

    private void setButton() {
        //set Position
        backButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 425, 400, 150);
        prevButton.setBounds(Gdx.graphics.getWidth() / 2 - 400, Gdx.graphics.getHeight() / 2 - 400, 100, 100);
        nextButton.setBounds(Gdx.graphics.getWidth() / 2 + 300, Gdx.graphics.getHeight() / 2 - 400, 100, 100);
        //set listeners
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setsScreen(Constant.NumeroScreen.MENUSCREEN);
                gameClass.setbSwitch(true);
            }
        });

        prevButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (moltiplicatore >= Constant.NUM_FOR_PAGES) {
                    end = false;
                    nPlayer += Constant.NUM_FOR_PAGES;
                    moltiplicatore -= Constant.NUM_FOR_PAGES;
                    stage.clear();
                    createScoreboard(moltiplicatore, classifica);
                }
                else {
                    Log.i("PREV BUTTON", "first page");
                }

            }
        });

        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!end) {
                    nPlayer -= Constant.NUM_FOR_PAGES;
                    moltiplicatore += Constant.NUM_FOR_PAGES;
                    stage.clear();
                    createScoreboard(moltiplicatore, classifica);
                }
                else {
                    Log.i("NEXT BUTTON", "last page");
                }
            }
        });
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.9f, 0.2f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameClass.batch.setProjectionMatrix(camera.combined);
        gameClass.batch.begin();
        gameClass.batch.end();
        stage.draw();
        stage.act();
    }

    public void dispose() {
        gameClass.batch.dispose();
        stage.dispose();
    }

    private void mergeSort(int[] punti, String[] nomi, int posIniz, int posFinale) {
        if (posIniz < posFinale) {
            final int posIntermezzo = (posIniz + posFinale) / 2;
            mergeSort(punti, nomi, posIniz, posIntermezzo);
            mergeSort(punti, nomi, posIntermezzo + 1, posFinale);
            trueMerge(punti, nomi, posIniz, posIntermezzo, posFinale);
        }
    }

    private void trueMerge(int[] punti, String[] nomi, int posIniziale, int posIntermezzo, int posFinale) {
        int iteratoreCrescente = posIniziale;
        int iteratoreDecrescente = posIntermezzo + 1;
        int posizione = 0;
        final int[] tempPunti = new int[posFinale - posIniziale + 1];
        final String[] tempNomi = new String[posFinale - posIniziale + 1];

        //controllo quale delle due metà è maggiore e ordino di conseguenza
        while (iteratoreCrescente <= posIntermezzo && iteratoreDecrescente <= posFinale) {
            if (punti[iteratoreCrescente] >= punti[iteratoreDecrescente]) {
                tempPunti[posizione] = punti[iteratoreCrescente];
                tempNomi[posizione] = nomi[iteratoreCrescente];
                iteratoreCrescente++;
            }
            else {
                tempPunti[posizione] = punti[iteratoreDecrescente];
                tempNomi[posizione] = nomi[iteratoreDecrescente];
                iteratoreDecrescente++;
            }
            posizione++;
        }

        //se non ho più cifre a destra devo inserire tutte le rimanenti fino alla metà del vettore
        while (iteratoreCrescente <= posIntermezzo) {
            tempPunti[posizione] = punti[iteratoreCrescente];
            tempNomi[posizione] = nomi[iteratoreCrescente];
            iteratoreCrescente++;
            posizione++;
        }

        //se non ho più nulla a sinistra devo inserire tutte le cifre fino alla fine del vettore
        while (iteratoreDecrescente <= posFinale) {
            tempPunti[posizione] = punti[iteratoreDecrescente];
            tempNomi[posizione] = nomi[iteratoreDecrescente];
            iteratoreDecrescente++;
            posizione++;
        }

        //ordinamento finito salvo nelle variabili le var temporanee
        for (posizione = posIniziale; posizione <= posFinale; posizione++) {
            punti[posizione] = tempPunti[posizione - posIniziale];
            nomi[posizione] = tempNomi[posizione - posIniziale];
        }
    }

    private void createScoreboard(int number, Label[] classifica) {
        for (int i = 0; i < Math.min(nPlayer, Constant.NUM_FOR_PAGES); i++) {
            classifica[number + i].setBounds(Gdx.graphics.getWidth() / 2 - TEXT_WIDTH / 2, Gdx.graphics.getHeight() - (350 + (i * 100)), TEXT_WIDTH, 150);

            if (nPlayer <= Constant.NUM_FOR_PAGES) {
                end = true;
            }
            stage.addActor(classifica[number + i]);
        }
        stage.addActor(backButton);
        stage.addActor(prevButton);
        stage.addActor(nextButton);

    }
}
