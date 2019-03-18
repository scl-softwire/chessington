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

public class BishopTest {

    @Test
    public void bishopCanMoveDiagonally() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates bishopCoordinates = new Coordinates(3, 5);
        board.placePiece(bishopCoordinates, bishop);
        Game game = new Game(board, Flags.forNewGame());

        // Act
        List<Move> moves = bishop.getAllowedMoves(bishopCoordinates, game);

        // Assert
        assertThat(moves).containsOnly(
                new Move(bishopCoordinates, new Coordinates(0, 2)),
                new Move(bishopCoordinates, new Coordinates(1, 3)),
                new Move(bishopCoordinates, new Coordinates(2, 4)),
                new Move(bishopCoordinates, new Coordinates(4, 6)),
                new Move(bishopCoordinates, new Coordinates(5, 7)),
                new Move(bishopCoordinates, new Coordinates(1, 7)),
                new Move(bishopCoordinates, new Coordinates(2, 6)),
                new Move(bishopCoordinates, new Coordinates(4, 4)),
                new Move(bishopCoordinates, new Coordinates(5, 3)),
                new Move(bishopCoordinates, new Coordinates(6, 2)),
                new Move(bishopCoordinates, new Coordinates(7, 1))
        );
    }

    @Test
    public void bishopCanCaptureEnemyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates bishopCoordinates = new Coordinates(3, 5);
        board.placePiece(bishopCoordinates, bishop);
        Game game = new Game(board, Flags.forNewGame());

        // Place an enemy in range of the bishop
        Piece enemyPiece = new Pawn(PlayerColour.BLACK);
        Coordinates enemyCoordinates = new Coordinates(5, 3);
        board.placePiece(enemyCoordinates, enemyPiece);

        // Act
        List<Move> moves = bishop.getAllowedMoves(bishopCoordinates, game);

        // Assert
        assertThat(moves).contains(new Move(bishopCoordinates, enemyCoordinates));
    }

    @Test
    public void bishopIsBlockedByEnemyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates bishopCoordinates = new Coordinates(3, 5);
        board.placePiece(bishopCoordinates, bishop);
        Game game = new Game(board, Flags.forNewGame());

        // Place an enemy in range of the bishop
        Piece enemyPiece = new Pawn(PlayerColour.BLACK);
        Coordinates enemyCoordinates = new Coordinates(2, 4);
        board.placePiece(enemyCoordinates, enemyPiece);

        // Act
        List<Move> moves = bishop.getAllowedMoves(bishopCoordinates, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(bishopCoordinates, new Coordinates(1, 3)));
    }

    @Test
    public void bishopCannotCaptureFriendlyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates bishopCoordinates = new Coordinates(3, 5);
        board.placePiece(bishopCoordinates, bishop);
        Game game = new Game(board, Flags.forNewGame());

        // Place an ally in range of the bishop
        Piece friendlyPiece = new Pawn(PlayerColour.WHITE);
        Coordinates friendlyCoordinates = new Coordinates(5, 7);
        board.placePiece(friendlyCoordinates, friendlyPiece);

        // Act
        List<Move> moves = bishop.getAllowedMoves(bishopCoordinates, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(bishopCoordinates, friendlyCoordinates));
    }

    @Test
    public void bishopIsBlockedByFriendlyPieces() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates bishopCoordinates = new Coordinates(3, 5);
        board.placePiece(bishopCoordinates, bishop);
        Game game = new Game(board, Flags.forNewGame());

        // Place an ally in range of the bishop
        Piece friendlyPiece = new Pawn(PlayerColour.WHITE);
        Coordinates friendlyCoordinates = new Coordinates(4, 6);
        board.placePiece(friendlyCoordinates, friendlyPiece);

        // Act
        List<Move> moves = bishop.getAllowedMoves(bishopCoordinates, game);

        // Assert
        assertThat(moves).doesNotContain(new Move(bishopCoordinates, new Coordinates(5, 7)));
    }

}
