package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static int[][] Matrix_From_File(String filen) throws IOException {
        List<int[]> rows = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filen));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            int[] row = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                row[i] = Integer.parseInt(parts[i]);
            }
            rows.add(row);
        }
        reader.close();
        return rows.toArray(new int[0][]);
    }

    public static void writeMatrixToFile(int[][] matrix, String filen) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filen));
        for (int[] row : matrix) {
            for (int j = 0; j < row.length; j++) {
                writer.write(String.valueOf(row[j]));
                if (j < row.length - 1) writer.write(" ");
            }
            writer.newLine();
        }
        writer.close();
    }

    public static void writeResultToFile(int result, String filen) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filen));
        writer.write(String.valueOf(result));
        writer.close();
    }
}
