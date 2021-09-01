package com.example.kokoko.libgdx.Screen;

import android.util.Log;

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
import com.badlogic.gdx.utils.StringBuilder;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.AbstractScreens;
import com.example.kokoko.libgdx.Background;
import com.example.kokoko.libgdx.GameClass;

public class BackGroundScreen extends AbstractScreens implements Screen {

    public GameClass gameClass;
    private Skin skin;
    private OrthographicCamera camera;
    private TextButton backButton;
    private TextButton prevTypeButton;
    private TextButton nextTypeButton;
    private Label typeBackgroundLabel;
    private TextButton prevMoveButton;
    private TextButton nextMoveButton;
    private Label moveBackgroundLabel;
    private String type[] = new String[] {"Both", "Orizontal", "Vertical", "no one"};
    private String move[] = new String[] {"Dynamic", "Static"};
    private Stage stage;
    private final Preferences prefs;
    private Background rectBK;
    private Texture title;

    public BackGroundScreen(final GameClass gameClass) {
        this.gameClass = gameClass;
        camera = new OrthographicCamera();

        skin = new Skin(Gdx.files.internal("skin.json"));
        prefs = Gdx.app.getPreferences("Zawardo");
        rectBK = new Background();

        prevTypeButton = new TextButton("<", skin);
        nextTypeButton = new TextButton(">", skin);
        typeBackgroundLabel = new Label(type[0], skin);
        prevMoveButton = new TextButton("<", skin);
        nextMoveButton = new TextButton(">", skin);
        moveBackgroundLabel = new Label(move[0], skin);
        backButton = new TextButton("Back", skin);

        prevTypeButton.getLabel().setFontScale(3);
        nextTypeButton.getLabel().setFontScale(3);
        typeBackgroundLabel.getStyle().font.getData().setScale(3);
        prevMoveButton.getLabel().setFontScale(3);
        nextMoveButton.getLabel().setFontScale(3);
        moveBackgroundLabel.getStyle().font.getData().setScale(3);
        backButton.getLabel().setFontScale(3);

        title = new Texture(Gdx.files.internal("option.png"));

        stage = new Stage();
        stage.clear();

        setButtons();

        stage.addActor(prevTypeButton);
        stage.addActor(nextTypeButton);
        stage.addActor(typeBackgroundLabel);
        stage.addActor(prevMoveButton);
        stage.addActor(nextMoveButton);
        stage.addActor(moveBackgroundLabel);
        stage.addActor(backButton);

        Gdx.input.setInputProcessor(this.stage);
    }

    private void setButtons() {

        prevTypeButton.setBounds(Gdx.graphics.getWidth() / 2 - 300, Gdx.graphics.getHeight() / 2 - 100, 100, 100);
        nextTypeButton.setBounds(Gdx.graphics.getWidth() / 2 + 300, Gdx.graphics.getHeight() / 2 - 100, 100, 100);
        typeBackgroundLabel.setBounds(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 100, 200, 100);
        prevMoveButton.setBounds(Gdx.graphics.getWidth() / 2 - 300, Gdx.graphics.getHeight() / 2 - 250, 100, 100);
        nextMoveButton.setBounds(Gdx.graphics.getWidth() / 2 + 300, Gdx.graphics.getHeight() / 2 - 250, 100, 100);
        moveBackgroundLabel.setBounds(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 250, 200, 100);
        backButton.setBounds(Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 450, 300, 100);


        prevTypeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (typeBackgroundLabel.getText().contains(type[0]))
                    Log.i("Background SCREEN:" , "it's the first element of the array");
                else if (typeBackgroundLabel.getText().contains(type[1]))
                    typeBackgroundLabel.setText(type[0]);
                else if (typeBackgroundLabel.getText().contains(type[2]))
                    typeBackgroundLabel.setText(type[1]);
                else
                    typeBackgroundLabel.setText(type[2]);
            }
        });

        nextTypeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (typeBackgroundLabel.getText().contains(type[3]))
                    Log.i("Background SCREEN:" , "it's the last element of the array");
                else if (typeBackgroundLabel.getText().contains(type[2]))
                    typeBackgroundLabel.setText(type[3]);
                else if (typeBackgroundLabel.getText().contains(type[1]))
                    typeBackgroundLabel.setText(type[2]);
                else
                    typeBackgroundLabel.setText(type[1]);
            }
        });

        prevMoveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (moveBackgroundLabel.getText().contains(move[0])) {
                    Log.i("Background SCREEN:", "it's the first element of the array");
                }
                else {
                    moveBackgroundLabel.setText(move[0]);
                    gameClass.setDynamicBkgrd(true);
                }
            }
        });

        nextMoveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (moveBackgroundLabel.getText().contains(move[1]))
                    Log.i("Background SCREEN:" , "it's the first element of the array");
                else {
                    moveBackgroundLabel.setText(move[1]);
                    gameClass.setDynamicBkgrd(false);
                    gameClass.prefs.putBoolean("isDynamicBkgrd", false);
                    gameClass.prefs.flush();
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setsScreen(Constant.NumeroScreen.OPTIONSCREEN);
                gameClass.setbSwitch(true);
                gameClass.prefs.putBoolean("isDynamicBkgrd", true);
                gameClass.prefs.flush();
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
