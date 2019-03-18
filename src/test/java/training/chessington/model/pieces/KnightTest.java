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

public class KnightTest {

    @Test
    public void knightCanMoveInLShape() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates knightCoordinates = new Coordinates(3, 5);
        board.placePiece(knightCoordinates, knight);
        Game game = new Game(board, Flags.forNewGame());

        // Act
        List<Move> knightMoves = knight.getAllowedMoves(knightCoordinates, game);

        // Assert
        assertThat(knightMoves).containsOnly(
                new Move(knightCoordinates, new Coordinates(4, 7)),
                new Move(knightCoordinates, new Coordinates(4, 3)),
                new Move(knightCoordinates, new Coordinates(5, 6)),
                new Move(knightCoordinates, new Coordinates(5, 4)),
                new Move(knightCoordinates, new Coordinates(2, 7)),
                new Move(knightCoordinates, new Coordinates(2, 3)),
                new Move(knightCoordinates, new Coordinates(1, 6)),
                new Move(knightCoordinates, new Coordinates(1, 4))
        );
    }

    @Test
    public void knightCanJumpOverPieces() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates knightCoordinates = new Coordinates(3, 5);
        board.placePiece(knightCoordinates, knight);
        Game game = new Game(board, Flags.forNewGame());

        // Put pieces surrounding the knight
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    board.placePiece(knightCoordinates.plus(i, j), new Pawn(PlayerColour.WHITE));
                }
            }
        }

        // Act
        List<Move> knightMoves = knight.getAllowedMoves(knightCoordinates, game);

        // Assert
        assertThat(knightMoves).containsOnly(
                new Move(knightCoordinates, new Coordinates(4, 7)),
                new Move(knightCoordinates, new Coordinates(4, 3)),
                new Move(knightCoordinates, new Coordinates(5, 6)),
                new Move(knightCoordinates, new Coordinates(5, 4)),
                new Move(knightCoordinates, new Coordinates(2, 7)),
                new Move(knightCoordinates, new Coordinates(2, 3)),
                new Move(knightCoordinates, new Coordinates(1, 6)),
                new Move(knightCoordinates, new Coordinates(1, 4))
        );
    }

    @Test
    public void knightCannotCaptureFriendlyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates knightCoordinates = new Coordinates(3, 5);
        board.placePiece(knightCoordinates, knight);
        Game game = new Game(board, Flags.forNewGame());

        // Put a friendly piece in range of the knight
        Piece friendlyPiece = new Pawn(PlayerColour.WHITE);
        Coordinates friendlyCoordinates = new Coordinates(4, 3);
        board.placePiece(friendlyCoordinates, friendlyPiece);

        // Act
        List<Move> knightMoves = knight.getAllowedMoves(knightCoordinates, game);

        // Assert
        assertThat(knightMoves).doesNotContain(new Move(knightCoordinates, friendlyCoordinates));
    }

    @Test
    public void knightCanCaptureEnemyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates knightCoordinates = new Coordinates(3, 5);
        board.placePiece(knightCoordinates, knight);
        Game game = new Game(board, Flags.forNewGame());

        // Put an enemy piece in range of the knight
        Piece enemyPiece = new Pawn(PlayerColour.BLACK);
        Coordinates enemyCoordinates = new Coordinates(4, 3);
        board.placePiece(enemyCoordinates, enemyPiece);

        // Act
        List<Move> knightMoves = knight.getAllowedMoves(knightCoordinates, game);

        // Assert
        assertThat(knightMoves).contains(new Move(knightCoordinates, enemyCoordinates));
    }
}
