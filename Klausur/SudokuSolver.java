public class SudokuSolver {

    public static void solve(int[][] board) {
        // 1. Suche die nächste freie Position (findOpenPosition) [cite: 619-620, 669]
        int[] nextPosition = findOpenPosition(board);

        // 2. Basisfall: Keine freien Felder mehr -> Sudoku gelöst [cite: 625-631]
        if (nextPosition == null) {
            printBoard(board);
            System.exit(0);
        }

        int row = nextPosition[0];
        int col = nextPosition[1];

        // 3. Probiere alle Zahlen von 1 bis 9 aus [cite: 642]
        for (int num = 1; num <= 9; num++) {

            // 4. Prüfe, ob die Zahl num an (row, col) gültig ist [cite: 646, 670]
            if (isValid(board, num, row, col)) {
                board[row][col] = num;

                // Rekursiver Aufruf für das nächste Feld [cite: 651]
                solve(board);
            }
        }

        // 5. Backtrack: Feld zurücksetzen, wenn kein Pfad zur Lösung führt [cite: 659-660]
        board[row][col] = 0;
    }

    // Die "valid"-Methode mit der extra Sudoku-Constraint [cite: 670, 676]
    private static boolean isValid(int[][] board, int num, int row, int col) {
        for (int i = 0; i < 9; i++) {
            // Check Zeile [cite: 670]
            if (board[row][i] == num) return false;
            // Check Spalte [cite: 670]
            if (board[i][col] == num) return false;
        }

        // Check 3x3 Unterquadrat (Die spezifische Sudoku-Regel)
        int boxRowStart = row - (row % 3);
        int boxColStart = col - (col % 3);

        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {
                if (board[r][c] == num) return false;
            }
        }

        return true;
    }

    private static int[] findOpenPosition(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) return new int[]{r, c};
            }
        }
        return null;
    }

    private static void printBoard(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.println();
        }
    }
}