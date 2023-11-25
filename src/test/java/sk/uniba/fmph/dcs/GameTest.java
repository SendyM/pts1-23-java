package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;

public class GameTest {
    private Game game;
    private FakeTableArea tableArea;
    private ArrayList<BoardInterface> boards;
    private ArrayList<TileSource> tileSources;
    private FakeGameObserver gameObserver;
    private int startingPlayerId;

    @Before
    public void setUp() {
        FakeTableCenter fakeTableCenter = new FakeTableCenter();
        FakeUsedTiles usedTiles = new FakeUsedTiles();
        boards = new ArrayList<>(List.of(new FakeBoard(), new FakeBoard()));
        Bag bag = new Bag(usedTiles);
        bag.setTiles(new ArrayList<>(Arrays.asList(Tile.RED, Tile.RED, Tile.BLUE, Tile.BLUE, Tile.GREEN, Tile.GREEN, Tile.GREEN, Tile.GREEN)));
        Factory factory = new Factory(fakeTableCenter, bag);
        // For testing purposes, we will set the factory to contain 2 red and 2 blue.
        tileSources = new ArrayList<TileSource>();
        // For simplicity, we will only have one factory and one table center in the game.
        tileSources.add(factory);
        tileSources.add(fakeTableCenter);
        gameObserver = new FakeGameObserver();
        tableArea = new FakeTableArea(tileSources);

        // Set up the game with 2 players.
        game = new Game(2, boards, tableArea, gameObserver);
        // Starting player is chosen randomly.
    }

    @Test
    public void testTakeTiles() {
        // Verify that the factory contains 2 red, 1 blue.
        assertEquals("RRBB", tileSources.get(0).state());

        // Starting player takes the red tiles from the factory.
        assertTrue(game.take(game.getCurrentPlayerId(), 0, 0, 0));

        // Verify that the factory is empty.
        assertEquals("", tileSources.get(0).state());

        // Verify that table center contains the starting player tile + the green two blue tiles.
        assertEquals("SBB", tileSources.get(1).state());
    }
    @Test
    public void testTakeTiles_PlayerChanges() {
        startingPlayerId = game.getCurrentPlayerId();
        game.take(game.getCurrentPlayerId(), 0, 0, 0);

        // Verify that the current player is different from the starting player.
        assertNotEquals(game.getCurrentPlayerId(), startingPlayerId);

        // If the starting player tries to take the tiles again, it should fail.
        assertFalse(game.take(startingPlayerId, 0, 0, 0));

    }
    @Test
    public void testTakeTiles_InvalidSourceId() {
        game.take(game.getCurrentPlayerId(), 0, 0, 0);

        // Player taking from a factory that`s empty should result in an exception.
        assertThrows(IllegalArgumentException.class, () -> game.take(game.getCurrentPlayerId(), 0, 0, 0));
    }

    @Test
    public void testTakeTiles_TakingStartingPlayerTile() {
        game.take(game.getCurrentPlayerId(), 0, 0, 0);

        // Current player should take the starting player tile and blue tile from table center.
        startingPlayerId = game.getCurrentPlayerId();
        assertTrue(game.take(game.getCurrentPlayerId(), 1, 1, 0));

        // The starting player should be the one who took the starting player tile.
        assertEquals(startingPlayerId, game.getCurrentPlayerId());
    }
}
