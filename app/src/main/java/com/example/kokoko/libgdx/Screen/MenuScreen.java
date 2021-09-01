package com.example.kokoko.libgdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.google.firebase.auth.FirebaseAuth;

/** Classe per lo screen del menu */
public class MenuScreen extends AbstractScreens implements Screen {

    public GameClass gameClass;
    private final Skin skin;
    private OrthographicCamera camera;
    private TextButton playButton;
    private TextButton exitButton;
    private TextButton optionButton;
    private TextButton scoreBoardButton;
    private TextButton logOutButton;
    private Stage stage;
    private Label namePlayer;
    private final Preferences prefs;
    private Texture title;
    private Background rectBK;

    public MenuScreen(final GameClass gameClass) {

        this.gameClass = gameClass;
        camera = new OrthographicCamera();

        skin = new Skin(Gdx.files.internal("skin.json"));
        prefs = Gdx.app.getPreferences("Zawardo");

        title = new Texture(Gdx.files.internal("Titolo.png"));
        rectBK = new Background();

        playButton = new TextButton("Play", skin);
        optionButton = new TextButton("Option", skin);
        scoreBoardButton = new TextButton("Scoreboard", skin);
        namePlayer = new Label(GameClass.getNickName(), skin);
        logOutButton = new TextButton("Logout", skin);
        exitButton = new TextButton(Constant.EXIT_TEXT, skin);

        playButton.getLabel().setFontScale(3);
        optionButton.getLabel().setFontScale(3);
        scoreBoardButton.getLabel().setFontScale(3);
        namePlayer.setFontScale(3);
        logOutButton.getLabel().setFontScale(3);
        exitButton.getLabel().setFontScale(3);

        setButton();

        stage = new Stage();
        stage.clear();
        stage.addActor(playButton);
        stage.addActor(optionButton);
        stage.addActor(scoreBoardButton);
        stage.addActor(logOutButton);
        stage.addActor(namePlayer);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(this.stage);
    }

    private void setButton() {
        playButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 100, 400, 125);
        optionButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 250, 400, 125);
        scoreBoardButton.setBounds(Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 400, 400, 125);
        namePlayer.setBounds(Gdx.graphics.getWidth() / 2 - 420, Gdx.graphics.getHeight() / 2 - 385, 200, 100);
        logOutButton.setBounds(Gdx.graphics.getWidth() / 2 + 500, Gdx.graphics.getHeight() / 2 - 350, 400, 150);
        exitButton.setBounds(Gdx.graphics.getWidth() / 2 + 500, Gdx.graphics.getHeight() / 2 - 500, 400, 150);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setsScreen(Constant.NumeroScreen.SELECTSCREEN);
                gameClass.setbSwitch(true);
            }
        });

        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setsScreen(Constant.NumeroScreen.OPTIONSCREEN);
                gameClass.setbSwitch(true);
            }
        });

        scoreBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setsScreen(Constant.NumeroScreen.SCOREBOARDSCREEN);
                gameClass.setbSwitch(true);

            }
        });

        logOutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                FirebaseAuth.getInstance().signOut();
                gameClass.gdxActivity.openLogIn();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.6f, 0.3f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameClass.batch.setProjectionMatrix(camera.combined);

        gameClass.batch.begin();
        rectBK.render(gameClass.batch);
        gameClass.batch.draw(title, -0.48f, .4f, 0.96f, 0.38f);
        gameClass.batch.end();

        rectBK.moveRect();

        stage.draw();
        stage.act();
    }



    public void dispose() {
        gameClass.batch.dispose();
        stage.dispose();
    }
}