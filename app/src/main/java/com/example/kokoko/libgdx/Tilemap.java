package com.example.kokoko.libgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.example.kokoko.Constant;
import com.example.kokoko.libgdx.Screen.GameScreen;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

/** Classe per la crezione di una Tilemap */
public class Tilemap {
    // punteggio
    public static int punteggioTotale;
    //map name
    private static String map_prefix = "lvl";
    private static String map_filetype = ".txt";
    //
    private static int mapNum;
    // gameClass
    private static GameClass gameClass;
    //base mappa
    public LinkedList<Tile> base;
    //oggetti mappa
    //public LinkedList<Tile> objects;
    private Texture potenziamento;
    //map
    private String[][] map;

    public Tilemap(GameClass gameClass) {
        this.gameClass = gameClass;
        base = new LinkedList<Tile>();
        mapNum = gameClass.getNumLvl() + 1;
        punteggioTotale = 0;
        map = new String[10][10];
        try {
            fillMap();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public void render(SpriteBatch batch) {
        for (Tile t : base) {
            t.render(batch);
        }
    }

    private void fillMap() throws IOException {
        final InputStream inputStream = gameClass.assetManager.open(mapdir());
        final int size = inputStream.available();
        final byte[] buffer = new byte[size];
        inputStream.read(buffer);

        final String bufferString = new String(buffer);
        int count_row = 0;
        int count_col = 0;

        final String[] temp = bufferString.split("\n");

        count_col = (temp[0].length() + 1) / 2;
        for (int i = 0; i < temp.length; i++) {
            map[count_row] = temp[count_row].split(" ");
            count_row++;
        }
        inputStream.close();

        map[0][0] = Constant.TILE_IN_START_POS; // per mettere il player
        for (int row = count_row - 1; row >= 0; row--) {
            for (int col = count_col - 1; col >= 0; col--) {
                //serve per la visuale isometrica
                final float xWorldPos = (row - col) * (Constant.TILE_SPOS_INIT_X);
                final float yWorldPos = (col + row) * (Constant.TILE_SPOS_INIT_Y);

                if (map[row][col].contains(Constant.TILE_IN_START_POS) && (GameScreen.getPlayer().getPos().x == row && GameScreen.getPlayer().getPos().y == col)) {
                    base.add(new Tile(new Vector2(row, col), new Vector2(xWorldPos, yWorldPos), true));
                    punteggioTotale = punteggioTotale + Constant.PUNTEGGIO_PER_TILE;
                } else if (map[row][col].contains(Constant.TILE_IN_START_POS) && ((GameScreen.getPlayer().getPos().x != row || GameScreen.getPlayer().getPos().y != col))) {
                    base.add(new Tile(new Vector2(row, col), new Vector2(xWorldPos, yWorldPos), false));
                    punteggioTotale = punteggioTotale + Constant.PUNTEGGIO_PER_TILE;
                }

            }
        }
    }

    private static String mapdir() {
        return Tilemap.map_prefix + Tilemap.mapNum + Tilemap.map_filetype;
    }

}

