package training.chessington.model.pieces;

import training.chessington.model.Coordinates;
import training.chessington.model.Game;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

public class Knight extends AbstractPiece {
    public Knight(PlayerColour colour) {
        super(PieceType.KNIGHT, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Game game) {
       return createValidMoves(game, from,
                from.plus(1, 2), from.plus(2, 1),
                from.plus(1, -2), from.plus(-2, 1),
                from.plus(-1, 2), from.plus(2, -1),
                from.plus(-1, -2), from.plus(-2, -1)
        );
    }
}
