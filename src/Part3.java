import javafx.util.Pair;
import java.util.ArrayList;

public class Part3 {

    /**
     * Runs the power method n times with the given matrix and vector and
     * returns the resuting eigenvector if  it is within the tolerance
     *
     * @param m a matrix to find the eigenvector for
     * @param v a vector to begin the iteration on
     * @param epsilon an accepted tolerance to compare the error to
     * @param n the numbe rof times to run the iteration
     */
    public ArrayList power_method(Matrix m, Vector v, double epsilon, int n) {
        // Always a square matrix
        if (m.getRows() != m.getCols()) {
            throw new IllegalArgumentException("The matrix must be a square matrix.");
        }
        if (v.getSize() != m.getRows()) {
            throw new IllegalArgumentException("The vector does not have the correct size.");
        }
        if (epsilon < 0) {
            throw new IllegalArgumentException("The tolerance cannot be negative.");
        }
        ArrayList<Object> returnList = new ArrayList<>();
        Vector iterateVector = v;
        Vector eigenvector = new Vector(m.getRows());
        double error = Double.MAX_VALUE;
        double eigenvalue = 0;
        double oldEigenvalue = 0;
        int count = 0;
        while (n > count && error > epsilon) {
            oldEigenvalue = eigenvalue;
            Vector result = m.times(iterateVector).toVector();

            // Eigenvalue is last element in result vector
            double[] eigenvalueContents = result.getContents();
            eigenvalue = eigenvalueContents[result.getSize() - 1];

            //Gets the eigenvector from result vector and eigenvalue
            double[] values = result.getContents();
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i] / eigenvalue;
            }
            eigenvector = new Vector(values);

            iterateVector = result;
            error = Math.abs(eigenvalue - oldEigenvalue);
            count++;
        }
        if (error > epsilon) {
            return null;
        }
        returnList.add(eigenvalue);
        returnList.add(eigenvector);
        returnList.add(count);
        return returnList;
    }
}
