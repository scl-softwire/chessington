package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Flags;
import training.chessington.model.Game;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class KingTest {

    @Test
    public void kingCanMoveOneSquareInAnyDirection() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates kingCoordinates = new Coordinates(5, 2);
        board.placePiece(kingCoordinates, king);
        Game game = new Game(board, Flags.forNewGame());

        // Act
        List<Move> kingMoves = king.getAllowedMoves(kingCoordinates, game);

        // Assert
        assertThat(kingMoves).containsOnly(
                new Move(kingCoordinates, new Coordinates(4, 1)),
                new Move(kingCoordinates, new Coordinates(4, 2)),
                new Move(kingCoordinates, new Coordinates(4, 3)),
                new Move(kingCoordinates, new Coordinates(5, 3)),
                new Move(kingCoordinates, new Coordinates(5, 1)),
                new Move(kingCoordinates, new Coordinates(6, 1)),
                new Move(kingCoordinates, new Coordinates(6, 2)),
                new Move(kingCoordinates, new Coordinates(6, 3))
        );
    }

    @Test
    public void kingCannotCaptureFriendlyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates kingCoordinates = new Coordinates(5, 2);
        board.placePiece(kingCoordinates, king);
        Game game = new Game(board, Flags.forNewGame());

        // Put a friendly piece in range of the king
        Piece friendlyPiece = new Pawn(PlayerColour.WHITE);
        Coordinates friendlyCoordinates = new Coordinates(4, 3);
        board.placePiece(friendlyCoordinates, friendlyPiece);

        // Act
        List<Move> kingMoves = king.getAllowedMoves(kingCoordinates, game);

        // Assert
        assertThat(kingMoves).doesNotContain(new Move(kingCoordinates, friendlyCoordinates));
    }

    @Test
    public void kingCanCaptureEnemyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates kingCoordinates = new Coordinates(5, 2);
        board.placePiece(kingCoordinates, king);
        Game game = new Game(board, Flags.forNewGame());

        // Put an enemy piece in range of the king
        Piece enemyPiece = new Pawn(PlayerColour.BLACK);
        Coordinates enemyCoordinates = new Coordinates(5, 1);
        board.placePiece(enemyCoordinates, enemyPiece);

        // Act
        List<Move> kingMoves = king.getAllowedMoves(kingCoordinates, game);

        // Assert
        assertThat(kingMoves).contains(new Move(kingCoordinates, enemyCoordinates));
    }
}
