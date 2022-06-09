package ar.edu.itba.ss.tp2.models;

import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp2.AliveDeadRules;
import ar.edu.itba.ss.tp2.parsers.OutputParser;

import java.util.List;

import static ar.edu.itba.ss.tp2.InputFileCreator.M_2D;

public class Environment2D {

    // Variables
    private int [][] env;
    // Para casos random hay que indicar que ratio de particulas tienen chances
    // de estar vivas.
    private final Integer x;
    private final Integer y;
    private Integer usedCells;

    private final NeighbourType neighbourType;
    private final int r;

    private boolean reachedBorder = false;
    private final int center;


    public Environment2D(List<Pair<Integer, Integer>> sInfo) {
        x = sInfo.get(1).first;
        y = sInfo.get(1).second;
        this.env = new int[x][y];
        if(sInfo.get(0).first == 0)
            neighbourType = NeighbourType.VON_NEUMANN;
        else
            neighbourType = NeighbourType.MOORE;
        r = sInfo.get(0).second;
        populate(sInfo);
        center = x/2;
    }

    // Methods
    private void populate(List<Pair<Integer, Integer>> sInfo) {
        this.usedCells = 0;
        for (int i = 2; i < sInfo.size(); i++) {
            usedCells++;
            int x = sInfo.get(i).first;
            int y = sInfo.get(i).second;
            this.env[x][y] = 1;
        }
    }

    public void simulate (int iterations) {
        // El algoritmo empieza acá
        long startTime = System.currentTimeMillis();

        // Vuelco a archivo la matriz inicial
        OutputParser.createCleanFile();
        OutputParser.createCleanPythonFile(usedCells);
        OutputParser.writeMatrix2DToFile(env, x, y, 0, usedCells, center);

        int i = 0;
        while( i < iterations && !reachedBorder ) {
            if(usedCells <= 0) {
                OutputParser.writeMatrix2DToFile(env, x, y, System.currentTimeMillis() - startTime, usedCells, center);
                System.out.println("All particles are dead after iteration " + i);
                break;
            }
            evolve();
            i++;
            if(usedCells > 0){
                double radius = getPatternRadius(env, x,y);
                OutputParser.writeAux(i,usedCells, radius);
                OutputParser.writeMatrix2DToFile(env, x, y, System.currentTimeMillis() - startTime, usedCells, center);
            }
            System.out.println("Finished iteration " + i);
            if(reachedBorder) {
                System.out.println("System reached border in iteration " + i);
            }
        }
    }

    private static double getPatternRadius(int[][] env, int x, int y){
        double max = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if(env[i][j] == 1){
                    double dist = Math.sqrt((Math.pow(i-(M_2D/2.0),2) + Math.pow(j-(M_2D/2.0),2)));
                    if(dist > max)
                        max = dist;
                }
            }
        }
        return max;
    }

    public void evolve () {
        //usedcells la reinicio
        usedCells=0;
        int [][] futureEnv = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if(neighbourType == NeighbourType.VON_NEUMANN){
                    futureEnv[i][j] = VonNeumann(i, j);
                } else if(neighbourType == NeighbourType.MOORE){
                    futureEnv[i][j] = Moore(i,j);
                }
            }
        }
        env = futureEnv;
    }

    public void printMatrix(int [][] m){
        for (int k = 0; k < x; k++) {
            for (int l = 0; l < y; l++) {
                System.out.print(((m[k][l] == 0) ? "." : "*") + " ");
            }
            System.out.println();
        }
    }

    public int VonNeumann(int cx, int cy) {
        int aliveCells = 0;
        int rowLimit = Math.min((cx + r), this.x-1);
        int aux = 0;
        for (int i = cx - r; i <= rowLimit; i++) {
            if(i >= 0) {
                for (int j = cy - aux; j <= Math.min(cy + aux, this.y-1); j++) {
                    if (j >= 0 && !(i == cx && j == cy)) {
                        if(env[i][j]==1){
                            aliveCells++;
                        }
                    }
                }
            }
            if(i >= cx){
                aux--;
            } else {
                aux++;
            }
        }
        int deadOrAlive = AliveDeadRules.checkRules2D(env[cx][cy], aliveCells);
        if(deadOrAlive == 1) {
            if(cx == 0 || cx == x-1 || cy == 0 || cy == y-1){
                reachedBorder = true;
            }
            usedCells++;
        }
        return deadOrAlive;
    }

    public int Moore(int cx, int cy){
        int aliveCells = 0;
        int rowLimit = Math.min((cx + r), this.x-1);
        int colLimit = Math.min((cy + r), this.y-1);
        for (int i = cx-r; i <= rowLimit; i++) {
            if(i >= 0){
                for (int j = cy-r; j <= colLimit; j++) {
                    if(j>=0 && !(i==cx && j==cy)){
                        if(env[i][j]==1){
                            aliveCells++;
                        }
                    }
                }
            }
        }
        int deadOrAlive = AliveDeadRules.checkRules2D(env[cx][cy], aliveCells);
        if(deadOrAlive == 1) {
            if(cx == 0 || cx == x-1 || cy == 0 || cy == y-1){
                reachedBorder = true;
            }
            usedCells++;
        }
        return deadOrAlive;
    }

}
