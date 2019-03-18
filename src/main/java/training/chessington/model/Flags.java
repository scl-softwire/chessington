package training.chessington.model;

import training.chessington.model.pieces.Result;

import java.util.Optional;

public class Flags {

    private boolean whiteKingsideCastle;
    private boolean whiteQueensideCastle;
    private boolean blackKingsideCastle;
    private boolean blackQueensideCastle;
    private Optional<Coordinates> enPassantSquare;
    private boolean gameOver;
    private Result result;
    private PlayerColour nextPlayer;

    public static Flags forNewGame() {
        Flags flags = new Flags();
        flags.whiteKingsideCastle = true;
        flags.whiteQueensideCastle = true;
        flags.blackKingsideCastle = true;
        flags.blackQueensideCastle = true;
        flags.enPassantSquare = Optional.empty();
        flags.gameOver = false;
        flags.result = Result.IN_PROGRESS;
        flags.nextPlayer = PlayerColour.WHITE;
        return flags;
    }

    public Flags withNoSpecialMoves() {
        Flags flags = createCopy();
        flags.whiteKingsideCastle = flags.whiteQueensideCastle = false;
        flags.blackKingsideCastle = flags.blackQueensideCastle = false;
        flags.enPassantSquare = Optional.empty();
        return flags;
    }

    public Flags createCopy() {
        Flags flags = new Flags();
        flags.whiteKingsideCastle = whiteKingsideCastle;
        flags.whiteQueensideCastle = whiteQueensideCastle;
        flags.blackKingsideCastle = blackKingsideCastle;
        flags.blackQueensideCastle = blackQueensideCastle;
        flags.enPassantSquare = enPassantSquare;
        flags.gameOver = gameOver;
        flags.result = result;
        flags.nextPlayer = nextPlayer;
        return flags;
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
        nextPlayer = nextPlayer.opponent();
    }

    public boolean isWhiteKingsideCastle() {
        return whiteKingsideCastle;
    }

    public void setWhiteKingsideCastle(boolean whiteKingsideCastle) {
        this.whiteKingsideCastle = whiteKingsideCastle;
    }

    public boolean isWhiteQueensideCastle() {
        return whiteQueensideCastle;
    }

    public void setWhiteQueensideCastle(boolean whiteQueensideCastle) {
        this.whiteQueensideCastle = whiteQueensideCastle;
    }

    public boolean isBlackKingsideCastle() {
        return blackKingsideCastle;
    }

    public void setBlackKingsideCastle(boolean blackKingsideCastle) {
        this.blackKingsideCastle = blackKingsideCastle;
    }

    public boolean isBlackQueensideCastle() {
        return blackQueensideCastle;
    }

    public void setBlackQueensideCastle(boolean blackQueensideCastle) {
        this.blackQueensideCastle = blackQueensideCastle;
    }

    public void forbidCastling(PlayerColour colour) {
        switch (colour) {
            case WHITE: whiteQueensideCastle = whiteKingsideCastle = false; break;
            case BLACK: blackQueensideCastle = blackKingsideCastle = false; break;
        }
    }

    public boolean isKingsideCastle(PlayerColour colour) {
        return colour == PlayerColour.WHITE ? whiteKingsideCastle : blackKingsideCastle;
    }

    public boolean isQueensideCastle(PlayerColour colour) {
        return colour == PlayerColour.WHITE ? whiteQueensideCastle : blackQueensideCastle;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
