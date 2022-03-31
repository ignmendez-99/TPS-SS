package ar.edu.itba.ss.tp2;

import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp2.models.Environment2D;
import ar.edu.itba.ss.tp2.models.Environment3D;
import ar.edu.itba.ss.tp2.parsers.EnvironmentParser2D;
import ar.edu.itba.ss.tp2.parsers.EnvironmentParser3D;
import ar.edu.itba.ss.tp2.parsers.OutputParser;

import java.util.List;

public class GameLife {

    private static final Boolean _3D = true;
    public static final Boolean RANDOM = false;

    private static final Integer M = 10;
    private static final Double lifeExpectancy = 30.0;
    private static final Double endCondition = 50.0;

    private static final String OUTPUT_FILE = "outputTP2.xyz";

    public static void main(String[] args) {
        OutputParser.setFileName(OUTPUT_FILE);
        if(_3D) {
            if (RANDOM) {
                //falta
            } else {
                List<Pair<Integer, Pair<Integer, Integer>>> staticInfo3D = EnvironmentParser3D.staticParsing("src/main/resources/environment3D");
                Environment3D env3D = new Environment3D(staticInfo3D, endCondition);
                env3D.simulate();
            }
        } else {
            if(RANDOM) {
                Environment2D env = new Environment2D(10, 10, lifeExpectancy, endCondition);
                env.simulate();
            } else {
                List<Pair<Integer, Integer>> staticInfo = EnvironmentParser2D.staticParsing("src/main/resources/environment2D");
                Environment2D env = new Environment2D(staticInfo, endCondition);
                env.simulate();
            }
        }
    }

}