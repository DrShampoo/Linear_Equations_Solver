package solver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class SolveEquations {
    private Matrix matrix;
    private CheckSolutions check;

    public SolveEquations(Matrix matrix, CheckSolutions check) {
        this.matrix = matrix;
        this.check = check;
    }

    // If the system has only one solution we find it
    public void solveSystem() {
        rowOperation(0);
        for (int i = 1, j = 0; i < matrix.rows && j < matrix.columns - 1; i++, j++)
            nullColumn(i, j);
        if (check.checkEquations()) {
            for (int i = matrix.rows - 1; i > 0; i--)
                nullTopPart(i);
        }
    }


    public void nullColumn(int raw, int column) {
        for (int j = raw; j < matrix.rows; j++) {
            if (Complex.nullComplex(matrix.array[j][column]) != 0) {
                Complex temp = Complex.multiply(matrix.array[j][column], new Complex(-1, 0));
                for (int k = column; k < matrix.columns; k++) {
                    matrix.array[j][k] = Complex.sum(matrix.array[j][k], Complex.multiply(matrix.array[column][k], temp));
                }
                System.out.println(temp.toString() + " * R" + raw + " + R" + (j + 1) + " -> R" + (j + 1));
            }
        }
        rowOperation(raw);
    }

    public void rowOperation(int i) {
        if (Complex.nullComplex(matrix.array[i][i]) == -1) {
            Complex temp = matrix.array[i][i];
            for (int k = i; k < matrix.columns; k++) {
                matrix.array[i][k] = Complex.divide(matrix.array[i][k], temp);
            }
            System.out.println(String.format("R%d / " + temp.toString() + " -> R%d", i + 1, i + 1));
        }
    }

    public void nullTopPart(int number) {
        for (int i = number - 1; i >= 0; i--) {
            Complex temp = Complex.multiply(matrix.array[i][number], new Complex(-1, 0));
            for (int j = 0; j < matrix.columns; j++) {
                matrix.array[i][j] = Complex.sum(matrix.array[i][j], Complex.multiply(matrix.array[number][j], temp));
            }
            System.out.println(String.format("R%d * %s + R%d -> R%d", number + 1, temp.toString(), i + 1, i + 1));
        }
    }

    public void writeFile(String fileName, String results) {
        File file = new File(fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(results);
            writer.write("\n");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void printResult(String fileName) {
        if (check.noSolutions) {
            System.out.println("No solutions");
            writeFile(fileName, "No solutions");
        } else if (check.manySolutions) {
            System.out.println("Infinitely many solutions");
            writeFile(fileName, "Infinitely many solutions");
        } else {
            System.out.print("The solution is: (");
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Integer, Integer> column : matrix.mapColumns.entrySet()) {
                for (int i = 0; i < matrix.rows; i++) {
                    if (Complex.nullComplex(matrix.array[i][column.getValue()]) == 1) {
                        Complex var = matrix.array[i][matrix.columns - 1];
                        sb.append(var.toString()).append("\n");
                        System.out.print(var.toString() + " ");
                    }
                }
            }
            writeFile(fileName, sb.toString());
            System.out.println(")");
        }
        System.out.println("Saved to file " + fileName);
    }

}
