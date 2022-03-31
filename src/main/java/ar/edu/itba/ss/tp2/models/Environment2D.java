package ar.edu.itba.ss.tp2.models;

import ar.edu.itba.ss.tp1.models.Pair;
import ar.edu.itba.ss.tp2.ShufflingUtils;
import ar.edu.itba.ss.tp2.parsers.OutputParser;

import java.util.List;
import java.util.Random;

public class Environment2D {

    // Variables
    private int [][] env;
    // Para casos random hay que indicar que ratio de particulas tienen chances
    // de estar vivas.
    private final Double endCondition;
    private final Integer x;
    private final Integer y;
    private Integer usedCells;

    private final NeighbourType neighbourType;
    private final int r;

    enum NeighbourType {
        VON_NEUMANN(),
        MOORE()
    }

    // Constructor RANDOM
    public Environment2D(Integer x, Integer y, Double lifeExpectancy, Double endCondition) {
        this.env = new int[x][y];
        this.x = x;
        this.y = y;
        populateRandom(lifeExpectancy);
        neighbourType = NeighbourType.MOORE;
        r = 2; // TODO: revisar este valor, ya que debería estar más parametrizado que esto
        this.endCondition = endCondition;
    }

    public Environment2D(List<Pair<Integer, Integer>> sInfo, Double endCondition) {
        x = sInfo.get(1).first;
        y = sInfo.get(1).second;
        this.env = new int[x][y];
        if(sInfo.get(0).first == 0)
            neighbourType = NeighbourType.VON_NEUMANN;
        else
            neighbourType = NeighbourType.MOORE;
        r = sInfo.get(0).second;
        this.endCondition = endCondition;
        populate(sInfo);
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

    /**
     * Primero llena la matriz con la cantidad necesaria de 1, para luego hacer shuffle
     */
    private void populateRandom(Double lifeExpectancy) {
        usedCells = 0;
        int numberOfCellsToActivate = (int) ((x*y) * (lifeExpectancy/100.0));
        for (int i = 0; i < numberOfCellsToActivate; i++) {
            env[i/x][i%y] = 1;
        }
        ShufflingUtils.shuffle(env, y, new Random());
        //printMatrix(env);
    }

    public void simulate () {
//        while( canEvolve() ) {
//            evolve();
//            // parseXYZ
//        }

        // Vuelco a archivo la matriz inicial
        OutputParser.writeMatrixToFile(env, x, y);
        int i = 0;
        while( i < 4 ) {
            printMatrix(env);
            evolve();
            i++;
            System.out.println("-----------------------------");
            // parseXYZ
        }
    }

    //todo: hay q ver como terminarlo cuando esta estacionario
    //todo: yo pense usando una variable booleana
    public boolean canEvolve() {
        double percentage = ((double) usedCells / (x*y))*100;
        return percentage < endCondition;
    }

    public void evolve () {
        //usedcells la reinicio
        usedCells=0;
        int [][] futureEnv = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                // Reglas de la bibliografía
                if(neighbourType == NeighbourType.VON_NEUMANN){
                    futureEnv[i][j] = VonNeumann(i, j);
                } else if(neighbourType == NeighbourType.MOORE){
                    futureEnv[i][j] = Moore(i,j);
                }
            }
        }
        //if(env.equals(futureEnv)
        env = futureEnv;
        OutputParser.writeMatrixToFile(env, x, y);
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
        if(aliveCells != 0)
            System.out.println("alive cells = " + aliveCells + " for cell: " + cx + "-" + cy);
        return checkRules(cx, cy, aliveCells);
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
        if(aliveCells != 0)
             System.out.println("alive cells = " + aliveCells + " for cell: " + cx + "-" + cy);
        //hasta aca tenemos la cantidad de alivecells ahora habria que chequear reglas.
        return checkRules(cx, cy, aliveCells);
    }

    public int checkRules(int cx, int cy, int aliveCells){
        if(env[cx][cy] == 0 && aliveCells == 3){
            usedCells++;
            return 1;
        }
        if(env[cx][cy] == 1 && (aliveCells == 2 || aliveCells == 3)){
            usedCells++;
            return 1;
        } else {
            return 0;
        }
    }

    // for i = 0 ... ==> recorro filas
    //     for j = r - i ; j < 2 * r - j ... ==> recorro cols
    // . . . . * . . . .
    // . . . * * * . . .
    // . . * * * * * . .
    // . * * * * * * * .
    // * * * * * * * * *
    // . * * * * * * * .
    // . . * * * * * . .
    // . . . * * * . . .
    // . . . . * . . . .

}
