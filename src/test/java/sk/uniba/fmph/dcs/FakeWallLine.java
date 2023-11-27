package sk.uniba.fmph.dcs;

import java.util.*;

public class FakeWallLine implements WallLineInterface{
    private final Set<Tile> tileTypes;
    private final Set<Tile> unfilledTiles;

    public FakeWallLine(ArrayList<Tile> tileTypes) {
        this.tileTypes = new HashSet<>(tileTypes);
        this.unfilledTiles = new HashSet<>(tileTypes);
    }

    @Override
    public boolean canPutTile(Tile tile) {
        return unfilledTiles.contains(tile);
    }

    @Override
    public ArrayList<Optional<Tile>> getTiles() {
        ArrayList<Optional<Tile>> tiles = new ArrayList<>();
        for (Tile tile : tileTypes) {
            if (unfilledTiles.contains(tile)) {
                tiles.add(Optional.empty());
            } else {
                tiles.add(Optional.of(tile));
            }
        }
        return tiles;
    }

    @Override
    public Points putTile(Tile tile) {
        unfilledTiles.remove(tile);
        // returns 1 point for simplicity of the test
        return new Points(1);
    }

    @Override
    public void setLineUp(WallLine lineUp) {

    }

    @Override
    public void setLineDown(WallLine lineDown) {

    }

    @Override
    public String state() {
        ArrayList<Optional<Tile>> tiles = getTiles();
        String toReturn = "";
        for (Optional<Tile> tile : tiles) {
            toReturn += tile.toString();
        }
        return toReturn;
    }
}
