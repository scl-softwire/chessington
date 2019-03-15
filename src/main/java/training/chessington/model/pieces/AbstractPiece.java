package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.PlayerColour;

public abstract class AbstractPiece implements Piece {

    protected final Piece.PieceType type;
    protected final PlayerColour colour;

    protected AbstractPiece(Piece.PieceType type, PlayerColour colour) {
        this.type = type;
        this.colour = colour;
    }

    @Override
    public Piece.PieceType getType() {
        return type;
    }

    @Override
    public PlayerColour getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return colour.toString() + " " + type.toString();
    }

    protected boolean isValidDestination(Board board, Coordinates coords) {
        return board.squareIsEmpty(coords) || !board.squareContainsPieceOfColour(coords, colour);
    }

    protected boolean containsEnemy(Board board, Coordinates coords) {
        return !board.squareIsEmpty(coords) && !board.squareContainsPieceOfColour(coords, colour);
    }
}
