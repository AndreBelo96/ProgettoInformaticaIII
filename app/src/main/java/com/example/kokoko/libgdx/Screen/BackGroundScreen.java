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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.AbstractScreens;
import com.example.kokoko.libgdx.Background;
import com.example.kokoko.libgdx.GameClass;

public class BackGroundScreen extends AbstractScreens implements Screen {

    public GameClass gameClass;
    private final Skin skin;
    private final OrthographicCamera camera;

    private Texture title;
    //Variables for rotation
    private Label typeName;
    private final String[] type = new String[] {"On","Off"};
    private TextButton prevTypeButton;
    private Label typeBackgroundLabel;
    private TextButton nextTypeButton;
    //Variables for rectangles color
    private Label colTypeName;
    private final Color[] color = new Color[] {Color.WHITE, Color.GOLD ,Color.RED, Color.BLUE, Color.BLACK};
    private TextButton prevColorTypeButton;
    private Label colorNameLabel;
    private TextButton nextColorTypeButton;
    //Variables for rectangles movement
    private Label moveName;
    private final String[] move = new String[] {"On", "Off"};
    private TextButton prevMoveButton;
    private Label moveBackgroundLabel;
    private TextButton nextMoveButton;

    private TextButton backButton;

    private final Stage stage;
    private final Background rectBK;


    public BackGroundScreen(final GameClass gameClass) {
        this.gameClass = gameClass;
        camera = new OrthographicCamera();

        skin = new Skin(Gdx.files.internal("skin.json"));
        rectBK = new Background();

        setActors();
        setFont();

        stage = new Stage();
        stage.clear();

        setButtons();
        addActor();

        Gdx.input.setInputProcessor(this.stage);
    }

    private void setActors() {
        title = new Texture(Gdx.files.internal("option.png"));

        prevTypeButton = new TextButton("<", skin);
        nextTypeButton = new TextButton(">", skin);
        if (GameClass.getBkgrndType())
            typeBackgroundLabel = new Label(type[0], skin);
        else
            typeBackgroundLabel = new Label(type[1], skin);
        typeName = new Label("Rotation", skin);

        prevColorTypeButton = new TextButton("<", skin);
        nextColorTypeButton = new TextButton(">", skin);
        if (GameClass.getRectColor().contains("WHITE"))
            colorNameLabel = new Label(colorToString(color[0]), skin);
        else if (GameClass.getRectColor().contains("GOLD"))
            colorNameLabel = new Label(colorToString(color[1]), skin);
        else if (GameClass.getRectColor().contains("RED"))
            colorNameLabel = new Label(colorToString(color[2]), skin);
        else if (GameClass.getRectColor().contains("BLUE"))
            colorNameLabel = new Label(colorToString(color[3]), skin);
        else if (GameClass.getRectColor().contains("BLACK"))
            colorNameLabel = new Label(colorToString(color[4]), skin);
        colTypeName = new Label("Square Color", skin);

        prevMoveButton = new TextButton("<", skin);
        nextMoveButton = new TextButton(">", skin);
        if (GameClass.isDynamicBkgrd())
            moveBackgroundLabel = new Label(move[0], skin);
        else
            moveBackgroundLabel = new Label(move[1], skin);
        moveName = new Label("Movement", skin);

        backButton = new TextButton("Back", skin);
    }

    private void setFont() {
        prevTypeButton.getLabel().setFontScale(3);
        nextTypeButton.getLabel().setFontScale(3);
        typeBackgroundLabel.getStyle().font.getData().setScale(3);
        typeBackgroundLabel.setAlignment(Align.center);
        typeName.getStyle().font.getData().setScale(3);

        prevColorTypeButton.getLabel().setFontScale(3);
        nextColorTypeButton.getLabel().setFontScale(3);
        colorNameLabel.getStyle().font.getData().setScale(3);
        colorNameLabel.setAlignment(Align.center);
        colTypeName.getStyle().font.getData().setScale(3);

        prevMoveButton.getLabel().setFontScale(3);
        nextMoveButton.getLabel().setFontScale(3);
        moveBackgroundLabel.getStyle().font.getData().setScale(3);
        moveBackgroundLabel.setAlignment(Align.center);
        moveName.getStyle().font.getData().setScale(3);

        backButton.getLabel().setFontScale(3);
    }

    private void addActor() {

        stage.addActor(prevTypeButton);
        stage.addActor(nextTypeButton);
        stage.addActor(typeBackgroundLabel);
        stage.addActor(typeName);

        stage.addActor(prevColorTypeButton);
        stage.addActor(nextColorTypeButton);
        stage.addActor(colorNameLabel);
        stage.addActor(colTypeName);

        stage.addActor(prevMoveButton);
        stage.addActor(nextMoveButton);
        stage.addActor(moveBackgroundLabel);
        stage.addActor(moveName);

        stage.addActor(backButton);
    }

    private void setButtons() {

        setButtonsPositions();
        /* ------------------------------------------------------------------------------------------------*/
        //ROTATION
        prevTypeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (typeBackgroundLabel.getText().contains(type[0]))
                    Log.i("Background SCREEN:" , "it's the first element of the array");
                else {
                    typeBackgroundLabel.setText(type[0]);
                    GameClass.setBkgrndType(true);
                    GameClass.prefs.putBoolean("isBkgrndRotating", true);
                    GameClass.prefs.flush();
                }

            }
        });

        nextTypeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (typeBackgroundLabel.getText().contains(type[1]))
                    Log.i("Background SCREEN:", "it's the last element of the array");
                else {
                    typeBackgroundLabel.setText(type[1]);
                    GameClass.setBkgrndType(false);
                    GameClass.prefs.putBoolean("isBkgrndRotating", false);
                    GameClass.prefs.flush();
                }
            }
        });
        /* ------------------------------------------------------------------------------------------------*/
        //COLOR
        prevColorTypeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (colorNameLabel.getText().contains("WHITE"))
                    Log.i("Background SCREEN:" , "it's the first element of the array");
                else if (colorNameLabel.getText().contains("GOLD")) {
                    colorNameLabel.setText("WHITE");
                    GameClass.setRectColor("WHITE");
                    GameClass.prefs.putString("rectColor", "WHITE");
                    GameClass.prefs.flush();
                }
                else if (colorNameLabel.getText().contains("RED")) {
                    colorNameLabel.setText("GOLD");
                    GameClass.setRectColor("GOLD");
                    GameClass.prefs.putString("rectColor", "GOLD");
                    GameClass.prefs.flush();
                }
                else if (colorNameLabel.getText().contains("BLUE")) {
                    colorNameLabel.setText("RED");
                    GameClass.setRectColor("RED");
                    GameClass.prefs.putString("rectColor", "RED");
                    GameClass.prefs.flush();
                }
                else {
                    colorNameLabel.setText("BLUE");
                    GameClass.setRectColor("BLUE");
                    GameClass.prefs.putString("rectColor", "BLUE");
                    GameClass.prefs.flush();
                }

            }
        });

        nextColorTypeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (colorNameLabel.getText().contains("BLACK"))
                    Log.i("Background SCREEN:" , "it's the last element of the array");
                else if (colorNameLabel.getText().contains("BLUE")) {
                    colorNameLabel.setText("BLACK");
                    GameClass.setRectColor("BLACK");
                    GameClass.prefs.putString("rectColor", "BLACK");
                    GameClass.prefs.flush();
                }
                else if (colorNameLabel.getText().contains("RED")) {
                    colorNameLabel.setText("BLUE");
                    GameClass.setRectColor("BLUE");
                    GameClass.prefs.putString("rectColor", "BLUE");
                    GameClass.prefs.flush();
                }
                else if (colorNameLabel.getText().contains("GOLD")) {
                    colorNameLabel.setText("RED");
                    GameClass.setRectColor("RED");
                    GameClass.prefs.putString("rectColor", "RED");
                    GameClass.prefs.flush();
                }
                else {
                    colorNameLabel.setText("GOLD");
                    GameClass.setRectColor("GOLD");
                    GameClass.prefs.putString("rectColor", "GOLD");
                    GameClass.prefs.flush();
                }

            }
        });
        /* ------------------------------------------------------------------------------------------------*/
        //MOVEMENT
        prevMoveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (moveBackgroundLabel.getText().contains(move[0])) {
                    Log.i("Background SCREEN:", "it's the first element of the array");
                }
                else {
                    moveBackgroundLabel.setText(move[0]);
                    GameClass.setDynamicBkgrd(true);
                    GameClass.prefs.putBoolean("isDynamicBkgrd", true);
                    GameClass.prefs.flush();
                }
            }
        });

        nextMoveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (moveBackgroundLabel.getText().contains(move[1]))
                    Log.i("Background SCREEN:" , "it's the last element of the array");
                else {
                    moveBackgroundLabel.setText(move[1]);
                    GameClass.setDynamicBkgrd(false);
                    GameClass.prefs.putBoolean("isDynamicBkgrd", false);
                    GameClass.prefs.flush();
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameClass.setsScreen(Constant.NumeroScreen.OPTIONSCREEN);
                gameClass.setbSwitch(true);

            }
        });
    }

    private void setButtonsPositions() {
        typeName.setBounds(Gdx.graphics.getWidth() / 2f - 500, Gdx.graphics.getHeight() / 2f + 100, 200, 100);
        prevTypeButton.setBounds(Gdx.graphics.getWidth() / 2f + 300, Gdx.graphics.getHeight() / 2f + 100, 100, 100);
        typeBackgroundLabel.setBounds(Gdx.graphics.getWidth() / 2f + 400, Gdx.graphics.getHeight() / 2f + 100, 300, 100);
        nextTypeButton.setBounds(Gdx.graphics.getWidth() / 2f + 700, Gdx.graphics.getHeight() / 2f + 100, 100, 100);

        colTypeName.setBounds(Gdx.graphics.getWidth() / 2f - 500, Gdx.graphics.getHeight() / 2f - 25, 200, 100);
        prevColorTypeButton.setBounds(Gdx.graphics.getWidth() / 2f + 300, Gdx.graphics.getHeight() / 2f - 25, 100, 100);
        colorNameLabel.setBounds(Gdx.graphics.getWidth() / 2f + 400, Gdx.graphics.getHeight() / 2f -25, 300, 100);
        nextColorTypeButton.setBounds(Gdx.graphics.getWidth() / 2f + 700, Gdx.graphics.getHeight() / 2f - 25, 100, 100);

        moveName.setBounds(Gdx.graphics.getWidth() / 2f - 500, Gdx.graphics.getHeight() / 2f - 150, 200, 100);
        prevMoveButton.setBounds(Gdx.graphics.getWidth() / 2f + 300, Gdx.graphics.getHeight() / 2f - 150, 100, 100);
        moveBackgroundLabel.setBounds(Gdx.graphics.getWidth() / 2f + 400, Gdx.graphics.getHeight() / 2f - 150, 300, 100);
        nextMoveButton.setBounds(Gdx.graphics.getWidth() / 2f + 700, Gdx.graphics.getHeight() / 2f - 150, 100, 100);

        backButton.setBounds(Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f - 450, 300, 100);
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

    private String colorToString(Color color) {
        if (color == Color.WHITE)
            return "WHITE";
        else if (color == Color.GOLD)
            return "GOLD";
        else if (color == Color.RED)
            return "RED";
        else if (color == Color.BLUE)
            return "BLUE";
        else
            return "BLACK";
    }
}
