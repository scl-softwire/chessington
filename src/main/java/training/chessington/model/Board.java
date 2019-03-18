package training.chessington.model;

import training.chessington.model.pieces.*;

public class Board {

    private Piece[][] board = new Piece[8][8];

    private Board() {
    }

    public Board createCopy() {
        Board copyOfBoard = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                copyOfBoard.board[i][j] = this.board[i][j];
            }
        }
        return copyOfBoard;
    }

    public static Board forNewGame() {
        Board board = new Board();
        board.setBackRow(0, PlayerColour.BLACK);
        board.setBackRow(7, PlayerColour.WHITE);

        for (int col = 0; col < 8; col++) {
            board.board[1][col] = new Pawn(PlayerColour.BLACK);
            board.board[6][col] = new Pawn(PlayerColour.WHITE);
        }

        return board;
    }

    public static Board empty() {
        return new Board();
    }

    private void setBackRow(int rowIndex, PlayerColour colour) {
        board[rowIndex][0] = new Rook(colour);
        board[rowIndex][1] = new Knight(colour);
        board[rowIndex][2] = new Bishop(colour);
        board[rowIndex][3] = new Queen(colour);
        board[rowIndex][4] = new King(colour);
        board[rowIndex][5] = new Bishop(colour);
        board[rowIndex][6] = new Knight(colour);
        board[rowIndex][7] = new Rook(colour);
    }

    public Piece get(Coordinates coords) {
        return board[coords.getRow()][coords.getCol()];
    }

    public void obliterate(Coordinates coords) {
        board[coords.getRow()][coords.getCol()] = null;
    }

    public void move(Coordinates from, Coordinates to) {
        board[to.getRow()][to.getCol()] = board[from.getRow()][from.getCol()];
        board[from.getRow()][from.getCol()] = null;
    }

    public void placePiece(Coordinates coords, Piece piece) {
        board[coords.getRow()][coords.getCol()] = piece;
    }

    public boolean inBounds(Coordinates coords) {
        return coords.getRow() >= 0 && coords.getRow() < Game.SIZE && coords.getCol() >= 0 && coords.getCol() < Game.SIZE;
    }

    public boolean squareIsEmpty(Coordinates coords) {
        return get(coords) == null;
    }

    public boolean squareContainsPieceOfColour(Coordinates coords, PlayerColour colour) {
        Piece piece = get(coords);
        return piece != null && piece.getColour() == colour;
    }

    public Coordinates findKingOfColour(PlayerColour colour) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getType() == Piece.PieceType.KING && piece.getColour() == colour) {
                    return new Coordinates(i, j);
                }
            }
        }
        return null;
    }
}
