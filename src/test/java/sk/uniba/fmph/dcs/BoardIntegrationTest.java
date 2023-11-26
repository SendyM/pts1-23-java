package sk.uniba.fmph.dcs;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class BoardIntegrationTest {
    private Board board;
    private Floor floor;
    private ArrayList<Points> points;
    private List<PatternLineInterface> patternLines;
    private List<WallLineInterface> wallLines;

    @Before
    public void setUp() {
        // set up points pattern, floor and points.
        ArrayList<Points> pointPattern = new ArrayList<>(Arrays.asList(new Points(-1), new Points(-1), new Points(-2), new Points(-2), new Points(-2), new Points(-3), new Points(-3)));
        UsedTiles usedTiles = new UsedTiles();
        floor = new Floor(usedTiles, pointPattern);
        points = new ArrayList<>();

        // set up wall lines
        LinkedList<Tile> tileTypes1 = new LinkedList<>(Arrays.asList(Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.GREEN, Tile.BLACK));
        LinkedList<Tile> tileTypes2 = new LinkedList<>(Arrays.asList(Tile.BLACK, Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.GREEN));
        LinkedList<Tile> tileTypes3 = new LinkedList<>(Arrays.asList(Tile.GREEN, Tile.BLACK, Tile.BLUE, Tile.YELLOW, Tile.RED));
        LinkedList<Tile> tileTypes4 = new LinkedList<>(Arrays.asList(Tile.RED, Tile.GREEN, Tile.BLACK, Tile.BLUE, Tile.YELLOW));
        LinkedList<Tile> tileTypes5 = new LinkedList<>(Arrays.asList(Tile.YELLOW, Tile.RED, Tile.GREEN, Tile.BLACK, Tile.BLUE));

        WallLine wallLine1 = new WallLine(tileTypes1, null, null);
        WallLine wallLine2 = new WallLine(tileTypes2, null, null);
        WallLine wallLine3 = new WallLine(tileTypes3, null, null);
        WallLine wallLine4 = new WallLine(tileTypes4, null, null);
        WallLine wallLine5 = new WallLine(tileTypes5, null, null);

        wallLine1.setLineDown(wallLine2);
        wallLine2.setLineDown(wallLine3);
        wallLine3.setLineDown(wallLine4);
        wallLine4.setLineDown(wallLine5);
        wallLine5.setLineUp(wallLine4);
        wallLine4.setLineUp(wallLine3);
        wallLine3.setLineUp(wallLine2);
        wallLine2.setLineUp(wallLine1);

        wallLines = Arrays.asList(wallLine1, wallLine2, wallLine3, wallLine4, wallLine5);

        // set up pattern lines
        patternLines = Arrays.asList(new PatternLine(1, wallLine1, floor, usedTiles), new PatternLine(2, wallLine2, floor, usedTiles), new PatternLine(3, wallLine3, floor, usedTiles), new PatternLine(4, wallLine4, floor, usedTiles), new PatternLine(5, wallLine5, floor, usedTiles));
        FinalPointsCalculation finalPointsCalculation = new FinalPointsCalculation();
        GameFinished gameFinished = new GameFinished();

        board = new Board(floor, points, patternLines, wallLines, finalPointsCalculation, gameFinished);
    }

    @Test
    public void testEndToEnd() {
        // round 1
        board.put(0, new ArrayList<>(List.of(Tile.BLUE)));
        board.put(1, new ArrayList<>(Arrays.asList(Tile.BLUE, Tile.BLUE)));
        board.put(2, new ArrayList<>(Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE))); // this should put 1 tile on the floor
        board.put(3, new ArrayList<>(Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE)));
        board.put(4, new ArrayList<>(Arrays.asList(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.BLUE)));

        assertEquals("B", patternLines.get(0).state());
        assertEquals("BB", patternLines.get(1).state());
        assertEquals("BBB", patternLines.get(2).state());
        assertEquals("BBBB", patternLines.get(3).state());
        assertEquals("BBBBB", patternLines.get(4).state());
        assertEquals("B", floor.state());

        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(4, Points.sum(points).getValue());

        assertEquals("B....", wallLines.get(0).state());
        assertEquals(".B...", wallLines.get(1).state());
        assertEquals("..B..", wallLines.get(2).state());
        assertEquals("...B.", wallLines.get(3).state());
        assertEquals("....B", wallLines.get(4).state());
        assertEquals("", floor.state());

        // round 2
        board.put(0, new ArrayList<>(List.of(Tile.YELLOW)));
        board.put(1, new ArrayList<>(Arrays.asList(Tile.BLACK, Tile.BLACK)));
        board.put(2, new ArrayList<>(Arrays.asList(Tile.BLACK, Tile.BLACK, Tile.BLACK)));
        board.put(3, new ArrayList<>(Arrays.asList(Tile.GREEN, Tile.GREEN, Tile.GREEN, Tile.GREEN)));
        board.put(4, new ArrayList<>(Arrays.asList(Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED)));

        assertEquals("I", patternLines.get(0).state());
        assertEquals("LL", patternLines.get(1).state());
        assertEquals("LLL", patternLines.get(2).state());
        assertEquals("GGGG", patternLines.get(3).state());
        assertEquals("RRRRR", patternLines.get(4).state());

        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(23, Points.sum(points).getValue());

        assertEquals("BI...", wallLines.get(0).state());
        assertEquals("LB...", wallLines.get(1).state());
        assertEquals(".LB..", wallLines.get(2).state());
        assertEquals(".G.B.", wallLines.get(3).state());
        assertEquals(".R..B", wallLines.get(4).state());

        // round 3. Since its getting repetitive, we will simplify the tests a bit.
        board.put(0, new ArrayList<>(List.of(Tile.RED)));
        board.put(1, new ArrayList<>(List.of(Tile.YELLOW)));

        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(26, Points.sum(points).getValue());

        // round 4
        board.put(0, new ArrayList<>(List.of(Tile.GREEN)));

        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(30, Points.sum(points).getValue());

        // round 5. This should end the game.
        board.put(0, new ArrayList<>(List.of(Tile.BLACK)));

        assertEquals(FinishRoundResult.GAME_FINISHED, board.finishRound());
        // The final points should be 54.
        assertEquals(54, Points.sum(points).getValue());
    }
}
