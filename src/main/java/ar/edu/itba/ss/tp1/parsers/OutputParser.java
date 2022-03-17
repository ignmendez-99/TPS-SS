package ar.edu.itba.ss.tp1.parsers;

import ar.edu.itba.ss.tp1.models.Agent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OutputParser {

    /**
     * Escribe en un archivo una lista de particulas y sus vecinas
     */
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

    /**
     * Escribe en un archivo.xyz toda la informacion necesaria para visualizar la lista de particulas, la particula "FOCO", y las vecinas de "FOCO"
     */
    public void buildOutput2 (Map<String, List<String>> neighbours, String fileName, Long eTime, Integer N, String myParticleId, Set<Agent> agents) {
        List<String> neighboursOfMyParticle = neighbours.get(myParticleId) != null ? neighbours.get(myParticleId) : new ArrayList<>();
        try {
            File myObj = new File(fileName + ".xyz");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                try {
                    FileWriter myWriter = new FileWriter(fileName + ".xyz");
                    StringBuilder dump = new StringBuilder("" + N + "\n" + eTime + "\n");
                    for (Agent a : agents) {
                        if(a.getId().equals(myParticleId)) {
                            dump.append("Si");
                        } else if(neighboursOfMyParticle.contains(a.getId())) {
                            dump.append("Bl");
                        } else {
                            dump.append("Gr");
                        }
                        dump.append("        ").append(a.getX()).append("      ").append(a.getY()).append("      ").append("0      0.37\n");
                    }
                    myWriter.write(dump.toString());
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("File already exists.");
            }
        } catch(IOException e) {
            System.out.println("No pude crear el archivo.");
            e.printStackTrace();
        }
    }

}
