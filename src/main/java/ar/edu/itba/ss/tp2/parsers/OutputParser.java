package ar.edu.itba.ss.tp2.parsers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class OutputParser {

    private static String fileName;

    public static void writeMatrix2DToFile(int[][] env, Integer x, Integer y, long eTime) {
        try {
            final int numberOfParticles = x * y;
            StringBuilder dump = new StringBuilder("" + numberOfParticles + "\n" + "Time=" + eTime + "\n");
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (env[i][j] == 1) {
                        dump.append("Gr");
                    } else {
                        dump.append("Bl");
                    }
                    dump.append("        ").append(i).append("      ").append(j).append("      ").append("0      0.5\n");
                }
            }
            appendToEndOfFile(dump.toString());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void setFileName(String fn) {
        fileName = fn;
    }

    public static void writeMatrix3DToFile(int[][][] env, Integer x, Integer y, Integer z, long eTime) {
        try {
            final int numberOfParticles = x * y * z;
            StringBuilder dump = new StringBuilder("" + numberOfParticles + "\n" + "Time=" + eTime + "\n");
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    for (int k = 0; k < z; k++) {
                        if (env[i][j][k] == 1) {
                            dump.append("Gr");
                        } else {
                            dump.append("Bl");
                        }
                        dump.append("        ").append(i).append("      ").append(j).append("      ").append(k).append("      0.1\n");
                    }
                }
            }
            appendToEndOfFile(dump.toString());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void appendToEndOfFile(String text) throws IOException {
        FileWriter fw = new FileWriter(fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(text);
        bw.close();
    }

    public static void createCleanFile() {
        Path fileToDeletePath = Paths.get(fileName);
        try {
            Files.deleteIfExists(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
