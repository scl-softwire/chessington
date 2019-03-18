package training.chessington.model.pieces;

import training.chessington.model.Coordinates;
import training.chessington.model.Game;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Queen extends AbstractPiece {
    public Queen(PlayerColour colour) {
        super(PieceType.QUEEN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Game game) {
        return new ArrayList<Move>(){{
            addAll(getOrthogonalMoves(game.getBoard(), from));
            addAll(getDiagonalMoves(game.getBoard(), from));
        }};
    }
}
