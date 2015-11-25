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
//import java.util.Random;

public class Part1 /*extends Application*/ {

    /*@Override
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
    }*/

    public void show() {

    }

    public static void main(String[] args) {

        double[][] lu = new double[11][3];
        for (int i = 2; i < 13; i++) {
            Matrix pascal = Matrix.pascalMatrix(i);
            ArrayList<Object> list = lu_fact(pascal);
            lu[i-2][0] = i;
            lu[i-2][1] = solve_lu_b(createB(i), (Matrix) list.get(0), (Matrix) list.get(1));
            lu[i-2][2] = (double) list.get(2); //Gets Error
        }
        Matrix luMatrix = new Matrix(lu);

        System.out.println("LU Matrix Factorization:");
        System.out.println("N   Solution    Error");
        System.out.println(luMatrix);

        double[][] qrhouse = new double[11][3];
        for (int i = 2; i < 13; i++) {
            Matrix pascal = Matrix.pascalMatrix(i);

            lu[i-2][0] = i;
            //lu[i-2][1] =
            //lu[i-2][2] = ; //Gets Error
        }
        Matrix qrHouseMatrix = new Matrix(qrhouse);

        System.out.println("QR Matrix Factorization using Householder:");
        System.out.println("N   Solution    Error");
        System.out.println(qrHouseMatrix);

        System.out.println("LU Matrix Factoriation using Givens:");
        System.out.println("N   Solution    Error");

        //launch(args);
    }

    public static Matrix createB(int n) {
        double[] bArray = new double[n];
        for (int i = 1; i <= n; i++) {
            bArray[i - 1] = 1/i;
        }
        Vector b = new Vector(bArray);
        return b.toMatrix();
    }

}
