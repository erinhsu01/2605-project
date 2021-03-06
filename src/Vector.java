import java.util.Arrays;
import java.util.Random;

public class Vector {
    private final int size;
    private double[] contents;



    // ---------------------- BEGIN CONSTRUCTORS ----------------------

    /**
     * Creates a Vector of size 0
     */
    public Vector() {
        this(0);
    }

    /**
     * Creates the zero vector in the given dimension
     * @param size number of elements in the vector
     */
    public Vector(int size) {
        this.size = size;
        contents = new double[size];
    }

    /**
     * Creates a random vector of a given size. Each element in the
     * vector falls within [lowerBound, upperBound]
     * @param size number of elements in the vector
     * @param lowerBound lowest possible number of the range for the
     *                   randomly generated elements
     * @param upperBound highest possible number of the range for
     *                   the randomly generated elements
     */
    public Vector(int size, double lowerBound, double upperBound) {
        this.size = size;
        contents = new double[size];
        Random r = new Random();
        for (int i = 0; i < size; i++) {
            contents[i] = lowerBound +
                    (r.nextDouble() * (upperBound - lowerBound));
        }
    }

    /**
     * Creates a vector from a given array
     * @param contents array to make this vector from
     */
    public Vector(double[] contents) {
        size = contents.length;
        this.contents = new double[size];
        for (int i = 0; i < size; i++) {
            this.contents[i] = contents[i];
        }
    }

    // ---------------------- END CONSTRUCTORS ----------------------



    // -------------------- BEGIN BASIC METHODS ---------------------

    /**
     * Finds the dot product of this vector and another vector v
     * @param v other vector
     * @return dot product of this vector and v
     */
    public double dot(Vector v) {
        if (this.size != v.size) {
            throw new java.lang.RuntimeException("Vectors must be of " +
                    "the same dimensions");
        }
        double sum = 0.0;
        for (int i = 0; i < size; i++) {
            sum += this.contents[i] * v.contents[i];
        }
        return sum;
    }

    /**
     * Finds the magnitude (length) of this vector
     * @return this vector's magnitude/length
     */
    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }

    /**
     * Finds the unit vector (direction) of this vector
     * @return this vector's unit vector/direction
     */
    public Vector unitVector() {
        double mag = this.magnitude();
        if (mag == 0) {
            throw new java.lang.RuntimeException("Vector has no direction");
        }
        return this.times(1.0 / mag);
    }

    /**
     * Finds the projection of this vector onto another vector v
     * (this dot v) divided by (the magnitude of v squared) times (v)
     * @param v other vector
     * @return projection of this vector onto v
     */
    public Vector projectedOnto(Vector v) {
        double vMag = v.magnitude();
        if (vMag == 0) {
            throw new java.lang.RuntimeException("Vector cannot be projected" +
                    " onto a zero length vector.");
        }
        return v.times(this.dot(v) / (vMag * vMag));
    }

    /**
     * Adds another vector to this vector
     * @param v other vector
     * @return this + v
     */
    public Vector plus(Vector v) {
        if (this.size != v.size) {
            throw new java.lang.RuntimeException("Vectors must be of " +
                    "the same dimensions");
        }
        Vector p = new Vector(size);
        for (int i = 0; i < size; i++) {
            p.contents[i] = this.contents[i] + v.contents[i];
        }
        return p;
    }

    /**
     * Subtracts another vector from this vector
     * @param v other vector
     * @return this - v
     */
    public Vector minus(Vector v) {
        return this.plus(v.times(-1));
    }

    /**
     * Multiplies this vector by a constant c
     * @param c real number constant
     * @return c * this vector
     */
    public Vector times(double c) {
        Vector t = new Vector(size);
        for (int i = 0; i < size; i++) {
            t.contents[i] = this.contents[i] * c;
        }
        return t;
    }

    // -------------------- END BASIC METHODS ---------------------



    // ------------------ BEGIN TESTING METHODS -------------------

    /**
     * Converts this vector into a matrix
     * @return a matrix representation of this vector
     */
    public Matrix toMatrix() {
        double[][] m = new double[size][1];
        for (int i = 0; i < size; i++) {
            m[i][0] = contents[i];
        }
        return new Matrix(m);
    }

    /**
     * toString method
     * examples:
     * <> for an empty vector
     * <1.0, 2.0, 3.0, 4.0> for a vector in R4
     * @return String representation of this vector
     */
    public String toString() {
        if (size == 0) {
            return "<>";
        }
        String s = "<";
        for (int i = 0; i < size - 1; i++) {
            s += contents[i] + ", ";
        }
        s += contents[size - 1] + ">";
        return s;
    }

    /**
     * Equals method (mostly for the sake of testing)
     * @param o object comparing this vector to
     * @return if this vector's contents = o's contents
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Vector)) {
            return false;
        }
        Vector v = ((Vector) o);
        return Arrays.equals(this.contents, v.getContents());
    }

    /**
     * Gets the size of this vector
     * @return number of elements (size) of this vector
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets a copy of this vector's elements
     * @return a copy of this vector's 2D array of doubles
     */
    public double[] getContents() {
        double[] copy = new double[size];
        for (int i = 0; i < contents.length; i++) {
            copy[i] = contents[i];
        }
        return copy;
    }

    public double getContent(int i) {
        if (i < 0 || i > size) {
            throw new java.lang.IllegalArgumentException("Index does not exist");
        } else {
            return contents[i];
        }
    }
}
