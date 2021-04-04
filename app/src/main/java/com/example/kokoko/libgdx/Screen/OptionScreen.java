package com.example.kokoko.libgdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.AbstractScreens;
import com.example.kokoko.libgdx.GameClass;

/** Classe per lo screen delle opzioni */
public class OptionScreen extends AbstractScreens implements Screen {

    public GameClass gameClass;
    private Skin skin;
    private OrthographicCamera camera;
    private TextButton backButton;
    private TextButton resetButton;
    private TextButton controllerButton;
    private Stage stage;
    private final Preferences prefs;
    private Texture title;

    public OptionScreen(final GameClass gameClass) {
        this.gameClass = gameClass;
        camera = new OrthographicCamera();

        skin = new Skin(Gdx.files.internal("skin.json"));
        prefs = Gdx.app.getPreferences("Zawardo");

        resetButton = new TextButton("Reset Account", skin);
        controllerButton = new TextButton("Controller Type", skin);
        backButton = new TextButton("Back", skin);

        resetButton.getLabel().setFontScale(3);
        controllerButton.getLabel().setFontScale(3);
        backButton.getLabel().setFontScale(3);

        title = new Texture(Gdx.files.internal("option.png"));

        stage = new Stage();
        stage.clear();

        controllerButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 200, 400, 100);
        resetButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 300, 400, 100);
        backButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 400, 400, 100);

        controllerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setNickName("Guest");
                gameClass.setnLvlMax(0);
                gameClass.setNumLvl(1);
                gameClass.setPunteggio(0);
                gameClass.prefs.putString("nickname", gameClass.getNickName());
                gameClass.prefs.putInteger("nLvl", gameClass.getnLvlMax());
                gameClass.prefs.putInteger("punteggio", gameClass.getPunteggio());
                gameClass.prefs.flush();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.sScreen = Constant.NumeroScreen.MENUSCREEN;
                gameClass.bSwitch = true;
            }
        });

        stage.addActor(controllerButton);
        stage.addActor(resetButton);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.9f, 0.2f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameClass.batch.setProjectionMatrix(camera.combined);

        gameClass.batch.begin();
        gameClass.batch.draw(title, -0.47f, .5f, 0.94f, 0.45f);
        gameClass.batch.end();

        stage.draw();
        stage.act();

    }
}