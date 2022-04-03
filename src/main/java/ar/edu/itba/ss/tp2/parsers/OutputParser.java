package ar.edu.itba.ss.tp2.parsers;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OutputParser {

    private static String fileName;
    private static boolean first = true;

    public static void writeMatrix2DToFile(int[][] env, Integer x, Integer y,
                                           long eTime, int particlesToDraw, int center) {
        try {
            StringBuilder dump = new StringBuilder("" + particlesToDraw + "\n" + "Time=" + eTime + "\n");
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (env[i][j]== 1) {
                        Point2D p = new Point2D(i, j);
                        Point2D p2 = new Point2D(center, center);
                        double distance = p.distance(p2);
                        int rainbowPercentage = (int) (255 * Math.abs(distance / center));
                        dump.append(rainbowPercentage).append(" ");
                        dump.append(i).append(" ").append(j).append(" ").append("0 0.5\n");
                    }
                }
            }
            appendToEndOfFile(dump.toString());
            writeAux(particlesToDraw);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeAux(int n) throws IOException {
        String pythonFilename = "outputForPython.csv";
        FileWriter fw = new FileWriter(pythonFilename, true);
        BufferedWriter bw = new BufferedWriter(fw);
        if(first){
            bw.write(String.valueOf(n));
            first=false;
        }else
            bw.write("," + n);
        bw.close();
    }

    public static void createCleanPythonFile(int n) {
        Path fileToDeletePath = Paths.get("outputForPython.csv");
        try {
            Files.deleteIfExists(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setFileName(String fn) {
        fileName = fn;
    }

    public static void writeMatrix3DToFile(int[][][] env, Integer x, Integer y, Integer z,
                                           long eTime, int particlesToDraw, int center) {
        try {
            StringBuilder dump = new StringBuilder("" + particlesToDraw + "\n" + "Time=" + eTime + "\n");
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    for (int k = 0; k < z; k++) {
                        if (env[i][j][k] == 1) {
                            Point3D p = new Point3D(i, j, k);
                            Point3D p2 = new Point3D(center, center, center);
                            double distance = p.distance(p2);
                            int rainbowPercentage = (int) (255 * Math.abs(distance / center));
                            dump.append(rainbowPercentage).append(" ");
                            dump.append(i).append(" ").append(j).append(" ").append(k).append(" 0.1\n");
                        }
                    }
                }
            }
            appendToEndOfFile(dump.toString());
            writeAux(particlesToDraw);
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
