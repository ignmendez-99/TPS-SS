package ar.edu.itba.ss.tp1.parsers;

import ar.edu.itba.ss.tp1.models.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DynamicParser {

    // Methods
    public static List<Pair<Double, Double>> dynamicParsing (String pathToFile){
        Scanner input = null;
        List<Pair<Double, Double>> list = new ArrayList<>();
        try {
            File file = new File(pathToFile);
            input = new Scanner(file);

            input.nextLine();

            while(input.hasNext()) {
                List<Double> numbers = Arrays.stream(input.nextLine().split("\\s\\s\\s")).filter(s -> !s.equals("")).map(Double::valueOf).collect(Collectors.toList());
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
