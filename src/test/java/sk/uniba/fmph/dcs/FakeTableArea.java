package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakeTableArea implements TableAreaInterface{
    ArrayList<TileSource> tileSources;

    public FakeTableArea(ArrayList<TileSource> tileSources) {
        this.tileSources = tileSources;
    }
    @Override
    public ArrayList<Tile> take(int sourceIdx, int idx) {
        return null;
    }

    @Override
    public boolean isRoundEnd() {
        return false;
    }

    @Override
    public void startNewRound() {

    }

    @Override
    public String state() {
        return null;
    }
}
