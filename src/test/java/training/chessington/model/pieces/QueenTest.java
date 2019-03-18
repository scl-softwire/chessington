package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class QueenTest {

    @Test
    public void queenCanMoveInAllDirections() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates queenCoordinates = new Coordinates(4, 4);
        board.placePiece(queenCoordinates, queen);

        // Act
        List<Move> moves = queen.getAllowedMoves(queenCoordinates, board);

        // Assert
        assertThat(moves).containsOnly(
                new Move(queenCoordinates, new Coordinates(0, 0)),
                new Move(queenCoordinates, new Coordinates(1, 1)),
                new Move(queenCoordinates, new Coordinates(2, 2)),
                new Move(queenCoordinates, new Coordinates(3, 3)),
                new Move(queenCoordinates, new Coordinates(5, 5)),
                new Move(queenCoordinates, new Coordinates(6, 6)),
                new Move(queenCoordinates, new Coordinates(7, 7)),
                new Move(queenCoordinates, new Coordinates(1, 7)),
                new Move(queenCoordinates, new Coordinates(2, 6)),
                new Move(queenCoordinates, new Coordinates(3, 5)),
                new Move(queenCoordinates, new Coordinates(5, 3)),
                new Move(queenCoordinates, new Coordinates(6, 2)),
                new Move(queenCoordinates, new Coordinates(7, 1)),
                new Move(queenCoordinates, new Coordinates(0, 4)),
                new Move(queenCoordinates, new Coordinates(1, 4)),
                new Move(queenCoordinates, new Coordinates(2, 4)),
                new Move(queenCoordinates, new Coordinates(3, 4)),
                new Move(queenCoordinates, new Coordinates(5, 4)),
                new Move(queenCoordinates, new Coordinates(6, 4)),
                new Move(queenCoordinates, new Coordinates(7, 4)),
                new Move(queenCoordinates, new Coordinates(4, 0)),
                new Move(queenCoordinates, new Coordinates(4, 1)),
                new Move(queenCoordinates, new Coordinates(4, 2)),
                new Move(queenCoordinates, new Coordinates(4, 3)),
                new Move(queenCoordinates, new Coordinates(4, 5)),
                new Move(queenCoordinates, new Coordinates(4, 6)),
                new Move(queenCoordinates, new Coordinates(4, 7))
        );
    }

    @Test
    public void queenCanCaptureEnemyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates queenCoordinates = new Coordinates(4, 4);
        board.placePiece(queenCoordinates, queen);

        // Place an enemy in range of the queen
        Piece enemyPiece = new Pawn(PlayerColour.BLACK);
        Coordinates enemyCoordinates = new Coordinates(6, 6);
        board.placePiece(enemyCoordinates, enemyPiece);

        // Act
        List<Move> moves = queen.getAllowedMoves(queenCoordinates, board);

        // Assert
        assertThat(moves).contains(new Move(queenCoordinates, enemyCoordinates));
    }

    @Test
    public void queenIsBlockedByEnemyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates queenCoordinates = new Coordinates(4, 4);
        board.placePiece(queenCoordinates, queen);

        // Place an enemy in range of the queen
        Piece enemyPiece = new Pawn(PlayerColour.BLACK);
        Coordinates enemyCoordinates = new Coordinates(6, 2);
        board.placePiece(enemyCoordinates, enemyPiece);

        // Act
        List<Move> moves = queen.getAllowedMoves(queenCoordinates, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(queenCoordinates, new Coordinates(7, 1)));
    }

    @Test
    public void queenCannotCaptureFriendlyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates queenCoordinates = new Coordinates(4, 4);
        board.placePiece(queenCoordinates, queen);

        // Place an ally in range of the queen
        Piece friendlyPiece = new Pawn(PlayerColour.WHITE);
        Coordinates friendlyCoordinates = new Coordinates(2, 4);
        board.placePiece(friendlyCoordinates, friendlyPiece);

        // Act
        List<Move> moves = queen.getAllowedMoves(queenCoordinates, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(queenCoordinates, friendlyCoordinates));
    }

    @Test
    public void queenIsBlockedByFriendlyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates queenCoordinates = new Coordinates(4, 4);
        board.placePiece(queenCoordinates, queen);

        // Place an ally in range of the queen
        Piece friendlyPiece = new Pawn(PlayerColour.WHITE);
        Coordinates friendlyCoordinates = new Coordinates(4, 6);
        board.placePiece(friendlyCoordinates, friendlyPiece);

        // Act
        List<Move> moves = queen.getAllowedMoves(queenCoordinates, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(queenCoordinates, new Coordinates(4, 7)));
    }

}
