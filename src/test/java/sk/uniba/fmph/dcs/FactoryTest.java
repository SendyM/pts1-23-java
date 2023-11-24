package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;

public class FactoryTest {
    private Factory factory;
    private TableCenterAddInterface fakeTableCenter;
    private Bag bag;

    @Before
    public void setUp() {
        UsedTilesTakeInterface usedTiles = new FakeUsedTiles();
        this.bag = new Bag(usedTiles);
        // Set the bag to contain 2 red, 1 blue, and 1 green tile.
        bag.setTiles(new ArrayList<>(Arrays.asList(Tile.RED, Tile.RED, Tile.BLUE, Tile.GREEN)));
        fakeTableCenter = new FakeTableCenter();
        factory = new Factory(fakeTableCenter, bag);
    }

    @Test
    public void testTakeTiles() {

        // Reset the factory and take the only 4 tiles from the bag. Now the factory should contain 2 red, 1 blue, and 1 green tile.
        factory.startNewRound();
        assertEquals("RRBG", factory.state());

        // Take red tiles from the factory.
        List<Tile> takenTiles = factory.take(0); // Assuming 0 is the index for the RED Tile.

        assertEquals(2, takenTiles.size()); // Should take two red tiles.
        assertTrue(takenTiles.stream().allMatch(tile -> tile == Tile.RED)); // All taken tiles should be red.
        assertTrue(factory.isEmpty()); // Factory should be empty.

        // Verify that blue and green tiles were moved to the table center.
        assertEquals("BG", fakeTableCenter.state());
    }

    @Test
    public void testTakeTiles_InvalidIndex() {
        assertThrows(IllegalArgumentException.class, () -> factory.take(-1));
        assertThrows(IllegalArgumentException.class, () -> factory.take(4));
    }

    @Test
    public void testStartNewRound_RefillsFactory() {
        // Set the bag to contain 1 red, 2 blue, and 1 green tile.
        bag.setTiles(new ArrayList<>(Arrays.asList(Tile.BLUE, Tile.RED, Tile.BLUE, Tile.GREEN)));

        // Factory should be empty initially.
        assertTrue(factory.isEmpty());

        factory.startNewRound();

        // Factory should be refilled and not empty.
        assertFalse(factory.isEmpty());
    }

}