package ar.edu.itba.ss.tp2;

public class AliveDeadRules {

    private final static int aliveUpperLimit_2D = 20;
    private final static int aliveBottomLimit_2D = 10;
    private final static int deadUpperLimit_2D = 20;
    private final static int deadBottomLimit_2D = 10;

    private final static int aliveUpperLimit_3D = 30;
    private final static int aliveBottomLimit_3D = 15;
    private final static int deadUpperLimit_3D = 50;
    private final static int deadBottomLimit_3D = 30;

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
