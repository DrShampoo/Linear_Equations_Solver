package solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Matrix {
    Complex[][] array;
    int columns;
    int rows;
    Map<Integer, Integer> mapColumns = new HashMap<>(); // this map contains the number of all the columns


    public void readFile(String fileName) {
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            String[] temp = scanner.nextLine().split(" ");
            columns = Integer.parseInt(temp[0]) + 1;
            rows = Integer.parseInt(temp[1]);
            array = new Complex[rows][columns];
            for (int i = 0; i < columns - 1; i++) {
                mapColumns.put(i, i);
            }
            for (int i = 0; i < rows; i++) {
                String[] line = scanner.nextLine().split(" ");
                for (int j = 0; j < columns; j++) {
                    array[i][j] = Complex.readComplex(line[j]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + fileName);
        }

    }
}
