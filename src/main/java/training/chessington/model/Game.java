package training.chessington.model;

import training.chessington.model.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Game {
    public static final int SIZE = 8;
    private final Board board;
    private final Flags flags;
    private final GameInfo gameInfo;

    public Game(Board board, Flags flags) {
        this.board = board;
        this.flags = flags;
        this.gameInfo = GameInfo.forNewGame();
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

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public Piece pieceAt(int row, int col) {
        return board.get(new Coordinates(row, col));
    }

    public List<Move> getAllowedMoves(Coordinates from) {
        if (gameInfo.isGameOver()) {
            return new ArrayList<>();
        }

        Piece piece = board.get(from);
        if (piece == null || piece.getColour() != gameInfo.getNextPlayer()) {
            return new ArrayList<>();
        }

        List<Move> candidateMoves = piece.getAllowedMoves(from, this);
        return candidateMoves.stream().filter(this::moveWouldNotLeavePlayerInCheck).collect(Collectors.toList());
    }

    private boolean moveWouldNotLeavePlayerInCheck(Move move) {
        PlayerColour thisPlayer = gameInfo.getNextPlayer();
        PlayerColour otherPlayer = thisPlayer.opponent();

        Game alternateGame = new Game(board.createCopy(), flags.createCopy());
        alternateGame.getBoard().move(move.getFrom(), move.getTo());
        Coordinates kingCoords = alternateGame.getBoard().findKingOfColour(thisPlayer);
        return kingCoords == null || !alternateGame.squareIsThreatenedByColour(kingCoords, otherPlayer);
    }

    public void makeMove(Move move) throws InvalidMoveException {
        if (gameInfo.isGameOver()) {
            throw new InvalidMoveException("Game has ended!");
        }

        Coordinates from = move.getFrom();
        Coordinates to = move.getTo();

        Piece piece = board.get(from);
        if (piece == null) {
            throw new InvalidMoveException(String.format("No piece at %s", from));
        }

        if (piece.getColour() != gameInfo.getNextPlayer()) {
            throw new InvalidMoveException(String.format("Wrong colour piece - it is %s's turn", gameInfo.getNextPlayer()));
        }

        if (!getAllowedMoves(move.getFrom()).contains(move)) {
            throw new InvalidMoveException(String.format("Cannot move piece %s from %s to %s", piece, from, to));
        }

        board.move(from, to);
        checkForEnPassantCapture(piece, to);
        checkForCastling(piece, from, to);
        updateFlags(piece, from, to);
    }

    public void promotePiece(Piece.PieceType pieceType) {
        Coordinates promotionSquare = flags.getPawnPromotionSquare().get();
        PlayerColour colour = board.get(promotionSquare).getColour();
        switch (pieceType) {
            case QUEEN: board.placePiece(promotionSquare, new Queen(colour)); break;
            case ROOK: board.placePiece(promotionSquare, new Rook(colour)); break;
            case BISHOP: board.placePiece(promotionSquare, new Bishop(colour)); break;
            case KNIGHT: board.placePiece(promotionSquare, new Knight(colour)); break;
        }
        flags.setPawnPromotionSquare(Optional.empty());
    }

    private void updateFlags(Piece piece, Coordinates from, Coordinates to) {

        // En passant
        if (piece.getType() == Piece.PieceType.PAWN && Math.abs(to.getRow() - from.getRow()) == 2) {
            Coordinates inBetween = piece.getColour() == PlayerColour.WHITE ? from.plus(-1, 0) : from.plus(1, 0);
            flags.setEnPassantSquare(Optional.of(inBetween));
        } else {
            flags.setEnPassantSquare(Optional.empty());
        }

        // Castling
        if (piece.getType() == Piece.PieceType.KING) {
            flags.forbidCastling(piece.getColour());
        }

        if (piece.getType() == Piece.PieceType.ROOK) {
            if (from.equals(new Coordinates(0, 0))) {
                flags.setBlackQueensideCastle(false);
            } else if (from.equals(new Coordinates(0, 7))) {
                flags.setBlackKingsideCastle(false);
            } else if (from.equals(new Coordinates(7, 0))) {
                flags.setWhiteQueensideCastle(false);
            } else if (from.equals(new Coordinates(7, 7))) {
                flags.setWhiteKingsideCastle(false);
            }
        }

        // Pawn promotion
        if (piece.getType() == Piece.PieceType.PAWN && (to.getRow() == 0 || to.getRow() == 7)) {
            flags.setPawnPromotionSquare(Optional.of(to));
        }

        // Next player
        gameInfo.switchPlayer();

        // Game over
        if (hasNoLegalMoves()) {
            gameInfo.setResult(isInCheck() ? winResult() : Result.DRAW);
        }
    }

    private Result winResult() {
        return gameInfo.getNextPlayer() == PlayerColour.WHITE ? Result.BLACK_WINS : Result.WHITE_WINS;
    }

    private boolean isInCheck() {
        Coordinates kingPosition = board.findKingOfColour(gameInfo.getNextPlayer());
        return kingPosition != null && squareIsThreatenedByColour(kingPosition, gameInfo.getNextPlayer().opponent());
    }

    private boolean hasNoLegalMoves() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!getAllowedMoves(new Coordinates(i, j)).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkForEnPassantCapture(Piece piece, Coordinates to) {
        if (piece.getType() == Piece.PieceType.PAWN) {
            if (flags.getEnPassantSquare().map(to::equals).orElse(false)) {
                int offset = piece.getColour() == PlayerColour.WHITE ? 1 : -1;
                board.obliterate(flags.getEnPassantSquare().get().plus(offset, 0));
            }
        }
    }

    private void checkForCastling(Piece piece, Coordinates from, Coordinates to) {
        if (piece.getType() == Piece.PieceType.KING && Math.abs(from.getCol() - to.getCol()) == 2) {
            int skippedCol = from.getCol() > to.getCol() ? from.getCol() - 1 : from.getCol() + 1;
            int rookCol = from.getCol() > to.getCol() ? 0 : 7;
            board.move(new Coordinates(from.getRow(), rookCol), new Coordinates(from.getRow(), skippedCol));
        }
    }

    public boolean squareIsThreatenedByColour(Coordinates square, PlayerColour colour) {
        // We don't care about special moves for detecting threats, as they add no additional threats, and
        // this prevents us infinitely recursing when we check if castling is allowed.
        Game dummyGame = new Game(board, flags.withNoSpecialMoves());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = pieceAt(i, j);
                if (piece != null && piece.getColour() == colour) {
                    if (piece.getAllowedMoves(new Coordinates(i, j), dummyGame).stream().map(Move::getTo).anyMatch(square::equals)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isEnded() {
        return gameInfo.isGameOver();
    }

    public String getResult() {
        return gameInfo.getResult().name();
    }
}
