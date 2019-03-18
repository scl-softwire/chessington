package training.chessington.model;

import org.junit.Test;
import training.chessington.model.pieces.Bishop;
import training.chessington.model.pieces.King;
import training.chessington.model.pieces.Pawn;
import training.chessington.model.pieces.Piece;
import training.chessington.model.pieces.Rook;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class GameTest {

    @Test
    public void whiteEnPassantCaptureRemovesPiece() throws InvalidMoveException {
        // Arrange
        Board board = Board.empty();

        Piece whitePawn = new Pawn(PlayerColour.WHITE);
        Coordinates whiteCoordinates = new Coordinates(3, 4);
        board.placePiece(whiteCoordinates, whitePawn);

        Piece blackPawn = new Pawn(PlayerColour.BLACK);
        Coordinates blackCoordinates = new Coordinates(1, 3);
        board.placePiece(blackCoordinates, blackPawn);

        Game game = new Game(board, Flags.forNewGame());
        game.getGameInfo().setNextPlayer(PlayerColour.BLACK);
        game.makeMove(new Move(blackCoordinates, blackCoordinates.plus(2, 0)));

        // Act
        Coordinates captureSquare = blackCoordinates.plus(1, 0);
        game.makeMove(new Move(whiteCoordinates, captureSquare));

        // Assert
        assertThat(game.getBoard().get(blackCoordinates)).isNull();
    }

    @Test
    public void blackEnPassantCaptureRemovesPiece() throws InvalidMoveException {
        // Arrange
        Board board = Board.empty();

        Piece whitePawn = new Pawn(PlayerColour.WHITE);
        Coordinates whiteCoordinates = new Coordinates(6, 4);
        board.placePiece(whiteCoordinates, whitePawn);

        Piece blackPawn = new Pawn(PlayerColour.BLACK);
        Coordinates blackCoordinates = new Coordinates(4, 3);
        board.placePiece(blackCoordinates, blackPawn);

        Game game = new Game(board, Flags.forNewGame());
        game.makeMove(new Move(whiteCoordinates, whiteCoordinates.plus(-2, 0)));

        // Act
        Coordinates captureSquare = whiteCoordinates.plus(-1, 0);
        game.makeMove(new Move(blackCoordinates, captureSquare));

        // Assert
        assertThat(game.getBoard().get(whiteCoordinates)).isNull();
    }

    @Test
    public void whiteKingsideCastleMovesWhiteRook() throws InvalidMoveException {
        // Arrange
        Board board = Board.forNewGame();
        board.obliterate(new Coordinates(7, 5));
        board.obliterate(new Coordinates(7, 6));
        Game game = new Game(board, Flags.forNewGame());

        // Act
        game.makeMove(new Move(new Coordinates(7, 4), new Coordinates(7, 6)));

        // Assert
        assertThat(game.pieceAt(7, 5)).isPiece(Piece.PieceType.ROOK).isColour(PlayerColour.WHITE);
    }

    @Test
    public void whiteQueensideCastleMovesWhiteRook() throws InvalidMoveException {
        // Arrange
        Board board = Board.forNewGame();
        board.obliterate(new Coordinates(7, 1));
        board.obliterate(new Coordinates(7, 2));
        board.obliterate(new Coordinates(7, 3));
        Game game = new Game(board, Flags.forNewGame());

        // Act
        game.makeMove(new Move(new Coordinates(7, 4), new Coordinates(7, 2)));

        // Assert
        assertThat(game.pieceAt(7, 3)).isPiece(Piece.PieceType.ROOK).isColour(PlayerColour.WHITE);
    }

    @Test
    public void blackKingsideCastleMovesBlackRook() throws InvalidMoveException {
        // Arrange
        Board board = Board.forNewGame();
        board.obliterate(new Coordinates(0, 5));
        board.obliterate(new Coordinates(0, 6));
        Game game = new Game(board, Flags.forNewGame());
        game.getGameInfo().setNextPlayer(PlayerColour.BLACK);

        // Act
        game.makeMove(new Move(new Coordinates(0, 4), new Coordinates(0, 6)));

        // Assert
        assertThat(game.pieceAt(0, 5)).isPiece(Piece.PieceType.ROOK).isColour(PlayerColour.BLACK);
    }

    @Test
    public void blackQueensideCastleMovesBlackRook() throws InvalidMoveException {
        // Arrange
        Board board = Board.forNewGame();
        board.obliterate(new Coordinates(0, 1));
        board.obliterate(new Coordinates(0, 2));
        board.obliterate(new Coordinates(0, 3));
        Game game = new Game(board, Flags.forNewGame());
        game.getGameInfo().setNextPlayer(PlayerColour.BLACK);

        // Act
        game.makeMove(new Move(new Coordinates(0, 4), new Coordinates(0, 2)));

        // Assert
        assertThat(game.pieceAt(0, 3)).isPiece(Piece.PieceType.ROOK).isColour(PlayerColour.BLACK);
    }

    @Test
    public void kingCannotMoveIntoCheck() {
        // Arrange
        Board board = Board.empty();
        Coordinates kingCoordinates = new Coordinates(4, 4);
        Piece king = new King(PlayerColour.WHITE);
        board.placePiece(kingCoordinates, king);

        Coordinates enemyRookCoordinates = new Coordinates(0, 5);
        Piece enemyRook = new Rook(PlayerColour.BLACK);
        board.placePiece(enemyRookCoordinates, enemyRook);

        Game game = new Game(board, Flags.forNewGame());

        // Act
        List<Move> kingMoves = game.getAllowedMoves(kingCoordinates);

        // Assert
        assertThat(kingMoves).doesNotContain(
                new Move(kingCoordinates, kingCoordinates.plus(-1, 1)),
                new Move(kingCoordinates, kingCoordinates.plus(0, 1)),
                new Move(kingCoordinates, kingCoordinates.plus(1, 1))
        );
    }

    @Test
    public void cannotExposeKingToCheck() {
        // Arrange
        Board board = Board.empty();

        Coordinates kingCoordinates = new Coordinates(4, 4);
        Piece king = new King(PlayerColour.WHITE);
        board.placePiece(kingCoordinates, king);

        Coordinates bishopCoordinates = new Coordinates(5, 4);
        Piece bishop = new Bishop(PlayerColour.WHITE);
        board.placePiece(bishopCoordinates, bishop);

        Coordinates enemyRookCoordinates = new Coordinates(6, 4);
        Piece enemyRook = new Rook(PlayerColour.BLACK);
        board.placePiece(enemyRookCoordinates, enemyRook);

        Game game = new Game(board, Flags.forNewGame());

        // Act
        List<Move> bishopMoves = game.getAllowedMoves(bishopCoordinates);

        // Assert
        assertThat(bishopMoves).isEmpty();
    }
}
