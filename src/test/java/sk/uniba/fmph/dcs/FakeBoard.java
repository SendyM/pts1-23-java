package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class FakeBoard implements BoardInterface{
    @Override
    public void put(int destinationIdx, List<Tile> tiles) {

    }

    @Override
    public FinishRoundResult finishRound() {
        return null;
    }

    @Override
    public void endGame() {

    }

    @Override
    public Points getPoints() {
        return null;
    }

    @Override
    public String state() {
        return null;
    }
}
