package training.chessington.model;

import training.chessington.model.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game {
    public static final int SIZE = 8;
    private final Board board;
    private Flags flags;

    public Game(Board board, Flags flags) {
        this.board = board;
        this.flags = flags;
    }

    public static Game newGame() {
        Board board = Board.forNewGame();
        Flags flags = Flags.forNewGame();
        return new Game(board, flags);
    }

    public Board getBoard() {
        return board;
    }

    public Flags getFlags() {
        return flags;
    }

    public Piece pieceAt(int row, int col) {
        return board.get(new Coordinates(row, col));
    }

    public List<Move> getAllowedMoves(Coordinates from) {
        if (flags.isGameOver()) {
            return new ArrayList<>();
        }

        Piece piece = board.get(from);
        if (piece == null || piece.getColour() != flags.getNextPlayer()) {
            return new ArrayList<>();
        }

        return piece.getAllowedMoves(from, this);
    }

    public void makeMove(Move move) throws InvalidMoveException {
        if (flags.isGameOver()) {
            throw new InvalidMoveException("Game has ended!");
        }

        Coordinates from = move.getFrom();
        Coordinates to = move.getTo();

        Piece piece = board.get(from);
        if (piece == null) {
            throw new InvalidMoveException(String.format("No piece at %s", from));
        }

        if (piece.getColour() != flags.getNextPlayer()) {
            throw new InvalidMoveException(String.format("Wrong colour piece - it is %s's turn", flags.getNextPlayer()));
        }

        if (!getAllowedMoves(move.getFrom()).contains(move)) {
            throw new InvalidMoveException(String.format("Cannot move piece %s from %s to %s", piece, from, to));
        }

        board.move(from, to);
        checkForEnPassantCapture(piece, to);
        updateFlags(piece, from, to);
    }

    private void updateFlags(Piece piece, Coordinates from, Coordinates to) {

        // En passant
        if (piece.getType() == Piece.PieceType.PAWN && Math.abs(to.getRow() - from.getRow()) == 2) {
            Coordinates inBetween = piece.getColour() == PlayerColour.WHITE ? from.plus(-1, 0) : from.plus(1, 0);
            flags.setEnPassantSquare(Optional.of(inBetween));
        } else {
            flags.setEnPassantSquare(Optional.empty());
        }

        // Next player
        flags.switchPlayer();
    }

    private void checkForEnPassantCapture(Piece piece, Coordinates to) {
        if (piece.getType() == Piece.PieceType.PAWN) {
            if (flags.getEnPassantSquare().isPresent() && flags.getEnPassantSquare().get().equals(to)) {
                int offset = piece.getColour() == PlayerColour.WHITE ? 1 : -1;
                board.obliterate(flags.getEnPassantSquare().get().plus(offset, 0));
            }
        }
    }

    public boolean isEnded() {
        return flags.isGameOver();
    }

    public String getResult() {
        return null;
    }
}
