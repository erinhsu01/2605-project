import javafx.util.Pair;

/**
 * Java class that contains the methods for part 2 of the project.
 * Will also contain the main method to run any selected instructions.
 *
 * Current problems: At some point in G-S, x_(k+1) = x_k and thus cuts off
 * earlier with less accuracy
 */

public class Part2 {

    public static void main(String[] args) {
        // currently testing - will write user interaction later
        double[] r = {9.0 / 190, 28.0 / 475, 33.0 / 475};
        Vector real = new Vector(r);
        System.out.println("Real solution: \t\t\t\t" + real);

        Vector v = new Vector(3, -1, 1);
        System.out.println("Jacobi approximation: \t\t" + jacobi_iter(v, 0.00005, 100));
        System.out.println("Gauss-Seidel approximation: " + gs_iter(v, 0.00005, 100));

    }

    /**
     * Recursive helper method for both iterations.
     * Will continue to iterate until the calculated solution is within
     * epsilon or if the number of iterations exceeds M.
     * Uses the formula x_(k+1) = S-inverse * (T*x_k + b)
     * @param SInverse inverse of S matrix for iterations
     * @param T T matrix for iterations
     * @param xk current best solution
     * @param b given b for Ax = b
     * @param m current number of iterations
     * @param M positive int that indicates max. number of times to iterate
     *          before quitting
     * @param epsilon maximum difference allowed between the current best
     *                solution and the previous one in order to determine
     *                when the current best solution is close enough
     * @return a Pair of data: the final solution within epsilon, and
     *                         number of iterations taken to reach the solution
     */
    private static Pair<Vector, Integer> iteration_helper(Matrix SInverse,
                                                          Matrix T, Vector xk,
                                                          Vector b, int m,
                                                          int M, double epsilon) {
        // continue iterating
        // x_(k+1) = S-inverse * (T*x_k + b)
        Matrix T_times_xk = T.times(xk);
        Matrix inside = T_times_xk.plus(b.toMatrix());
        Matrix x_kPlus1 = SInverse.times(inside);
        m++;

        // successfully found a solution within epsilon
        // magnitude of (x_(k+1) - x_k) <= epsilon
        double difference = x_kPlus1.toVector().minus(xk).magnitude();
        if ((x_kPlus1.toVector().minus(xk)).magnitude() <= epsilon) {
            return new Pair<>(x_kPlus1.toVector(), m);
        }

        // failed to find a solution within M iterations
        if (m >= M) {
            return new Pair<>(xk, m);
        }

        return iteration_helper(SInverse, T, x_kPlus1.toVector(), b, m, M, epsilon);
    }

    /**
     * Calculates a solution using Jacobi iteration for a
     * specific matrix A and vector b in part 2 of the project.
     * @param x0 initial solution vector
     * @param epsilon a positive number that determines when solution
     *                is close enough
     * @param m positive int that indicates max. number of times to iterate
     *          before quitting
     * @return a Pair of data: the final solution within epsilon, and
     *                         number of iterations taken to reach the solution
     */
    public static Pair<Vector, Integer> jacobi_iter(Vector x0, double epsilon, int m) {
        double[][] a = {
                {1.0, 0.5, (1.0 / 3)},
                {0.5, 1.0, 0.25},
                {(1.0 / 3), 0.25, 1.0}
        };
        double[] b = {0.1, 0.1, 0.1};
        return jacobi_iter(new Matrix(a), new Vector(b), x0, epsilon, m);
    }

    /**
     * Calculates a solution using Jacobi iteration for a
     * given matrix A and vector b. (solving for x in Ax = b)
     * @param a given matrix
     * @param b given solution
     * @param x0 initial solution vector
     * @param epsilon a positive number that determines when solution
     *                is close enough
     * @param m positive int that indicates max. number of times to iterate
     *          before quitting
     * @return a Pair of data: the final solution within epsilon, and
     *                         number of iterations taken to reach the solution
     */
    public static Pair<Vector, Integer> jacobi_iter(Matrix a, Vector b, Vector x0,
                                                    double epsilon, int m) {
        double e = Math.abs(epsilon);
        int M = Math.abs(m);

        // S for Jacobi is just the diagonal along A (S = D)
        Matrix S = a.D();

        // Inverse for the diagonal matrix S
        double[][] sInverse = new double[S.getRows()][S.getCols()];
        for (int i = 0; i < sInverse[0].length; i++) {
            sInverse[i][i] = 1.0 / S.getElement(i, i);
        }
        Matrix SInverse = new Matrix(sInverse);

        // T = -(L + U)
        Matrix LPlusU = Matrix.add(a.L(), a.U());
        Matrix T = LPlusU.times(-1);

        return iteration_helper(SInverse, T, x0, b, 0, M, e);
    }



    /**
     * Iteratively calculates a solution using Gauss-Seidel iteration for a
     * specific matrix A and vector b in part 2 of the project.
     * Will continue to iterate until the calculated solution is within
     * epsilon or if the number of iterations exceeds M.
     * @param x0 initial solution vector
     * @param epsilon a positive number that determines when solution
     *                is close enough
     * @param m positive int that indicates max. number of times to iterate
     *          before quitting
     * @return a Pair of data: the final solution within epsilon, and
     *                         number of iterations taken to reach the solution
     */
    public static Pair<Vector, Integer> gs_iter(Vector x0, double epsilon, int m) {
        double[][] a = {
                {1.0, 0.5, (1.0 / 3)},
                {0.5, 1.0, 0.25},
                {(1.0 / 3), 0.25, 1.0}
        };
        double[] b = {0.1, 0.1, 0.1};
        return gs_iter(new Matrix(a), new Vector(b), x0, epsilon, m);
    }

    /**
     * Iteratively calculates a solution using Gauss-Seidel iteration for a
     * given matrix A and vector b.
     * Will continue to iterate until the calculated solution is within
     * epsilon or if the number of iterations exceeds M.
     * @param A given matrix
     * @param B given solution
     * @param x0 initial solution vector
     * @param epsilon a positive number that determines when solution
     *                is close enough
     * @param m positive int that indicates max. number of times to iterate
     *          before quitting
     * @return a Pair of data: the final solution within epsilon, and
     *                         number of iterations taken to reach the solution
     */
    public static Pair<Vector, Integer> gs_iter(Matrix A, Vector B, Vector x0,
                                                double epsilon, int m) {
        double ep = Math.abs(epsilon);
        int M = Math.abs(m);

        // S for Gauss-Seidel is the diagonal along A plus its lower triangular
        // matrix
        Matrix S = Matrix.add(A.D(), A.L());

        if (A.getRows() != 3) {
            throw new java.lang.IllegalArgumentException("This method was" +
                    " not written for anything other than 3x3 matrices.");
        }

        // S-inverse, according to given formula
        double[][] sInverse = new double[S.getRows()][S.getCols()];
        double a = S.getElement(0, 0);
        double b = S.getElement(1, 0);
        double c = S.getElement(1, 1);
        double d = S.getElement(2, 0);
        double e = S.getElement(2, 1);
        double f = S.getElement(2, 2);
        sInverse[0][0] = 1.0 / a;
        sInverse[1][0] = -b / (a * c);
        sInverse[1][1] = 1.0 / c;
        sInverse[2][0] = (-(c * d) + (b * e)) / (a * c * f);
        sInverse[2][1] = -e / (c * f);
        sInverse[2][2] = 1.0 / f;
        Matrix SInverse = new Matrix(sInverse);

        // T = -U * (L + D)
        Matrix U = A.U();
        Matrix negativeU = U.times(-1);
        Matrix LPlusD = Matrix.add(A.L(), A.D());
        Matrix T = negativeU.times(LPlusD);

        return iteration_helper(SInverse, T, x0, B, 0, M, ep);
    }
}
