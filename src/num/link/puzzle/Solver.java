package num.link.puzzle;

public class Solver {
    private static final Solver instance = new Solver();

    public static Solver getInstance() {
        return instance;
    }

    public Puzzle solve(Puzzle puzzle) {
        int targetNumber = 0;
        CellNumberDetails targetNumberDetails = puzzle.getTargetNumberDetails(targetNumber);
        boolean[][] passedCells = new boolean[puzzle.getSizeX()][puzzle.getSizeY()];
        return solveRecursive(puzzle, passedCells, targetNumberDetails.x1, targetNumberDetails.y1, targetNumberDetails, Direction.ANY);
    }

    private Puzzle solveRecursive(Puzzle puzzle, boolean[][] passedCells, int curPosX, int curPosY, CellNumberDetails targetNumberDetails, Direction targetDirection) {
        Puzzle puzzleClone = puzzle.clone();
        boolean[][] passedCellsClone = cloneBooleanArray(passedCells);
        passedCellsClone[curPosX][curPosY] = true;

        // Up
        if ((targetDirection == Direction.ANY || targetDirection == Direction.UP) && curPosY > 0) {
            Puzzle result = checkAllCellPossibilities(puzzleClone, passedCellsClone, curPosX, curPosY - 1, targetNumberDetails, new Cell[]{Cell.CORNER_SE, Cell.CORNER_SW, Cell.VERTICAL}, Direction.UP);
            if (result != null) {
                return result;
            }
        }
        // Right
        if ((targetDirection == Direction.ANY || targetDirection == Direction.RIGHT) && curPosX < puzzleClone.getSizeX() - 1) {
            Puzzle result = checkAllCellPossibilities(puzzleClone, passedCellsClone, curPosX + 1, curPosY, targetNumberDetails, new Cell[]{Cell.CORNER_SE, Cell.CORNER_NE, Cell.HORIZONTAL}, Direction.RIGHT);
            if (result != null) {
                return result;
            }
        }
        // Down
        if ((targetDirection == Direction.ANY || targetDirection == Direction.DOWN) && curPosY < puzzleClone.getSizeY() - 1) {
            Puzzle result = checkAllCellPossibilities(puzzleClone, passedCellsClone, curPosX, curPosY + 1, targetNumberDetails, new Cell[]{Cell.CORNER_NE, Cell.CORNER_NW, Cell.VERTICAL}, Direction.DOWN);
            if (result != null) {
                return result;
            }
        }
        // Left
        if ((targetDirection == Direction.ANY || targetDirection == Direction.LEFT) && curPosX > 0) {
            Puzzle result = checkAllCellPossibilities(puzzleClone, passedCellsClone, curPosX - 1, curPosY, targetNumberDetails, new Cell[]{Cell.CORNER_NW, Cell.CORNER_SW, Cell.HORIZONTAL}, Direction.LEFT);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private boolean[][] cloneBooleanArray(boolean[][] array) {
        boolean[][] clone = new boolean[array.length][];
        for (int i = 0; i < array.length; i++) {
            clone[i] = new boolean[array[i].length];
            System.arraycopy(array[i], 0, clone[i], 0, array[i].length);
        }
        return clone;
    }

    private Puzzle checkAllCellPossibilities(Puzzle puzzle, boolean[][] passedCells, int curPosX, int curPosY, CellNumberDetails targetNumberDetails, Cell[] possibilities, Direction direction) {
        Puzzle puzzleClone = puzzle.clone();
        if (passedCells[curPosX][curPosY]) {
            return null;
        }
        boolean[][] passedCellsClone = cloneBooleanArray(passedCells);
        passedCellsClone[curPosX][curPosY] = true;
        if (puzzleClone.get(curPosX, curPosY) != Cell.EMPTY) {
            // check if it is target number
            if (curPosX == targetNumberDetails.x2 && curPosY == targetNumberDetails.y2) {
                // Target number hit
                if (targetNumberDetails.number == puzzleClone.getNumberOfNumbers() - 1) {
                    // Very nice, we solved it!
                    // Now need to check if all cells were passed
                    for (int x = 0; x < puzzleClone.getSizeX(); x++) {
                        for (int y = 0; y < puzzleClone.getSizeY(); y++) {
                            if (!passedCellsClone[x][y]) {
                                return null;
                            }
                        }
                    }
                    return puzzleClone;
                }
                // Not solved yet, go to next number
                CellNumberDetails newTargetNumberDetails = puzzleClone.getTargetNumberDetails(targetNumberDetails.number + 1);
                return solveRecursive(puzzleClone, passedCellsClone, newTargetNumberDetails.x1, newTargetNumberDetails.y1, newTargetNumberDetails, Direction.ANY);
            }

            int curPosNumber = puzzleClone.getNumber(curPosX, curPosY);
            if (curPosNumber != -1 && curPosNumber != targetNumberDetails.number) {
                // Bad luck, current cell is a different number than target number. Stop checking.
                return null;
            }
        }
        for (Cell possibility : possibilities) {
            puzzleClone.set(curPosX, curPosY, possibility);
            Direction targetDirection = CellToDirection(direction, possibility);
            Puzzle result = solveRecursive(puzzleClone, passedCellsClone, curPosX, curPosY, targetNumberDetails, targetDirection);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private Direction CellToDirection(Direction fromDirection, Cell cellType) {
        return switch (cellType) {
            case HORIZONTAL, VERTICAL -> fromDirection;
            case CORNER_NW -> fromDirection == Direction.DOWN ? Direction.LEFT : Direction.UP;
            case CORNER_NE -> fromDirection == Direction.DOWN ? Direction.RIGHT : Direction.UP;
            case CORNER_SW -> fromDirection == Direction.UP ? Direction.LEFT : Direction.DOWN;
            case CORNER_SE -> fromDirection == Direction.UP ? Direction.RIGHT : Direction.DOWN;
            default -> Direction.ANY;
        };
    }
}
enum Direction {
    ANY, UP, RIGHT, DOWN, LEFT;
}
