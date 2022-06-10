package ar.edu.itba.ss.tp2;

import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp2.models.Environment2D;
import ar.edu.itba.ss.tp2.models.Environment3D;
import ar.edu.itba.ss.tp2.parsers.EnvironmentParser2D;
import ar.edu.itba.ss.tp2.parsers.EnvironmentParser3D;
import ar.edu.itba.ss.tp2.parsers.OutputParser;

import java.util.List;

import static ar.edu.itba.ss.tp2.InputFileCreator.fileName2D;
import static ar.edu.itba.ss.tp2.InputFileCreator.fileName3D;

public class GameLife {

    /******************
     *  PARAMETROS PUBLICOS USADOS POR TODOS
     *********************/
    public static final String REGLA = "_3Da";    // _3Da _3Db _3Dc _2Da _2Db _2Dc
    public static final String LIFE_EXP = "_10";
    public static final Boolean _3D = false;


    private static final String OUTPUT_FILE_3D = "XYZ/outputTP2_3D" + REGLA + LIFE_EXP + ".xyz";
    private static final String OUTPUT_FILE_2D = "XYZ/outputTP2_2D" + REGLA + LIFE_EXP + ".xyz";

    private static final int iterations = 200;

    public static void main(String[] args) {
        if(_3D) {
            OutputParser.setFileName(OUTPUT_FILE_3D);
            List<Pair<Integer, Pair<Integer, Integer>>> staticInfo3D = EnvironmentParser3D.staticParsing("src/main/resources/tp2/"+fileName2D);
            Environment3D env3D = new Environment3D(staticInfo3D);
            env3D.simulate(iterations);
        } else {
            OutputParser.setFileName(OUTPUT_FILE_2D);
            List<Pair<Integer, Integer>> staticInfo = EnvironmentParser2D.staticParsing("src/main/resources/tp2/"+fileName3D);
            Environment2D env = new Environment2D(staticInfo);
            env.simulate(iterations);
        }
    }

}
