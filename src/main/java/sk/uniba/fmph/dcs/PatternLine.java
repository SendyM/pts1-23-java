package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class PatternLine {
    private final int capacity;
    private ArrayList<Tile> tiles;
    private final WallLineInterface wallLine;
    private final UsedTilesGiveInterface usedTiles;
    private Floor floor;

    public PatternLine(int capacity, WallLineInterface wallLine, Floor floor, UsedTilesGiveInterface usedTiles) {
        this.tiles = new ArrayList<>();
        this.usedTiles = usedTiles;
        this.capacity = capacity;
        this.wallLine = wallLine;
        this.floor = floor;
    }

    public void put(ArrayList<Tile> tilesToAdd) {
        if(tilesToAdd.isEmpty()) return;
        if(!tiles.isEmpty() && tilesToAdd.get(0) != tiles.get(0)) {
            for (int i = 0; i < tilesToAdd.size(); i++) {
                floor.put(tilesToAdd);
            }
            return;
        }
        while(this.tiles.size() < capacity && !tilesToAdd.isEmpty() && wallLine.canPutTile(tilesToAdd.get(0))) {
            this.tiles.add(tilesToAdd.remove(0));
        }
        if(!tilesToAdd.isEmpty()) {
            floor.put(tilesToAdd);
        }
    }

    public Points finishRound() {
        if(tiles.size() == capacity) {
            Points points = wallLine.putTile(tiles.get(0));
            usedTiles.give(tiles);
            tiles.clear();
            return points;
        }
        return new Points(0);
    }

    public String state() {
        String toReturn = "";
        for (final Tile tile : tiles) {
            toReturn += tile.toString();
        }
        for (int i = 0; i < capacity - tiles.size(); i++) {
            toReturn += ".";
        }
        return toReturn;
    }

}
