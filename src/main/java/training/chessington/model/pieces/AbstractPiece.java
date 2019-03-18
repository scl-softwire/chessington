package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Game;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    protected List<Move> longRangeMovesInDirection(Board board, Coordinates start, int rowDelta, int colDelta) {
        List<Move> allowedMoves = new ArrayList<>();
        for (int i = 1; i < Game.SIZE; i++) {
            Move candidateMove = new Move(start, start.plus(rowDelta * i, colDelta * i));
            if (!board.inBounds(candidateMove.getTo())) {
                break;
            }
            if (isValidDestination(board, candidateMove.getTo())) {
                allowedMoves.add(new Move(start, candidateMove.getTo()));
            }
            if (!board.squareIsEmpty(candidateMove.getTo())) {
                break;
            }
        }
        return allowedMoves;
    }
}
