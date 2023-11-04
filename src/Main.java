import num.link.puzzle.Puzzle;
import num.link.puzzle.Generator;
import num.link.puzzle.Solver;

public class Main {
    public static void main(String[] args) {
        Solver solver = Solver.getInstance();
        Generator generator = Generator.getInstance();
        for (int size = 2; size < 15; size++) {
            System.out.println("Festgelegte Größe: " + size);
            long startTime = System.currentTimeMillis();
            long numberOfPuzzlesGenerated = 0;
            Puzzle solvedPuzzle = null;
            while (solvedPuzzle == null) {
                Puzzle puzzle = generator.generate(size,size, size);
                numberOfPuzzlesGenerated++;
                long duration = System.currentTimeMillis() - startTime;
                if (numberOfPuzzlesGenerated % 10 == 0) {
                    System.out.println("Anzahl der generierten Rätsel: " + numberOfPuzzlesGenerated + " in " + duration + "ms, Zeit pro Rätsel: " + (duration / numberOfPuzzlesGenerated) + "ms");
                }
                if (duration > 10000) {
                    break;
                }
                solvedPuzzle = solver.solve(puzzle);
            }
            long duration = System.currentTimeMillis() - startTime;
            if (solvedPuzzle != null) {
                solvedPuzzle.print();
            }
            System.out.println("Anzahl der generierten Rätsel: " + numberOfPuzzlesGenerated + " in " + duration + "ms, Zeit pro Rätsel: " + (duration / numberOfPuzzlesGenerated) + "ms");
            System.out.println();
        }
    }
}