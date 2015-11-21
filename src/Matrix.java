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
            sum += elements[i][i];
        }
        return sum;
    }

    /**
     * Calculates the determinant of this matrix
     * @param m a matrix to find the determinant of
     * @return the determinant of this matrix
     */
    public double determinant(Matrix m) {
        if (m.getRows() != m.getCols()) {
            throw new IllegalArgumentException("The matrix msut be square.");
        }
        double[][] mData = m.getElements();
        return determinant(mData);
    }

    /**
     * method to recursively find the determinant
     * @param data a 2D array to find the determinant of
     * @return the determinant of this 2D array
     */
    private double determinant(double[][] data) {
        if (data.length == 1) {
            return data[0][0];
        }
        double det = 0;
        int mult;
        for (int i = 0; i < data.length; i++) {
            double[][] submatrix = new double[data.length - 1][data.length - 1];
            for (int j = 1; j < data.length; j++) {
                for (int k = 0; k < data.length; k++) {
                    if (k < i) {
                        submatrix[j - 1][k] = data[j][k];
                    } else if (k > i) {
                        submatrix[j - 1][k - 1] = data[j][k];
                    }
                }
            }
            if (i % 2 == 0) {
                mult = 1;
            } else {
                mult = -1;
            }
            det += mult * data[0][i] * determinant(submatrix);
        }
        return det;
    }

    public Matrix inverse2x2() {
        if (getRows() != 2 || getCols() != 2) {
            throw new IllegalArgumentException("This is not a 2x2 square matrix.");
        }
        if (determinant(this) == 0) {
            return null;
        }
        //Create inverse matrix's bacckign array and add data
        double[][] inverseData = new double[2][2];
        double temp = elements[0][0];
        inverseData[0][0] = elements[1][1];
        inverseData[1][1] = temp;
        inverseData[0][1] = elements[0][1] * -1;
        inverseData[1][0] = elements[1][0] * -1;

        // Coefficient of inverse is 1 / (ad-bc)
        double coefficient = 1 / ((elements[0][0] * elements[1][1]) -
            (elements[0][1] * elements[1][0]));

        //Multiply each element by the coefficient
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inverseData[i][j] = inverseData[i][j] * coefficient;
            }
        }
        Matrix inverse = new Matrix(inverseData);
        return inverse;
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
            for (int j = 0; j < cols; j++) {
                // last element of a row
                if (cols - j != 1) {
                    m += elements[i][j] + "  ";
                } else if (cols - j == 1 && rows - i != 1) {
                    m += elements[i][j] + "]\n"; // new line
                } else {
                    m += elements[i][j] + "]";
                }
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
     * Gets a copy of this matrix's elements
     * @return a copy of this matrix's 2D array of doubles
     */
    public double[][] getElements() {
        double[][] copy = new double[rows][];
        for (int i = 0; i < rows; i++) {
            copy[i] = Arrays.copyOf(elements[i], elements[i].length);
        }
        return copy;
    }

    /**
     * Gets the element at the given row and column location
     * @param r row index
     * @param c column index
     * @return the double at the given row and column index
     */
    public double getElement(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            throw new java.lang.IllegalArgumentException("Given location " +
                    "does not exist within this matrix.");
        }
        return elements[r][c];
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
//            double[] v = new double[cols];
//            for (int i = 0; i < cols; i++) {
//                v[i] = elements[0][i];
//            }
            return new Vector(elements[0]);
        } else {
            throw new java.lang.IllegalArgumentException("Cannot turn this" +
                    " matrix into a vector");
        }
    }
}
