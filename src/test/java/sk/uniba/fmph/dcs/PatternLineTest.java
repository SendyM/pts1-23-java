package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;

public class PatternLineTest {
    private PatternLine patternLine;
    private FakeWallLine fakeWallLine;
    private Floor floor;

    @Before
    public void setUp() {
        FakeUsedTiles usedTiles = new FakeUsedTiles();
        fakeWallLine = new FakeWallLine(new ArrayList<>(Arrays.asList(Tile.GREEN, Tile.BLACK, Tile.BLUE, Tile.YELLOW, Tile.RED)));
        ArrayList<Points> pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));
        floor = new Floor(usedTiles,pointPattern);
        patternLine = new PatternLine(3, fakeWallLine, floor, usedTiles);
    }
    @Test
    public void testPutTiles() {
        // Put 2 red tiles on the pattern line.
        patternLine.put(new ArrayList<>(Arrays.asList(Tile.RED, Tile.RED)));
        assertEquals("RR.", patternLine.state()); // Verify that the pattern line contains the red tile.
    }

    @Test
    public void testPutTiles_WrongTileColor() {
        // Try to put a blue tile on the pattern line.
        patternLine.put(new ArrayList<>(Collections.singletonList(Tile.BLUE))); //Should go to the floor.
        assertEquals("B", floor.state()); // Verify that the floor contains the blue tile.
        assertEquals("RR.", patternLine.state()); // Verify that the pattern line didn't change.
    }

    @Test
    public void testPutTiles_Overflow() {
        //Try to put 3 red tiles on the pattern line (2 should go to the floor).
        patternLine.put(new ArrayList<>(Arrays.asList(Tile.RED, Tile.RED, Tile.RED)));
        assertEquals("RRR", floor.state()); // Verify that the pattern line is full.
        // Verify that the floor received the extra tiles.
        assertEquals("BRR", floor.state());
    }

    @Test
    public void testFinishRound_PatternLineFull() {
        assertEquals(new Points(1), patternLine.finishRound()); // Assuming putting a tile gives 1 point
        assertEquals("", patternLine.state()); // Verify that the pattern line is empty now.
    }

    @Test
    public void testFinishRound_PatternLineNotFull() {
        // Put a red tile on the pattern line.
        patternLine.put(new ArrayList<>(Collections.singletonList(Tile.RED)));
        assertEquals(new Points(0), patternLine.finishRound()); // Should not give any points.
        assertEquals("R..", patternLine.state()); // Verify that the pattern line didn't change.
    }
}
