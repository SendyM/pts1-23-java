package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public interface BoardInterface {
    void put(int destinationIdx, List<Tile> tiles);
    FinishRoundResult finishRound();
    void endGame();
    Points getPoints();
    String state();
}
