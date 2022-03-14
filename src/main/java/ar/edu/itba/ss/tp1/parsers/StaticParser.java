package ar.edu.itba.ss.tp1.parsers;



import ar.edu.itba.ss.tp1.models.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class StaticParser {

    // Methods
    public static List<Pair<Double, Double>> staticParsing (String pathToFile){
        Scanner input = null;

        List<Pair<Double, Double>> list = new ArrayList<>();
        try {
            File file = new File(pathToFile);
            input = new Scanner(file);

            Double N = Arrays.stream(input.nextLine().split("\\s\\s\\s\\s")).filter(s -> !s.equals("")).map(Double::valueOf).collect(Collectors.toList()).get(0);
            Double L = Arrays.stream(input.nextLine().split("\\s\\s\\s\\s")).filter(s -> !s.equals("")).map(Double::valueOf).collect(Collectors.toList()).get(0);
            Pair<Double, Double> p = new Pair<>(N,L);
            list.add(p);

            while(input.hasNext()) {
                List<Double> numbers = Arrays.stream(input.nextLine().split("\\s\\s\\s\\s")).filter(s -> !s.equals("")).map(Double::valueOf).collect(Collectors.toList());
                Pair<Double, Double> pair = new Pair<>(numbers.get(0), numbers.get(1));
                list.add(pair);
            }
        } catch(FileNotFoundException e) {
            System.out.println("I could not read the file :(");
            e.printStackTrace();
        } finally {
            if(input != null)
                input.close();
        }
        return list;
    }
}
