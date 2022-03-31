package ar.edu.itba.ss.tp2.parsers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputParser {

    private static String fileName;

    public static void writeMatrixToFile(int[][] env, Integer x, Integer y) {
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                try {
                    FileWriter myWriter = new FileWriter(fileName);
                    // TODO FALTA DESARROLLO ACA, NACHO SE ENCARGA
//                    StringBuilder dump = new StringBuilder("" + N + "\n" + eTime + "\n");
//                    for (Agent a : agents) {
//                        if (a.getId().equals(myParticleId)) {
//                            dump.append("Si");
//                        } else if (neighboursOfMyParticle.contains(a.getId())) {
//                            dump.append("Bl");
//                        } else {
//                            dump.append("Gr");
//                        }
//                        dump.append("        ").append(a.getX()).append("      ").append(a.getY()).append("      ").append("0      0.37\n");
//                    }
//                    myWriter.write(dump.toString());
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("No pude crear el archivo.");
            e.printStackTrace();
        }
    }

    public static void setFileName(String fn) {
        fileName = fn;
    }
}
