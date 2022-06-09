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
            particlesToDraw = particlesToDraw + 4;
            StringBuilder dump = new StringBuilder("" + particlesToDraw + "\n" + "Time=" + eTime + "\n");
            dump.append("0 0 0 0 0.00001\n");
            dump.append("0 1000 0 0 0.00001\n");
            dump.append("0 0 1000 0 0.00001\n");
            dump.append("0 1000 1000 0 0.00001\n");
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
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeAux(int it, int n, double radius) {
        try{
            String pythonFilename = "PythonFiles/outputForPython2.csv";
            StringBuilder dump = new StringBuilder("");
            if(first){
                dump.append("I,N,R\n");
                first = false;
            }
            dump.append(it).append(",").append(n).append(",").append(radius).append("\n");
            FileWriter fw = new FileWriter(pythonFilename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(dump.toString());
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void createCleanPythonFile(int n) {
        Path fileToDeletePath = Paths.get("PythonFiles/outputForPython.csv");
        first = true;
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
            int aux = particlesToDraw + 8;
            StringBuilder dump = new StringBuilder("" + aux + "\n" + "Time=" + eTime + "\n");
            dump.append("0 0 0 0 0.00001\n");
            dump.append("0 0 0 100 0.00001\n");
            dump.append("0 0 100 100 0.00001\n");
            dump.append("0 100 100 100 0.00001\n");
            dump.append("0 0 100 0 0.00001\n");
            dump.append("0 100 100 0 0.00001\n");
            dump.append("0 100 0 0 0.00001\n");
            dump.append("0 100 0 100 0.00001\n");
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
