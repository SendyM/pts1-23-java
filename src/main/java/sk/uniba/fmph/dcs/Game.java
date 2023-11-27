package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Random;

public class Game implements GameInterface{
    private final TableAreaInterface tableArea;
    private ArrayList<BoardInterface> playerBoards;
    private int currentPlayerId;
    private int startingPlayerId;
    private int playerCount;
    private ObserverInterface gameObserver;
    private int seed;
    public boolean isGameOver;

    public Game(int playerCount, ArrayList<BoardInterface> playerBoards, TableAreaInterface tableArea, ObserverInterface gameObserver){
        this.gameObserver = gameObserver;
        this.playerCount = playerCount;
        this.tableArea = tableArea;
        this.playerBoards = playerBoards;
        this.isGameOver = false;
        this.seed = 0;
        this.tableArea.startNewRound();
        Random random = new Random(seed);
        startingPlayerId = random.nextInt(playerCount);
        currentPlayerId = startingPlayerId;
        gameObserver.notifyEverybody("Game started");
        gameObserver.notifyEverybody("Player " + currentPlayerId + " starts");
    }
    @Override
    public boolean take(int playerId, int sourceId, int idx, int destinationIdx){
        if(isGameOver) {
            gameObserver.notifyEverybody("You cant take because the Game is Finished");
            return false;
        }
        if(playerId != currentPlayerId) return false;
        ArrayList<Tile> tiles = tableArea.take(sourceId, idx);
        if(tiles.isEmpty()) return false;
        if(tiles.contains(Tile.STARTING_PLAYER)) startingPlayerId = currentPlayerId;
        ArrayList<Tile> tilesToPrint = new ArrayList<>(tiles);
        playerBoards.get(playerId).put(destinationIdx, tiles);
        gameObserver.notifyEverybody("Player " + playerId + " took " + tilesToPrint +
                                    " from " + sourceId + " placed to: "+ destinationIdx + "\nHis pattern lines after placing:\n" + playerBoards.get(playerId).state().substring(0, 35));
        if(tableArea.isRoundEnd()){
            handleRoundEnd();
            currentPlayerId = startingPlayerId;
            if(isGameOver) return finishGame();
            gameObserver.notifyEverybody("Player " + currentPlayerId + " starts this round");
        }else{
            currentPlayerId = (currentPlayerId + 1) % playerCount;
            gameObserver.notifyEverybody("Player " + currentPlayerId + " plays");
        }
        return true;
    }

    void handleRoundEnd(){
        FinishRoundResult result = FinishRoundResult.NORMAL;
        for(BoardInterface board : playerBoards){
            if(board.finishRound() == FinishRoundResult.GAME_FINISHED){
                result = FinishRoundResult.GAME_FINISHED;
            }
        }
        if(result == FinishRoundResult.GAME_FINISHED){
            for(BoardInterface board : playerBoards){
                board.endGame();
            }
            isGameOver = true;
            gameObserver.notifyEverybody("Game finished");
        }
        else{
            gameObserver.notifyEverybody("Round finished");
            tableArea.startNewRound();
        }

    }

    boolean finishGame(){
        int maxPoints = 0;
        int winnerId = 0;
        for(int i = 0; i < playerCount; i++){
            int points = playerBoards.get(i).getPoints().getValue();
            gameObserver.notifyEverybody("Player " + i + " has " + points + " points");
            if(points > maxPoints){
                maxPoints = points;
                winnerId = i;
            }
        }
        gameObserver.notifyEverybody("Player " + winnerId + " won with " + maxPoints + " points");
        return true;
    }
    // for testing purposes.
    public int getCurrentPlayerId(){
        return currentPlayerId;
    }

}
