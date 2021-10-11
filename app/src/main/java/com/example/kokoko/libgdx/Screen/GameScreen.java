package com.example.kokoko.libgdx.Screen;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.AbstractScreens;
import com.example.kokoko.libgdx.Arrow;
import com.example.kokoko.libgdx.Background;
import com.example.kokoko.libgdx.GameClass;
import com.example.kokoko.libgdx.Player;
import com.example.kokoko.libgdx.Tile;
import com.example.kokoko.libgdx.Tilemap;

/** Classe per lo screen del Game */
public class GameScreen extends AbstractScreens implements Screen {
    //Static variables
    public static Player player;
    private static TextureAtlas atlas;
    //non-static variables
    public final GameClass gameClass;
    private OrthographicCamera camera;
    private Tilemap map;
    private boolean bool_switch;
    private boolean bool_win;
    private boolean bool_lose = true;
    private Stage stage;
    private Actor winActor;
    private Actor loseActor;
    private Actor moveUpRightActor;
    private Actor moveUpLeftActor;
    private Actor moveBottomRightActor;
    private Actor moveBottomLeftActor;
    private Background rectBK;

    public GameScreen(final GameClass gameClass) {
        this.gameClass = gameClass;
        atlas = new TextureAtlas("Asset_Proj.pack");
        camera = new OrthographicCamera(Constant.NUMERO_PIXEL_X, Constant.NUMERO_PIXEL_Y);
        camera.position.set(Constant.TILE_WIDHT / 2, Constant.TILE_WIDHT / 2 + Constant.BORDER_HEIGHT, 0);
        player = new Player(this, camera);
        map = new Tilemap(this.gameClass);

        bool_switch = false;
        bool_win = false;
        rectBK = new Background();
        stage = new Stage();
        stage.clear();

        winActor = new Actor();
        //pauseActor = new Actor();
        winActor.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameClass.setsScreen(Constant.NumeroScreen.WINSCREEN);
                gameClass.setbSwitch(true);
                return true;
            }
        });
        loseActor = new Actor();
        loseActor.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameClass.setsScreen(Constant.NumeroScreen.LOSESCREEN);
                gameClass.setbSwitch(true);
                return true;
            }
        });

        setMovementActor();

        stage.addActor(moveBottomLeftActor);
        stage.addActor(moveBottomRightActor);
        stage.addActor(moveUpLeftActor);
        stage.addActor(moveUpRightActor);
        stage.addActor(winActor);
        stage.addActor(loseActor);

        Gdx.input.setInputProcessor(this.stage);

    }

    private void setMovementActor() {
        moveUpRightActor = new Actor();
        moveUpRightActor.setBounds(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        moveUpRightActor.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (player.isCanJump()) {
                    player.move(Constant.Direzioni.TOPRIGHT);
                }
                return true;
            }
        });

        moveUpLeftActor = new Actor();
        moveUpLeftActor.setBounds(0, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        moveUpLeftActor.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (player.isCanJump()) {
                    player.move(Constant.Direzioni.TOPLEFT);
                }
                return true;
            }
        });

        moveBottomLeftActor = new Actor();
        moveBottomLeftActor.setBounds(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        moveBottomLeftActor.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (player.isCanJump()) {
                    player.move(Constant.Direzioni.BOTTOMLEFT);
                }
                return true;
            }
        });

        moveBottomRightActor = new Actor();
        moveBottomRightActor.setBounds(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        moveBottomRightActor.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (player.isCanJump()) {
                    player.move(Constant.Direzioni.BOTTOMRIGHT);
                }
                return true;
            }
        });

    }

    //metodo ciclico che mi permette di controllare e disegnare i vari elementi sul mio screen
    @Override
    public void render(float deltaTime) {
        deltaTime++;
        //pulisco lo screen
        Gdx.gl.glClearColor(0.65f, 0.65f, 0.65f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // disegno sulla camera
        gameClass.batch.setProjectionMatrix(camera.combined);

        //cambia colore tile
        for (Tile t : map.base) {
            if (player.getPos().x == t.tileMapPos.y && player.getPos().y == t.tileMapPos.x && bool_switch) {
                t.onBoolean = !t.onBoolean;
                bool_switch = false;
            }
        }

        useArrow();

        cameraInput();
        player.update(deltaTime);
        camera.update();

        gameClass.batch.begin();
        gameClass.batch.setColor(StringToColor(gameClass.getRectColor()));
        rectBK.render(gameClass.batch);
        gameClass.batch.setColor(Color.WHITE);
        map.render(gameClass.batch);
        player.render(gameClass.batch);
        gameClass.batch.end();

        if (gameClass.isDynamicBkgrd()) {
            rectBK.moveRect();
            rectBK.resetRect();
        } else
            Log.i("MenuScreen SCREEN:" , "No movement");

        if (gameClass.getBkgrndType()) {
            rectBK.rotateYRect();
            rectBK.rotateXRect();
        } else
            Log.i("MenuScreen SCREEN:" , "No rotation");

        for (Tile t : map.base) {
            if (!t.onBoolean) {
                bool_win = false;
                break;
            }
            bool_win = true;
        }

        for (Tile t : map.base) {
            if (player.getPos().x == t.tileMapPos.y && player.getPos().y == t.tileMapPos.x) {
                bool_lose = false;
                break;
            }
            bool_lose = true;
        }

        controlInput();
    }

    private void useArrow() {
        for (Arrow a : map.arrows) {
            if (playerInTile(a)) {
                if (a.direzione == Constant.Direzioni.TOPLEFT) {
                    player.move(Constant.Direzioni.TOPLEFT);
                }
                else if (a.direzione == Constant.Direzioni.TOPRIGHT) {
                    player.move(Constant.Direzioni.TOPRIGHT);
                }
                else if (a.direzione == Constant.Direzioni.BOTTOMLEFT) {
                    player.move(Constant.Direzioni.BOTTOMLEFT);
                }
                else if (a.direzione == Constant.Direzioni.BOTTOMRIGHT) {
                    player.move(Constant.Direzioni.BOTTOMRIGHT);
                }
            }
        }
    }

    private boolean playerInTile(Arrow arrow) {
        return player.getPos().x == arrow.arrowMapPos.y && player.getPos().y == arrow.arrowMapPos.x;
    }

    private void controlInput() {
        if (bool_win) {
            winActor.fire(new Event());
        }

        if (bool_lose) {
            loseActor.fire(new Event());
        }

        if (Gdx.input.justTouched()) {
            moveBottomRightActor.fire(new Event());
            moveBottomLeftActor.fire(new Event());
            moveUpRightActor.fire(new Event());
            moveUpLeftActor.fire(new Event());
        }
    }

    public void setBoolSwitch(boolean bool_switch) {
        this.bool_switch = bool_switch;
    }

    //metodo per controllare gli input da tastiera
    private void cameraInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) && player.isCanJump()) {
            player.move(Constant.Direzioni.BOTTOMLEFT);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D) && player.isCanJump()) {
            player.move(Constant.Direzioni.TOPRIGHT);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W) && player.isCanJump()) {
            player.move(Constant.Direzioni.TOPLEFT);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S) && player.isCanJump()) {
            player.move(Constant.Direzioni.BOTTOMRIGHT);
        }
    }



    public static Player getPlayer() {
        return player;
    }

    public void dispose() {
        super.dispose();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void setTileMap(Tilemap map) {
        this.map = map;
    }

    private GameScreen getGameScreen() {
        return this;
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
                return Color.WHITE;
        }

    }

}
