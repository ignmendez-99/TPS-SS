package ar.edu.itba.ss.tp1;

import java.util.*;
import java.util.stream.Collectors;

public class Universe {

    //Variables
    private Integer N;
    private Double L;
    private Integer M;
    private Boolean periodic;
    private Double RC;
    private Set<Agent> agents;

    private Cell[][] grid;

    // Constructor
    public Universe(Integer n, Double l, Integer m, Boolean periodic, Double rc) {
        N = n;
        L = l;
        M = m;
        RC = rc;
        agents = populate(n, l, 0.2);
        this.periodic = periodic;

        createGrid();
    }

    // Methods
    private static Set<Agent> populate(Integer n, Double l, Double radius){
        Random random = new Random();
        Set<Agent> aux= new TreeSet<>(new Comparator<Agent>() {
            @Override
            public int compare(Agent o1, Agent o2) {
                if(Objects.equals(o1.getX(), o2.getX()) && Objects.equals(o1.getY(), o2.getY())) {
                    return 0;
                }
                if((Math.sqrt(Math.pow(o1.getX()-o2.getX(),2)+Math.pow(o1.getY()-o2.getY(),2))-(o1.getRadius()+o2.getRadius())) < 0.0) {
                    return 0;
                }
                return 1;
            }
        });
        double x, y;
        while(aux.size()!=n) {
            x = random.nextDouble() * l;
            y = random.nextDouble() * l;
            Agent newAgent = new Agent(x,y,0.0,0.0, radius);
            if (!aux.contains(newAgent)) {
                aux.add(newAgent);
            }
        }
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
                        .filter(a -> Math.floor(a.getX() / cellSize) == finalI
                                && Math.floor(a.getY() / cellSize) == finalJ)
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
                System.out.print(grid[i][j].getAgents().size() + " // ");
            }
            System.out.println("\n");
        }
    }

}
