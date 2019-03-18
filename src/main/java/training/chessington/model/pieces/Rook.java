package training.chessington.model.pieces;

import training.chessington.model.Coordinates;
import training.chessington.model.Game;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

public class Rook extends AbstractPiece {
    public Rook(PlayerColour colour) {
        super(PieceType.ROOK, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Game game) {
        return getOrthogonalMoves(game.getBoard(), from);
    }
}
