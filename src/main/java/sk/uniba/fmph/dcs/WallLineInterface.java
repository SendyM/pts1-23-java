package sk.uniba.fmph.dcs;

import java.util.List;
import java.util.Optional;

public interface WallLineInterface {
    boolean canPutTile(Tile tile);
    List<Optional<Tile>> getTiles();
    Points putTile(Tile tile);
    void setLineUp(WallLine lineUp);
    void setLineDown(WallLine lineDown);
    String state();
}