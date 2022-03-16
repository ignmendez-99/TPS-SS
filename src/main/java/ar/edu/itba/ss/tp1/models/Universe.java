package ar.edu.itba.ss.tp1.models;

import ar.edu.itba.ss.tp1.models.Agent;
import ar.edu.itba.ss.tp1.models.Cell;

import java.awt.geom.Point2D;
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
    private Map<String, List<String>> particleWithNeighbours;
    private Map<String, Pair<Agent, Agent>> relationHashMap;

    // Constructor
    public Universe(List<Pair<Double, Double>> staticInfo, List<Pair<Double, Double>> dynamicInfo, Integer m, Boolean periodic, Double rc) {
        N = staticInfo.get(0).first.intValue();
        L = staticInfo.get(0).second;
        staticInfo.remove(0);
        M = m;
        RC = rc;
        agents = populate(staticInfo, dynamicInfo);
        this.periodic = periodic;
        this.particleWithNeighbours = new HashMap<>();
        this.relationHashMap = new HashMap<>();
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

    public void calculateDistances(){
        for (int i = 0; i < this.M; i++) {
            for (int j = 0; j < this.M; j++) {

                // CENTRAL ==> No tiene verificaciones
                Cell centerCell = this.grid[i][j];

                // UPPER RIGHT ==> Tiene verificaciones
                Cell rightUpperCell = null;
                PeriodicType rightUpperCellType;
                if ((i - 1) < 0 && (j + 1 <= this.M-1) && this.periodic) {
                    rightUpperCell = this.grid[this.M-1][j + 1];
                    rightUpperCellType = PeriodicType.UPPER;
                } else if ((i - 1 >= 0) && (j + 1 > this.M-1) && this.periodic) {
                    rightUpperCell = this.grid[i - 1][0];
                    rightUpperCellType = PeriodicType.RIGHT;
                } else if ((i - 1 < 0) && (j + 1 > this.M-1) && this.periodic) {
                    rightUpperCell = this.grid[this.M-1][0];
                    rightUpperCellType = PeriodicType.UPPER_RIGHT;
                } else if(((i-1 < 0) || (j+1>this.M-1)) && !this.periodic) {
                    rightUpperCell = null;
                    rightUpperCellType = PeriodicType.NO_PERIODIC;
                }else {
                    rightUpperCell = this.grid[i - 1][j + 1];
                    rightUpperCellType = PeriodicType.NO_PERIODIC;
                }

                // RIGHT ==> Tiene verificaciones
                Cell rightCell = null;
                PeriodicType rightCellType;
                if ((j+1)>this.M-1 && this.periodic) {
                    rightCell = this.grid[i][0];
                    rightCellType = PeriodicType.RIGHT;
                } else if ((j+1)>this.M-1 && !this.periodic) {
                    rightCell = null;
                    rightCellType = PeriodicType.NO_PERIODIC;
                } else {
                    rightCell = this.grid[i][j + 1];
                    rightCellType = PeriodicType.NO_PERIODIC;
                }

                // RIGHT BOTTOM ==> Tiene verificaciones
                Cell rightBottomCell = null;
                PeriodicType rightBottomCellType;
                if((i+1 > this.M-1) && (j+1) <= this.M-1 && this.periodic) {
                    rightBottomCell = this.grid[0][j + 1];
                    rightBottomCellType = PeriodicType.BOTTOM;
                } else if((i+1 <= this.M-1) && (j+1)>this.M-1 && this.periodic) {
                    rightBottomCell = this.grid[i + 1][0];
                    rightBottomCellType = PeriodicType.RIGHT;
                } else if((i+1 > this.M-1) && (j+1)>this.M-1 && this.periodic) {
                    rightBottomCell = this.grid[0][0];
                    rightBottomCellType = PeriodicType.BOTTOM_RIGHT;
                } else if(((i+1>this.M-1) || (j+1>this.M-1)) && !this.periodic) {
                    rightBottomCell = null;
                    rightBottomCellType = PeriodicType.NO_PERIODIC;
                } else {
                    rightBottomCell = this.grid[i + 1][j + 1];
                    rightBottomCellType = PeriodicType.NO_PERIODIC;
                }

                // BOTTOM ==> Tiene verificaciones
                Cell bottomCell = null;
                PeriodicType bottomCellType;
                if((i+1) > this.M-1 && this.periodic) {
                    bottomCell = this.grid[0][j];
                    bottomCellType = PeriodicType.BOTTOM;
                } else if ((i+1 > this.M-1) && !this.periodic) {
                    bottomCell = null;
                    bottomCellType = PeriodicType.NO_PERIODIC;
                } else {
                    bottomCell = this.grid[i + 1][j];
                    bottomCellType = PeriodicType.NO_PERIODIC;
                }

                for(Agent a : centerCell.getAgents()) {
                    // El de mi misma celda
                    calculateNeighbours(a, centerCell, PeriodicType.NO_PERIODIC);
                    // El de la derecha
                    if(rightCell != null)
                        calculateNeighbours(a, rightCell, rightCellType);
                    // El de abajo
                    if(bottomCell != null)
                        calculateNeighbours(a, bottomCell, bottomCellType);
                    // El de abajo a la derecha
                    if(rightBottomCell != null)
                        calculateNeighbours(a, rightBottomCell, rightBottomCellType);
                    // El de arriba a la derecha
                    if(rightUpperCell != null)
                        calculateNeighbours(a, rightUpperCell, rightUpperCellType);
                }
            }
        }
    }

    private enum PeriodicType {
        UPPER(),
        UPPER_RIGHT(),
        BOTTOM(),
        BOTTOM_RIGHT(),
        RIGHT(),
        NO_PERIODIC()
    }

    private void calculateNeighbours(Agent agent, Cell cell, PeriodicType type) {
        List<String> neighbours = particleWithNeighbours.get(agent.getId());
        List<String> auxList;
        if ( cell.getAgentQty() != 0) {
            if (neighbours == null) {
                auxList = new ArrayList<>();
            } else {
                auxList = neighbours;
            }
            for (Agent a : cell.getAgents()) {
                String relationId1 = agent.getId() + a.getId();
                String relationId2 = a.getId() + agent.getId();
                if ( agent.getId() != a.getId() ) {
                    if (relationHashMap.containsKey(relationId1) || relationHashMap.containsKey(relationId2)) {
                        // Ya sabemos que son vecinas, no hace falta calcular la distancia
                        auxList.add(a.getId());
                    } else {
                        // calcular distancia entre agent y cada "a"
                        double dist = 0;
                        switch (type) {
                            case NO_PERIODIC:
                                dist = Point2D.distance(
                                        agent.getX().doubleValue(),
                                        agent.getY().doubleValue(),
                                        a.getX().doubleValue(),
                                        a.getY().doubleValue());
                                break;
                            case RIGHT:
                                dist = Point2D.distance(
                                        agent.getX().doubleValue(),
                                        agent.getY().doubleValue(),
                                        a.getX().doubleValue() + this.L,
                                        a.getY().doubleValue());
                                break;
                            case BOTTOM:
                                dist = Point2D.distance(
                                        agent.getX().doubleValue(),
                                        agent.getY().doubleValue(),
                                        a.getX().doubleValue(),
                                        a.getY().doubleValue() + this.L);
                                break;
                            case UPPER:
                                dist = Point2D.distance(
                                        agent.getX().doubleValue(),
                                        agent.getY().doubleValue(),
                                        a.getX().doubleValue(),
                                        a.getY().doubleValue() - this.L);
                                break;
                            case UPPER_RIGHT:
                                dist = Point2D.distance(
                                        agent.getX().doubleValue(),
                                        agent.getY().doubleValue(),
                                        a.getX().doubleValue() + this.L,
                                        a.getY().doubleValue() - this.L);
                                break;
                            case BOTTOM_RIGHT:
                                dist = Point2D.distance(
                                        agent.getX().doubleValue(),
                                        agent.getY().doubleValue(),
                                        a.getX().doubleValue() + this.L,
                                        a.getY().doubleValue() + this.L);
                                break;
                        }
                        if (dist < this.RC) {
                            auxList.add(a.getId());
                            relationHashMap.put(agent.getId() + a.getId(), new Pair<>(agent, a));
                        }
                    }
                }
            }
            if(!auxList.isEmpty())
                particleWithNeighbours.put(agent.getId(), auxList);
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

    public Map<String, List<String>> getParticleWithNeighbours() {
        return this.particleWithNeighbours;
    }
}
