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

/** Classe per lo screen della sconfitta */
public class LoseScreen extends AbstractScreens implements Screen {
    private OrthographicCamera camera;
    private TextButton reloadbutton;
    private TextButton exitbutton;
    private TextButton backButton;
    private TextButton falsebutton;
    private Stage stage;
    private BitmapFont font;
    private GameClass gameClass;

    public LoseScreen(final GameClass gameClass) {
        this.gameClass = gameClass;
        camera = new OrthographicCamera();
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        font = new BitmapFont(Gdx.files.internal("roboto_light.fnt"));
        font.setColor(Color.WHITE);
        style.font = font;
        falsebutton = new TextButton(Constant.LOSE_TEXT, style);
        reloadbutton = new TextButton(Constant.RELOAD_LVL_TEXT, style);
        backButton = new TextButton(Constant.BACK_TEXT, style);
        exitbutton = new TextButton(Constant.EXIT_TEXT, style);

        falsebutton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 200, 200, 100);
        reloadbutton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 50, 200, 100);
        backButton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 250, 200, 100);
        exitbutton.setBounds(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 350, 200, 100);

        reloadbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
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
        exitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(falsebutton);
        stage.addActor(reloadbutton);
        stage.addActor(backButton);
        stage.addActor(exitbutton);
    }

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
