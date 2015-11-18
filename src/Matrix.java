import javafx.util.Pair;

import java.util.Arrays;
import java.util.Random;

public class Matrix {
    private final int rows;
    private final int cols;
    private final double[][] elements;



    // ---------------------- BEGIN CONSTRUCTORS ----------------------

    /**
     * Creates a matrix with 0 rows and columns
     */
    public Matrix() {
        this(0, 0);
    }

    /**
     * Creates a 2D matrix of all zeroes
     * @param height the number of arrays within the encompassing array
     * @param width the number of elements in each sub-array
     */
    public Matrix(int height, int width) {
        if (height < 0 || width < 0) {
            throw new RuntimeException("Matrix dimensions must be >= 0");
        }
        this.rows = height;
        this.cols = width;
        elements = new double[height][width];
    }

    /**
     * Creates a random matrix with of a given size. Each element in the
     * matrix falls within [lowerBound, upperBound]
     * @param height number of rows in the matrix
     * @param width number of columns in the matrix
     * @param lowerBound lowest number of the range for the randomly
     *                   generated elements
     * @param upperBound highest number of the range for the randomly
     *                   generated elements
     */
    public Matrix(int height, int width, double lowerBound, double upperBound) {
        rows = height;
        cols = width;
        elements = new double[rows][cols];
        Random r = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                elements[i][j] = lowerBound +
                        (r.nextDouble() * (upperBound - lowerBound));
            }
        }
    }

    /**
     * Creates a 2D matrix from a given 2D array
     * @param elements 2D matrix to copy elements from
     */
    public Matrix(double[][] elements) {
        this.rows = elements.length;
        if (rows != 0) {
            this.cols = elements[0].length;
        } else {
            cols = 0;
        }
        this.elements = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                    this.elements[i][j] = elements[i][j];
    }

    /**
     * Creates the identity matrix
     * @param n number of rows and columns
     * @return identity matrix
     */
    public static Matrix identityMatrix(int n) {
        if (n == 0) {
            throw new java.lang.IllegalArgumentException("Identity matrix" +
                    " does not exist in this dimension");
        }
        double[][] id = new double[n][n];
        for (int i = 0; i < n; i++) {
            id[i][i] = 1;
        }
        return new Matrix(id);
    }

    // ---------------------- END CONSTRUCTORS ----------------------



    // -------------------- BEGIN BASIC METHODS ---------------------

    /**
     * Adds a series of matrices together
     * @param m var args of Matrix objects to be added together
     * @return a Matrix sum of all the given matrices
     */
    public static Matrix add(Matrix... m) {
        // Start with matrix of right size but with all zeroes
        Matrix ret = new Matrix(m[0].getRows(), m[0].getCols());

        // then add each matrix to the current sum
        for (Matrix matrix : m) {
            ret = ret.plus(matrix);
        }
        return ret;
    }

    /**
     * Adds this matrix and y together
     * @param y the matrix to add to this matrix
     * @return this + y
     */
    public Matrix plus(Matrix y) {
        if (this.rows != y.rows || this.cols != y.cols) {
            throw new IllegalArgumentException("The matrices do not have the same dimensions.");
        }
        Matrix p = new Matrix(this.rows, this.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                p.elements[i][j] = this.elements[i][j] + y.elements[i][j];
            }
        }
        return p;
    }

    /**
     * Subtracts Matrix y from this matrix
     * @param y the matrix to subtract from this matrix
     * @return this - y
     */
    public Matrix subtract(Matrix y) {
        return this.plus(y.times(-1));
    }

    /**
     * Multiply this matrix by a scalar
     * @param d the scalar to multiply x by
     * @return this * d where d is a scalar
     */
    public Matrix times(double d) {
        Matrix t = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                t.elements[i][j] = this.elements[i][j] * d;
            }
        }
        return t;
    }

    /**
     * Multiply this matrix by Matrix y
     * @param y the Matrix to multiply x by
     * @return this * y where y is a matrix
     */
    public Matrix times(Matrix y) {
        if (this.cols != y.rows) {
            throw new IllegalArgumentException("The matrices do not have corresponding dimensions");
        }
        Matrix t = new Matrix(this.rows, y.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < t.cols; j++) {
                for (int k = 0; k < this.cols; k++) {
                    t.elements[i][j] += this.elements[i][k] * y.elements[k][j];
                }
            }
        }
        return t;
    }

    /**
     * Multiply this matrix by a vector v
     * @param v vector to multiply this matrix with
     * @return this * v where v is a vector
     */
    public Matrix times(Vector v) {
        return this.times(v.toMatrix());
    }

    /**
     * Returns the transpose of this matrix
     * @return this matrix transposed
     */
    public Matrix transpose() {
        double[][] t = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                t[j][i] = elements[i][j];
            }
        }
        return new Matrix(t);
    }

    /**
     * Returns the lower triangular matrix L for this matrix
     * if this matrix is square and not of size 0 rows, 0 columns
     * @return this matrix's lower triangular matrix
     */
    public Matrix L() {
        if (rows != cols) {
            throw new java.lang.IllegalArgumentException("Matrix must be" +
                    " square");
        } else if (rows == 0) {
            throw new java.lang.IllegalArgumentException("Cannot find the" +
                    " L matrix for an empty matrix");
        }

        double[][] l = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (i > j) {
                    l[i][j] = elements[i][j];
                } else {
                    l[i][j] = 0;
                }
            }
        }
        return new Matrix(l);
    }

    /**
     * Returns the upper triangular matrix U for this matrix
     * if this matrix is square and not of size 0 rows, 0 columns
     * @return this matrix's upper triangular matrix
     */
    public Matrix U() {
        if (rows != cols) {
            throw new IllegalArgumentException("Matrix must be" +
                    " square");
        } else if (rows == 0) {
            throw new IllegalArgumentException("Cannot find the" +
                    " U matrix for an empty matrix");
        }

        double[][] u = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (j > i) {
                    u[i][j] = elements[i][j];
                } else {
                    u[i][j] = 0;
                }
            }
        }
        return new Matrix(u);
    }

    /**
     * Returns the diagonal matrix D for this matrix
     * (NOT diagonal matrix D for singular value decomposition)
     * if this matrix is square and not of size 0 rows, 0 columns
     * @return this matrix's upper triangular matrix
     */
    public Matrix D() {
        if (rows != cols) {
            throw new IllegalArgumentException("Matrix must be" +
                    " square");
        } else if (rows == 0) {
            throw new IllegalArgumentException("Cannot find the" +
                    " U matrix for an empty matrix");
        }

        double[][] d = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            d[i][i] = elements[i][i];
        }
        return new Matrix(d);
    }

    // -------------------- END BASIC METHODS ---------------------



    // ------------------ BEGIN COMPLEX METHODS -------------------

    /**
     * Calculates the trace of this matrix
     * @return the sum of the diagonal elements (elements[i][i])
     */
    public double trace() {
        double sum = 0;
        for (int i = 0; i < rows; i++) {
            sum += this.elements[i][i];
        }
        return sum;
    }

    /**
     * Iteratively calculates a solution using Jacobi iteration for a
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
     * Iteratively calculates a solution using Jacobi iteration for a
     * given matrix A and vector b.
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
    public static Pair<Vector, Integer> jacobi_iter(Matrix a, Vector b, Vector x0,
                                             double epsilon, int m) {
        double e = Math.abs(epsilon);
        int M = Math.abs(m);

        // S for Jacobi is just the diagonal along A
        Matrix S = a.D();

        // Inverse for the diagonal matrix S
        double[][] sInverse = new double[S.getRows()][S.getCols()];
        for (int i = 0; i < sInverse[0].length; i++) {
            sInverse[i][i] = 1.0 / S.getElements()[i][i];
        }
        Matrix SInverse = new Matrix(sInverse);

        // L + U
        Matrix LPlusU = Matrix.add(a.L(), a.U());

        return jacobi_helper(SInverse, LPlusU, x0, b, 0, M, e);
    }

    private static Pair<Vector, Integer> jacobi_helper(Matrix SInverse,
                                                       Matrix LPlusU, Vector xk,
                                                       Vector b, int m,
                                                       int M, double epsilon) {
        // continue iterating
        // x_(k+1) = S-inverse * (-(L+U)*x_k + b)
        Matrix minusLPlusU = LPlusU.times(-1);
        Matrix minusLPlusUTimesxk = minusLPlusU.times(xk);
        Matrix first = minusLPlusUTimesxk.plus(b.toMatrix());
        Matrix x_kPlus1 = SInverse.times(first);
        m++;

        // successfully found a solution within epsilon
        // magnitude of (x_(k+1) - x_k) <= epsilon
        Vector v = x_kPlus1.toVector();
        if ((x_kPlus1.toVector().minus(xk)).magnitude() <= epsilon) {
            return new Pair<>(x_kPlus1.toVector(), m);
        }

        // failed to find a solution within M iterations
        if (m >= M) {
            return new Pair<>(xk, m);
        }

        return jacobi_helper(SInverse, LPlusU, x_kPlus1.toVector(), b, m, M, epsilon);
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
     * @param x0 initial solution vector
     * @param epsilon a positive number that determines when solution
     *                is close enough
     * @param m positive int that indicates max. number of times to iterate
     *          before quitting
     * @return a Pair of data: the final solution within epsilon, and
     *                         number of iterations taken to reach the solution
     */
    public static Pair<Vector, Integer> gs_iter(Matrix a, Vector b, Vector x0,
                                         double epsilon, int m) {
        double e = Math.abs(epsilon);
        int M = Math.abs(m);

        // S for Gauss-Seidel is the diagonal along A plus its lower triangular
        // matrix
        Matrix S = Matrix.add(a.D(), a.L());


        // TODO
        return new Pair<>(x0, M);
    }

    // ------------------- END COMPLEX METHODS --------------------



    // ------------------ BEGIN TESTING METHODS -------------------

    /**
     * Gets the number of rows
     * @return the number of rows in the matrix
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns
     * @return the number of columns in the matrix
     */
    public int getCols() {
        return cols;
    }

    /**
     * toString method
     *
     * examples:
     * [] for an empty matrix
     *
     * [[1  2  3]]
     *
     * [[1]
     *  [2]
     *  [3]]
     *
     * [[1  2]
     *  [3  4]]
     *
     * etc.
     *
     * @return String representation of this matrix
     */
    public String toString() {
        String m = "[";
        for (int i = 0; i < rows; i++) {
            if (i == 0) {
                m += "[";
            } else {
                m += " ["; // extra space here
            }
            for (int j = 0; j < cols - 1; j++) {
                m += elements[i][j] + "  ";
            }
            if (cols - 1 >= 0) {
                m += elements[i][cols - 1] + "]\n";
            } else {
                m += "]";
            }
        }
        m += "]";
        return m;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Matrix)) {
            return false;
        }
        Matrix m = ((Matrix) o);
        return Arrays.deepEquals(this.elements, m.getElements());
    }

    /**
     * Private getter mostly for testing
     * @return this matrix's 2D array of doubles
     */
    private double[][] getElements() {
        return elements;
    }

    /**
     * Checks to see if this matrix is symmetric
     * (that is, if this matrix equals this matrix transposed)
     * @return whether this matrix is symmetric
     */
    public boolean isSymmetric() {
        return this.equals(this.transpose());
    }

    /**
     * If possible, return a Vector representation of this matrix
     * @return a vector representation of this matrix
     */
    public Vector toVector() {
        if (cols == 1) {
            double[] v = new double[rows];
            for (int i = 0; i < rows; i++) {
                v[i] = elements[i][0];
            }
            return new Vector(v);
        } else if (rows == 1) {
            double[] v = new double[cols];
            for (int i = 0; i < cols; i++) {
                v[i] = elements[0][i];
            }
            return new Vector(elements[0]);
        } else {
            throw new java.lang.IllegalArgumentException("Cannot turn this" +
                    " matrix into a vector");
        }
    }
}
