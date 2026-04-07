package model;

public class Figure {
    private boolean isWhite;
    private boolean isQueen;

    public Figure(boolean isWhite, boolean isQueen) {
        this.isWhite = isWhite;
        this.isQueen = isQueen;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isQueen() {
        return isQueen;
    }
}