package ar.edu.itba.ss.tp2;

import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp2.models.Environment2D;
import ar.edu.itba.ss.tp2.models.Environment3D;
import ar.edu.itba.ss.tp2.parsers.EnvironmentParser2D;
import ar.edu.itba.ss.tp2.parsers.EnvironmentParser3D;
import ar.edu.itba.ss.tp2.parsers.OutputParser;

import java.util.List;

import static ar.edu.itba.ss.tp2.InputFileCreator.*;

public class GameLife {

    /******************
     *  PARAMETROS PARA VARIAR
     *********************/
    public static Boolean _3D = false;
    public static String RULE = "a";  // a  b  c
    public static Double LF = 15.0;   // 15, 25, 40, 60, 80, 100
    private static final int iterations = 200;



    public static String LIFE_EXP;
    public static String REGLA;    // _3D_a _3D_b _3D_c _2D_a _2D_b _2D_c


    public static void main(String[] args) {
        run(false);
    }

    public static void run(Boolean fromAutomatizer) {

        LIFE_EXP = "_" + String.valueOf(LF).split("\\.")[0];
        REGLA = "_" + (_3D ? "3D" : "2D") + "_" + RULE;
        final String OUTPUT_FILE_3D = "XYZ/outputTP2" + REGLA + LIFE_EXP + ".xyz";
        final String OUTPUT_FILE_2D = "XYZ/outputTP2" + REGLA + LIFE_EXP + ".xyz";
        fileName2D = "src/main/resources/tp2/environment2D" + LIFE_EXP;
        fileName3D = "src/main/resources/tp2/environment3D" + LIFE_EXP;

        if(_3D) {
            int[][][] env = populateRandom3D();
            if(!fromAutomatizer)
                createCleanFile(fileName3D);
            writeToFile3D(env);
            OutputParser.setFileName(OUTPUT_FILE_3D);
            List<Pair<Integer, Pair<Integer, Integer>>> staticInfo3D = EnvironmentParser3D.staticParsing(fileName3D);
            Environment3D env3D = new Environment3D(staticInfo3D);
            env3D.simulate(iterations);
        } else {
            int[][] env = populateRandom2D();
            if(!fromAutomatizer)
                createCleanFile(fileName2D);
            writeToFile2D(env);
            OutputParser.setFileName(OUTPUT_FILE_2D);
            List<Pair<Integer, Integer>> staticInfo = EnvironmentParser2D.staticParsing(fileName2D);
            Environment2D env2D = new Environment2D(staticInfo);
            env2D.simulate(iterations);
        }
    }
}
