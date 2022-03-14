package ar.edu.itba.ss.tp1.models;

import ar.edu.itba.ss.tp1.models.Agent;
import ar.edu.itba.ss.tp1.models.Cell;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Universe {

    //Variables
    private static Integer N;
    private Double L;
    private Integer M;
    private Boolean periodic;
    private Double RC;
    private Set<Agent> agents;

    private Cell[][] grid;

    // Constructor
    public Universe(List<Pair<Double, Double>> staticInfo, List<Pair<Double, Double>> dynamicInfo, Integer m, Boolean periodic, Double rc) {
        N = staticInfo.get(0).first.intValue();
        L = staticInfo.get(0).second;
        staticInfo.remove(0);
        M = m;
        RC = rc;
        agents = populate(staticInfo, dynamicInfo);
        this.periodic = periodic;
        createGrid();
    }

    // Methods
    private static Set<Agent> populate(List<Pair<Double, Double>> staticInfo, List<Pair<Double, Double>> dynamicInfo ){
        Random random = new Random();
        int overlaped = 0;
        Set<Agent> aux= new TreeSet<>(new Comparator<Agent>() {
            @Override
            public int compare(Agent o1, Agent o2) {
                if(Objects.equals(o1.getX(), o2.getX()) && Objects.equals(o1.getY(), o2.getY())) {
                    return 0;
                }
//                if((o1.getX()
//                        .subtract(o2.getX())
//                        .pow(2).add(o1.getY().subtract(o2.getY()).pow(2))
//                        .sqrt(new MathContext(10))
//                        .subtract(BigDecimal.valueOf(o1.getRadius()).add(BigDecimal.valueOf(o2.getRadius()))).doubleValue()
//                             < 0.0)) {
//                    return 0;
//                }
                if((Math.sqrt(Math.pow(o1.getX().doubleValue()-o2.getX().doubleValue(),2)+Math.pow(o1.getY().doubleValue()-o2.getY().doubleValue(),2))-(o1.getRadius()+o2.getRadius())) < 0.0) {
                    return 0;
                }
                return 1;
            }
        });
        for (int i = 0 ; i < N ; i++) {
            Agent a = new Agent(new BigDecimal(dynamicInfo.get(i).first), new BigDecimal(dynamicInfo.get(i).second),0.0,0.0,staticInfo.get(i).first);
            if(!aux.add(a))
                overlaped++;
        }
        System.out.println("Found "+overlaped+" overlaped entities. Entities added = "+(N-overlaped)+".");
        return aux;
    }

    private void createGrid() {
        double cellSize = this.L/this.M;
        this.grid = new Cell[this.M][this.M];
        for (int i = 0; i < this.M; i++) {
            for (int j = 0; j < this.M; j++) {
                this.grid[i][j] = new Cell();
                int finalI = i;
                int finalJ = j;
                List<Agent> auxList = agents.stream()
                        .filter(a -> Math.floor(a.getX().doubleValue() / cellSize) == finalI
                                && Math.floor(a.getY().doubleValue() / cellSize) == finalJ)
                        .collect(Collectors.toList());
                Agent[] auxArray = new Agent[auxList.size()];
                for (int k = 0; k < auxList.size(); k++) {
                    auxArray[k] = auxList.get(k);
                }
                this.grid[i][j].addAgents(auxArray);
            }
        }
    }

    public void printGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j].getAgents().size() + " | ");
            }
            System.out.println("\n");
        }
    }

}
