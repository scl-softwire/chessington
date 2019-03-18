package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractPiece {
    public Rook(PlayerColour colour) {
        super(PieceType.ROOK, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        return new ArrayList<Move>(){{
           addAll(longRangeMovesInDirection(board, from, 1, 0));
           addAll(longRangeMovesInDirection(board, from, 0, 1));
           addAll(longRangeMovesInDirection(board, from, -1, 0));
           addAll(longRangeMovesInDirection(board, from, 0, -1));
        }};
    }
}
