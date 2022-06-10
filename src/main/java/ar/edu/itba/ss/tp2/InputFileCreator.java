package ar.edu.itba.ss.tp2;

import ar.edu.itba.ss.tp2.models.NeighbourType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ar.edu.itba.ss.tp2.GameLife.*;

public class InputFileCreator {

    private static final NeighbourType neighbourType = NeighbourType.MOORE;
    private static final int r = 1;

    public static final int M_2D = 1000; // PONER SIEMPRE NUMEROS QUE DEN ENTERO AL DIVIDIRLO POR 2
    public static final int M_3D = 100;  // PONER SIEMPRE NUMEROS QUE DEN ENTERO AL DIVIDIRLO POR 2

    public static String fileName2D;
    public static String fileName3D;

    public static void main(String[] args) {
        /**
         * CODIGO MOVIDO AL MAIN DE GameLife.java PARA ASI SOLO TENER QUE CORRER 1 PROGRAMA
         */
//        if(_3D) {
//            int[][][] env = populateRandom3D();
//            createCleanFile(fileName3D);
//            writeToFile3D(env);
//        } else {
//            int[][] env = populateRandom2D();
//            createCleanFile(fileName2D);
//            writeToFile2D(env);
//        }
    }


    public static int[][][] populateRandom3D() {
        int[][][] env = new int[M_3D][M_3D][M_3D];
        double lowerPercentage = 0.45;
        double upperPercentage = 0.55;
        final int fromX = (int) Math.ceil(M_3D * lowerPercentage);
        final int toX = (int) Math.ceil(M_3D * upperPercentage);
        final int fromY = (int) Math.ceil(M_3D * lowerPercentage);
        final int toY = (int) Math.ceil(M_3D * upperPercentage);
        final int fromZ = (int) Math.ceil(M_3D * lowerPercentage);
        final int toZ = (int) Math.ceil(M_3D * upperPercentage);

        int numberOfCellsToActivate = (toX - fromX) * (toY - fromY) * (toZ - fromZ);
        numberOfCellsToActivate = (int) (numberOfCellsToActivate * (LF/100.0));

        int breakAux = 0;
        for (int i = fromX; i < toX; i++) {
            for (int j = fromY; j < toY; j++) {
                for (int k = fromZ; k < toZ; k++) {
                    env[i][j][k] = 1;
                    breakAux++;
                    if(breakAux == numberOfCellsToActivate)
                        break;
                }
                if(breakAux == numberOfCellsToActivate)
                    break;
            }
            if(breakAux == numberOfCellsToActivate)
                break;
        }
        ShufflingUtils.shuffle3D(env, fromX, toX, fromY, toY, fromZ, toZ);
        return env;
    }

    public static int[][] populateRandom2D() {
        int[][] env = new int[M_2D][M_2D];
        double lowerPercentage = 0.4;
        double upperPercentage = 0.6;
        final int fromX = (int) Math.ceil(M_2D * lowerPercentage);
        final int toX = (int) Math.ceil(M_2D * upperPercentage);
        final int fromY = (int) Math.ceil(M_2D * lowerPercentage);
        final int toY = (int) Math.ceil(M_2D * upperPercentage);

        int numberOfCellsToActivate = (toX - fromX) * (toY - fromY);
        numberOfCellsToActivate = (int) (numberOfCellsToActivate * (LF/100.0));

        int breakAux = 0;
        for (int i = fromX; i < toX; i++) {
            for (int j = fromY; j < toY; j++) {
                env[i][j] = 1;
                breakAux++;
                if(breakAux == numberOfCellsToActivate)
                    break;
            }
            if(breakAux == numberOfCellsToActivate)
                break;
        }
        ShufflingUtils.shuffle2D(env, fromX, toX, fromY, toY);
        return env;
    }

    public static void writeToFile3D(int[][][] env) {
        try {
            StringBuilder dump = new StringBuilder(neighbourType.type + " " + r + "\n");
            dump.append(M_3D + " " + M_3D + " " + M_3D + "\n");
            for (int i = 0; i < M_3D; i++) {
                for (int j = 0; j < M_3D; j++) {
                    for (int k = 0; k < M_3D; k++) {
                        if (env[i][j][k]== 1) {
                            dump.append(i).append(" ").append(j).append(" ").append(k).append("\n");
                        }
                    }

                }
            }
            appendToEndOfFile(dump.toString(), fileName3D);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeToFile2D(int[][] env) {
        try {
            StringBuilder dump = new StringBuilder(neighbourType.type + " " + r + "\n");
            dump.append(M_2D + " " + M_2D + " " + M_2D + "\n");
            for (int i = 0; i < M_2D; i++) {
                for (int j = 0; j < M_2D; j++) {
                    if (env[i][j]== 1) {
                        dump.append(i).append(" ").append(j).append(" ").append("\n");
                    }
                }
            }
            appendToEndOfFile(dump.toString(), fileName2D);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void appendToEndOfFile(String text, String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(text);
        bw.close();
    }

    public static void createCleanFile(String fileName) {
        Path fileToDeletePath = Paths.get(fileName);
        try {
            Files.deleteIfExists(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
