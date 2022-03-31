package ar.edu.itba.ss.tp2;

import java.util.Random;

public class ShufflingUtils {

    /** Shuffles a 2D array with the same number of columns for each row. */
    public static void shuffle(int[][] matrix, int columns, Random rnd) {
        int size = matrix.length * columns;
        for (int i = size; i > 1; i--)
            swap(matrix, columns, i - 1, rnd.nextInt(i));
    }

    /**
     * Swaps two entries in a 2D array, where i and j are 1-dimensional indexes, looking at the
     * array from left to right and top to bottom.
     */
    public static void swap(int[][] matrix, int columns, int i, int j) {
        int tmp = matrix[i / columns][i % columns];
        matrix[i / columns][i % columns] = matrix[j / columns][j % columns];
        matrix[j / columns][j % columns] = tmp;
    }

    /** Just some test code. */
    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        shuffle(matrix, 3, new Random());
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                System.out.print(matrix[r][c] + "\t");
            }
            System.out.println();
        }
    }
}
