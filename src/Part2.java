import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;


/**
 * Java class that contains the methods for part 2 of the project.
 * Launches as a javafx app with all parts available for viewing on one
 * screen.
 */
public class Part2 extends Application {

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
        spPartA.setMaxWidth(MAX_WIDTH / 3);
        spPartA.setPrefHeight(MAX_HEIGHT * 0.37);
        VBox partA = new VBox(3); // default spacing between children: 5
        Label partALabel = new Label("Part A");
        String AIntro = "Given the above matrix A and vector b, and x0\n" +
                "randomly selected below, the approximate solution\n" +
                "to Ax = b by Jacobi and Gauss-Seidel methods\n" +
                "and the number of iterations taken to reach\n" +
                "said solution is shown below.";
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

        Button partAButton = new Button("Execute again with different x0");
        partAButton.setOnAction(e -> {
            partAHelper(epsilonField, MField, x0, jacobiSolution, jM,
                    gsSolution, gsM, errorLabel);
        });

        partAHelper(epsilonField, MField, x0, jacobiSolution, jM, gsSolution,
                gsM, errorLabel);
        partA.getChildren().addAll(x0Label, x0,
                jLabel, jacobiSolution, jMLabel, jM, gsLabel,
                gsSolution, gsMLabel, gsM);
        spPartA.setContent(partA);
        VBox contentA = new VBox(5);
        contentA.setPrefHeight(MAX_HEIGHT  * 0.85);
        contentA.setPrefWidth(MAX_WIDTH * 0.25);
        contentA.getChildren().addAll(partALabel, partAIntroLabel, spPartA,
                partAButton);

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
        Label xavgLabel = new Label("xavg:");
        Label xavg = new Label();
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

        partB_CButton.setOnAction(e -> {
            partB_CHelper(epsilonField, MField, numVectorsField, xavg,
                    R, table, errorLabel);
        });

        HBox B_CFieldandButton = new HBox(5);
        B_CFieldandButton.getChildren().addAll(numVectorsField, partB_CButton);

        partB_CHelper(epsilonField, MField, numVectorsField, xavg,
                R, table, errorLabel);

        partB_C.getChildren().addAll(partB_CLabel, partB_CIntroLabel,
                xavgLabel, xavg, RLabel, R, numVectorsLabel,
                B_CFieldandButton, table);

        // ----------------------- end parts B & C ---------------------------




        // --------------------------- part D --------------------------------
        VBox partD = new VBox(5);

        hbox.getChildren().addAll(contentA, partB_C, partD);
        vbox.getChildren().addAll(grid, hbox);
        Scene scene = new Scene(vbox, MAX_WIDTH, MAX_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private void partAHelper(TextField e, TextField m, Label x0, Label j,
                           Label jm, Label gs, Label gsm, Label error) {
        String errorText = "";
        error.setText(errorText);
        Vector v = new Vector(3, -1, 1);
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
        j.setText(jacobi.getKey().toString());
        jm.setText(jacobi.getValue().toString());

        Pair<Vector, Integer> gaussSeidel = gs_iter(v, epsilon, M);
        gs.setText(gaussSeidel.getKey().toString());
        gsm.setText(gaussSeidel.getValue().toString());
    }

    private void partB_CHelper(TextField e, TextField m, TextField n,
                               Label xavg, Label r, TableView<B_CTableData> t, Label error) {
        double xavg_x = 0;
        double xavg_y = 0;
        double xavg_z = 0;
        double RSum = 0;
        String errorText = "";
        error.setText(errorText);
        ObservableList<B_CTableData> data = FXCollections.observableArrayList();

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

        for (int i = 0; i < num; i++) {
            Vector v = new Vector(3, -1, 1);
            Pair<Vector, Integer> j = jacobi_iter(v, epsilon, M);
            Pair<Vector, Integer> gs = gs_iter(v, epsilon, M);
            data.add(new B_CTableData(v, j.getKey(), j.getValue(), gs.getKey(),
                    gs.getValue()));
            xavg_x += j.getKey().getContent(0) + gs.getKey().getContent(0);
            xavg_y += j.getKey().getContent(1) + gs.getKey().getContent(1);
            xavg_z += j.getKey().getContent(2) + gs.getKey().getContent(2);
            RSum += j.getValue() / gs.getValue();
        }
        double[] x = {
                (xavg_x / (2 * num)),
                (xavg_y / (2 * num)),
                (xavg_z / (2 * num))
        };
        Vector xAvg = new Vector(x);
        xavg.setText(xAvg.toString());
        double R = RSum / num;
        r.setText(Double.toString(R));
        t.setItems(data);
    }

    public static void main(String[] args) {
        launch(args);
        // currently testing - will write user interaction later
//        double[] r = {9.0 / 190, 28.0 / 475, 33.0 / 475};
//        Vector real = new Vector(r);
//        System.out.println("Real solution: \t\t\t\t" + real);
//        System.out.println();
//
//        for (int i = 0; i < 100; i++) {
//            Vector v = new Vector(3, -1, 1);
////            System.out.println("x0: \t\t\t\t\t\t" + v);
//            System.out.println();
//            System.out.println("Jacobi approximation: \t\t" + jacobi_iter(v, 0.00005, 100));
//            System.out.println("Gauss-Seidel approximation: " + gs_iter(v, 0.00005, 100));
//        }
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
}
