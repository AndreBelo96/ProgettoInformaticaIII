package com.example.kokoko.libgdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.Screen.GameScreen;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(MockitoJUnitRunner.class)
class PlayerTest {

    private static final Constant.Direzioni BL = Constant.Direzioni.BOTTOMLEFT;
    private static final Constant.Direzioni TL = Constant.Direzioni.TOPLEFT;
    private static final Constant.Direzioni TR = Constant.Direzioni.TOPRIGHT;
    private static final Constant.Direzioni BR = Constant.Direzioni.BOTTOMRIGHT;



    @Test
    public void testMoveBL(){
        GameScreen gameScreen = Mockito.mock(GameScreen.class);
        Player player = new Player(gameScreen, new OrthographicCamera());
        int ris = player.move(BL);
        assertEquals(ris, 3);
    }

    @Test
    public void testMoveTR(){
        GameScreen gameScreen = Mockito.mock(GameScreen.class);
        Player player = new Player(gameScreen, new OrthographicCamera());
        int ris = player.move(TR);
        assertEquals(ris, 4);
    }

    @Test
    public void testMoveTL(){
        GameScreen gameScreen = Mockito.mock(GameScreen.class);
        Player player = new Player(gameScreen, new OrthographicCamera());
        int ris = player.move(TL);
        assertEquals(ris, 1);
    }

    @Test
    public void testMoveBR(){
        GameScreen gameScreen = Mockito.mock(GameScreen.class);
        Player player = new Player(gameScreen, new OrthographicCamera());
        int ris = player.move(BR);
        assertEquals(ris, 2);
    }

    @Test
    public void testCanJump(){
        GameScreen gameScreen = Mockito.mock(GameScreen.class);
        Player player = new Player(gameScreen, new OrthographicCamera());
        assertTrue(player.isCanJump());
    }

    @Test
    public void testVect2(){
        GameScreen gameScreen = Mockito.mock(GameScreen.class);
        Player player = new Player(gameScreen, new OrthographicCamera());
        assertNotNull(player.getPos());
    }

    @Test
    void testUpDate(){
        GameScreen gameScreen = Mockito.mock(GameScreen.class);
        Player player = new Player(gameScreen, new OrthographicCamera());
        player.update(Mockito.anyFloat());
        assertTrue(player.time>=0);
    }
}