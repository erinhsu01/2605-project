public class Matrix {
    private final int height;
    private final int width;
    private final double[][] elements;

    /**
     * Creates an empty 2D matrix
     * @param height the number of arrays within the encompassing array
     * @param width the number of elements in each sub-array
     */
    public Matrix(int height, int width) {
        this.height = height;
        this.width = width;
        elements = new double[height][width];
    }

    /**
     * Creates a 2D matrix from a given 2D array
     * @param matrix 2D matrix to copy elements from
     */
    public Matrix(double[][] elements) {
        int height = elements.length;
        int width = elements[0].length;
        this.elements = new double[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                    this.elements[i][j] = elements[i][j];
    }

    /**
     * Adds Matrices x and y together to produce Matrix z
     * @param y the matrix to add to Matrix x
     * @return the additive Matrix
     */
    public Matrix add(Matrix y) {
        Matrix x = this;
        if (x.height != y.height || x.width != y.width) {
            throw new IllegalArgumentException("Tha matrices do not have the same dimensions.");
        }
        Matrix z = new Matrix(x.height, x.width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                z.elements[i][j] = x.elements[i][j] + y.elements[i][j];
            }
        }
        return z;
    }

    /**
     * Subtracts Matrix y from Matrix x
     * @param y the matrix to subtract from Matrix x
     * @return the resulting Matrix
     */
    public Matrix subtract(Matrix y) {
        Matrix x = this;
        if (x.height != y.height || x.width != y.width) {
            throw new IllegalArgumentException("Tha matrices do not have the same dimensions.");
        }
        Matrix z = new Matrix(x.height, x.width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                z.elements[i][j] = x.elements[i][j] - y.elements[i][j];
            }
        }
        return z;
    }

    /**
     * Multiply Matrix x by a scalar
     * @param d the scalar to multiply x by
     * @return the multiplicative Matrix
     */
    public Matrix add(double d) {
        Matrix x = this;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                x.elements[i][j] = x.elements[i][j] * d;
            }
        }
        return x;
    }

    /**
     * Multiply Matrix x by Matrix y
     * @param y the Matrix to multiply x by
     * @return the resulting Matrix
     */
    public Matrix add(Matrix y) {
        Matrix x = this;
        if (x.width != y.height) {
            throw new IllegalArgumentException("The matrices do not have corresponding dimensions");
        }
        Matrix z = new Matrix(x.height, y.width);
        for (int i = 0; i < z.width; i++) {
            for (int j = 0; j < z.height; j++) {
                for (int k = 0; k , x.width; k++) {
                    z.elements[i][j] = x.elements[i][k] * y.elements[k][j];
                }
            }
        }
        return z;
    }

    /**
     * Calculates the trace of the matrix
     * @return the sum of the diagonal elements (elements[i][i])
     */
    public double trace() {
        double sum;
        for (int i = 0; i < height; i++) {
            sum += this.elements[i][i];
        }
        return sum;
    }
}
