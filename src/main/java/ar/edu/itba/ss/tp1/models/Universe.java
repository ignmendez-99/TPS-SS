package ar.edu.itba.ss.tp1.models;

import ar.edu.itba.ss.tp1.parsers.OutputParser;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Universe {

    //Variables
    private final Integer N;
    private final Double L;
    private final Integer M;
    private final Boolean periodic;
    private final Double RC;
    private final Set<Agent> agents;

    private Cell[][] grid;
    private final Map<String, List<String>> particleWithNeighbours;
    private final Map<String, Pair<Agent, Agent>> relationHashMap;

    private final Long startTime;
    private Long endTime;

    // Constructor
    public Universe(List<Pair<Double, Double>> staticInfo, List<Pair<Double, Double>> dynamicInfo, Integer m, Boolean periodic, Double rc) {
        startTime = System.currentTimeMillis();
        N = staticInfo.get(0).first.intValue();
        L = staticInfo.get(0).second;
        staticInfo.remove(0);
        M = m;
        RC = rc;
        agents = populate(staticInfo, dynamicInfo);
        this.periodic = periodic;
        particleWithNeighbours = new HashMap<>();
        relationHashMap = new HashMap<>();
        createGrid();
    }

    // Constructor for when we have random agents
    public Universe(Set<Agent> agents, Integer n, Integer m, Double l, Boolean periodic, Double rc) {
        startTime = System.currentTimeMillis();
        N = n;
        L = l;
        M = m;
        RC = rc;
        this.agents = agents;
        this.periodic = periodic;
        particleWithNeighbours = new HashMap<>();
        relationHashMap = new HashMap<>();
        createGrid();
    }

    // Methods
    private Set<Agent> populate(List<Pair<Double, Double>> staticInfo, List<Pair<Double, Double>> dynamicInfo ){
        int overlaped = 0;
        Set<Agent> aux= new TreeSet<>(new Comparator<Agent>() {
            @Override
            public int compare(Agent o1, Agent o2) {
                if(Objects.equals(o1.getX(), o2.getX()) && Objects.equals(o1.getY(), o2.getY())) {
                    // Same position ==> same particle
                    return 0;
                }
                double x1 = o1.getX().doubleValue();
                double y1 = o1.getY().doubleValue();
                double x2 = o2.getX().doubleValue();
                double y2 = o2.getY().doubleValue();
                if(Point2D.distance(x1, y1, x2, y2) - (o1.getRadius()+o2.getRadius()) < 0.0) {
                    // If both particles intersect ==> same particle
                    return 0;
                }
                return 1;
            }
        });
        for (int i = 0 ; i < N ; i++) {
            Agent a = new Agent(
                    new BigDecimal(dynamicInfo.get(i).first),
                    new BigDecimal(dynamicInfo.get(i).second),
                    0.0,0.0,
                    staticInfo.get(i).first);
            if(!aux.add(a))
                overlaped++;
        }
        System.out.println("Found " + overlaped + " overlaped entities. Entities added = " + (N-overlaped) + ".");
        return aux;
    }

    private void createGrid() {
        double cellSize = L/M;
        grid = new Cell[M][M];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                grid[i][j] = new Cell();
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
                grid[i][j].addAgents(auxArray);
            }
        }
    }

    public void calculateDistances(){
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {

                // CENTRAL ==> No tiene verificaciones
                Cell centerCell = grid[i][j];

                // UPPER RIGHT ==> Tiene verificaciones
                Cell rightUpperCell;
                PeriodicType rightUpperCellType;
                if ((i - 1) < 0 && (j + 1 <= M-1) && periodic) {
                    rightUpperCell = grid[M-1][j + 1];
                    rightUpperCellType = PeriodicType.UPPER;
                } else if ((i - 1 >= 0) && (j + 1 > M-1) && periodic) {
                    rightUpperCell = grid[i - 1][0];
                    rightUpperCellType = PeriodicType.RIGHT;
                } else if ((i - 1 < 0) && (j + 1 > M-1) && periodic) {
                    rightUpperCell = grid[M-1][0];
                    rightUpperCellType = PeriodicType.UPPER_RIGHT;
                } else if(((i-1 < 0) || (j+1>M-1)) && !periodic) {
                    rightUpperCell = null;
                    rightUpperCellType = PeriodicType.NO_PERIODIC;
                }else {
                    rightUpperCell = grid[i - 1][j + 1];
                    rightUpperCellType = PeriodicType.NO_PERIODIC;
                }

                // RIGHT ==> Tiene verificaciones
                Cell rightCell;
                PeriodicType rightCellType;
                if ((j+1)>M-1 && periodic) {
                    rightCell = grid[i][0];
                    rightCellType = PeriodicType.RIGHT;
                } else if ((j+1)>M-1 && !periodic) {
                    rightCell = null;
                    rightCellType = PeriodicType.NO_PERIODIC;
                } else {
                    rightCell = grid[i][j + 1];
                    rightCellType = PeriodicType.NO_PERIODIC;
                }

                // RIGHT BOTTOM ==> Tiene verificaciones
                Cell rightBottomCell;
                PeriodicType rightBottomCellType;
                if((i+1 > M-1) && (j+1) <= M-1 && periodic) {
                    rightBottomCell = grid[0][j + 1];
                    rightBottomCellType = PeriodicType.BOTTOM;
                } else if((i+1 <= M-1) && (j+1)>M-1 && periodic) {
                    rightBottomCell = grid[i + 1][0];
                    rightBottomCellType = PeriodicType.RIGHT;
                } else if((i+1 > M-1) && (j+1)>M-1 && periodic) {
                    rightBottomCell = grid[0][0];
                    rightBottomCellType = PeriodicType.BOTTOM_RIGHT;
                } else if(((i+1>M-1) || (j+1>M-1)) && !periodic) {
                    rightBottomCell = null;
                    rightBottomCellType = PeriodicType.NO_PERIODIC;
                } else {
                    rightBottomCell = grid[i + 1][j + 1];
                    rightBottomCellType = PeriodicType.NO_PERIODIC;
                }

                // BOTTOM ==> Tiene verificaciones
                Cell bottomCell;
                PeriodicType bottomCellType;
                if((i+1) > M-1 && periodic) {
                    bottomCell = grid[0][j];
                    bottomCellType = PeriodicType.BOTTOM;
                } else if ((i+1 > M-1) && !periodic) {
                    bottomCell = null;
                    bottomCellType = PeriodicType.NO_PERIODIC;
                } else {
                    bottomCell = grid[i + 1][j];
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
        endTime = System.currentTimeMillis();
        OutputParser op = new OutputParser();

        op.buildOutput(particleWithNeighbours, "output", endTime-startTime);
        //op.buildOutput2(particleWithNeighbours, "output", endTime-startTime, N, "66", agents);
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
                String relationId1 = agent.getId() + " " + a.getId();
                String relationId2 = a.getId() + " " + agent.getId();
                if (!agent.getId().equals(a.getId())) {
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
                                        a.getX().doubleValue(),
                                        a.getY().doubleValue() + L);
                                break;
                            case BOTTOM:
                                dist = Point2D.distance(
                                        agent.getX().doubleValue(),
                                        agent.getY().doubleValue(),
                                        a.getX().doubleValue() + L,
                                        a.getY().doubleValue());
                                break;
                            case UPPER:
                                dist = Point2D.distance(
                                        agent.getX().doubleValue(),
                                        agent.getY().doubleValue(),
                                        a.getX().doubleValue() - L,
                                        a.getY().doubleValue());
                                break;
                            case UPPER_RIGHT:
                                dist = Point2D.distance(
                                        agent.getX().doubleValue(),
                                        agent.getY().doubleValue(),
                                        a.getX().doubleValue() - L,
                                        a.getY().doubleValue() + L);
                                break;
                            case BOTTOM_RIGHT:
                                dist = Point2D.distance(
                                        agent.getX().doubleValue(),
                                        agent.getY().doubleValue(),
                                        a.getX().doubleValue() + L,
                                        a.getY().doubleValue() + L);
                                break;
                        }
                        if (dist - agent.getRadius() - a.getRadius() <= RC) {
                            auxList.add(a.getId());
                            relationHashMap.put(agent.getId() + " " + a.getId(), new Pair<>(agent, a));
                            List<String> neighbourList = particleWithNeighbours.get(a.getId());
                            if(neighbourList == null){
                                neighbourList = new ArrayList<>();
                            }
                            neighbourList.add(agent.getId());
                            particleWithNeighbours.put(a.getId(), neighbourList);
                        }
                    }
                }
            }

            if(!auxList.isEmpty())
                particleWithNeighbours.put(agent.getId(), auxList);
        }
    }

    /**
     * Printea la grid para que sea mas facil debuggear
     */
    public void printGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                System.out.print(grid[i][j].getAgents().size() + " | ");
            }
            System.out.println("\n");
        }
    }

    public static Set<Agent> generateRandomAgents(Integer n, Double l, Double radius){
        Random random = new Random();
        Set<Agent> aux= new TreeSet<>(new Comparator<Agent>() {
            @Override
            public int compare(Agent o1, Agent o2) {
                if(Objects.equals(o1.getX(), o2.getX()) && Objects.equals(o1.getY(), o2.getY())) {
                    // Same position ==> same particle
                    return 0;
                }
                double x1 = o1.getX().doubleValue();
                double y1 = o1.getY().doubleValue();
                double x2 = o2.getX().doubleValue();
                double y2 = o2.getY().doubleValue();
                if(Point2D.distance(x1, y1, x2, y2) - (o1.getRadius()+o2.getRadius()) < 0.0) {
                    // If both particles intersect ==> same particle
                    return 0;
                }
                return 1;
            }
        });
        double x, y;
        while(aux.size()!=n) {
            x = random.nextDouble() * l;
            y = random.nextDouble() * l;
            Agent newAgent = new Agent(
                    new BigDecimal(x),
                    new BigDecimal(y),
                    0.0,0.0,
                    radius);
            if (!aux.contains(newAgent)) {
                aux.add(newAgent);
            }
        }
        return aux;
    }
}
