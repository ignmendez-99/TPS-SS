package ar.edu.itba.ss.tp2.models;

import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp2.AliveDeadRules;
import ar.edu.itba.ss.tp2.ShufflingUtils;
import ar.edu.itba.ss.tp2.parsers.OutputParser;

import java.util.List;
import java.util.Random;
import static ar.edu.itba.ss.tp2.InputFileCreator.M_3D;

public class Environment3D {
    private int [][][] env;
    private final Integer x;
    private final Integer y;
    private final Integer z;
    private Integer usedCells;

    private boolean reachedBorder = false;

    private final NeighbourType neighbourType;
    private final int r;
    private final int center;


    public Environment3D(List<Pair<Integer, Pair<Integer, Integer>>> sInfo) {
        x = sInfo.get(1).first;
        y = sInfo.get(1).second.first;
        z = sInfo.get(1).second.second;
        this.env = new int[x][y][z];
        if(sInfo.get(0).first == 0)
            neighbourType = NeighbourType.VON_NEUMANN;
        else
            neighbourType = NeighbourType.MOORE;
        r = sInfo.get(0).second.first;
        populate(sInfo);
        center = x/2;
    }

    private void populate(List<Pair<Integer, Pair<Integer, Integer>>> sInfo) {
        this.usedCells = 0;
        for (int i = 2; i < sInfo.size(); i++) {
            usedCells++;
            int x = sInfo.get(i).first;
            int y = sInfo.get(i).second.first;
            int z = sInfo.get(i).second.second;
            this.env[x][y][z] = 1;
        }
    }

    public void simulate (int iterations) {
        // El algoritmo empieza acá
        long startTime = System.currentTimeMillis();

        // Vuelco a archivo la matriz inicial
        OutputParser.createCleanFile();
        OutputParser.createCleanPythonFile(usedCells);
        OutputParser.writeMatrix3DToFile(env, x, y, z, 0, usedCells, center);
        OutputParser.writeAux(0,usedCells, getPatternRadius(env, x,y,z),0);

        int i = 0;
        int change_state = 0;
        while( i < iterations && !reachedBorder ) {
            if(usedCells <= 0) {
                OutputParser.writeMatrix3DToFile(env, x, y, z, System.currentTimeMillis() - startTime, usedCells, center);
                System.out.println("All particles are dead in iteration " + i);
                if(i == 0)
                    OutputParser.writeAux(i,usedCells, 0, 0);
                break;
            }
            change_state = evolve();
            i++;
            if(usedCells > 0){
                double rad = getPatternRadius(env, x, y, z);
                OutputParser.writeAux(i, usedCells, rad, change_state);
                OutputParser.writeMatrix3DToFile(env, x, y, z, System.currentTimeMillis() - startTime, usedCells, center);
            }
            // System.out.println("Finished iteration " + i);
            if(reachedBorder) {
                System.out.println("System reached border in iteration " + i);
            }
        }
    }

    private static double getPatternRadius(int[][][] env, int x, int y, int z){
        double max = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    if(env[i][j][k] == 1){
                        double dist = Math.sqrt((Math.pow(i-(M_3D/2.0),2) + Math.pow(j-(M_3D/2.0),2) + Math.pow(k-(M_3D/2.0), 2)));
                        if(dist > max)
                            max = dist;
                    }
                }
            }
        }
        return max;
    }

    public int evolve () {
        //usedcells la reinicio
        usedCells=0;
        int change_state = 0;
        int [][][] futureEnv = new int[x][y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    if (neighbourType == NeighbourType.VON_NEUMANN) {
                        futureEnv[i][j][k] = VonNeumann(i, j, k);
                    } else if (neighbourType == NeighbourType.MOORE) {
                        if(futureEnv[i][j][k] != env[i][j][k])
                            change_state++;
                        futureEnv[i][j][k] = Moore(i, j, k);
                    }
                }
            }
        }
        env = futureEnv;
        return change_state;
    }

    public int VonNeumann(int cx, int cy, int cz) {
        int xLimit = Math.min((cx + r), this.x-1);
        int aux = 0;
        int aliveCells = 0;
        for (int i = cx - r; i <= xLimit; i++) {
            if(i >= 0){
                aliveCells += layerAnalizer(cy, cz, aux, i);
            }
            if(i >= cx) {
                aux--;
            } else {
                aux++;
            }
        }
        int deadOrAlive = AliveDeadRules.checkRules3D(env[cx][cy][cz], aliveCells);
        if(deadOrAlive == 1) {
            if(cx == 0 || cx == x-1 || cy == 0 || cy == y-1 || cz == 0 || cz == z-1){
                reachedBorder = true;
            }
            usedCells++;
        }
        return deadOrAlive;
    }

    public int layerAnalizer(int cy, int cz, int rp, int cx) {
        int [][][] auxm = new int[x][y][z];
        int aliveCells = 0;
        int rowLimit = Math.min((cy + rp), this.y-1);
        int aux = 0;
        for (int i = cy - rp; i <= rowLimit; i++) {
            if(i >= 0) {
                for (int j = cz - aux; j <= Math.min(cz + aux, this.z-1); j++) {
                    if (j >= 0 && !(i == cy && j == cz  && rp==r)) {
                        if(env[cx][i][j]==1){
                            aliveCells++;
                        }
                    }
                }
            }
            if(i >= cy){
                aux--;
            } else {
                aux++;
            }
        }
        return aliveCells;
    }

    public int Moore(int cx, int cy, int cz){
        int aliveCells = 0;
        int xLimit = Math.min((cx + r), this.x-1);
        int yLimit = Math.min((cy + r), this.y-1);
        int zLimit = Math.min((cz + r), this.z-1);
        for (int i = cx-r; i <= xLimit; i++) {
            if(i >= 0){
                for (int j = cy-r; j <= yLimit; j++) {
                    if(j>=0){
                        for (int k = cz-r; k <= zLimit; k++) {
                             if(k >= 0 && !(i==cx && j==cy && k==cz)){
                                 if(env[i][j][k]==1){
                                    aliveCells++;
                                 }
                             }
                        }
                    }
                }
            }
        }

        int deadOrAlive = AliveDeadRules.checkRules3D(env[cx][cy][cz], aliveCells);
        if(deadOrAlive == 1) {
            if(cx == 0 || cx == x-1 || cy == 0 || cy == y-1 || cz == 0 || cz == z-1){
                reachedBorder = true;
            }
            usedCells++;
        }
        return deadOrAlive;
    }
}
