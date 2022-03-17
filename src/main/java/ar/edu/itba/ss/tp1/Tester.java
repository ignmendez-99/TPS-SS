package ar.edu.itba.ss.tp1;

public class Tester {

    public static void main(String[] args) {
        Integer[] Ms = new Integer[]{1, 5, 10, 20, 50};
        Integer[] Ns = new Integer[]{100, 1000, 5000, 10000, 30000, 100000};
        for (Integer m : Ms) {
            for (Integer n : Ns) {
                CellIndexMethod method = new CellIndexMethod(m, n);
                method.run();
            }
        }
    }
}
