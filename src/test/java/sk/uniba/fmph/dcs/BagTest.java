package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;

public class BagTest {

    @Test
    public void testTaking() {
        FakeUsedTiles usedTiles = new FakeUsedTiles();
        usedTiles.tiles = new ArrayList<>(Arrays.asList(Tile.RED, Tile.RED));

        // Initialize the bag with 100 shuffled tiles.
        Bag bag = new Bag(usedTiles);
        assertEquals(100, bag.state().length());

        // For testing purposes, set the bag to contain 10 tiles, 2 of each color.
        bag.setTiles(new ArrayList<>(Arrays.asList(
            Tile.RED, Tile.RED,
            Tile.GREEN, Tile.GREEN,
            Tile.BLUE, Tile.BLUE,
            Tile.YELLOW, Tile.YELLOW,
            Tile.BLACK, Tile.BLACK
        )));

        // Draw the first set of 4 tiles.
        List<Tile> firstDrawnTiles = bag.take(4);
        assertEquals(4, firstDrawnTiles.size());

        // Draw the second set of 4 tiles.
        List<Tile> secondDrawnTiles = bag.take(4);
        assertEquals(4, secondDrawnTiles.size());

        // Draw the remaining 2 tiles and refill from UsedTiles.
        List<Tile> lastDrawnTiles = bag.take(4);
        assertEquals(4, lastDrawnTiles.size());

        // Assert that the bag is now empty.
        assertEquals("", bag.state());

        // Assert that drawing more tiles than are available throws an exception.
        assertThrows(IllegalArgumentException.class, () -> bag.take(1));
    }
}
