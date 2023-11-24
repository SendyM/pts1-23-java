package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;

class FakeUsedTiles implements UsedTilesGiveInterface, UsedTilesTakeInterface {
    public ArrayList<Tile> tiles;

    public FakeUsedTiles() {
        tiles = new ArrayList<Tile>();
    }

    public void give(Collection<Tile> t) {
        tiles.addAll(t);
    }

    public ArrayList<Tile> takeAll() {
        ArrayList<Tile> tilesToReturn = new ArrayList<Tile>(this.tiles);
        this.tiles.clear();
        return tilesToReturn;
    }
}
