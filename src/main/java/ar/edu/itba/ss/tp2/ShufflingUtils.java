package ar.edu.itba.ss.tp2;

import java.util.Random;

public class ShufflingUtils {

    public static void shuffle2D(int[][] matrix, int fromX, int toX, int fromY, int toY) {
        Random rnd = new Random();
        for (int i = 0; i < (toX-fromX) * (toY-fromY) * 2; i++) {
            int randomX = rnd.nextInt(toX - fromX) + fromX;
            int randomY = rnd.nextInt(toY - fromY) + fromY;
            int aux = matrix[randomX][randomY];
            int randomX2 = rnd.nextInt(toX - fromX) + fromX;
            int randomY2 = rnd.nextInt(toY - fromY) + fromY;
            matrix[randomX][randomY] = matrix[randomX2][randomY2];
            matrix[randomX2][randomY2] = aux;
        }
    }

    public static void shuffle3D(int[][][] matrix, int fromX, int toX, int fromY, int toY, int fromZ, int toZ) {
        Random rnd = new Random();
        for (int i = 0; i < (toX-fromX) * (toY-fromY) * (toZ-fromZ) * 2; i++) {
            int randomX = rnd.nextInt(toX - fromX) + fromX;
            int randomY = rnd.nextInt(toY - fromY) + fromY;
            int randomZ = rnd.nextInt(toZ - fromZ) + fromZ;
            int aux = matrix[randomX][randomY][randomZ];
            int randomX2 = rnd.nextInt(toX - fromX) + fromX;
            int randomY2 = rnd.nextInt(toY - fromY) + fromY;
            int randomZ2 = rnd.nextInt(toZ - fromZ) + fromZ;
            matrix[randomX][randomY][randomZ] = matrix[randomX2][randomY2][randomZ2];
            matrix[randomX2][randomY2][randomZ2] = aux;
        }
    }
}
