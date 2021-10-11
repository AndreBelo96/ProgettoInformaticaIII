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
    private TextButton reloadButton;
    private TextButton exitButton;
    private TextButton backButton;
    private TextButton falseButton;
    private Stage stage;
    private BitmapFont font;
    private GameClass gameClass;

    public LoseScreen(final GameClass gameClass) {
        this.gameClass = gameClass;
        camera = new OrthographicCamera();
        final TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        font = new BitmapFont(Gdx.files.internal("Immortal.fnt"));
        font.setColor(Color.WHITE);
        style.font = font;
        falseButton = new TextButton(Constant.LOSE_TEXT, style);
        reloadButton = new TextButton(Constant.RELOAD_LVL_TEXT, style);
        backButton = new TextButton(Constant.BACK_TEXT, style);
        exitButton = new TextButton(Constant.EXIT_TEXT, style);

        falseButton.setBounds(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() - 200, 200, 100);
        reloadButton.setBounds(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f - 50, 200, 100);
        backButton.setBounds(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f - 250, 200, 100);
        exitButton.setBounds(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f - 350, 200, 100);

        reloadButton.addListener(new ClickListener() {
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
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(falseButton);
        stage.addActor(reloadButton);
        stage.addActor(backButton);
        stage.addActor(exitButton);
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
