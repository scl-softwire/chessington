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

public class RookTest {

    @Test
    public void rookCanMoveHorizontallyAndVertically() {
        // Arrange
        Board board = Board.empty();
        Piece rook  = new Rook(PlayerColour.WHITE);
        Coordinates rookCoordinates = new Coordinates(2, 5);
        board.placePiece(rookCoordinates, rook);
        Game game = new Game(board, Flags.forNewGame());

        // Act
        List<Move> moves = rook.getAllowedMoves(rookCoordinates, game);

        // Assert
        assertThat(moves).containsOnly(
                new Move(rookCoordinates, new Coordinates(2, 0)),
                new Move(rookCoordinates, new Coordinates(2, 1)),
                new Move(rookCoordinates, new Coordinates(2, 2)),
                new Move(rookCoordinates, new Coordinates(2, 3)),
                new Move(rookCoordinates, new Coordinates(2, 4)),
                new Move(rookCoordinates, new Coordinates(2, 6)),
                new Move(rookCoordinates, new Coordinates(2, 7)),
                new Move(rookCoordinates, new Coordinates(0, 5)),
                new Move(rookCoordinates, new Coordinates(1, 5)),
                new Move(rookCoordinates, new Coordinates(3, 5)),
                new Move(rookCoordinates, new Coordinates(4, 5)),
                new Move(rookCoordinates, new Coordinates(5, 5)),
                new Move(rookCoordinates, new Coordinates(6, 5)),
                new Move(rookCoordinates, new Coordinates(7, 5))
        );
    }

    @Test
    public void rookCanCaptureEnemyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates rookCoordinates = new Coordinates(2, 5);
        board.placePiece(rookCoordinates, rook);
        Game game = new Game(board, Flags.forNewGame());

        // Place an enemy in range of the rook
        Piece enemyPiece = new Pawn(PlayerColour.BLACK);
        Coordinates enemyCoordinates = new Coordinates(6, 5);
        board.placePiece(enemyCoordinates, enemyPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(rookCoordinates, game);

        // Assert
        assertThat(moves).contains(new Move(rookCoordinates, enemyCoordinates));
    }

    @Test
    public void rookIsBlockedByEnemyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates rookCoordinates = new Coordinates(2, 5);
        board.placePiece(rookCoordinates, rook);
        Game game = new Game(board, Flags.forNewGame());

        // Place an enemy in range of the rook
        Piece enemyPiece = new Pawn(PlayerColour.BLACK);
        Coordinates enemyCoordinates = new Coordinates(1, 5);
        board.placePiece(enemyCoordinates, enemyPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(rookCoordinates, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(rookCoordinates, new Coordinates(0, 5)));
    }

    @Test
    public void rookCannotCaptureFriendlyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates rookCoordinates = new Coordinates(2, 5);
        board.placePiece(rookCoordinates, rook);
        Game game = new Game(board, Flags.forNewGame());

        // Place an ally in range of the rook
        Piece friendlyPiece = new Pawn(PlayerColour.WHITE);
        Coordinates friendlyCoordinates = new Coordinates(2, 7);
        board.placePiece(friendlyCoordinates, friendlyPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(rookCoordinates, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(rookCoordinates, friendlyCoordinates));
    }

    @Test
    public void rookIsBlockedByFriendlyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates rookCoordinates = new Coordinates(2, 5);
        board.placePiece(rookCoordinates, rook);
        Game game = new Game(board, Flags.forNewGame());

        // Place an ally in range of the rook
        Piece friendlyPiece = new Pawn(PlayerColour.WHITE);
        Coordinates friendlyCoordinates = new Coordinates(2, 2);
        board.placePiece(friendlyCoordinates, friendlyPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(rookCoordinates, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(rookCoordinates, new Coordinates(2, 1)));
    }


}
