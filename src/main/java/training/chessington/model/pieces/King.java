package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class King extends AbstractPiece {
    public King(PlayerColour colour) {
        super(PieceType.KING, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Coordinates> potentialDestinations = Arrays.asList(
                from.plus(-1, -1), from.plus(-1, 0), from.plus(-1, 1),
                from.plus(0, -1), from.plus(0, 1),
                from.plus(1, -1), from.plus(1, 0), from.plus(1, 1)
        );

        return potentialDestinations.stream()
                .filter(board::inBounds)
                .filter(to -> !board.squareContainsPieceOfColour(to, colour))
                .map(to -> new Move(from, to))
                .collect(Collectors.toList());
    }
}
