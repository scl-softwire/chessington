package training.chessington.model;

public enum PlayerColour {
    WHITE, BLACK;

    public PlayerColour opponent() {
        return this == WHITE ? BLACK : WHITE;
    }
}
