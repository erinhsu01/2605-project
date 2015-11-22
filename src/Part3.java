import java.util.ArrayList;
import java.util.Random;

public class Part3 {

    /**
     * Runs the power method n times with the given matrix and vector and
     * returns the resuting eigenvector if  it is within the tolerance
     *
     * @param m a matrix to find the eigenvector for
     * @param v a vector to begin the iteration on
     * @param epsilon an accepted tolerance to compare the error to
     * @param n the number of times to run the iteration
     */
    private static ArrayList<Object> power_method(Matrix m, Vector v,
            double epsilon, int n) {
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

            // Factor the result vector to get eigenvector
            double[] eigenvectorContents = result.getContents();
            double factor = eigenvectorContents[result.getSize() - 1];
            double[] values = eigenvectorContents;
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i] / factor;
            }
            eigenvector = new Vector(values);

            //Calculates eigenvalue uisng Rayleigh quotient (Ax・x) / x・x
            // where x is the simplified eigenvector
            eigenvalue = ((m.times(eigenvector)).toVector().dot(eigenvector))
                / (eigenvector.dot(eigenvector));

            iterateVector = result;
            count++;
            error = Math.abs(eigenvalue - oldEigenvalue);
            if (error <= epsilon) {
                returnList.add(eigenvalue);
                returnList.add(eigenvector);
                returnList.add(count);
                return returnList;
            }
        }
        if (error > epsilon) {
            return null;
        }
        returnList.add(eigenvalue);
        returnList.add(eigenvector);
        returnList.add(count);
        return returnList;
    }

    /**
     * Creates a 2x2 matrix where each value is between [-2,2] to be used by
     *      the generateRandom2x2Matrix() method
     * @return a 2x2 random matrix
     */
    private static Matrix create2x2Matrix() {
        Random rand = new Random();
        double [][] matrixData = new double[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                double data = ((rand.nextDouble()) * 4) - 2;
                matrixData[i][j] = data;
            }
        }
        Matrix m = new Matrix(matrixData);
        return m;
    }

    /**
     * Generates a 2x2 matrix and finds the trace, determinant, minimum
     * eigenvalue, maximum eigenvalue, and the number of iterations to find each * of the eigenvalues
     * @return an arraylist that contains the trace of A, determinant of A, the
     *         number of iterations to get an largest eigenvalue for A, and the
     *         number of iterations to get the smallest eigenvalue of A
     */
    private static ArrayList<Object> generateRandom2x2Matrix() {
        Matrix m = create2x2Matrix();
        Matrix mInverse = m.inverse2x2();
        if (mInverse == null) {
            while (mInverse == null) {
                m = create2x2Matrix();
                mInverse = m.inverse2x2();
            }
        }
        double[] initialData = {1,1};
        Vector initial = new Vector(initialData);
        ArrayList<Object> result = power_method(m, initial, 0.00005, 100);
        if (result == null) {
            return null;
        }
        ArrayList<Object> inverseResult = power_method(mInverse, initial,
            0.00005, 100);
        if (inverseResult == null) {
            return null;
        }
        double t = m.trace();
        double d = m.determinant();
        int iterations = (int) result.get(2);
        int inverseIterations = (int) inverseResult.get(2);

        ArrayList<Object> returnList = new ArrayList<>();
        returnList.add(t);
        returnList.add(d);
        returnList.add(iterations);
        returnList.add(inverseIterations);

        /*testing
        returnList.add(m);
        returnList.add(mInverse);
        returnList.add(initial);*/
        return returnList;
    }

    public static void main(String[] args) {
        /*Testing Code

        //test trace, determinant, power_method
        double[][] mArray = new double[2][2];
        mArray[0][0] = 2.0;
        mArray[0][1] = -12.0;
        mArray[1][0] = 1.0;
        mArray[1][1] = -5.0;
        Matrix m = new Matrix(mArray);
        double[] initialData = {1,1};
        Vector v = new Vector(initialData);
        System.out.println("Initial Array\n");
        System.out.println(m.toString());

        ArrayList<Object> result = power_method(m, v, 0.00005, 100);
        if (result != null) {
            System.out.println("\nEigenvalue\n");
            System.out.println(result.get(0).toString());
            System.out.println("\nEigenvector\n");
            System.out.println(result.get(1).toString());
            System.out.println("\n# of Iterations:\n");
            System.out.println(result.get(2));

            System.out.println("\nTrace:\n");
            System.out.println(m.trace());

            System.out.println("\nDeterminant:\n");
            System.out.println(m.determinant());
        } else {
            System.out.println("null");
        }

        //Testing generateRandom2x2Matrix, create2x2Matrix
        ArrayList<Object> result = generateRandom2x2Matrix();
        if (result != null) {
            System.out.println("\nTrace\n");
            System.out.println(result.get(0));
            System.out.println("\nDeterminant\n");
            System.out.println(result.get(1));
            System.out.println("\n# of Iterations:\n");
            System.out.println(result.get(2));
            System.out.println("\n# of Inverse Iterations:\n");
            System.out.println(result.get(3));

            System.out.println("\nMatrix:\n");
            System.out.println(result.get(4).toString());

            System.out.println("\nInverse Matrix:\n");
            System.out.println(result.get(5).toString());


            System.out.println("\nVector:\n");
            System.out.println(result.get(6).toString());
        } else {
            System.out.println("null");
        }*/



    }
}
