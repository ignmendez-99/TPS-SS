package ar.edu.itba.ss.tp2;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Automatizer {

    public static void main(String[] args) throws IOException {
        final double[] lf = {15, 25, 40, 55, 65, 75};
        final boolean[] _3d_2d = {false, true};
        final String[] rules = {"a", "b", "c"};

        // 6x2x3 = 36 corridas

        File directory1 = new File("src/main/resources/tp2");
        File directory2 = new File("XYZ");
        File directory3 = new File("PythonFiles");
        FileUtils.cleanDirectory(directory1);
        FileUtils.cleanDirectory(directory2);
        FileUtils.cleanDirectory(directory3);

        for (double v : lf) {
            for (boolean b : _3d_2d) {
                for (String rule : rules) {
                    GameLife._3D = b;
                    GameLife.LF = v;
                    GameLife.RULE = rule;
                    System.out.println("\n-----------------------\n" +
                            "CORRIDA CON LifeExpectancy = " + v + "   regla " + (b?"3D-":"2D-") + rule +
                            "\n-----------------------\n");
                    GameLife.run(true);
                }
            }
        }
    }
}
