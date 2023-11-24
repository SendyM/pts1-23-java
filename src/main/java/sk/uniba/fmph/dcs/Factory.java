package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class Factory implements TileSource {
    private ArrayList<Tile> tiles;
    private final int factorySize = 4;
    private final TableCenterAddInterface tableCenter;
    private final Bag bag;

    public Factory(TableCenterAddInterface tableCenter, Bag bag) {
        this.tableCenter = tableCenter;
        this.bag = bag;
    }

    @Override
    public ArrayList<Tile> take(int idx) {
        Tile pickedTile = tiles.get(idx);
        ArrayList<Tile> pickedTiles = new ArrayList<>();
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i) == pickedTile) {
                pickedTiles.add(tiles.remove(i));
                i--;
            }
        }
        tableCenter.add(tiles);
        tiles.clear();
        return pickedTiles;
    }

    @Override
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    @Override
    public void startNewRound() {
        tiles.clear();
        tiles.addAll(bag.take(factorySize));
    }

    @Override
    public String State() {
        String toReturn = "";
        for (final Tile tile : tiles) {
            toReturn += tile.toString();
        }
        return toReturn;
    }
}
