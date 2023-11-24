package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Random;

public class Game implements GameInterface{
    private final TableAreaInterface tableArea;
    private ArrayList<BoardInterface> playerBoards;
    private int currentPlayerId;
    private int playerCount;
    private GameObserverInterface gameObserver;
    boolean isGameOver;


    public Game(int playerCount, ArrayList<BoardInterface> playerBoards, TableAreaInterface tableArea, GameObserverInterface gameObserver){
        this.gameObserver = gameObserver;
        this.playerCount = playerCount;
        this.tableArea = tableArea;
        this.playerBoards = playerBoards;
        this.isGameOver = false;
        Random random = new Random();
        currentPlayerId = random.nextInt(playerCount);
        gameObserver.notifyEveryBody("Game started");
        gameObserver.notifyEveryBody("Player " + currentPlayerId + " starts");
    }
    @Override
    public boolean take(int playerId, int sourceId, int idx, int destinationIdx){
        if(isGameOver) {
            gameObserver.notifyEveryBody("You cant take because the Game is Finished");
            return false;
        }
        if(playerId != currentPlayerId) return false;
        ArrayList<Tile> tiles = tableArea.take(sourceId, idx);
        if(tiles.isEmpty()) return false;

        playerBoards.get(playerId).put(destinationIdx, tiles);
        if(tableArea.isRoundEnd()){
            handleRoundEnd();
            if(isGameOver) return finishGame();
        }
        currentPlayerId = (currentPlayerId + 1) % playerCount;
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
            gameObserver.notifyEveryBody("Game finished");
        }
        else{
            gameObserver.notifyEveryBody("Round finished");
            tableArea.startNewRound();
        }

    }

    boolean finishGame(){
        int maxPoints = 0;
        int winnerId = 0;
        for(int i = 0; i < playerCount; i++){
            int points = playerBoards.get(i).getPoints().getValue();
            gameObserver.notifyEveryBody("Player " + i + " has " + points + " points");
            if(points > maxPoints){
                maxPoints = points;
                winnerId = i;
            }
        }
        gameObserver.notifyEveryBody("Player " + winnerId + " won with " + maxPoints + " points");
        return true;
    }

}
