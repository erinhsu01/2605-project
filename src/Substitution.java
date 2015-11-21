// Chad Gerhard, Erin Hsu, Michael Sharpe
// 2605 Project 11/24/15

public class Substitution
{
    public static void main(String[] args) {
        // for testing purposes
//        double[][] upperT = {
//                {1, -3, 1},
//                {0, -2, 6},
//                {0, 0, -54}
//        };
//        Matrix matrix = new Matrix(upperT);
//
//        double[][] lowerT = {
//                {3, 0, 0, 0},
//                {-1, 1, 0, 0},
//                {3, -2, -1, 0},
//                {1, -2, 6, 2}
//        };
//        Matrix matrix1 = new Matrix(lowerT);
//        double[][] b1 = {{5}, {6}, {4}, {2}};
//        double[][] b2 = {{4}, {-10}, {108}};
//        Matrix bVector1 = new Matrix(b1);
//        Matrix bVector = forwardSubstitution(matrix1, bVector1);
//        System.out.println(bVector);
//
//        double[][] exp = {{3}, {-1}, {2}};
//        Matrix expected = new Matrix(exp);
//
//        Matrix backSub = backwardSubstitution(matrix, bVector);
//        Matrix forwardSub = forwardSubstitution(matrix, bVector);
//
//        System.out.println(expected);
//        System.out.println(backSub);
    }

	public static Matrix forwardSubstitution(Matrix coefficientMatrix, Matrix constantMatrix)
    {
    	//Copies of the matrix are made to convert them into double arrays
        double[][] coefficientCopy = coefficientMatrix.getElements();
        double[][] constantCopy = constantMatrix.getElements();
        
        //The vector solution always has a width of one and a height equal to that of the original matrices
        double[][] solutionVector = new double[coefficientMatrix.getRows()][1];
		double solutionValue = 0.0;
		
		//This loop iterates through the entirety of the input coefficient matrix, but only cares to evaluate the diagonal terms.
        solutionVector[0][0] = constantCopy[0][0] / coefficientCopy[0][0];
        for (int i = 0; i < coefficientMatrix.getRows(); i++) {
            if (coefficientCopy[i][i] != 0) {
                for (int j = 0; j < i; j++) {
                    solutionValue += coefficientCopy[i][j] * solutionVector[j][0];
                }
            }
            solutionVector[i][0] = (constantCopy[i][0] - solutionValue) / coefficientCopy[i][i];
            solutionValue = 0;
        }
//        for (int i = 0; i < coefficientMatrix.getRows(); i++)
//        {
//            for (int j = 0; j < coefficientMatrix.getCols(); j++)
//            {
//                if(i == j)
//                {
//                	//This conditional looks for nonzero terms in the diagonal, which require processing
//                	if(i != 0)
//                	{
//                		solutionValue = 0;
//                		for (int k = 0; k < j-1; k++)
//                		{
//                			solutionValue += (solutionVector[k][0] * coefficientCopy[j][k]);
//                		}
//                		solutionVector[i][0] = (constantCopy[j][0] - solutionValue) / coefficientCopy[i][j];
//                	}
//                	//If the term is 0, then the solution number requires no special processing
//                	else
//                	{
//                		solutionVector[i][0] = constantCopy[i][0] / coefficientCopy[j][j];
//                	}
//                }
//            }
//        }
        //Should return a 1x(height of original matrix) matrix which can be interpreted as a vector solution
        return new Matrix(solutionVector);
    }
	
	//This function solves a linear system that has been put into some row echelon form
    //It's not necessary reduced row echelon, but must be an upper triangle matrix
    public static Matrix backwardSubstitution(Matrix upperTriangle, Matrix constantMatrix)
    {
    	//This result will hold a 1 width by original matrix's height matrix, which is the vector solution
        double[][] result = new double[upperTriangle.getCols()][1];
        for(int i = upperTriangle.getRows() - 1; i >= 0; i--)
        {
        	//Because this evaluates upper triangle matrices, the diagonal pivots are used to process the other upper values, being the triangle's i by i values.
            double x = constantMatrix.getElement(i, 0) / upperTriangle.getElement(i, i);
            double subtractionValue;
            
            //These loops iterate through the upper triangle matrix from the bottom to the top, because the goal of backward substitution
            //is to solve an upper triangle matrix, meaning the bottom row has a trivial solution: the corresponding constant/the coefficient = solution
            for(int j = 0; j < upperTriangle.getCols(); j++)
            {
            	//These conditionals verify a diagonal value isn't being simplified
            	if(i != j)
                {
            		subtractionValue = (result[j][0]* upperTriangle.getElement(i, j) / upperTriangle.getElement(i, i));
                    x -= subtractionValue;
                }
            }
            result[i][0] = x;
        } 
        return new Matrix(result);
    }
    
    public static Matrix set(Matrix inputMatrix, int i, int j, double x)
    {
    	double[][] newMatrix = new double[inputMatrix.getRows()][inputMatrix.getCols()];
        for(int row = 0; row < inputMatrix.getRows(); row++)
        {
        	for(int col = 0; col < inputMatrix.getCols(); col++)
        	{
        		if(row == i && col == j)
        		{
        			newMatrix[i][j] = x;
        		}
        		else newMatrix[i][j] = inputMatrix.getElement(i, j);
        	}
        }
        return new Matrix(newMatrix);
    }
    
}
