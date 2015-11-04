public class Matrix {
    private final int rows;
    private final int cols;
    private final double[][] elements;

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
            throw new RuntimeException("Matrix dimensions must be > 0");
        }
        this.rows = height;
        this.cols = width;
        elements = new double[height][width];
    }

    /**
     * Creates a 2D matrix from a given 2D array
     * @param elements 2D matrix to copy elements from
     */
    public Matrix(double[][] elements) {
        this.rows = elements.length;
        this.cols = elements[0].length;
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
        double[][] id = new double[n][n];
        for (int i = 0; i < n; i++) {
            id[i][i] = 1;
        }
        return new Matrix(id);
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
                p.elements[i][j] = p.elements[i][j] + y.elements[i][j];
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
        for (int i = 0; i < t.cols; i++) {
            for (int j = 0; j < t.rows; j++) {
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
                m += " [";
            }
            for (int j = 0; j < cols - 1; j++) {
                m += elements[i][j] + "  ";
            }
            m += elements[i][cols - 1] + "]\n";
        }
        m += "]";
        return m;
    }
}
