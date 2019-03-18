package training.chessington.model;

import java.util.Optional;

public class Flags {

    private boolean whiteCastle;
    private boolean blackCastle;
    private Optional<Coordinates> enPassantSquare;
    private boolean gameOver;
    private PlayerColour nextPlayer;

    public static Flags forNewGame() {
        Flags flags = new Flags();
        flags.whiteCastle = true;
        flags.blackCastle = true;
        flags.enPassantSquare = Optional.empty();
        flags.gameOver = false;
        flags.nextPlayer = PlayerColour.WHITE;
        return flags;
    }

    public boolean isWhiteCastle() {
        return whiteCastle;
    }

    public boolean isBlackCastle() {
        return blackCastle;
    }

    public Optional<Coordinates> getEnPassantSquare() {
        return enPassantSquare;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public PlayerColour getNextPlayer() {
        return nextPlayer;
    }

    public void setWhiteCastle(boolean whiteCastle) {
        this.whiteCastle = whiteCastle;
    }

    public void setBlackCastle(boolean blackCastle) {
        this.blackCastle = blackCastle;
    }

    public void setEnPassantSquare(Optional<Coordinates> enPassantSquare) {
        this.enPassantSquare = enPassantSquare;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setNextPlayer(PlayerColour nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void switchPlayer() {
        nextPlayer = nextPlayer == PlayerColour.WHITE ? PlayerColour.BLACK : PlayerColour.WHITE;
    }
}
