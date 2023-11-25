package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakeTableArea implements TableAreaInterface{
    ArrayList<TileSource> tileSources;

    public FakeTableArea(ArrayList<TileSource> tileSources) {
        this.tileSources = tileSources;
    }
    @Override
    public ArrayList<Tile> take(int sourceIdx, int idx) {
        return tileSources.get(sourceIdx).take(idx);
    }

    @Override
    public boolean isRoundEnd() {
        for(TileSource tileSource : tileSources){
            if(!tileSource.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public void startNewRound() {
        for(TileSource tileSource : tileSources){
            tileSource.startNewRound();
        }
    }

    @Override
    public String state() {
        return null;
    }
}
