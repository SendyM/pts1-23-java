package sk.uniba.fmph.dcs;

import java.util.List;
import java.util.Optional;

public interface GameFinishedInterface {
    FinishRoundResult gameFinished(List<List<Optional<Tile>>> wall);
}
