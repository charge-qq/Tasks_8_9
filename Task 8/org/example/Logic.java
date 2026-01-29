package org.example;

public class Logic {
    public static int checkWinner(int[][] field) {
        boolean five_ones = five_in_row(field, 1);
        boolean five_zeros = five_in_row(field, 0);

        if (five_ones && !five_zeros) {
            return 1;
        }
        if (five_zeros && !five_ones) {
            return -1;
        }
        return 0;
    }

    private static boolean five_in_row(int[][] field, int value) {
        int rows = field.length;
        int cols = field[0].length;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c <= cols-5; c++) {
                boolean found = true;
                for (int k = 0; k < 5; k++) {
                    if (field[r][c+k] != value) {
                        found = false;
                        break;
                    }
                }
                if (found) return true;
            }
        }

        for (int r = 0; r <= rows-5; r++) {
            for (int c = 0; c < cols; c++) {
                boolean found = true;
                for (int k = 0; k < 5; k++) {
                    if (field[r+k][c] != value) {
                        found = false;
                        break;
                    }
                }
                if (found) return true;
            }
        }

        for (int r = 0; r <= rows-5; r++) {
            for (int c = 0; c < cols-5; c++) {
                boolean found = true;
                for (int k = 0; k < 5; k++) {
                    if (field[r+k][c+k] != value) {
                        found = false;
                        break;
                    }
                }
                if (found) return true;
            }
        }

        for (int r = 0; r <= rows-5; r++) {
            for (int c = 4; c < cols; c++) {
                boolean found = true;
                for (int k = 0; k < 5; k++) {
                    if (field[r+k][c-k] != value) {
                        found = false;
                        break;
                    }
                }
                if (found) return true;
            }
        }
        return false;
    }
}
