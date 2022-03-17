package ar.edu.itba.ss.tp1;

import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp1.models.Universe;
import ar.edu.itba.ss.tp1.parsers.DynamicParser;
import ar.edu.itba.ss.tp1.parsers.StaticParser;
import java.util.List;

public class CellIndexMethod {

    // L/M > Rc

    public static void main(String[] args) {
        List<Pair<Double,Double>> staticInfo = StaticParser.staticParsing("src/main/resources/Static100");
        List<Pair<Double,Double>> dynamicInfo = DynamicParser.dynamicParsing("src/main/resources/Dynamic100");
        long start = System.currentTimeMillis();
        Universe universe = new Universe(staticInfo, dynamicInfo, 5, true, 2.0);
        //universe.printGrid();
        universe.calculateDistances();
        long end = System.currentTimeMillis();
        System.out.println("Execution Time: " + (end - start) + "ms");
        /*for(String id: universe.getParticleWithNeighbours().keySet()){
            System.out.print(id + ":");
            for (String neighboursIds : universe.getParticleWithNeighbours().get(id)){
                System.out.print("\t" + neighboursIds);
            }
            System.out.println("\n");
        }*/

    }
}