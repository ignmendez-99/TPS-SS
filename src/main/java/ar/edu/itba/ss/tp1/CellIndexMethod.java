package ar.edu.itba.ss.tp1;

import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp1.models.Universe;
import ar.edu.itba.ss.tp1.parsers.DynamicParser;
import ar.edu.itba.ss.tp1.parsers.StaticParser;
import java.util.List;

public class CellIndexMethod {

    public static void main(String[] args) {
        List<Pair<Double,Double>> staticInfo = StaticParser.staticParsing("src/main/resources/Static100");
        List<Pair<Double,Double>> dynamicInfo = DynamicParser.dynamicParsing("src/main/resources/Dynamic100");
        Universe universe = new Universe(staticInfo, dynamicInfo, 5, false, 1.0);
        universe.printGrid();
    }
}