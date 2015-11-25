ABOUT THE PROGRAM

Part 2 covers the Jacobi and Gauss-Seidel methods of solving the matrix equation Ax = b iteratively. 

------

RUNNING THE PROGRAM

Compile and run Part2.java as you would with any other normal java program. If necessary, adjust the size of the window by dragging its edges until it fits on your screen. 

------

USING THE PROGRAM

Upon running, the program will display a window with 4 major sections: the top of the window (referred to as the Basic Information Section), the Part A Section, the Part B & C Section, and the Part D Section.

Basic Information Section
Along the top of the window is the basic information section. This section lists the matrix A and vector b that the rest of the program will use. These two inputs cannot be changed from within the window itself. Also included are two other values, epsilon and M, which can be changed in this section. Epsilon is the maximum difference between the vector solution from one iteration and the next, and it defaults to 0.00005 at the beginning of the program and whenever the program does not recognize the input as a valid number. M is the maximum whole number of iterations for both methods before quitting in failure, and it defaults to 100 at the beginning of the program and whenever the program does not recognize the input as a valid number. The absolute value of epsilon and M will be used upon execution of any part.

Part A Section
The Part A Section is beneath the Basic Information Section on the leftmost part of the window, labeled above by "Part A." This section includes a brief description of part A, a horizontally-scrollable collection of data generated from executing part A, two buttons, and three input fields. Part A will have already executed once when the window first opens, so the scrollable section will already have data within it. 

The first button of the Part A Section is labeled "Execute again with random x0" and, when clicked, will run part A again with x0 being a 3x1 vector with each of its elements randomly generated within the range [-1, 1]. The second button is labeled "Execute again with specific x0:" and, when clicked, will run part A again with x0 being a 3x1 vector with its elements from the three input fields below the button. Each of the input fields defaults to 0.5 at the beginning of the program and whenever the program does not recognize the input as a valid number.

Part B & C Section
The Part B & C Section is located below the Basic Information Section and immediately to the right of the Part A Section. This section contains a brief description of part B and part C, a collection of data generated from executing parts B & C, an input field and button, and a table scrollable both horizontally and vertically. Parts B & C are automatically executed when the window opens.

Clicking the button will execute part A with a random vector x0 as many times as listed in the input field (which defaults to 100 at the beginning of the program and whenever the program does not recognize the input as a valid number). For each of the vectors, the table will automatically fill in the randomly generated x0, the approximate solution using the Jacobi method, the number of iterations it took to reach that solution using Jacobi, the approximate solution using the Gauss-Seidel method, and the number of iterations it took to reach that solution using Gauss-Seidel. 

The average approximate solution using Jacobi and using Gauss-Seidel, as well as the average ratio of iterations it took to reach each approximate solution, will additionally be listed below the description and above the table.

Part D Section
The Part D Section is located below the Basic Information Section and at the rightmost part of the window. This section contains a brief description of part D and a scatter plot of data collected from parts B & C. A new plot will form whenever the button in Part B & C is clicked.