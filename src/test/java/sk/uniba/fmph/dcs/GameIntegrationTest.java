package sk.uniba.fmph.dcs;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;

public class GameIntegrationTest {
    private Game game;
    private Bag bag;
    private TableArea tableArea;
    private ArrayList<TileSource> tileSources;
    private GameObserver gameObserver;
    private TableCenter tableCenter;
    private int startingPlayerId;
    ArrayList<BoardInterface> boards = new ArrayList<>();

    @Before
    public void setUp() {
        int playerCount = 2;
        List<PatternLineInterface> patternLines;
        // set up used tiles and floor
        ArrayList<Points> pointPattern = new ArrayList<>(Arrays.asList(new Points(-1), new Points(-1), new Points(-2), new Points(-2), new Points(-2), new Points(-3), new Points(-3)));
        UsedTiles usedTiles = new UsedTiles();
        Floor floor = new Floor(usedTiles, pointPattern);

        // set up for player boards
        // set up points pattern, floor and points
        // create player boards
        for (int i = 0; i < playerCount; i++) {

        ArrayList<Points> points = new ArrayList<>();
        List<WallLineInterface> wallLines = new ArrayList<>();
        bag = new Bag(usedTiles);

        // set up tile types for wall lines
        ArrayList<LinkedList<Tile>> tileTypesList = new ArrayList<>();
        tileTypesList.add(new LinkedList<>(Arrays.asList(Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.GREEN, Tile.BLACK)));
        tileTypesList.add(new LinkedList<>(Arrays.asList(Tile.BLACK, Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.GREEN)));
        tileTypesList.add(new LinkedList<>(Arrays.asList(Tile.GREEN, Tile.BLACK, Tile.BLUE, Tile.YELLOW, Tile.RED)));
        tileTypesList.add(new LinkedList<>(Arrays.asList(Tile.RED, Tile.GREEN, Tile.BLACK, Tile.BLUE, Tile.YELLOW)));
        tileTypesList.add(new LinkedList<>(Arrays.asList(Tile.YELLOW, Tile.RED, Tile.GREEN, Tile.BLACK, Tile.BLUE)));

        // create wall lines
        for (int j = 0; j < 5; j++) {
            wallLines.add(new WallLine(tileTypesList.get(j), null, null));
        }
        // set up wall lines connections up
        for (int j = 0; j < 4; j++) {
            wallLines.get(j).setLineUp((WallLine) wallLines.get(j + 1));
        }
        // set up wall lines connections down
        for (int j = 1; j < 5; j++) {
            wallLines.get(j).setLineDown((WallLine) wallLines.get(j - 1));
        }

        // set up pattern lines
        patternLines = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            patternLines.add(new PatternLine(j + 1, wallLines.get(j), floor, usedTiles));
        }

        // set up final points calculation and game finished
        FinalPointsCalculation finalPointsCalculation = new FinalPointsCalculation();
        GameFinished gameFinished = new GameFinished();

        boards.add(new Board(floor, points, patternLines, wallLines, finalPointsCalculation, gameFinished));
        }

        // set up table area
        tableCenter = new TableCenter();
        tileSources = new ArrayList<>();
        tileSources.add(tableCenter);
        tileSources.add(new Factory(tableCenter, bag));
        for(int i = 0; i < playerCount; i++){
            tileSources.add(new Factory(tableCenter, bag));
            tileSources.add(new Factory(tableCenter, bag));
        }
        tableArea = new TableArea(tileSources);

        GameObserver gameObserver = new GameObserver();
        ConsoleGameObserver consoleGameObserver = new ConsoleGameObserver();
        gameObserver.registerObserver(consoleGameObserver);

        // Set up the game with 2 players.
        game = new Game(playerCount, boards, tableArea, gameObserver);
        // Starting player is chosen randomly.
    }

    @Test
    public void testGameEndToEnd() {

        // Simulation of a game with 2 players. The players are not very smart :), that's why their scores are so low.
        while(!game.isGameOver) {
            for(int i = 5; i > 0 ; i--) {
                game.take(game.getCurrentPlayerId(), i, 0, i-1);
            }
            while (!tableCenter.isEmpty()) {
                game.take(game.getCurrentPlayerId(), 0, 0, 0);
            }
        }
        // The game is over, let's check the scores.
        assertEquals(4, boards.get(0).getPoints().getValue());
        assertEquals(8, boards.get(1).getPoints().getValue());

    }

}
