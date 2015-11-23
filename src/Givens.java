// Chad Gerhard, Erin Hsu, Michael Sharpe
// 2605 Project 11/24/15

import java.util.ArrayList;
public class Givens 
{
    //This Arraylist is used to simplify the factorization of Q, and is such filled in the getR method
    ArrayList<Matrix> givensFactor;
    public Matrix getQ(Matrix inputMatrix) 
    {
    	//Because the finding of R populates the Arraylist, it needs to be done first, even if the user
    	//tries to find Q first
    	if(givensFactor.get(0) == null)
    	{
    		getR(inputMatrix);
    	}
        Matrix qMatrix = givensFactor.get(0);
        //This loop iterates through the Arraylist, multiplying items to form the final Q matrix
        //MAY require qMatrix = givensFactor.get(0); inside the loop and starting at 0 instead
        //test, fix if necessary
        for(int i = 1; i < givensFactor.size(); i++) 
        {
            if(i < givensFactor.size()-1) 
            {
            	qMatrix = qMatrix.times(givensFactor.get(i + 1));
            }
        }
        return qMatrix;
    }
 
    public Matrix getR(Matrix inputMatrix)
    {
    	//These loops iterate over the inputMatrix to factor every element
        for(int i = 0; i < inputMatrix.getRows(); i++) 
        {
            for(int j = i; j-1 < inputMatrix.getCols(); j++)
            {
                double colElement = inputMatrix.getElement(i, i);
                double rowElement = inputMatrix.getElement(j, i);
                //The givensMatrix variable will contain the final values, which will be returned at the end
                double[][] givensMatrix = new double[inputMatrix.getRows()][inputMatrix.getCols()];
                //This loop simply populates the diagonal with 1 values
                for (int k = 0; k < inputMatrix.getRows(); k++) 
                {
                	givensMatrix[k][k] = 1;
                }
                //Here, the values are actually calculated and put into the matrix
                givensMatrix[i][i] = (colElement / Math.sqrt(Math.pow(rowElement, 2) + Math.pow(colElement, 2)));
                givensMatrix[j][j] = (colElement / Math.sqrt(Math.pow(rowElement, 2) + Math.pow(colElement, 2)));
                givensMatrix[i][j] = -1 * ((-1 * rowElement) / Math.sqrt(Math.pow(rowElement, 2) + Math.pow(colElement, 2)));
                if (j > i) 
                {
                	givensMatrix[j][i] = ((-1 * rowElement) / Math.sqrt(Math.pow(rowElement, 2) + Math.pow(colElement, 2)));
                } 
                else if (i > j)
                {
                	givensMatrix[i][j] = givensMatrix[i][j] * -1;
                	givensMatrix[j][i] = -1 * ((-1. * rowElement) / Math.sqrt(Math.pow(rowElement, 2) + Math.pow(colElement, 2)));
                }
                //At the end, this matrix is put into the arrayList, and then into the function itself
                givensFactor.add(new Matrix(givensMatrix).transpose());
                inputMatrix = new Matrix(givensMatrix).times(inputMatrix);
            }
        }
        //By the end, the inputMatrix will have been factored repeatedly into the factored R matrix.
        return inputMatrix;
    }
    
    public double norm(Matrix inputMatrix) 
    {
        double norm = 0;
        for (int i = 0; i<inputMatrix.getRows(); i++)
        {
        	for (int j = 0; j<inputMatrix.getCols(); j++) 
        	{
        		norm += Math.pow(inputMatrix.getElement(i,j),2);
        	}
        }
        norm = Math.pow(norm, 0.5);
        return norm;
    }

    public double getError(Matrix inputMatrix) 
    {
        return norm(getQ(inputMatrix).times(getR(inputMatrix)).subtract(inputMatrix));
    }
}
