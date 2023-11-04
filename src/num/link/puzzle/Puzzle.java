package num.link.puzzle;

public class Puzzle {
    private final Cell[][] puzzle;
    private final int[][] numbers;
    private final int sizeX;
    private final int sizeY;
    private final int numberOfNumbers;

    public Puzzle(int sizeX, int sizeY, int numberOfNumbers) {
        this(new Cell[sizeX][sizeY], new int[sizeX][sizeY], sizeX, sizeY, numberOfNumbers);
    }
    private Puzzle(Cell[][] puzzle, int[][] numbers, int sizeX, int sizeY, int numberOfNumbers) {
        this.puzzle = puzzle;
        this.numbers = numbers;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfNumbers = numberOfNumbers;
    }

    public void print() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                if (puzzle[x][y] == Cell.NUMBER) {
                    System.out.print((char)('a' + numbers[x][y]));
                } else {
                    System.out.print(puzzle[x][y].getSymbol());
                }
            }
            System.out.println();
        }
    }

    public void clear() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                puzzle[x][y] = Cell.EMPTY;
                numbers[x][y] = -1;
            }
        }
    }
    public void set(int x, int y, Cell value, int number) {
        puzzle[x][y] = value;
        numbers[x][y] = number;
    }
    public void set(int x, int y, Cell value) {
        puzzle[x][y] = value;
    }

    public Cell get(int x, int y) {
        return puzzle[x][y];
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    @Override
    public Puzzle clone() {
        Cell[][] puzzleClone = new Cell[sizeX][sizeY];
        int[][] numbersClone = new int[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                puzzleClone[x][y] = puzzle[x][y];
                numbersClone[x][y] = numbers[x][y];
            }
        }
        return new Puzzle(puzzleClone, numbersClone, sizeX, sizeY, numberOfNumbers);
    }

    public CellNumberDetails getTargetNumberDetails(int targetNumber) {
        // TODO performance could be improved by initializing the number's positions in a hash map
        boolean first = true;
        CellNumberDetails targetNumberDetails = new CellNumberDetails();
        targetNumberDetails.number = targetNumber;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (numbers[x][y] == targetNumber) {
                    if (first) {
                        targetNumberDetails.x1 = x;
                        targetNumberDetails.y1 = y;
                        first = false;
                    } else {
                        targetNumberDetails.x2 = x;
                        targetNumberDetails.y2 = y;
                        return targetNumberDetails;
                    }
                }
            }
        }
        throw new RuntimeException("No number there");
    }

    public int getNumberOfNumbers() {
        return numberOfNumbers;
    }

    public int getNumber(int x, int y) {
        return numbers[x][y];
    }
}
