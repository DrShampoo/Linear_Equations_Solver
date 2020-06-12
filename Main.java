package solver;


public class Main {

    public static void main(String[] args) {
        Matrix matrix = new Matrix();
        matrix.readFile(args[1]);

        System.out.println("Start solving the equation.");
        FirstRowOperations firstOperations = new FirstRowOperations(matrix);
        firstOperations.findDiagonalElements();

        CheckSolutions check = new CheckSolutions(matrix);

        SolveEquations solve = new SolveEquations(matrix, check);
        solve.solveSystem();
        solve.printResult(args[3]);

    }
}
