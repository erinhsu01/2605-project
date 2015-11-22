import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Part3 extends Application {

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        ArrayList<ArrayList<Object>> matrices1000 = generate1000Matrices();

        stage.setTitle("Graphs");
        final NumberAxis xAxis = new NumberAxis(-10, 10, 1);
        final NumberAxis yAxis = new NumberAxis(-10, 10, 1);
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
            Circle point = new Circle(4, /*getColor(matrices1000.get(i).get(2))*/ Color.BLUE);
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
            Circle point = new Circle(4, /*getColor(matrices1000.get(i).get(5))*/ Color.BLUE);
            point.setStroke(Color.BLACK);
            data.setNode(point);
            series2.getData().add(data);
        }

        scatterchart.getData().addAll(series1);
        scatterchart2.getData().addAll(series2);
        VBox vBox1 = new VBox();
        Scene scene = new Scene(new Group());
        vBox1.getChildren().addAll(scatterchart, scatterchart2);
        ((Group)scene.getRoot()).getChildren().add(vBox1);
        stage.setScene(scene);
        stage.show();
    }

    public void show() {

    }

    public static void main(String[] args) {
        launch(args);

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

            System.out.println("\nInverse Trace\n");
            System.out.println(result.get(5));

            System.out.println("\nInverse Determinant\n");
            System.out.println(result.get(5));

            System.out.println("\n# of Inverse Iterations:\n");
            System.out.println(result.get(5));

            System.out.println("\nMatrix:\n");
            System.out.println(result.get(6).toString());

            System.out.println("\nInverse Matrix:\n");
            System.out.println(result.get(7).toString());


            System.out.println("\nVector:\n");
            System.out.println(result.get(7).toString());
        } else {
            System.out.println("null");
        }*/
    }

    /*public Color getColor(int i) {
        if (i < 50) {
            if (i < 25) {
                if (i < 10) {
                    if (i == 1) {
                        return Color.ALICEBLUE;
                    } else if (i == 2) {
                        return Color.ANTIQUEWHITE;
                    } else if (i == 3) {
                        return Color.AQUA;
                    } else if (i == 4) {
                        return Color.AQUAMARINE;
                    } else if (i == 5) {
                        return Color.AZURE;
                    } else if (i == 6) {
                        return Color.BEIGE;
                    } else if (i == 7) {
                        return Color.BISQUE;
                    } else if (i == 8) {
                        return Color.ORANGE;
                    } else if (i == 9) {
                        return Color.BLANCHEDALMOND;
                } else {
                    if (i == 10) {
                        return Color.BLUE;
                    } else if (i == 11) {
                        return Color.BLUEVIOLET;
                    } else if (i == 12) {
                        return Color.BROWN;
                    } else if (i == 13) {
                        return Color.BURLYWOOD;
                    } else if (i == 14) {
                        return Color.CADETBLUE;
                    } else if (i == 15) {
                        return Color.CHARTREUSE;
                    } else if (i == 16) {
                        return Color.CHOCOLATE;
                    } else if (i == 17) {
                        return Color.CORAL;
                    } else if (i == 18) {
                        return Color.CORNFLOWERBLUE;
                    } else if (i == 19) {
                        return Color.CORNSILK;
                    } else if (i == 20) {
                        return Color.CRIMSON;
                    } else if (i == 21) {
                        return Color.CYAN;
                    } else if (i == 22) {
                        return Color.DARKBLUE;
                    } else if (i == 23) {
                        return Color.DARKCYAN;
                    } else if (i == 24) {
                        return Color.DARKGOLDENROD;
                    }
                }
            } else {
                if (i < 35) {
                    } else if (i == 25) {
                        return Color.DARKGRAY;
                    } else if (i == 26) {
                        return Color.DARKGREEN;
                    } else if (i == 27) {
                        return Color.DARKGREY;
                    } else if (i == 28) {
                        return Color.DARKKHAKI;
                    } else if (i == 29) {
                        return Color.DARKMAGENTA;
                    } else if (i == 30) {
                        return Color.DARKOLIVEGREEN;
                    } else if (i == 31) {
                        return Color.DARKORANGE;
                    } else if (i == 32) {
                        return Color.DARKORCHID;
                    } else if (i == 33) {
                        return Color.DARKRED;
                    } else if (i == 34) {
                        return Color.DARKSALMON;
                } else {
                    if (i == 35) {
                        return Color.DARKSEAGREEN;
                    } else if (i == 36) {
                        return Color.DARKSLATEBLUE;
                    } else if (i == 37) {
                        return Color.DARKSLATEGRAY;
                    } else if (i == 38) {
                        return Color.DARKTURQUOISE;
                    } else if (i == 39) {
                        return Color.DARKVIOLET;
                    } else if (i == 40) {
                        return Color.DEEPPINK;
                    } else if (i == 41) {
                        return Color.DEEPSKYBLUE;
                    } else if (i == 42) {
                        return Color.DIMGRAY;
                    } else if (i == 43) {
                        return Color.DODGERBLUE;
                    } else if (i == 44) {
                        return Color.FIREBRICK;
                    } else if (i == 45) {
                        return Color.FLORALWHITE;
                    } else if (i == 46) {
                        return Color.FORESTGREEN;
                    } else if (i == 47) {
                        return Color.FUCHSIA;
                    } else if (i == 48) {
                        return Color.GAINSBORO;
                    } else if (i == 49) {
                        return Color.GHOSTWHITE;
                    }
                }
            }
        } else {
            if (i == 50) {
                return Color.GOLD;
            } else if (i == 51) {
                return Color.GOLDENROD;
            } else if (i == 52) {
                return Color.GRAY;
            } else if (i == 53) {
                return Color.GREEN;
            } else if (i == 54) {
                return Color.GREENYELLOW;
            } else if (i == 55) {
                return Color.GREY;
            } else if (i == 56) {
                return Color.HONEYDEW;
            } else if (i == 57) {
                return Color.HOTPINK;
            } else if (i == 58) {
                return Color.INDIANRED;
            } else if (i == 59) {
                return Color.INDIGO;
            } else if (i == 60) {
                return Color.IVORY;
            } else if (i == 61) {
                return Color.KHAKI;
            } else if (i == 62) {
                return Color.LAVENDER;
            } else if (i == 63) {
                return Color.LAVENDERBLUSH;
            } else if (i == 64) {
                return Color.LAWNGREEN;
            } else if (i == 65) {
                return Color.LEMONCHIFFON;
            } else if (i == 66) {
                return Color.LIGHTBLUE;
            } else if (i == 67) {
                return Color.LIGHTCORAL;
            } else if (i == 68) {
                return Color.LIGHTCYAN;
            } else if (i == 69) {
                return Color.LIGHTGOLDENRODYELLOW;
            } else if (i == 70) {
                return Color.LIGHTGRAY;
            } else if (i == 71) {
                return Color.LIGHTGREEN;
            } else if (i == 72) {
                return Color.LIGHTPINK;
            } else if (i == 73) {
                return Color.LIGHTSALMON;
            } else if (i == 74) {
                return Color.LIGHTSEAGREEN;
            } else if (i == 75) {
                return Color.LIGHTSKYBLUE;
            } else if (i == 76) {
                return Color.LIGHTSLATEGRAY;
            } else if (i == 77) {
                return Color.LIGHTSTEELBLUE;
            } else if (i == 78) {
                return Color.LIGHTYELLOW;
            } else if (i == 79) {
                return Color.LIME;
            } else if (i == 80) {
                return Color.LIMEGREEN;
            } else if (i == 81) {
                return Color.LINEN;
            } else if (i == 82) {
                return Color.MAGENTA;
            } else if (i == 83) {
                return Color.MAROON;
            } else if (i == 84) {
                return Color.MEDIUMAQUAMARINE;
            } else if (i == 85) {
                return Color.MEDIUMBLUE;
            } else if (i == 86) {
                return Color.MEDIUMORCHID;
            } else if (i == 87) {
                return Color.MEDIUMPURPLE;
            } else if (i == 88) {
                return Color.MEDIUMSEAGREEN;
            } else if (i == 89) {
                return Color.MEDIUMSLATEBLUE;
            } else if (i == 90) {
                return Color.MEDIUMSPRINGGREEN;
            } else if (i == 91) {
                return Color.MEDIUMTURQUOISE;
            } else if (i == 92) {
                return Color.MEDIUMVIOLETRED;
            } else if (i == 93) {
                return Color.MIDNIGHTBLUE;
            } else if (i == 94) {
                return Color.MINTCREAM;
            } else if (i == 95) {
                return Color.MISTYROSE;
            } else if (i == 96) {
                return Color.MOCCASIN;
            } else if (i == 97) {
                return Color.NAVAJOWHITE;
            } else if (i == 98) {
                return Color.NAVY;
            } else if (i == 99) {
                return Color.OLDLACE;
            } else {
                return Color.OLIVE;
            }
        }
    }*/

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
     * Generates a 2x2 matrix and finds the determinant, trace, minimum
     * eigenvalue, maximum eigenvalue, and the number of iterations to find each * of the eigenvalues
     * @return an arraylist that contains the determinant of A, trace of A, the
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
        double inverseD = mInverse.determinant();
        double inverseT = mInverse.trace();
        int iterations = (int) result.get(2);
        int inverseIterations = (int) inverseResult.get(2);

        ArrayList<Object> returnList = new ArrayList<>();
        returnList.add(d);
        returnList.add(t);
        returnList.add(iterations);
        returnList.add(inverseD);
        returnList.add(inverseT);
        returnList.add(inverseIterations);

        /*testing
        returnList.add(m);
        returnList.add(mInverse);
        returnList.add(initial);*/
        return returnList;
    }

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
