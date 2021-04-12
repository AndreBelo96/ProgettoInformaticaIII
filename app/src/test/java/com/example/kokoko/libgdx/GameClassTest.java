package com.example.kokoko.libgdx;

import com.example.kokoko.Constant;
import com.example.kokoko.android.activities.MainActivity;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GameClassTest {

    private static final Constant.NumeroScreen GS = Constant.NumeroScreen.GAMESCREEN;
    private static final Constant.NumeroScreen LS = Constant.NumeroScreen.LOSESCREEN;
    private static final Constant.NumeroScreen MS = Constant.NumeroScreen.MENUSCREEN;
    private static final Constant.NumeroScreen OS = Constant.NumeroScreen.OPTIONSCREEN;
    private static final Constant.NumeroScreen WS = Constant.NumeroScreen.WINSCREEN;
    @Mock
    private GameClass gc = Mockito.spy(GameClass.class);

    @Test
    void testCreate(){
        gc.create();
        Mockito.verify(gc).create();
    }

    @Test
    void testConstructor(){
        assertNotNull(gc);
    }

    @Test
    void setMenuScreen() {
        Mockito.when(gc.setScreen()).thenReturn(Constant.NumeroScreen.MENUSCREEN);
        Constant.NumeroScreen ris = gc.setScreen();
        assertEquals(ris, MS);
    }

    @Test
    void setGameScreen() {
        Mockito.when(gc.setScreen()).thenReturn(Constant.NumeroScreen.GAMESCREEN);
        Constant.NumeroScreen ris = gc.setScreen();
        assertEquals(ris, GS);
    }

    @Test
    void setLoseScreen() {
        Mockito.when(gc.setScreen()).thenReturn(Constant.NumeroScreen.LOSESCREEN);
        Constant.NumeroScreen ris = gc.setScreen();
        assertEquals(ris, LS);
    }

    @Test
    void setWinScreen() {
        Mockito.when(gc.setScreen()).thenReturn(Constant.NumeroScreen.WINSCREEN);
        Constant.NumeroScreen ris = gc.setScreen();
        assertEquals(ris, WS);
    }

    @Test
    void setOptionScreen() {
        Mockito.when(gc.setScreen()).thenReturn(Constant.NumeroScreen.OPTIONSCREEN);
        Constant.NumeroScreen ris = gc.setScreen();
        assertEquals(ris, OS);
    }

}