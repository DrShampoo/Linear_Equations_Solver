package solver;

import java.util.Map;

//first part of the algorithm
public class FirstRowOperations {
    private Matrix matrix;

    public FirstRowOperations(Matrix matrix) {
        this.matrix = matrix;
    }

    //We find all the corresponding elements
    public void findDiagonalElements() {
        for (int i = 0; i < matrix.rows; i ++) {
            boolean nullElement = false;

            //We check all the elements in the corresponding column
            if (Complex.nullComplex(matrix.array[i][i]) == 0) {
                nullElement = true;
                for (int j = i + 1; j < matrix.rows; j++) {
                    if (Complex.nullComplex(matrix.array[j][i]) != 0) {
                        swapRow(i, j);
                        nullElement = false;
                        break;
                    }
                }
            }

            //We check all the elements in the corresponding raw
            if (nullElement) {
                for (int j = i + 1; j < matrix.columns - 1; j++) {
                    if (Complex.nullComplex(matrix.array[i][j]) != 0) {
                        swapColumn(i, j);
                        nullElement = false;
                        break;
                    }
                }
            }

            //We check all the elements in the whole bottom-left part of the linear system
            if (nullElement) {
                for (int j = i + 1; j < matrix.columns - 1 && nullElement; j++) {
                    for (int k = i + 1; k < matrix.rows; k++) {
                        if (Complex.nullComplex(matrix.array[k][j]) != 0) {
                            swapRow(i, k);
                            swapColumn(i, j);
                            nullElement = false;
                            break;
                        }
                    }
                }
            }
        }
    }


    public void swapRow (int oldRaw, int newRow) {
        for (int i = 0; i < matrix.columns; i++) {
            Complex temp = matrix.array[newRow][i];
            matrix.array[newRow][i] = matrix.array[oldRaw][i];
            matrix.array[oldRaw][i] = temp;
        }
        System.out.println(String.format("R%d <-> R%d", oldRaw + 1, newRow + 1));
    }

    public void swapColumn (int oldColumn, int newColumn) {
        for (int i = 0 ; i < matrix.rows; i++) {
            Complex temp = matrix.array[i][newColumn];
            matrix.array[i][newColumn] = matrix.array[i][oldColumn];
            matrix.array[i][oldColumn] = temp;
        }
        System.out.println(String.format("C%d <-> C%d", oldColumn + 1, newColumn + 1));

        //When we change the position of the column we also change the value of this column in our map
        for (Map.Entry<Integer, Integer> columns : matrix.mapColumns.entrySet()) {
            if (columns.getValue() == oldColumn) {
                matrix.mapColumns.put(columns.getKey(), newColumn);
                continue;
            }
            if (columns.getValue() == newColumn) {
                matrix.mapColumns.put(columns.getKey(), oldColumn);
            }
        }
    }
}
