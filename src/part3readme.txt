Part 3 ReadMe
_____________________________________________

To run Part 3, compile the Part3 java file by entering into the command line
once you are in the correct directory:

    javac Part3.java

Once the source file is compiled, in the command line, run the program by
entering into the command line:

    java Part3

This will create 1000 matrices, open the Java application, and display the two
graphs in the application with each point in the graph being the respective
determinant vs. trace of that particular matrix with color being directed by the
number of iterations that matrix needed.

To run the program again, close the Java application window and run

    java Part3

again in the command line.

Some things to note:
    * The ArrayList returned by the generateRandom2x2Matrix method contains the
      following data in this order:
        * determinant of A
        * trace of A,
        * the number of iterations to get the largest eigenvalue for A
        * the determinant of A-Inverse
        * the trace of A-Inverse
        * the number of iterations to get the smallest eigenvalue of A
        * the largest eigenvalue of A
        * the smallest eigenvalue of A (or the largest eigenvalue of A-Inverse)
        * the eigenvector of A
        * the eigenvector of A-Inverse
    * Instead of creating 1000 matrices, I actually create 2000 matrices
      because some of them will return null because an accurate eigenvalue
      cannot be found with 100 iterations. However, the method that displays
      the points only gets the first 1000 matrices out of the ArrayList.
