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

            qrhouse[i-2][0] = i;
            qrhouse[i-2][1] = solve_qr_b.houseSolve(pascal);
            qrhouse[i-2][2] = solve_qr_b.getQRError(pascal); //Gets Error
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
