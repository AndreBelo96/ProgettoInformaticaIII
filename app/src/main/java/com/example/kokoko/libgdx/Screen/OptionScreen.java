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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.AbstractScreens;
import com.example.kokoko.libgdx.Background;
import com.example.kokoko.libgdx.GameClass;

/** Classe per lo screen delle opzioni */
public class OptionScreen extends AbstractScreens implements Screen {

    public GameClass gameClass;
    private Skin skin;
    private OrthographicCamera camera;
    private TextButton backButton;
    private TextButton changeNickButton;
    private TextButton soundButton;
    private TextButton resetButton;
    private TextButton controllerButton;
    private TextButton backgroundButton;
    private TextField namePlayer;
    private Stage stage;
    private final Preferences prefs;
    private Texture title;
    private Background rectBK;

    public OptionScreen(final GameClass gameClass) {
        this.gameClass = gameClass;
        camera = new OrthographicCamera();

        skin = new Skin(Gdx.files.internal("skin.json"));
        prefs = Gdx.app.getPreferences("Zawardo");
        rectBK = new Background();

        changeNickButton = new TextButton("Change Nickname", skin);
        soundButton = new TextButton("Sound", skin);
        resetButton = new TextButton("Reset Account", skin);
        controllerButton = new TextButton("Controller Type", skin);
        backgroundButton = new TextButton("Background", skin);
        namePlayer = new TextField(GameClass.getNickName(), skin);
        backButton = new TextButton("Back", skin);

        changeNickButton.getLabel().setFontScale(3);
        soundButton.getLabel().setFontScale(3);
        resetButton.getLabel().setFontScale(3);
        controllerButton.getLabel().setFontScale(3);
        backgroundButton.getLabel().setFontScale(3);
        namePlayer.getStyle().font.getData().setScale(3);
        backButton.getLabel().setFontScale(3);

        title = new Texture(Gdx.files.internal("option.png"));

        stage = new Stage();
        stage.clear();

        setButtons();

        stage.addActor(changeNickButton);
        stage.addActor(soundButton);
        stage.addActor(controllerButton);
        stage.addActor(backgroundButton);
        stage.addActor(resetButton);
        stage.addActor(namePlayer);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(this.stage);
    }

    private void setButtons() {

        namePlayer.setBounds(Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 + 100, 300, 125);
        changeNickButton.setBounds(Gdx.graphics.getWidth() / 2 - 800, Gdx.graphics.getHeight() / 2, 650, 100);
        soundButton.setBounds(Gdx.graphics.getWidth() / 2 - 800, Gdx.graphics.getHeight() / 2 - 150, 650, 100);
        controllerButton.setBounds(Gdx.graphics.getWidth() / 2 - 800, Gdx.graphics.getHeight() / 2 - 300, 650, 100);
        resetButton.setBounds(Gdx.graphics.getWidth() / 2 + 150, Gdx.graphics.getHeight() / 2, 650, 100);
        backgroundButton.setBounds(Gdx.graphics.getWidth() / 2 + 150, Gdx.graphics.getHeight() / 2 - 150, 650, 100);
        backButton.setBounds(Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 450, 300, 100);

        changeNickButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setNickName(namePlayer.getText());
                gameClass.prefs.putString(Constant.NICK_NAME_STRING, gameClass.getNickName());
                gameClass.prefs.flush();
            }
        });

        controllerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setSound(!gameClass.isSound());
            }
        });

        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setNickName("Guest");
                gameClass.setnLvlMax(0);
                gameClass.setNumLvl(1);
                gameClass.setPunteggio(0);
                gameClass.prefs.putString(Constant.NICK_NAME_STRING, gameClass.getNickName());
                gameClass.prefs.putInteger("nLvl", gameClass.getnLvlMax());
                gameClass.prefs.putInteger("punteggio", gameClass.getPunteggio());
                gameClass.prefs.flush();
            }
        });

        backgroundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setsScreen(Constant.NumeroScreen.BACKGROUNDOPTION);
                gameClass.setbSwitch(true);
            }
        });

        controllerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setsScreen(Constant.NumeroScreen.MENUSCREEN);
                gameClass.setbSwitch(true);
            }
        });
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.6f, 0.3f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameClass.batch.setProjectionMatrix(camera.combined);

        gameClass.batch.begin();
        rectBK.render(gameClass.batch);
        gameClass.batch.draw(title, -0.47f, .5f, 0.9f, 0.45f);
        gameClass.batch.end();

        rectBK.moveRect();

        stage.draw();
        stage.act();
    }
}
