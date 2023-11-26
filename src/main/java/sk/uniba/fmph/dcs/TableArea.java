package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class TableArea implements TableAreaInterface{
    ArrayList<TileSource> tileSources;

    public TableArea(ArrayList<TileSource> tileSources) {
        this.tileSources = tileSources;
    }

    public ArrayList<Tile> take(int sourceIdx, int idx) {
        return tileSources.get(sourceIdx).take(idx);
    }

    public boolean isRoundEnd() {
        for (TileSource tileSource : tileSources) {
            if (!tileSource.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void startNewRound() {
        for (TileSource tileSource : tileSources) {
            tileSource.startNewRound();
        }
    }


    public String state() {
        String toReturn = "";
        for (final TileSource tileSource : tileSources) {
            toReturn += tileSource.state();
        }
        return toReturn;
    }
}