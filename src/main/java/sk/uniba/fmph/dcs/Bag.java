package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;

public class Bag {
    private final int bagSize = 100;
    private UsedTilesTakeInterface usedTiles;

    ArrayList<Tile> tiles;

    public Bag(UsedTilesTakeInterface usedTiles) {
        this.usedTiles = usedTiles;
        int numberOfDifferentTiles = Tile.values().length - 1;
        int numberOfEachTile = bagSize / numberOfDifferentTiles;
        tiles = new ArrayList<>();
        for (Tile tile : Tile.values()) {
            for (int i = 0; i < numberOfEachTile; i++) {
                tiles.add(tile);
            }
        }
        Collections.shuffle(tiles);
    }

    public ArrayList<Tile> take(int count) {
        ArrayList<Tile> takenTiles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (tiles.isEmpty()) {
                tiles.addAll(usedTiles.takeAll());
                Collections.shuffle(tiles);
            }
            takenTiles.add(tiles.remove(0));
        }
        return takenTiles;
    }

    public String State() {
        String toReturn = "";
        for (final Tile tile : tiles) {
            toReturn += tile.toString();
        }
        return toReturn;
    }
}
