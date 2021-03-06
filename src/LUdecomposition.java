// Chad Gerhard, Erin Hsu, Michael Sharpe
// 2605 Project 11/24/15

import java.util.ArrayList;
import java.util.LinkedList;
public class LUdecomposition
{
    private static Matrix upperTriangle;
    private static Matrix lowerTriangle;

    public static ArrayList<Object> lu_fact(Matrix inputMatrix)
    {
    	LinkedList<Matrix> factorList = new LinkedList<Matrix>();
        double [][]original = inputMatrix.getElements();
        double [][]upper = original;

        for (int i = 0; i < inputMatrix.getRows(); i++)
        {
            for (int j = 1; j < inputMatrix.getRows(); j++)
            {
                if (i < j)
                {
                    double[][] factorMatrices = Matrix.identityMatrix(inputMatrix.getRows()).getElements();
                    factorMatrices[j][i] = -1 * upper[j][i] / upper[i][i];
                    factorList.add(new Matrix(factorMatrices));
                    for (int k = 0; k < inputMatrix.getRows(); k++)
                    {
                        upper[j][k] = upper[j][k] + (factorMatrices[j][i] * upper[i][k]);
                    }
                }
            }
        }

        Matrix factorMatrix = factorList.remove();
        for (int i = 0; i < inputMatrix.getRows(); i++)
        {
            for (int j = 0; j < inputMatrix.getCols(); j++)
            {
                if (i != j)
                {
                    factorMatrix = set(factorMatrix, i, j, -1 * factorMatrix.getElement(i, j));
                }
            }
        }

        lowerTriangle = factorMatrix;
        while (factorList.peek() != null)
        {
        	factorMatrix = factorList.remove();
            for (int i = 0; i < inputMatrix.getRows(); i++)
            {
                for (int j = 0; j < inputMatrix.getCols(); j++)
                {
                    if (i != j)
                    {
                        factorMatrix = set(factorMatrix, i, j, -1 * factorMatrix.getElement(i, j));
                    }
                }
            }
            lowerTriangle = lowerTriangle.times(factorMatrix);
        }

        upperTriangle = new Matrix(upper);
        for(int row = 0; row < lowerTriangle.getRows(); row++)
        {
        	for(int col = 0; col < lowerTriangle.getCols(); col++)
        	{
        	    if(row != col)
        	    {
        		    lowerTriangle = set(lowerTriangle, row, col,
                        lowerTriangle.getElement(row, col) * -1);
        		}
        	}
        }
        ArrayList<Object> ret = new ArrayList<>();
        ret.add(upperTriangle);
        ret.add(lowerTriangle);
        double error = getError(inputMatrix);
        ret.add(error);
        return ret;
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
        			newMatrix[row][col] = x;
        		}
        		else newMatrix[row][col] = inputMatrix.getElement(row, col);
        	}
        }
        return new Matrix(newMatrix);
    }

    //Uses backward and forward substitution to solve a factored system
    public static Matrix solve_lu_b(Matrix inputMatrix, Matrix upperTriangle, Matrix lowerTriangle)
    {
        return
            Substitution.backwardSubstitution(upperTriangle,Substitution.forwardSubstitution(lowerTriangle,inputMatrix));
    }

    //Simple function to find the error in factorization for an already-factored matrix
    public static double getError(Matrix inputMatrix)
    {
        return norm((lowerTriangle.times(upperTriangle)).subtract(inputMatrix));
    }

    //Simple function to find the norm of a input matrix
    public static double norm(Matrix inputMatrix)
    {
        double norm = 0;
        for (int i = 0; i<inputMatrix.getRows(); i++)
        {
        	for (int j = 0; j<inputMatrix.getCols(); j++)
        	{
        		norm += inputMatrix.getElement(i,j) * inputMatrix.getElement(i,j);
        	}
        }
        norm = Math.pow(norm, 0.5);
        return norm;
    }
}
