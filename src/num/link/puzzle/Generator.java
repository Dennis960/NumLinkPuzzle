package num.link.puzzle;

public class Generator {
    static Generator instance = new Generator();

    public static Generator getInstance() {
        return instance;
    }

    public Puzzle generate(int sizeX, int sizeY, int numOfNum) {
        Puzzle puzzle = new Puzzle(sizeX, sizeY, numOfNum);
        puzzle.clear();
        for (int i = 0; i < numOfNum; i++) {
            for (int j = 0; j < 2; j++) {
                boolean wasPlaced = false;
                while (!wasPlaced) {
                    int x = (int) Math.round(Math.random() * (sizeX - 1));
                    int y = (int) Math.round(Math.random() * (sizeY - 1));
                    if (puzzle.get(x, y) == Cell.EMPTY) {
                        puzzle.set(x, y, Cell.NUMBER, i);
                        wasPlaced = true;
                    }
                }
            }
        }
        return puzzle;
    }
}
