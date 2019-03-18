package training.chessington.model.pieces;

import training.chessington.model.Coordinates;
import training.chessington.model.Game;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Game game) {
        List<Move> allowedMoves = new ArrayList<>();
        int offset = colour == PlayerColour.WHITE ? -1 : 1;

        // Move forward one square
        Coordinates onceInFront = from.plus(offset, 0);
        if (game.getBoard().inBounds(onceInFront) && game.getBoard().squareIsEmpty(onceInFront)) {
            allowedMoves.add(new Move(from, from.plus(offset, 0)));
        }

        // Move forward two squares
        Coordinates twiceInFront = from.plus(2 * offset, 0);
        if (onStartingSquare(from) && game.getBoard().inBounds(twiceInFront) && game.getBoard().squareIsEmpty(onceInFront) && game.getBoard().squareIsEmpty(twiceInFront)) {
            allowedMoves.add(new Move(from, from.plus(2 * offset, 0)));
        }

        // Captures
        Stream.of(new Move(from, from.plus(offset, 1)), new Move(from, from.plus(offset, -1)))
                .filter(move -> isValidCapture(game, move))
                .forEach(allowedMoves::add);

        return allowedMoves;
    }

    private boolean onStartingSquare(Coordinates currentSquare) {
        int startingRow = colour == PlayerColour.WHITE ? Game.SIZE - 2 : 1;
        return currentSquare.getRow() == startingRow;
    }

    private boolean isValidCapture(Game game, Move move) {
        if (!game.getBoard().inBounds(move.getTo())) {
            return false;
        }
        return containsEnemy(game.getBoard(), move.getTo()) || game.getFlags().getEnPassantSquare().map(move.getTo()::equals).orElse(false);
    }
}
