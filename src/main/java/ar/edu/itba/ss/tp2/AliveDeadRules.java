package ar.edu.itba.ss.tp2;

public class AliveDeadRules {
    //2d
    // restrictivas 3-5 3-5 = 5k
    // medio 2-6 2-6= 16k
    // poco -> 1-7 1-7 = 28k

    // 3d
    // restrictivas 15-9 15-9 11k
    // medio 16-9 16-9 56k
    // poco 8-17 8-17 132k y muerio it 85

    private final static int aliveUpperLimit_2D = 5;
    private final static int aliveBottomLimit_2D = 3;
    private final static int deadUpperLimit_2D = 5;
    private final static int deadBottomLimit_2D = 3;

    private final static int aliveUpperLimit_3D = 15;
    private final static int aliveBottomLimit_3D = 9;
    private final static int deadUpperLimit_3D = 15;
    private final static int deadBottomLimit_3D = 9;

    public static int checkRules2D(int currentState, int aliveNeighbours) {
        if(currentState == 1) {
            if(aliveNeighbours > aliveUpperLimit_2D || aliveNeighbours < aliveBottomLimit_2D) {
                // Estoy vivo, pero me muero porque hay mucha gente o muy poca gente
                return 0;
            } else {
                // Estoy comodo en el centro del rango. Me quedo vivo
                return 1;
            }
        } else {
            if(aliveNeighbours > deadUpperLimit_2D || aliveNeighbours < deadBottomLimit_2D) {
                // Estoy muerto y me quedo muerto porque hay mucha gente o muy poca gente
                return 0;
            } else {
                // Estoy muerto, pero revivo ya que estoy en el centro del rango
                return 1;
            }
        }
    }

    public static int checkRules3D(int currentState, int aliveNeighbours) {
        if(currentState == 1) {
            if(aliveNeighbours > aliveUpperLimit_3D || aliveNeighbours < aliveBottomLimit_3D) {
                // Estoy vivo, pero me muero porque hay mucha gente o muy poca gente
                return 0;
            } else {
                // Estoy comodo en el centro del rango. Me quedo vivo
                return 1;
            }
        } else {
            if(aliveNeighbours > deadUpperLimit_3D || aliveNeighbours < deadBottomLimit_3D) {
                // Estoy muerto, pero revivo ya que hay mucha gente cerca mio, o muy poca gente cerca mio
                return 0;
            } else {
                // Estoy muerto y me quedo muerto
                return 1;
            }
        }
    }
}
