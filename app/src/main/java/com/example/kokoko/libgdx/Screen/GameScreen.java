package com.example.kokoko.libgdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.example.kokoko.libgdx.GameClass;
import com.example.kokoko.libgdx.Player;
import com.example.kokoko.libgdx.Screens;
import com.example.kokoko.libgdx.Tile;
import com.example.kokoko.libgdx.Tilemap;

enum direzione{TopRight, TopLeft, BottomRight, BottomLeft};

public class GameScreen extends Screens implements Screen {

    public final GameClass gameClass;
    private OrthographicCamera camera;
    private Tilemap map;
    public static Player player;
    private boolean bool_switch,bool_win, bool_lose;
    private Stage stage;
    private static TextureAtlas atlas;
    private float clickY;
    private Actor winActor,pauseActor, loseActor,moveUpRightActor,moveUpLeftActor,moveBottomRightActor,moveBottomLeftActor;


    public GameScreen(final GameClass gameClass){
        this.gameClass = gameClass;
        atlas = new TextureAtlas("Asset_Proj.pack");
        camera = new OrthographicCamera(Constant.NUMERO_PIXEL_X,Constant.NUMERO_PIXEL_Y);
        camera.position.set(Constant.TILE_WIDHT/2, Constant.TILE_WIDHT/2 + Constant.BORDER_HEIGHT,0);
        player = new Player(this);
        map = new Tilemap(this.gameClass);

        bool_switch = false;
        bool_win = false;
        bool_lose = true;

        stage = new Stage();
        stage.clear();

        winActor = new Actor();
        pauseActor = new Actor();
        winActor.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameClass.sScreen = 2;
                gameClass.bSwitch = true;
                return true;
            }
        });
        loseActor = new Actor();
        loseActor.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameClass.sScreen = 4;
                gameClass.bSwitch = true;
                return true;
            }
        });

        moveUpRightActor = new Actor();
        moveUpRightActor.setBounds(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        moveUpRightActor.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(player.isCanIJump()) {
                    camera.position.y += Constant.CAMERA_MOVEMENT_Y;
                    camera.position.x += Constant.CAMERA_MOVEMENT_X;
                    player.move(Constant.direzioni.TOPRIGHT);
                    bool_lose = true;
                }
                return true;
            }
        });

        moveUpLeftActor = new Actor();
        moveUpLeftActor.setBounds(0,Gdx.graphics.getHeight()/2,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        moveUpLeftActor.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(player.isCanIJump()) {
                    camera.position.y += Constant.CAMERA_MOVEMENT_Y;
                    camera.position.x -= Constant.CAMERA_MOVEMENT_X;
                    player.move(Constant.direzioni.TOPLEFT);
                    bool_lose = true;
                }
                return true;
            }
        });

        moveBottomLeftActor = new Actor();
        moveBottomLeftActor.setBounds(0,0,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        moveBottomLeftActor.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(player.isCanIJump()){
                    camera.position.y -= Constant.CAMERA_MOVEMENT_Y;
                    camera.position.x -= Constant.CAMERA_MOVEMENT_X;
                    player.move(Constant.direzioni.BOTTOMLEFT);
                    bool_lose = true;
                }
                return true;
            }
        });

        moveBottomRightActor = new Actor();
        moveBottomRightActor.setBounds(Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        moveBottomRightActor.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(player.isCanIJump()) {
                    camera.position.y -= Constant.CAMERA_MOVEMENT_Y;
                    camera.position.x += Constant.CAMERA_MOVEMENT_X;
                    player.move(Constant.direzioni.BOTTOMRIGHT);
                    bool_lose = true;
                }
                return true;
            }
        });

        /*pauseActor.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                gameClass.setScreen(new PauseMenuScreen(gameClass,getGameScreen()));
                return true;
            }
        });*/

        stage.addActor(moveBottomLeftActor);
        stage.addActor(moveBottomRightActor);
        stage.addActor(moveUpLeftActor);
        stage.addActor(moveUpRightActor);
        stage.addActor(winActor);
        stage.addActor(loseActor);

        Gdx.input.setInputProcessor(this.stage);

    }

    @Override
    public void render(float delta) {
        delta++;
        //pulisco lo screen
        Gdx.gl.glClearColor(0.5f,0.3f,0.3f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // disegno sulla camera
        gameClass.batch.setProjectionMatrix(camera.combined);
        cameraInput();

        camera.update();
        player.update(delta);

        //cambia colore tile
        for(Tile t : map.base) {
            if (player.getPos().x == t.tileMapPos.y && player.getPos().y == t.tileMapPos.x && bool_switch) {
                t.on = !t.on;
                bool_switch = false;
            }
        }

        gameClass.batch.begin();
        map.render(gameClass.batch);
        player.render(gameClass.batch);
        gameClass.batch.end();

        for(Tile t : map.base) {
            if (!t.on) {
                bool_win = false;
                break;
            }
            bool_win = true;
        }

        for(Tile t : map.base) {
            if(player.getPos().x == t.tileMapPos.y && player.getPos().y == t.tileMapPos.x){
                bool_lose = false;
                break;
            }else{
                bool_lose = true;
            }
        }

        if(bool_win){
            System.out.println("YOU WIN");
            winActor.fire(new Event());
        }

        if(bool_lose){
            System.out.println("YOU LOSE");
            loseActor.fire(new Event());
        }

        if(Gdx.input.justTouched()){
            moveBottomRightActor.fire(new Event());
            moveBottomLeftActor.fire(new Event());
            moveUpRightActor.fire(new Event());
            moveUpLeftActor.fire(new Event());
        }
    }


    public void setBool_switch(boolean bool_switch) {
        this.bool_switch = bool_switch;
    }

    public void cameraInput() {
        //clickY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            pauseActor.fire(new Event());} // TODO pausa(?)
        if(Gdx.input.isKeyJustPressed(Input.Keys.A) && player.isCanIJump()){
            camera.position.y -= Constant.CAMERA_MOVEMENT_Y;
            camera.position.x -= Constant.CAMERA_MOVEMENT_X;
            player.move(Constant.direzioni.BOTTOMLEFT);
            bool_lose = true;
        } else if((Gdx.input.isKeyJustPressed(Input.Keys.D) || moveUpRightActor.fire(new Event())) && player.isCanIJump()){
            camera.position.y += Constant.CAMERA_MOVEMENT_Y;
            camera.position.x += Constant.CAMERA_MOVEMENT_X;
            player.move(Constant.direzioni.TOPRIGHT);
            bool_lose = true;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.W) && player.isCanIJump()){
            camera.position.y += Constant.CAMERA_MOVEMENT_Y;
            camera.position.x -= Constant.CAMERA_MOVEMENT_X;
            player.move(Constant.direzioni.TOPLEFT);
            bool_lose = true;
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.S) && player.isCanIJump()){
            camera.position.y -= Constant.CAMERA_MOVEMENT_Y;
            camera.position.x += Constant.CAMERA_MOVEMENT_X;
            player.move(Constant.direzioni.BOTTOMRIGHT);
            bool_lose = true;
        } else if(Gdx.input.isKeyPressed(Input.Keys.Z)){
            camera.zoom -= 0.005;
        } else if(Gdx.input.isKeyPressed(Input.Keys.X)){
            camera.zoom += 0.005;
        }
    }

    public static Player getPlayer(){
        return player;
    }
    public void dispose() {
        super.dispose();
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }
    public void setTileMap(Tilemap map){ this.map = map; }
    private GameScreen getGameScreen(){ return this; }

}
