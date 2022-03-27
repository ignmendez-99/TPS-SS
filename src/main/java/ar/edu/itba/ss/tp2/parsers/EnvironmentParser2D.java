package ar.edu.itba.ss.tp2.parsers;

import ar.edu.itba.ss.tp1.models.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EnvironmentParser2D {

    // Methods
    public static List<Pair<Integer, Integer>> staticParsing (String pathToFile){
        Scanner input = null;
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        try {
            File file = new File(pathToFile);
            input = new Scanner(file);
            while(input.hasNext()) {
                List<Integer> numbers = Arrays.stream(input.nextLine().split("\\s")).filter(s -> !s.equals("")).map(Integer::valueOf).collect(Collectors.toList());
                Pair<Integer, Integer> pair = new Pair<>(numbers.get(0), numbers.get(1));
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
