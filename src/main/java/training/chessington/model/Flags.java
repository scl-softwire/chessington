package training.chessington.model;

import java.util.Optional;

public class Flags {

    private boolean whiteKingsideCastle;
    private boolean whiteQueensideCastle;
    private boolean blackKingsideCastle;
    private boolean blackQueensideCastle;
    private Optional<Coordinates> enPassantSquare;
    private Optional<Coordinates> pawnPromotionSquare;

    public static Flags forNewGame() {
        Flags flags = new Flags();
        flags.whiteKingsideCastle = true;
        flags.whiteQueensideCastle = true;
        flags.blackKingsideCastle = true;
        flags.blackQueensideCastle = true;
        flags.enPassantSquare = Optional.empty();
        flags.pawnPromotionSquare = Optional.empty();
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
        return flags;
    }

    public Optional<Coordinates> getEnPassantSquare() {
        return enPassantSquare;
    }

    public void setEnPassantSquare(Optional<Coordinates> enPassantSquare) {
        this.enPassantSquare = enPassantSquare;
    }

    public void setWhiteKingsideCastle(boolean whiteKingsideCastle) {
        this.whiteKingsideCastle = whiteKingsideCastle;
    }

    public void setWhiteQueensideCastle(boolean whiteQueensideCastle) {
        this.whiteQueensideCastle = whiteQueensideCastle;
    }

    public void setBlackKingsideCastle(boolean blackKingsideCastle) {
        this.blackKingsideCastle = blackKingsideCastle;
    }

    public void setBlackQueensideCastle(boolean blackQueensideCastle) {
        this.blackQueensideCastle = blackQueensideCastle;
    }

    public void forbidCastling(PlayerColour colour) {
        switch (colour) {
            case WHITE:
                whiteQueensideCastle = whiteKingsideCastle = false;
                break;
            case BLACK:
                blackQueensideCastle = blackKingsideCastle = false;
                break;
        }
    }

    public boolean isKingsideCastle(PlayerColour colour) {
        return colour == PlayerColour.WHITE ? whiteKingsideCastle : blackKingsideCastle;
    }

    public boolean isQueensideCastle(PlayerColour colour) {
        return colour == PlayerColour.WHITE ? whiteQueensideCastle : blackQueensideCastle;
    }

    public Optional<Coordinates> getPawnPromotionSquare() {
        return pawnPromotionSquare;
    }

    public void setPawnPromotionSquare(Optional<Coordinates> pawnPromotionSquare) {
        this.pawnPromotionSquare = pawnPromotionSquare;
    }
}