package ar.edu.itba.ss.tp2.models;

import ar.edu.itba.ss.tp1.models.Pair;


import java.util.List;
import java.util.function.IntBinaryOperator;

public class Environment2D {

    // Variables
    private int [][] env;
    // Para casos random hay que indicar que ratio de particulas tienen chances
    // de estar vivas.
    private final Double lifeExpectancy;
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
        // TODO: faltaria popular la matriz de manera random
        neighbourType = NeighbourType.VON_NEUMANN;
        r = 2; // TODO: revisar este valor
        this.x = x;
        this.y = y;
        this.lifeExpectancy = lifeExpectancy;
        this.endCondition = endCondition;
    }
    public Environment2D(List<Pair<Integer, Integer>> sInfo, Double endCondition) {
        this.env = new int[sInfo.get(0).first][sInfo.get(0).second];
        if(sInfo.get(0).first == 0)
            neighbourType = NeighbourType.VON_NEUMANN;
        else
            neighbourType = NeighbourType.MOORE;
        r = sInfo.get(0).second;
        x = sInfo.get(1).first;
        y = sInfo.get(1).second;
        this.lifeExpectancy = -1.0;
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

    // while ( se puede evolucionar )
    //  evolve()
    //  ==> lo aniado al archivo para ovito

    public void simulate () {
        while( canEvolve() ) {
            evolve();
        }
    }

    public boolean canEvolve() {
        double percentage = ((double) usedCells / (x*y))*100;
        return percentage < endCondition;
    }

    public void evolve () {
        int [][] futureEnv = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                // Reglas de la bibliografía

            }
        }
        env = futureEnv;
    }

    // for i = 0 ... ==> recorro filas
    //     for j = r - i ; j < 2 * r - j ... ==> recorro cols
    // . . . . * . . . .
    // . . . * * * . . .
    // . . * * * * * . .
    // . * * * * * * * .
    // * * * * * * * * *

}
