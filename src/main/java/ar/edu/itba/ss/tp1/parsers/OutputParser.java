package ar.edu.itba.ss.tp1.parsers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OutputParser {

    public boolean buildOutput (Map<String, List<String>> neighbours, String fileName, Long eTime) {
        try {
            File myObj = new File(fileName + ".csv");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                try {
                    FileWriter myWriter = new FileWriter(fileName + ".csv");
                    //myWriter.write("Files in Java might be tricky, but it is fun enough!");
                    //myWriter.write(eTime.toString()+"\n");
                    for (String k: neighbours.keySet()) {
                        String dump = "";
                        int i = 0;
                        for(String id : neighbours.get(k)) {
                            if (i == 0){
                                i++;
                                dump += id;
                            } else {
                                dump += "    " + id;
                            }
                        }
                        myWriter.write("[" + k + "    " + dump + "]\n");
                    }
                    //myWriter.write(("---------\n"));
                    //myWriter.write(eTime.toString()+";\n");
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                    return true;
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                    return false;
                }
            } else {
                System.out.println("File already exists.");
                return false;
            }
        } catch(IOException e) {
            System.out.println("No pude crear el archivo.");
            e.printStackTrace();
            return false;
        }
    }

}
