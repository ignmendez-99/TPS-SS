package ar.edu.itba.ss.tp2.models;

import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp2.parsers.OutputParser;

import java.util.List;

public class Environment3D {
    private int [][][] env;
    private final Double endCondition;
    private final Integer x;
    private final Integer y;
    private final Integer z;
    private Integer usedCells;

    private final NeighbourType neighbourType;
    private final int r;

    enum NeighbourType {
        VON_NEUMANN(),
        MOORE()
    }

    // Constructor RANDOM
    public Environment3D(Integer x, Integer y, Integer z, Double lifeExpectancy, Double endCondition) {
        this.env = new int[x][y][z];
        this.x = x;
        this.y = y;
        this.z = z;
        //populateRandom(lifeExpectancy);
        neighbourType = NeighbourType.MOORE;
        r = 2; // TODO: revisar este valor, ya que debería estar más parametrizado que esto
        this.endCondition = endCondition;
    }

    public Environment3D(List<Pair<Integer, Pair<Integer, Integer>>> sInfo, Double endCondition) {
        x = sInfo.get(1).first;
        y = sInfo.get(1).second.first;
        z = sInfo.get(1).second.second;
        this.env = new int[x][y][z];
        if(sInfo.get(0).first == 0)
            neighbourType = NeighbourType.VON_NEUMANN;
        else
            neighbourType = NeighbourType.MOORE;
        r = sInfo.get(0).second.first;
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println("z = " + z);
        System.out.println("r = " + r);
        System.out.println("Moo o Van = " + neighbourType);
        this.endCondition = endCondition;
        populate(sInfo);
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

    public void printMatrix(int [][] m){
        for (int k = 0; k < x; k++) {
            for (int l = 0; l < y; l++) {
                System.out.print(((m[k][l] == 0) ? "." : "*") + " ");
            }
            System.out.println();
        }
    }

    public void simulate () {
//        while( canEvolve() ) {
//            evolve();
//            // parseXYZ
//        }

        // Vuelco a archivo la matriz inicial
        //OutputParser.writeMatrixToFile(env, x, y);
        int i = 0;
        evolve();
        /*while( i < 4 ) {
            //printMatrix(env);
            evolve();
            i++;
            System.out.println("-----------------------------");
            // parseXYZ
        }

         */
    }

    public void evolve () {
        //usedcells la reinicio
        usedCells=0;
        int [][][] futureEnv = new int[x][y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    // Reglas de la bibliografía
                    if (neighbourType == NeighbourType.VON_NEUMANN) {
                        futureEnv[i][j][k] = VonNeumann(i, j, k);
                    } else if (neighbourType == NeighbourType.MOORE) {
                        futureEnv[i][j][k] = Moore(i, j, k);
                    }
                }
            }
        }
        env = futureEnv;
        //OutputParser.writeMatrixToFile(env, x, y);
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
        return checkRules(cx, cy, cz, aliveCells);
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
        //System.out.println("Estamos en la particula => " + cx + " " + cy + " " + cz);
        //System.out.println("cx:" + cx);
        //printMatrix(auxm[cx]);
        //if(aliveCells != 0)
        //    System.out.println("alive cells = " + aliveCells + " for cell: " + cx + "-" + cy);
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
        if(aliveCells != 0)
            System.out.println("Alive cells = " + aliveCells + " ==> cell: " + cx + "-" + cy + "-" + cz);
        //hasta aca tenemos la cantidad de alivecells ahora habria que chequear reglas.
        return checkRules(cx, cy, cz, aliveCells);
    }

    public int checkRules(int cx, int cy, int cz, int aliveCells){
        if(env[cx][cy][cz] == 0 && aliveCells == 3){
            usedCells++;
            return 1;
        }
        if(env[cx][cy][cz] == 1 && (aliveCells == 2 || aliveCells == 3)){
            usedCells++;
            return 1;
        } else {
            return 0;
        }
    }
}
