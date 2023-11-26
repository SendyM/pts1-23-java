package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;

public class TableAreaIntegrationTest {
    private Bag bag;
    private ArrayList<TileSource> tileSources;
    private int playerCount;
    private TableCenter tableCenter;

    @Before
    public void setUp() {
        playerCount = 2;
        UsedTiles usedTiles = new UsedTiles();
        bag = new Bag(usedTiles);
        tileSources = new ArrayList<>();
        tableCenter = new TableCenter();
        tileSources.add(tableCenter);
        tileSources.add(new Factory(tableCenter, bag));
        for(int i = 0; i < playerCount; i++){
            tileSources.add(new Factory(tableCenter, bag));
            tileSources.add(new Factory(tableCenter, bag));
        }
    }

    @Test
    public void testStartNewRound() {
        TableArea tableArea = new TableArea(tileSources);
        tableArea.startNewRound();
        // 5 factories + 1 table center
        assertEquals("There should be 4 random tiles in each factory and a starting player tile in the table center",
                21, tableArea.state().length());

        // Verify that if there is not enough tiles in the bag + UsedTiles, it will throw an exception.
        bag.setTiles(new ArrayList<>(Arrays.asList(Tile.RED, Tile.RED, Tile.BLUE, Tile.BLUE)));
        assertThrows(IllegalArgumentException.class, tableArea::startNewRound);
    }
    @Test
    public void testTakeTiles() {
        bag.setTiles(new ArrayList<>(Arrays.asList(Tile.RED, Tile.RED, Tile.BLUE, Tile.BLUE)));
        tileSources = new ArrayList<>();
        TableCenter tableCenter = new TableCenter();
        tileSources.add(tableCenter);
        Factory factory = new Factory(tableCenter, bag);
        tileSources.add(factory);
        TableArea tableArea = new TableArea(tileSources);
        tableArea.startNewRound();

        // For testing purposes, we will use only one factory and one table center.
        // The factory will contain 2 red and 2 blue tiles.

        // Verify that the take method returns the 2 red tiles.
        assertEquals(new ArrayList<>(Arrays.asList(Tile.RED, Tile.RED)),tableArea.take(1, 0));

        // Verify that the factory is empty.
        assertEquals("The factory should be empty", "", factory.state());

        // Verify that table center contains the starting player tile + the two blue tiles.
        assertEquals("There should be the starting player tile + the two blue tiles", "SBB", tableCenter.state());

        // Verify that if the index is invalid, it will throw an exception.
        assertThrows(IndexOutOfBoundsException.class, () -> tableArea.take(0, -1));
        assertThrows(IllegalArgumentException.class, () -> tableArea.take(1, 0));
    }

    @Test
    public void testIsRoundEnd() {
        // Same testing setup as in testTakeTiles().
        bag.setTiles(new ArrayList<>(Arrays.asList(Tile.RED, Tile.RED, Tile.BLUE, Tile.BLUE)));
        tileSources = new ArrayList<>();
        TableCenter tableCenter = new TableCenter();
        tileSources.add(tableCenter);
        Factory factory = new Factory(tableCenter, bag);
        tileSources.add(factory);
        TableArea tableArea = new TableArea(tileSources);
        tableArea.startNewRound();

        tableArea.take(1, 0);

        // Verify that the round is not over yet.
        assertFalse(tableArea.isRoundEnd());

        // Verify that take will return the 2 blue tiles + the starting player tile.
        assertEquals("Should take 2 blue tiles and 1 starting player tile", new ArrayList<>(Arrays.asList(Tile.STARTING_PLAYER,Tile.BLUE, Tile.BLUE)) , tableArea.take(0, 1));

        // Verify that the round is over.
        assertTrue(tableArea.isRoundEnd());
    }
}
