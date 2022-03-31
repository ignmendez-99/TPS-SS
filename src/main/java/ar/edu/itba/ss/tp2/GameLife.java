package ar.edu.itba.ss.tp2;

import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp2.models.Environment2D;
import ar.edu.itba.ss.tp2.parsers.EnvironmentParser2D;

import java.util.List;

public class GameLife {

    private static final Boolean _3D = false;
    private static final Integer M = 10;
    private static final Double lifeExpectancy = 0.3;
    private static final Double endCondition = 50.0;

    public static void main(String[] args) {
        if(_3D) {
            //falta
        } else {
            List<Pair<Integer, Integer>> staticInfo = EnvironmentParser2D.staticParsing("src/main/resources/environment2D");
            Environment2D env = new Environment2D(staticInfo, endCondition);
            env.simulate();
        }
    }

}
