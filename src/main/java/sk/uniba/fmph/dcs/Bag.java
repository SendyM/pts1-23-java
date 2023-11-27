package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Bag {
    private final int bagSize = 100;
    private UsedTilesTakeInterface usedTiles;
    private int seed;

    ArrayList<Tile> tiles;

    public Bag(UsedTilesTakeInterface usedTiles) {
        this.seed = 0;
        this.usedTiles = usedTiles;
        int numberOfDifferentTiles = Tile.values().length - 1;
        int numberOfEachTile = bagSize / numberOfDifferentTiles;
        tiles = new ArrayList<>();
        for (Tile tile : Tile.values()) {
            if (tile == Tile.STARTING_PLAYER) continue;
            for (int i = 0; i < numberOfEachTile; i++) {
                tiles.add(tile);
            }
        }
        Collections.shuffle(tiles, new Random(seed));
    }

    public ArrayList<Tile> take(int count) {
        ArrayList<Tile> takenTiles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (tiles.isEmpty()) {
                tiles.addAll(usedTiles.takeAll());
                if(tiles.size() < count - i) throw new IllegalArgumentException("Not enough tiles in bag and used tiles combined");
                Collections.shuffle(tiles, new Random(seed));
            }
            takenTiles.add(tiles.remove(0));
        }
        return takenTiles;
    }

    // for testing
    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public String state() {
        String toReturn = "";
        for (final Tile tile : tiles) {
            toReturn += tile.toString();
        }
        return toReturn;
    }
}
