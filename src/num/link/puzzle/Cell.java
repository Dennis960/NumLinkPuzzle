package num.link.puzzle;

public enum Cell {
    EMPTY('.'),
    CORNER_NW('┘'),
    CORNER_NE('└'),
    CORNER_SW('┐'),
    CORNER_SE('┌'),
    VERTICAL('|'),
    HORIZONTAL('─'),
    NUMBER('0');

    private final char symbol;

    Cell(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
