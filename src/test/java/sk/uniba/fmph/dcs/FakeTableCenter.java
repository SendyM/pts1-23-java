package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakeTableCenter implements TableCenterAddInterface, TileSource{
    private ArrayList<Tile> tiles;
    private boolean isFirst;

    public FakeTableCenter() {
        tiles = new ArrayList<Tile>();
        isFirst = true;
    }

    @Override
    public void add(ArrayList<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    @Override
    public ArrayList<Tile> take(int idx) {
        if(idx < 0 || idx >= tiles.size()) throw new IllegalArgumentException("Invalid index");
        Tile pickedTile = tiles.get(idx);
        ArrayList<Tile> pickedTiles = new ArrayList<>();
        if(isFirst) pickedTiles.add(tiles.remove(0));
        isFirst = false;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i) == pickedTile) {
                pickedTiles.add(tiles.remove(i));
                i--;
            }
        }
        return pickedTiles;
    }

    @Override
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    @Override
    public void startNewRound() {
        tiles.clear();
        tiles.add(Tile.STARTING_PLAYER);
        isFirst = true;
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
