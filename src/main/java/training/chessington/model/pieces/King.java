package training.chessington.model.pieces;

import training.chessington.model.Coordinates;
import training.chessington.model.Game;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

public class King extends AbstractPiece {
    public King(PlayerColour colour) {
        super(PieceType.KING, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Game game) {
        List<Move> moves = createValidMoves(game, from,
                from.plus(-1, -1), from.plus(-1, 0), from.plus(-1, 1),
                from.plus(0, -1), from.plus(0, 1),
                from.plus(1, -1), from.plus(1, 0), from.plus(1, 1)
        );

        if (canCastleKingside(game)) {
            moves.add(new Move(from, from.plus(0, 2)));
        }

        if (canCastleQueenside(game)) {
            moves.add(new Move(from, from.plus(0, -2)));
        }

        return moves;
    }

    private boolean canCastleKingside(Game game) {
        int homeRow = colour == PlayerColour.WHITE ? 7 : 0;
        return game.getFlags().isKingsideCastle(colour) &&
                noSquaresThreatened(game, homeRow, 4, 6) &&
                noSquaresOccupied(game, homeRow, 5, 6);
    }

    private boolean canCastleQueenside(Game game) {
        int homeRow = colour == PlayerColour.WHITE ? 7 : 0;
        return game.getFlags().isQueensideCastle(colour) &&
                noSquaresThreatened(game, homeRow, 1, 4) &&
                noSquaresOccupied(game, homeRow, 1, 3);
    }

    private boolean noSquaresThreatened(Game game, int homeRow, int startCol, int endCol) {
        for (int col = startCol; col <= endCol; col++) {
            Coordinates square = new Coordinates(homeRow, col);
            if (game.squareIsThreatenedByColour(square, colour.opponent())) {
                return false;
            }
        }
        return true;
    }

    private boolean noSquaresOccupied(Game game, int homeRow, int startCol, int endCol) {
        for (int col = startCol; col <= endCol; col++) {
            if (game.pieceAt(homeRow, col) != null) {
                return false;
            }
        }
        return true;
    }
}
