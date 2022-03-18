package ar.edu.itba.ss.tp1;

public class Tester {

    public static void main(String[] args) {
        Integer[] Ms = new Integer[]{1, 5, 10, 20, 50};
        Integer[] Ns = new Integer[]{100, 500, 1000};
        for (Integer m : Ms) {
            for (Integer n : Ns) {
                System.out.println("\n<--- Para M="+m+" y N="+n+" --->\n");
                CellIndexMethod method = new CellIndexMethod(m, n);
                method.run();
            }
        }
    }
}
