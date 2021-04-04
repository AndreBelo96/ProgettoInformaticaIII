package com.example.kokoko.libgdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.AbstractScreens;
import com.example.kokoko.libgdx.GameClass;
import com.example.kokoko.libgdx.Tilemap;

/** Classe per lo screen della vittoria */
public class WinScreen extends AbstractScreens implements Screen {
    private OrthographicCamera camera;
    private Stage stage;
    private GameClass gameClass;

    public WinScreen(final GameClass gameClass) {
        this.gameClass = gameClass;
        camera = new OrthographicCamera();
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = new BitmapFont(Gdx.files.internal("roboto_light.fnt"));
        style.font.setColor(Color.WHITE);

        //dichiarazione tasti
        final TextButton falsebutton = new TextButton(Constant.WIN_TEXT, style);
        final TextButton reloadbutton = new TextButton(Constant.RELOAD_LVL_TEXT, style);
        final TextButton nextbutton = new TextButton(Constant.NEXT_LVL_TEXT, style);
        final TextButton backButton = new TextButton(Constant.BACK_TEXT, style);
        final TextButton exitbutton = new TextButton(Constant.EXIT_TEXT, style);

        falsebutton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 200, 200, 100);
        reloadbutton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 50, 200, 100);
        nextbutton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 150, 200, 100);
        backButton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 250, 200, 100);
        exitbutton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 350, 200, 100);

        //dichiaro i listener
        reloadbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                gameClass.sScreen = Constant.NumeroScreen.GAMESCREEN;
                gameClass.bSwitch = true;
            }
        });
        nextbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (gameClass.getnLvlMax() < (gameClass.getNumLvl())) {
                    gameClass.setnLvlMax(gameClass.getNumLvl());
                    gameClass.setPunteggio(gameClass.getPunteggio() + Tilemap.punteggioTotale);
                    gameClass.prefs.putInteger("nLvl", gameClass.getnLvlMax());
                    gameClass.prefs.putInteger("punteggio", gameClass.getPunteggio());
                    gameClass.prefs.flush();
                    gameClass.setNumLvl(gameClass.getNumLvl() + 1);
                }
                else {
                    gameClass.setNumLvl(gameClass.getNumLvl() + 1);

                }
                gameClass.sScreen = Constant.NumeroScreen.GAMESCREEN;
                gameClass.bSwitch = true;
            }
        });

        exitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.sScreen = Constant.NumeroScreen.MENUSCREEN;
                gameClass.bSwitch = true;
            }
        });
        //creo lo stage
        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(falsebutton);
        stage.addActor(reloadbutton);
        stage.addActor(nextbutton);
        stage.addActor(backButton);
        stage.addActor(exitbutton);
    }

    //metodo per disegnare a schermo i tasti
    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        gameClass.batch.setProjectionMatrix(camera.combined);
        gameClass.batch.begin();
        stage.draw();
        gameClass.batch.end();

    }
}
