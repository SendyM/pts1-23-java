package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class FakeBoard implements BoardInterface{
    @Override
    public void put(int destinationIdx, ArrayList<Tile> tiles) {

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
