package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WallLine implements WallLineInterface {
    private ArrayList<Tile> tileTypes;
    private WallLine lineUp;
    private WallLine lineDown;

    private boolean[] occupiedTiles;


    public WallLine(List<Tile> tileTypes, WallLine lineUp, WallLine lineDown) {
        this.tileTypes = new ArrayList<>(tileTypes);
        this.lineUp = lineUp;
        this.lineDown = lineDown;

        this.occupiedTiles = new boolean[tileTypes.size()];
    }

    public void setLineUp(WallLine lineUp) {
        this.lineUp = lineUp;
    }

    public void setLineDown(WallLine lineDown) {
        this.lineDown = lineDown;
    }

    public boolean canPutTile(Tile tile) {
        return tileTypes.contains(tile) && !occupiedTiles[tileTypes.indexOf(tile)];
    }

    public List<Optional<Tile>> getTiles() {
        ArrayList<Optional<Tile>> tiles = new ArrayList<>();
        for (int i = 0; i < tileTypes.size(); i++) {
            if (occupiedTiles[i]) {
                tiles.add(Optional.ofNullable(this.tileTypes.get(i)));
            } else {
                Tile t = null;
                tiles.add(Optional.ofNullable(t));
            }
        }
        return tiles;
    }

    public Points putTile(Tile tile) {
        if (canPutTile(tile)) {
            int idx = tileTypes.indexOf(tile);
            this.occupiedTiles[idx] = true;

            int points = 1;

            int offset = 1;
            while (offset + idx < tileTypes.size()) {
                if (occupiedTiles[offset + idx]) {
                    points++;
                    offset++;
                } else {
                    break;
                }
            }

            offset = 1;
            while (idx - offset >= 0) {
                if (occupiedTiles[idx - offset]) {
                    points++;
                    offset++;
                } else {
                    break;
                }
            }
            WallLine current = this;
            while (current.lineUp != null) {
                if (current.lineUp.getTiles().get(idx).isPresent()) {
                    points++;
                    current = current.lineUp;
                } else {
                    break;
                }
            }

            current = this;
            while (current.lineDown != null) {
                if (current.lineDown.getTiles().get(idx).isPresent()) {
                    points++;
                    current = current.lineDown;
                } else {
                    break;
                }
            }

            return new Points(points);

        }
        return new Points(0);
    }

    public String state() {
        String toReturn = "";
        for (int i = 0; i < tileTypes.size(); i++) {
            if (occupiedTiles[i]) {
                toReturn += tileTypes.get(i).toString();
            }else{
                toReturn += ".";
            }
        }
        return toReturn;
    }
}