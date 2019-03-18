package training.chessington.model;

import training.chessington.model.pieces.Result;

public class GameInfo {

    private Result result;
    private PlayerColour nextPlayer;

    public static GameInfo forNewGame() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.result = Result.IN_PROGRESS;
        gameInfo.nextPlayer = PlayerColour.WHITE;
        return gameInfo;
    }

    public boolean isGameOver() {
        return result != Result.IN_PROGRESS;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public PlayerColour getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(PlayerColour nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void switchPlayer() {
        this.nextPlayer = nextPlayer.opponent();
    }
}
