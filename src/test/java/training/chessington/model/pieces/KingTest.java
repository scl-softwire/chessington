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
        Game game = new Game(board, Flags.forNewGame().withNoSpecialMoves());

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

    @Test
    public void whiteCanCastleKingside() {
        // Arrange
        Board board = Board.forNewGame();
        Coordinates kingCoordinates = new Coordinates(7, 4);
        board.obliterate(new Coordinates(7, 5));
        board.obliterate(new Coordinates(7, 6));
        Game game = new Game(board, Flags.forNewGame());

        // Act
        List<Move> moves = board.get(kingCoordinates).getAllowedMoves(kingCoordinates, game);

        // Assert
        assertThat(moves).contains(new Move(kingCoordinates, kingCoordinates.plus(0, 2)));
    }

    @Test
    public void whiteCanCastleQueenside() {
        // Arrange
        Board board = Board.forNewGame();
        Coordinates kingCoordinates = new Coordinates(7, 4);
        board.obliterate(new Coordinates(7, 1));
        board.obliterate(new Coordinates(7, 2));
        board.obliterate(new Coordinates(7, 3));
        Game game = new Game(board, Flags.forNewGame());

        // Act
        List<Move> moves = board.get(kingCoordinates).getAllowedMoves(kingCoordinates, game);

        // Assert
        assertThat(moves).contains(new Move(kingCoordinates, kingCoordinates.plus(0, -2)));
    }

    @Test
    public void blackCanCastleKingside() {
        // Arrange
        Board board = Board.forNewGame();
        Coordinates kingCoordinates = new Coordinates(0, 4);
        board.obliterate(new Coordinates(0, 5));
        board.obliterate(new Coordinates(0, 6));
        Game game = new Game(board, Flags.forNewGame());

        // Act
        List<Move> moves = board.get(kingCoordinates).getAllowedMoves(kingCoordinates, game);

        // Assert
        assertThat(moves).contains(new Move(kingCoordinates, kingCoordinates.plus(0, 2)));
    }

    @Test
    public void blackCanCastleQueenside() {
        // Arrange
        Board board = Board.forNewGame();
        Coordinates kingCoordinates = new Coordinates(0, 4);
        board.obliterate(new Coordinates(0, 1));
        board.obliterate(new Coordinates(0, 2));
        board.obliterate(new Coordinates(0, 3));
        Game game = new Game(board, Flags.forNewGame());

        // Act
        List<Move> moves = board.get(kingCoordinates).getAllowedMoves(kingCoordinates, game);

        // Assert
        assertThat(moves).contains(new Move(kingCoordinates, kingCoordinates.plus(0, -2)));
    }

    @Test
    public void cannotCastleOutOfCheck() {
        // Arrange
        Board board = Board.forNewGame();
        Coordinates kingCoordinates = new Coordinates(7, 4);
        board.obliterate(new Coordinates(7, 5));
        board.obliterate(new Coordinates(7, 6));
        Game game = new Game(board, Flags.forNewGame());

        // Put the King in check
        board.placePiece(new Coordinates(6, 4), new Rook(PlayerColour.BLACK));

        // Act
        List<Move> moves = board.get(kingCoordinates).getAllowedMoves(kingCoordinates, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(kingCoordinates, kingCoordinates.plus(0, 2)));
    }

    @Test
    public void cannotCastleThroughCheck() {
        // Arrange
        Board board = Board.forNewGame();
        Coordinates kingCoordinates = new Coordinates(7, 4);
        board.obliterate(new Coordinates(7, 5));
        board.obliterate(new Coordinates(7, 6));
        Game game = new Game(board, Flags.forNewGame());

        // Put a piece attacking an intermediate square
        board.obliterate(new Coordinates(6, 5));
        board.placePiece(new Coordinates(3, 5), new Rook(PlayerColour.BLACK));

        // Act
        List<Move> moves = board.get(kingCoordinates).getAllowedMoves(kingCoordinates, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(kingCoordinates, kingCoordinates.plus(0, 2)));
    }

    @Test
    public void cannotCastleThroughObstructingPieces() {
        // Arrange
        Board board = Board.forNewGame();
        Coordinates kingCoordinates = new Coordinates(7, 4);
        board.obliterate(new Coordinates(7, 6));
        Game game = new Game(board, Flags.forNewGame());

        // Act
        List<Move> moves = board.get(kingCoordinates).getAllowedMoves(kingCoordinates, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(kingCoordinates, kingCoordinates.plus(0, 2)));
    }
}
