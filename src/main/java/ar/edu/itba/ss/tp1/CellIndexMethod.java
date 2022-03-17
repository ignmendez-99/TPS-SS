package ar.edu.itba.ss.tp1;

import ar.edu.itba.ss.tp1.models.Agent;
import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp1.models.Universe;
import ar.edu.itba.ss.tp1.parsers.DynamicParser;
import ar.edu.itba.ss.tp1.parsers.StaticParser;
import java.util.List;
import java.util.Set;

public class CellIndexMethod {

    // L/M > Rc

    private static Integer N = 1000;
    private static Integer M = 5;
    private static final Double L = 20.0;
    private static final Double RADIUS = 0.25;
    private static final Double RC = 1.0;
    private static final Boolean PERIODIC = true;

    public CellIndexMethod(Integer m, Integer n) {
        M = m;
        N = n;
    }

    public static void main(String[] args) {
        staticRun();
    }

    public static void staticRun() {
        List<Pair<Double,Double>> staticInfo = StaticParser.staticParsing("src/main/resources/Static100");
        List<Pair<Double,Double>> dynamicInfo = DynamicParser.dynamicParsing("src/main/resources/Dynamic100");
        Set<Agent> randomAgents = Universe.generateRandomAgents(N, L, RADIUS);

        long start = System.currentTimeMillis();

        Universe randomUniverse = new Universe(randomAgents, N, M, L, PERIODIC, RC);

        Universe universe = new Universe(staticInfo, dynamicInfo, M, PERIODIC, RC);

        //universe.printGrid();
        randomUniverse.calculateDistances();
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

    public void run() {
        List<Pair<Double,Double>> staticInfo = StaticParser.staticParsing("src/main/resources/Static100");
        List<Pair<Double,Double>> dynamicInfo = DynamicParser.dynamicParsing("src/main/resources/Dynamic100");
        Set<Agent> randomAgents = Universe.generateRandomAgents(N, L, RADIUS);

        long start = System.currentTimeMillis();

        Universe randomUniverse = new Universe(randomAgents, N, M, L, PERIODIC, RC);

        Universe universe = new Universe(staticInfo, dynamicInfo, M, PERIODIC, RC);

        //universe.printGrid();
        randomUniverse.calculateDistances();
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