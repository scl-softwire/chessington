package training.chessington.model;

import org.junit.Test;
import training.chessington.model.pieces.Pawn;
import training.chessington.model.pieces.Piece;

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

        Flags flags = Flags.forNewGame();
        flags.setNextPlayer(PlayerColour.BLACK);

        Game game = new Game(board, flags);
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

        Flags flags = Flags.forNewGame();
        flags.setNextPlayer(PlayerColour.WHITE);

        Game game = new Game(board, flags);
        game.makeMove(new Move(whiteCoordinates, whiteCoordinates.plus(-2, 0)));

        // Act
        Coordinates captureSquare = whiteCoordinates.plus(-1, 0);
        game.makeMove(new Move(blackCoordinates, captureSquare));

        // Assert
        assertThat(game.getBoard().get(whiteCoordinates)).isNull();
    }
}
