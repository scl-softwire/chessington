package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> allowedMoves = new ArrayList<>();
        int offset = colour == PlayerColour.WHITE ? -1 : 1;

        // Move forward one square
        Coordinates onceInFront = from.plus(offset, 0);
        if (board.inBounds(onceInFront) && board.squareIsEmpty(onceInFront)) {
            allowedMoves.add(new Move(from, from.plus(offset, 0)));
        }

        // Move forward two squares
        Coordinates twiceInFront = from.plus(2 * offset, 0);
        if (onStartingSquare(from) && board.inBounds(twiceInFront) && board.squareIsEmpty(onceInFront) && board.squareIsEmpty(twiceInFront)) {
            allowedMoves.add(new Move(from, from.plus(2 * offset, 0)));
        }

        return allowedMoves;
    }

    private boolean onStartingSquare(Coordinates currentSquare) {
        int startingRow = colour == PlayerColour.WHITE ? 6 : 1;
        return currentSquare.getRow() == startingRow;
    }
}
