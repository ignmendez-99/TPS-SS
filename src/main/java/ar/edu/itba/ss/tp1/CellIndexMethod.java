package ar.edu.itba.ss.tp1;

import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp1.models.Universe;
import ar.edu.itba.ss.tp1.parsers.DynamicParser;
import ar.edu.itba.ss.tp1.parsers.StaticParser;
import java.util.List;

public class CellIndexMethod {

    // L/M > Rc

    private static final Double RC = 7.0;
    private static final Boolean PERIODIC = false;

    public static void main(String[] args) {
        List<Pair<Double,Double>> staticInfo = StaticParser.staticParsing("src/main/resources/Static100");
        List<Pair<Double,Double>> dynamicInfo = DynamicParser.dynamicParsing("src/main/resources/Dynamic100");

        final Integer M = calculateOptimumM(staticInfo);
        System.out.println(M);

        long start = System.currentTimeMillis();

        Universe universe = new Universe(staticInfo, dynamicInfo, M, PERIODIC, RC);

        universe.printGrid();
        universe.calculateDistances();
        long end = System.currentTimeMillis();
        System.out.println("Execution Time: " + (end - start) + "ms");
        for(String id: universe.getParticleWithNeighbours().keySet()){
            System.out.print(id + ":");
            for (String neighboursIds : universe.getParticleWithNeighbours().get(id)){
                System.out.print("\t" + neighboursIds);
            }
            System.out.println("\n");
        }
    }

    private static Integer calculateOptimumM(List<Pair<Double,Double>> staticInfo) {
        // L / (rc + 2rmax) > M
        Double L = staticInfo.get(0).second;
        Double rmax = 0.0;
        for (int i = 1; i < staticInfo.size(); i++) {
            if(staticInfo.get(i).first > rmax) {
                rmax = staticInfo.get(i).first;
            }
        }
        return (int) Math.floor(L / (RC + (2*rmax)));
    }
}