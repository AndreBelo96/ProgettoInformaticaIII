package com.example.kokoko.libgdx.Screen;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
    private final Skin skin;
    private final OrthographicCamera camera;
    private final TextButton backButton;
    private final TextButton changeNickButton;
    private final TextButton soundButton;
    private final TextButton resetButton;
    private final TextButton controllerButton;
    private final TextButton backgroundButton;
    private final TextField namePlayer;
    private final Stage stage;
    private final Texture title;
    private final Background rectBK;

    public OptionScreen(final GameClass gameClass) {
        this.gameClass = gameClass;
        camera = new OrthographicCamera();
        skin = new Skin(Gdx.files.internal("skin.json"));
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
        backButton.getLabel().setFontScale(3);
        //namePlayer.getStyle().font.getData().setScale(3);

        TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font.getData().setScale(1.5f);

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

        namePlayer.setBounds(Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f + 125, 300, 75);
        changeNickButton.setBounds(Gdx.graphics.getWidth() / 2f - 800, Gdx.graphics.getHeight() / 2f, 650, 100);
        soundButton.setBounds(Gdx.graphics.getWidth() / 2f - 800, Gdx.graphics.getHeight() / 2f - 150, 650, 100);
        controllerButton.setBounds(Gdx.graphics.getWidth() / 2f - 800, Gdx.graphics.getHeight() / 2f - 300, 650, 100);
        resetButton.setBounds(Gdx.graphics.getWidth() / 2f + 150, Gdx.graphics.getHeight() / 2f, 650, 100);
        backgroundButton.setBounds(Gdx.graphics.getWidth() / 2f + 150, Gdx.graphics.getHeight() / 2f - 150, 650, 100);
        backButton.setBounds(Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f - 450, 300, 100);

        changeNickButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameClass.setNickName(namePlayer.getText());
                GameClass.prefs.putString(Constant.NICK_NAME_STRING, GameClass.getNickName());
                GameClass.prefs.flush();
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
                GameClass.setSound(!GameClass.isSound());
            }
        });

        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameClass.setNickName("Guest");
                GameClass.setnLvlMax(0);
                GameClass.setNumLvl(1);
                GameClass.setPunteggio(0);
                GameClass.prefs.putString(Constant.NICK_NAME_STRING, GameClass.getNickName());
                GameClass.prefs.putInteger("nLvl", GameClass.getnLvlMax());
                GameClass.prefs.putInteger("punteggio", GameClass.getPunteggio());
                GameClass.prefs.flush();
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
        Gdx.gl.glClearColor(0.65f, 0.65f, 0.65f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        GameClass.batch.setProjectionMatrix(camera.combined);

        GameClass.batch.begin();
        GameClass.batch.setColor(StringToColor(GameClass.getRectColor()));
        rectBK.render(GameClass.batch);
        GameClass.batch.setColor(Color.WHITE);
        GameClass.batch.draw(title, ((Gdx.graphics.getWidth() / 2000f) - 1.2f), .45f, 0.6f, 0.5f);
        GameClass.batch.end();

        if (GameClass.isDynamicBkgrd()) {
            rectBK.moveRect();
            rectBK.resetRect();
        }
        else
            Log.i("MenuScreen SCREEN:" , "No movement");

        if (GameClass.getBkgrndType()) {
            rectBK.rotateYRect();
            rectBK.rotateXRect();
        }
        else
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
                return Color.FIREBRICK;
        }

    }
}
