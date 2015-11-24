import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Part3 extends Application {

    public static Color getColor(int i) {
        if (i < 10) {
            return Color.RED;
        } else if (i < 20) {
            return Color.ORANGE;
        } else if (i < 30) {
            return Color.YELLOW;
        } else if (i < 40) {
            return Color.GREEN;
        } else if (i < 50) {
            return Color.BLUE;
        } else if (i < 60) {
            return Color.PURPLE;
        } else if (i < 70) {
            return Color.PINK;
        } else if (i < 80) {
            return Color.GRAY;
        } else if (i < 90) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        ArrayList<ArrayList<Object>> matrices1000 = generate1000Matrices();

        stage.setTitle("Graphs");
        final NumberAxis xAxis = new NumberAxis(-6, 6, 1);
        final NumberAxis yAxis = new NumberAxis(-5, 5, 1);
        final NumberAxis xAxis2 = new NumberAxis(-10, 10, 1);
        final NumberAxis yAxis2 = new NumberAxis(-10, 10, 1);
        final ScatterChart<Number, Number> scatterchart = new
                ScatterChart<Number, Number>(xAxis, yAxis);

        final ScatterChart<Number, Number> scatterchart2 = new
                ScatterChart<Number, Number>(xAxis2, yAxis2);
        xAxis.setLabel("Determinant of A");
        yAxis.setLabel("Trace of A");
        xAxis2.setLabel("Determinant of A-Inverse");
        yAxis2.setLabel("Trace of A-Inverse");
        scatterchart.setTitle("Determinant vs. Trace for 1000 Matrices");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Matrix A");
        for (int i = 0; i < 1000; i++) {
            XYChart.Data<Number, Number> data = new
                XYChart.Data(matrices1000.get(i).get(0),
                matrices1000.get(i).get(1));
            Circle point = new Circle(4, getColor( (int)
                matrices1000.get(i).get(2)));
            point.setStroke(Color.BLACK);
            data.setNode(point);
            series1.getData().add(data);
        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Matrix A-Inverse");
        for (int i = 0; i < 1000; i++) {
            XYChart.Data<Number, Number> data = new
                XYChart.Data(matrices1000.get(i).get(3),
                matrices1000.get(i).get(4));
            Circle point = new Circle(4, getColor( (int)
                matrices1000.get(i).get(5)));
            point.setStroke(Color.BLACK);
            data.setNode(point);
            series2.getData().add(data);
        }

        scatterchart.getData().addAll(series1);
        scatterchart2.getData().addAll(series2);
        HBox hBox1 = new HBox();
        Scene scene = new Scene(new Group());
        hBox1.getChildren().addAll(scatterchart, scatterchart2);
        ((Group)scene.getRoot()).getChildren().add(hBox1);
        stage.setScene(scene);
        stage.show();
    }

    public void show() {

    }

    public static void main(String[] args) {
        launch(args);

        /*Testing Code

        //test trace, determinant, power_method
        /*double[][] mArray = new double[2][2];
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

            System.out.println("\nDeterminant\n");
            System.out.println(result.get(0));

            System.out.println("\nTrace\n");
            System.out.println(result.get(1));

            System.out.println("\n# of Iterations:\n");
            System.out.println(result.get(2));

            System.out.println("\nInverse Determinant\n");
            System.out.println(result.get(3));

            System.out.println("\nInverse Trace\n");
            System.out.println(result.get(4));

            System.out.println("\n# of Inverse Iterations:\n");
            System.out.println(result.get(5));

            System.out.println("\nLargest Eigenvalue:\n");
            System.out.println(result.get(6));

            System.out.println("\nSmallst Eigenvalue:\n");
            System.out.println(result.get(7));

            System.out.println("\nEigenvector:\n");
            System.out.println(result.get(8));

            System.out.println("\nInverse Eigenvector:\n");
            System.out.println(result.get(9));

            System.out.println("\nVector:\n");
            System.out.println(result.get(10).toString());

            System.out.println("\nMatrix:\n");
            System.out.println(result.get(11).toString());

            System.out.println("\nInverse Matrix:\n");
            System.out.println(result.get(12).toString());
        } else {
            System.out.println("null");
        }*/
    }

    /**
     * Runs the power method n times with the given matrix and vector and
     * returns the resuting eigenvector if  it is within the tolerance
     *
     * @param m a matrix to find the eigenvector for
     * @param v a vector to begin the iteration on
     * @param epsilon an accepted tolerance to compare the error to
     * @param n the number of times to run the iteration
     * @return an ArrayLisat whose contents are Eigenvalue, Eigenvector,
     *         # of Iterations
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
     * Generates a 2x2 matrix and finds the determinant, trace, minimum
     * eigenvalue, maximum eigenvalue, and the number of iterations to find
     * each of the eigenvalues
     * @return an ArrayList that contains the determinant of A, trace of A, the
     *         number of iterations to get the largest eigenvalue for A, the
     *         determinant of A-Inverse, the trace of A-Inverse, and the
     *         number of iterations to get the smallest eigenvalue of A, the
     *         largest eigenvalue of A, the smallest eigenvalue of A (or the
     *         largest eigenvalue of A-Inverse), the eigenvector of A, and the
     *         eigenvector of A-Inverse
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
        double inverseD = mInverse.determinant();
        double inverseT = mInverse.trace();
        int iterations = (int) result.get(2);
        int inverseIterations = (int) inverseResult.get(2);
        double largestEigenvalue = (double) result.get(0);
        double smallestEigenvalue = (double) inverseResult.get(0);
        Vector eigenvector = (Vector) result.get(1);
        Vector inverseEigenvector = (Vector) inverseResult.get(1);

        ArrayList<Object> returnList = new ArrayList<>();

        returnList.add(d);
        returnList.add(t);
        returnList.add(iterations);
        returnList.add(inverseD);
        returnList.add(inverseT);
        returnList.add(inverseIterations);
        returnList.add(largestEigenvalue);
        returnList.add(smallestEigenvalue);
        returnList.add(eigenvector);
        returnList.add(inverseEigenvector);


        /*testing
        returnList.add(initial);
        returnList.add(m);
        returnList.add(mInverse);
        */

        return returnList;
    }

    /**
     * Method that generates 2000 matrices instead of 1000 matrices to account
     * for matrices that return null.
     * If it doesn't return null, the matrix gets added to the ArrayList, which
     * the ArrayList pulls data from
     * @return an Arraylist that contains the non-null matrices' data
     */
    private ArrayList<ArrayList<Object>> generate1000Matrices() {
        ArrayList<ArrayList<Object>> matrices1000 = new
            ArrayList<ArrayList<Object>>();
        for (int i = 0; i < 2000; i++) {
            ArrayList<Object> matrixData = generateRandom2x2Matrix();
            if (matrixData != null) {
                matrices1000.add(matrixData);
            }
        }
        return matrices1000;
    }
}
