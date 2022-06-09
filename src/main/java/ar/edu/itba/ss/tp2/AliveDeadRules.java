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
    private final static int[] rules2D_1 = {3, 2, 3, 3}; //default
    private final static int[] rules2D_2 = {6, 3, 6, 3}; //poco restrictiva
    private final static int[] rules2D_3 = {4, 4, 4, 3}; //mata programas
    
    private final static int[] rules3D_1 = {3, 2, 3, 3}; //poco restrictiva se va a la mierda
    private final static int[] rules3D_2 = {13, 5, 13, 12}; 
    private final static int[] rules3D_3 = {19, 10, 18, 10}; //hace q mueran ni muy rapido ni muy lento

    public static int checkRules2D(int currentState, int aliveNeighbours) {
        int[] rules = rules2D_2;
        if(currentState == 1) {
            if(aliveNeighbours > rules[0] || aliveNeighbours < rules[1]) {
                // Estoy vivo, pero me muero porque hay mucha gente o muy poca gente
                return 0;
            } else {
                // Estoy comodo en el centro del rango. Me quedo vivo
                return 1;
            }
        } else {
            if(aliveNeighbours > rules[2] || aliveNeighbours < rules[3]) {
                // Estoy muerto y me quedo muerto porque hay mucha gente o muy poca gente
                return 0;
            } else {
                // Estoy muerto, pero revivo ya que estoy en el centro del rango
                return 1;
            }
        }
    }

    public static int checkRules3D(int currentState, int aliveNeighbours) {
        int[] rules = rules3D_1;
        if(currentState == 1) {
            if(aliveNeighbours > rules[0] || aliveNeighbours < rules[1]) {
                // Estoy vivo, pero me muero porque hay mucha gente o muy poca gente
                return 0;
            } else {
                // Estoy comodo en el centro del rango. Me quedo vivo
                return 1;
            }
        } else {
            if(aliveNeighbours > rules[2] || aliveNeighbours < rules[3]) {
                // Estoy muerto, pero revivo ya que hay mucha gente cerca mio, o muy poca gente cerca mio
                return 0;
            } else {
                // Estoy muerto y me quedo muerto
                return 1;
            }
        }
    }
}
