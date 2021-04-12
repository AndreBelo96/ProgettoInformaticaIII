package com.example.kokoko.libgdx;

import com.example.kokoko.Constant;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class PlayerTest {

    private static final Constant.Direzioni BL = Constant.Direzioni.BOTTOMLEFT;
    private static final Constant.Direzioni TL = Constant.Direzioni.TOPLEFT;
    private static final Constant.Direzioni TR = Constant.Direzioni.TOPRIGHT;
    private static final Constant.Direzioni BR = Constant.Direzioni.BOTTOMRIGHT;
    @Mock
    private Player player = Mockito.mock(Player.class);


    @Test
    public void testConstructor(){
        assertNotNull(player);
    }


    @Test
    public void testMoveBL(){
        Mockito.when(player.move(Mockito.any(Constant.Direzioni.class))).thenReturn(3);
        int ris = player.move(BL);
        assertEquals(ris, 3);
    }

    @Test
    public void testMoveTR(){
        Mockito.when(player.move(Mockito.any(Constant.Direzioni.class))).thenReturn(4);
        int ris = player.move(TR);
        assertEquals(ris, 4);
    }

    @Test
    public void testMoveTL(){
        Mockito.when(player.move(Mockito.any(Constant.Direzioni.class))).thenReturn(1);
        int ris = player.move(TL);
        assertEquals(ris, 1);
    }

    @Test
    public void testMoveBR(){
        Mockito.when(player.move(Mockito.any(Constant.Direzioni.class))).thenReturn(2);
        int ris = player.move(BR);
        assertEquals(ris, 2);
    }

    @Test
    public void testCanJump(){
        Mockito.when(player.isCanJump()).thenReturn(true);
        assertTrue(player.isCanJump());
    }

    @Test
    void testUpDate(){
        player.update(Mockito.anyFloat());
        Mockito.verify(player).update(Mockito.anyFloat());
    }
}