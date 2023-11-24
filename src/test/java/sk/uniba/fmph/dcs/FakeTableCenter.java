package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakeTableCenter implements TableCenterAddInterface {
    public ArrayList<Tile> tiles;

    public FakeTableCenter() {
        tiles = new ArrayList<Tile>();
    }

    @Override
    public void add(ArrayList<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    @Override
    public String state() {
        String toReturn = "";
        for (final Tile tile : tiles) {
            toReturn += tile.toString();
        }
        return toReturn;
    }
}
