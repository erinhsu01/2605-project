import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Pair;


/**
 * Java class that contains the methods for part 2 of the project.
 * Launches as a javafx app with all parts available for viewing on one
 * screen.
 */
public class Part2 extends Application {

    @Override
    public void start(Stage stage) {
        final double MAX_HEIGHT = 600;
        final double MAX_WIDTH = 1300;

        VBox vbox = new VBox(10);
        HBox hbox = new HBox(6);

        // ----------------------------- intro ------------------------------
        double[][] a = {
                {1, 0.5, (1.0 / 3)},
                {0.5, 1, 0.25},
                {(1.0 / 3), 0.25, 1}
        };
        Matrix A = new Matrix(a);
        double[] B = {0.1, 0.1, 0.1};
        Vector b = new Vector(B);

        GridPane grid = new GridPane();
        grid.setPrefHeight(MAX_HEIGHT * 0.15);
//        grid.setGridLinesVisible(true);
        grid.setVgap(3);
        grid.setHgap((20));

        Label ALabel = new Label("Matrix A: \n" + A.toString());
        grid.add(ALabel, 0, 0, 1, 2);
        Label bLabel = new Label("Vector b: \n" + b.toString());
        grid.add(bLabel, 1, 0, 1, 2);
        Label epsilonLabel = new Label("Epsilon: ");
        grid.add(epsilonLabel, 2, 0);
        TextField epsilonField = new TextField("0.00005");
        grid.add(epsilonField, 2, 1);
        Label MLabel = new Label("M: ");
        grid.add(MLabel, 3, 0);
        TextField MField = new TextField("100");
        grid.add(MField, 3, 1);

        // -------------------------- end intro -------------------------------




        // ---------------------------- part A --------------------------------

        ScrollPane spPartA = new ScrollPane();
        spPartA.setPrefHeight(MAX_HEIGHT * 0.37);
        VBox partA = new VBox(3); // default spacing between children: 5
        Label partALabel = new Label("Part A");
        String AIntro = "Given the above matrix A, vector b, epsilon, M, \n" +
                "and x0 randomly selected (or specifically chosen) \n" +
                "below, the approximate solution to Ax = b by Jacobi \n" +
                "and Gauss-Seidel methods and the number of iterations \n" +
                "taken to reach the solution is shown below.";
        Label partAIntroLabel = new Label(AIntro);
        Label x0Label = new Label("x0: ");
        Label x0 = new Label();
        Label jLabel = new Label("Approximate solution using Jacobi: ");
        Label jacobiSolution = new Label();
        Label jMLabel = new Label("# of iterations using Jacobi: ");
        Label jM = new Label();
        Label gsLabel = new Label("Approximate solution using Gauss-Seidel:" );
        Label gsSolution = new Label();
        Label gsMLabel = new Label("# of iterations using Gauss-Seidel: ");
        Label gsM = new Label();
        Label errorLabel = new Label();

        Button partAButton = new Button("Execute again with random x0");
        partAButton.setOnAction(e -> {
            partAHelper(null, epsilonField, MField, x0, jacobiSolution, jM,
                    gsSolution, gsM, errorLabel);
        });

        Button partASpecificButton = new Button("Execute again with" +
                " specific x0:");
        HBox specificx0Box = new HBox();
        TextField x0_x = new TextField("0.5");
        TextField x0_y = new TextField("0.5");
        TextField x0_z = new TextField("0.5");
        specificx0Box.getChildren().addAll(new Label("<"), x0_x,
                new Label(","), x0_y, new Label(","), x0_z,
                new Label(">"));
        partASpecificButton.setOnAction(e -> {
            double x;
            double y;
            double z;
            try {
                x = Double.parseDouble(x0_x.getText());
            } catch (Exception ex) {
                x = 0.5;
                x0_x.setText("0.5");
            }
            try {
                y = Double.parseDouble(x0_y.getText());
            } catch (Exception ex) {
                y = 0.5;
                x0_y.setText("0.5");
            }
            try {
                z = Double.parseDouble(x0_z.getText());
            } catch (Exception ex) {
                z = 0.5;
                x0_z.setText("0.5");
            }
            Vector v = new Vector(new double[] {x, y, z});
            partAHelper(v, epsilonField, MField, x0, jacobiSolution, jM,
                    gsSolution, gsM, errorLabel);
        });

        partAHelper(null, epsilonField, MField, x0, jacobiSolution, jM, gsSolution,
                gsM, errorLabel);
        partA.getChildren().addAll(x0Label, x0,
                jLabel, jacobiSolution, jMLabel, jM, gsLabel,
                gsSolution, gsMLabel, gsM);
        spPartA.setContent(partA);
        VBox contentA = new VBox(5);
        contentA.setPrefHeight(MAX_HEIGHT  * 0.85);
        contentA.setPrefWidth(MAX_WIDTH * 0.28);
        contentA.getChildren().addAll(partALabel, partAIntroLabel, spPartA,
                partAButton, partASpecificButton, specificx0Box);

        // --------------------------- end part A -----------------------------




        // -------------------------- parts B & C -----------------------------
        VBox partB_C = new VBox(5);
        partB_C.setPrefWidth(MAX_WIDTH * 0.42);
        Label partB_CLabel = new Label("Part B & C");
        String partB_CIntro = "Part B: Part A executed multiple times with" +
                " exact results shown \nin the table below.\n" +
                "Part C: average approximate solution xavg from the results " +
                "in part B \nand the average ratio R of number of " +
                "iterations used in Jacobi \nmethod to number of iterations " +
                "used in Gauss-Seidel method.";
        Label partB_CIntroLabel = new Label(partB_CIntro);
        Label numVectorsLabel = new Label("# of times to execute part A:");
        TextField numVectorsField = new TextField("100");
        Button partB_CButton = new Button("Execute again");
        Label xavgJLabel = new Label("xavg for Jacobi:");
        Label xavgJ = new Label();
        Label xavgGSLabel = new Label("xavg for Gauss-Seidel:");
        Label xavgGS = new Label();
        Label RLabel = new Label("R:");
        Label R = new Label();

        TableView<B_CTableData> table = new TableView<>();
        table.setMaxHeight(MAX_HEIGHT * 0.3);
        TableColumn<B_CTableData, String> x0Column = new TableColumn<>("x0");
        x0Column.setCellValueFactory(cellData -> cellData.getValue().getX0());
        TableColumn<B_CTableData, String> jColumn = new TableColumn<>("J approx.");
        jColumn.setCellValueFactory(cellData -> cellData.getValue().getJacobi());
        TableColumn<B_CTableData, String> jMColumn = new TableColumn<>("N for J");
        jMColumn.setCellValueFactory(cellData -> cellData.getValue().getJacobiN());
        TableColumn<B_CTableData, String> gsColumn = new TableColumn<>("GS approx.");
        gsColumn.setCellValueFactory(cellData -> cellData.getValue().getGaussSeidel());
        TableColumn<B_CTableData, String> gsMColumn = new TableColumn<>("N for GS");
        gsMColumn.setCellValueFactory(cellData -> cellData.getValue().getGaussSeidelN());
        table.getColumns().addAll(x0Column, jColumn, jMColumn, gsColumn,
                gsMColumn);

        HBox B_CFieldandButton = new HBox(5);
        B_CFieldandButton.getChildren().addAll(numVectorsField, partB_CButton);

        partB_C.getChildren().addAll(partB_CLabel, partB_CIntroLabel,
                xavgJLabel, xavgJ, xavgGSLabel, xavgGS, RLabel, R, numVectorsLabel,
                B_CFieldandButton, table);

        // ----------------------- end parts B & C ---------------------------




        // ---------------------------- part D -------------------------------
        // note: part D is calculated within partB_CHelper
        VBox partD = new VBox(5);
        Label partDLabel = new Label("Part D");
        String partDIntro = "A scatter plot of the data collected from " +
                "this execution of part B. Blue \npoints represent data " +
                "from using the Jacobi method. Black points \nrepresent " +
                "data from using the Gauss-Seidel method. " +
                "\nExecute part B again for a new plot.";
        Label partDIntroLabel = new Label(partDIntro);
        NumberAxis yAxis = new NumberAxis();
        NumberAxis xAxis = new NumberAxis();
        ScatterChart<Number, Number> scatterChart = new
                ScatterChart<>(xAxis, yAxis);
        xAxis.setLabel("initial error (|| x0 - xexact ||)");
        yAxis.setLabel("iterations (N)");
        XYChart.Series<Number, Number> jacobi = new XYChart.Series<>();
//        jacobi.setName("Jacobi method");
        XYChart.Series<Number, Number> gaussSeidel = new XYChart.Series<>();
//        gaussSeidel.setName("Gauss-Seidel Method");
        scatterChart.getData().addAll(jacobi, gaussSeidel);

        partD.getChildren().addAll(partDLabel, partDIntroLabel, scatterChart);


        // -------------------------- end part D -----------------------------

        partB_CButton.setOnAction(e -> {
            partB_CHelper(epsilonField, MField, numVectorsField, xavgJ, xavgGS,
                    R, table, jacobi, gaussSeidel, errorLabel);
        });

        partB_CHelper(epsilonField, MField, numVectorsField, xavgJ, xavgGS,
                R, table, jacobi, gaussSeidel, errorLabel);

        hbox.getChildren().addAll(contentA, partB_C, partD);
        vbox.getChildren().addAll(grid, hbox);
        Scene scene = new Scene(vbox, MAX_WIDTH, MAX_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void partAHelper(Vector v, TextField e, TextField m, Label x0, Label j,
                           Label jm, Label gs, Label gsm, Label error) {
        String errorText = "";
        error.setText(errorText);
        if (v == null) {
            v = new Vector(3, -1, 1);
        }
        x0.setText(v.toString());

        double epsilon;
        try {
            epsilon = Double.parseDouble(e.getCharacters().toString());
        } catch (Exception ex) {
            epsilon = 0.00005;
            e.setText("0.00005");
            errorText += "Invalid epsilon. Using epsilon = 0.00005. ";
            error.setText(errorText);
        }

        int M;
        try {
            M = Integer.parseInt(m.getCharacters().toString());
        } catch (Exception ex) {
            M = 100;
            m.setText("100");
            errorText += "Invalid M. Using M = 100.";
            error.setText(errorText);
        }

        Pair<Vector, Integer> jacobi = jacobi_iter(v, epsilon, M);
        try {
            j.setText(jacobi.getKey().toString());
        } catch (NullPointerException ex) {
            j.setText("No vector approximation found");
        }
        try {
            jm.setText(jacobi.getValue().toString());
        } catch (NullPointerException ex) {
            jm.setText("Quit after " + M + " attempts");
        }

        Pair<Vector, Integer> gaussSeidel = gs_iter(v, epsilon, M);
        try {
            gs.setText(gaussSeidel.getKey().toString());
        } catch (NullPointerException ex) {
            gs.setText("No vector approximation found");
        }
        try {
            gsm.setText(gaussSeidel.getValue().toString());
        } catch (NullPointerException ex) {
            gsm.setText("Quit after " + M + " attempts");
        }
    }

    private void partB_CHelper(TextField e, TextField m, TextField n,
                               Label xavgJ, Label xavgGS, Label r,
                               TableView<B_CTableData> t,
                               XYChart.Series jacobiSeries,
                               XYChart.Series gaussSeidelSeries, Label error) {
        double xavgJ_x = 0;
        double xavgJ_y = 0;
        double xavgJ_z = 0;
        double xavgGS_x = 0;
        double xavgGS_y = 0;
        double xavgGS_z = 0;
        double RSum = 0;
        String errorText = "";
        error.setText(errorText);
        ObservableList<B_CTableData> data = FXCollections.observableArrayList();
        jacobiSeries.getData().clear();
        gaussSeidelSeries.getData().clear();

        double epsilon;
        try {
            epsilon = Double.parseDouble(e.getCharacters().toString());
        } catch (Exception ex) {
            epsilon = 0.00005;
            e.setText("0.00005");
            errorText += "Invalid epsilon. Using epsilon = 0.00005. ";
            error.setText(errorText);
        }

        int M;
        try {
            M = Integer.parseInt(m.getCharacters().toString());
        } catch (Exception ex) {
            M = 100;
            m.setText("100");
            errorText += "Invalid M. Using M = 100. ";
            error.setText(errorText);
        }

        int num;
        try {
            num = Integer.parseInt(n.getCharacters().toString());
        } catch (Exception ex) {
            num = 100;
            n.setText("100");
            errorText += "Invalid M. Using M = 100.";
            error.setText(errorText);
        }

        double[] exact = {(9.0 / 190), (28.0 / 475), (33.0 / 475)};
        Vector exactVector = new Vector(exact);

        for (int i = 0; i < num; i++) {
            Vector v = new Vector(3, -1, 1);
            Pair<Vector, Integer> j = jacobi_iter(v, epsilon, M);
            Pair<Vector, Integer> gs = gs_iter(v, epsilon, M);

            try {
                // adding to the table
                data.add(new B_CTableData(v, j.getKey(), j.getValue(), gs.getKey(),
                        gs.getValue()));

                // adding to the scatter chart
                double jDifference = j.getKey().minus(exactVector).magnitude();
                XYChart.Data<Number, Number> jData = new XYChart.Data<>(jDifference, j.getValue());
                Circle jPoint = new Circle(4, Color.BLUE);
                jPoint.setStroke(Color.BLACK);
                jData.setNode(jPoint);
                jacobiSeries.getData().add(jData);
                double gsDifference = gs.getKey().minus(exactVector).magnitude();
                XYChart.Data<Number, Number> gsData = new XYChart.Data<>(gsDifference, gs.getValue());
                Circle gsPoint = new Circle(4, Color.BLACK);
                gsPoint.setStroke(Color.RED);
                gsData.setNode(gsPoint);
                gaussSeidelSeries.getData().add(gsData);

                // calculating xavg and R
                xavgJ_x += j.getKey().getContent(0);
                xavgGS_x += gs.getKey().getContent(0);
                xavgJ_y += j.getKey().getContent(1);
                xavgGS_y += gs.getKey().getContent(1);
                xavgJ_z += j.getKey().getContent(2);
                xavgGS_z += gs.getKey().getContent(2);
                RSum += j.getValue() / gs.getValue();
            } catch (NullPointerException ex) {
                // if the method fails (M is reached before an approx. is found), then
                // redo another one in its place
                i--;
            }
            double[] xJ = {
                    (xavgJ_x / num),
                    (xavgJ_y / num),
                    (xavgJ_z / num)
            };
            double[] xGS = {
                    (xavgGS_x / num),
                    (xavgGS_y / num),
                    (xavgGS_z / num)
            };
            Vector xAvgJ = new Vector(xJ);
            xavgJ.setText(xAvgJ.toString());
            Vector xAvgGS = new Vector(xGS);
            xavgGS.setText(xAvgGS.toString());
            double R = RSum / num;
            r.setText(Double.toString(R));
            t.setItems(data);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Recursive helper method for both iterations.
     * Will continue to iterate until the calculated solution is within
     * epsilon or if the number of iterations exceeds M.
     * Uses the formula x_(k+1) = S-inverse * (T*x_k + b)
     * @param SInverse inverse of S matrix for iterations
     * @param T T matrix for iterations
     * @param xk current best solution
     * @param b given b for Ax = b
     * @param m current number of iterations
     * @param M positive int that indicates max. number of times to iterate
     *          before quitting
     * @param epsilon maximum difference allowed between the current best
     *                solution and the previous one in order to determine
     *                when the current best solution is close enough
     * @return a Pair of data: the final solution within epsilon, and
     *                         number of iterations taken to reach the solution
     */
    private static Pair<Vector, Integer> iteration_helper(Matrix SInverse,
                                                          Matrix T, Vector xk,
                                                          Vector b, int m,
                                                          int M, double epsilon) {
        // continue iterating
        // x_(k+1) = S-inverse * (T*x_k + b)
        Matrix T_times_xk = T.times(xk);
        Matrix inside = T_times_xk.plus(b.toMatrix());
        Matrix x_kPlus1 = SInverse.times(inside);
        m++;

        // successfully found a solution within epsilon
        // magnitude of (x_(k+1) - x_k) <= epsilon
        double difference = x_kPlus1.toVector().minus(xk).magnitude();
        if (difference <= epsilon) {
            return new Pair<>(x_kPlus1.toVector(), m);
        }

        // failed to find a solution within M iterations
        if (m >= M) {
            return null;
        }

        return iteration_helper(SInverse, T, x_kPlus1.toVector(), b, m, M, epsilon);
    }

    /**
     * Calculates a solution using Jacobi iteration for a
     * specific matrix A and vector b in part 2 of the project.
     * @param x0 initial solution vector
     * @param epsilon a positive number that determines when solution
     *                is close enough
     * @param m positive int that indicates max. number of times to iterate
     *          before quitting
     * @return a Pair of data: the final solution within epsilon, and
     *                         number of iterations taken to reach the solution
     */
    public static Pair<Vector, Integer> jacobi_iter(Vector x0, double epsilon, int m) {
        double[][] a = {
                {1.0, 0.5, (1.0 / 3)},
                {0.5, 1.0, 0.25},
                {(1.0 / 3), 0.25, 1.0}
        };
        double[] b = {0.1, 0.1, 0.1};
        return jacobi_iter(new Matrix(a), new Vector(b), x0, epsilon, m);
    }

    /**
     * Calculates a solution using Jacobi iteration for a
     * given matrix A and vector b. (solving for x in Ax = b)
     * @param a given matrix
     * @param b given solution
     * @param x0 initial solution vector
     * @param epsilon a positive number that determines when solution
     *                is close enough
     * @param m positive int that indicates max. number of times to iterate
     *          before quitting
     * @return a Pair of data: the final solution within epsilon, and
     *                         number of iterations taken to reach the solution
     */
    public static Pair<Vector, Integer> jacobi_iter(Matrix a, Vector b, Vector x0,
                                                    double epsilon, int m) {
        double e = Math.abs(epsilon);
        int M = Math.abs(m);

        // S for Jacobi is just the diagonal along A (S = D)
        Matrix S = a.D();

        // Inverse for the diagonal matrix S
        double[][] sInverse = new double[S.getRows()][S.getCols()];
        for (int i = 0; i < sInverse[0].length; i++) {
            sInverse[i][i] = 1.0 / S.getElement(i, i);
        }
        Matrix SInverse = new Matrix(sInverse);

        // T = -(L + U)
        Matrix LPlusU = Matrix.add(a.L(), a.U());
        Matrix T = LPlusU.times(-1);

        return iteration_helper(SInverse, T, x0, b, 0, M, e);
    }



    /**
     * Iteratively calculates a solution using Gauss-Seidel iteration for a
     * specific matrix A and vector b in part 2 of the project.
     * Will continue to iterate until the calculated solution is within
     * epsilon or if the number of iterations exceeds M.
     * @param x0 initial solution vector
     * @param epsilon a positive number that determines when solution
     *                is close enough
     * @param m positive int that indicates max. number of times to iterate
     *          before quitting
     * @return a Pair of data: the final solution within epsilon, and
     *                         number of iterations taken to reach the solution
     */
    public static Pair<Vector, Integer> gs_iter(Vector x0, double epsilon, int m) {
        double[][] a = {
                {1.0, 0.5, (1.0 / 3)},
                {0.5, 1.0, 0.25},
                {(1.0 / 3), 0.25, 1.0}
        };
        double[] b = {0.1, 0.1, 0.1};
        return gs_iter(new Matrix(a), new Vector(b), x0, epsilon, m);
    }

    /**
     * Iteratively calculates a solution using Gauss-Seidel iteration for a
     * given matrix A and vector b.
     * Will continue to iterate until the calculated solution is within
     * epsilon or if the number of iterations exceeds M.
     * @param A given matrix
     * @param B given solution
     * @param x0 initial solution vector
     * @param epsilon a positive number that determines when solution
     *                is close enough
     * @param m positive int that indicates max. number of times to iterate
     *          before quitting
     * @return a Pair of data: the final solution within epsilon, and
     *                         number of iterations taken to reach the solution
     */
    public static Pair<Vector, Integer> gs_iter(Matrix A, Vector B, Vector x0,
                                                double epsilon, int m) {
        double ep = Math.abs(epsilon);
        int M = Math.abs(m);

        // S for Gauss-Seidel is the diagonal along A plus its lower triangular
        // matrix
        Matrix S = Matrix.add(A.D(), A.L());

        if (A.getRows() != 3) {
            throw new java.lang.IllegalArgumentException("This method was" +
                    " not written for anything other than 3x3 matrices.");
        }

        // S-inverse, according to given formula
        double[][] sInverse = new double[S.getRows()][S.getCols()];
        double a = S.getElement(0, 0);
        double b = S.getElement(1, 0);
        double c = S.getElement(1, 1);
        double d = S.getElement(2, 0);
        double e = S.getElement(2, 1);
        double f = S.getElement(2, 2);
        sInverse[0][0] = 1.0 / a;
        sInverse[1][0] = -b / (a * c);
        sInverse[1][1] = 1.0 / c;
        sInverse[2][0] = (-(c * d) + (b * e)) / (a * c * f);
        sInverse[2][1] = -e / (c * f);
        sInverse[2][2] = 1.0 / f;
        Matrix SInverse = new Matrix(sInverse);

        // T = -U
        Matrix U = A.U();
        Matrix T = U.times(-1);

        return iteration_helper(SInverse, T, x0, B, 0, M, ep);
    }


    private class B_CTableData {
        private final SimpleStringProperty x0;
        private final SimpleStringProperty jacobi;
        private final SimpleStringProperty jacobiN;
        private final SimpleStringProperty gaussSeidel;
        private final SimpleStringProperty gaussSeidelN;

        private B_CTableData(Vector x, Vector j, int jn, Vector gs, int gsn) {
            x0 = new SimpleStringProperty(x.toString());
            jacobi = new SimpleStringProperty(j.toString());
            jacobiN = new SimpleStringProperty(Integer.toString(jn));
            gaussSeidel = new SimpleStringProperty(gs.toString());
            gaussSeidelN = new SimpleStringProperty(Integer.toString(gsn));
        }

        public SimpleStringProperty getX0() {
            return x0;
        }

        public SimpleStringProperty getJacobi() {
            return jacobi;
        }

        public SimpleStringProperty getJacobiN() {
            return jacobiN;
        }

        public SimpleStringProperty getGaussSeidel() {
            return gaussSeidel;
        }

        public SimpleStringProperty getGaussSeidelN() {
            return gaussSeidelN;
        }
    }
}
