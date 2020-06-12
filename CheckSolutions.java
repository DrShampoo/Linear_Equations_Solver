package solver;

public class CheckSolutions {
    private Matrix matrix;
    boolean noSolutions = false;
    boolean manySolutions = false;

    public CheckSolutions(Matrix matrix) {
        this.matrix = matrix;
    }

    // We check the possibility of no solutions.
    public boolean checkEquations () {
        int zeroRows = 0;
        for (int i = 0; i < matrix.rows; i ++) {
            int countNull = 0;
            for (int j = 0; j < matrix.columns - 1; j++) {
                if (Complex.nullComplex(matrix.array[i][j]) == 0) {
                    countNull++;
                }
            }
            if (countNull == matrix.columns - 1 && Complex.nullComplex(matrix.array[i][matrix.columns - 1]) != 0) {
                noSolutions = true;
                return false;
            }
            if (countNull == matrix.columns - 1) {
                zeroRows++;
            }
        }
        if (matrix.rows - zeroRows == matrix.columns - 1) {
            return true;
        } else if (matrix.rows - zeroRows > matrix.columns - 1){
            noSolutions = true;
            return false;
        } else {
            manySolutions = true;
            return false;
        }
    }

}
