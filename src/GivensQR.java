// Chad Gerhard, Erin Hsu, Michael Sharpe
// 2605 Project 11/24/15

import java.util.ArrayList;
public class GivensQR 
{
    ArrayList<Matrix> Givens;
    public Matrix Q;
    public Matrix R;
    public static double error;

    GivensQR(Matrix inputMatrix) 
    {
        Givens = new ArrayList<Matrix>(10);
        R = findR(inputMatrix);
        Q = findQ();
        error = getError(inputMatrix);
    }

    public Matrix findR(Matrix inputMatrix)
    {
        for(int i = 0; i < inputMatrix.getCols(); i++) 
        {
            for(int j = i + 1; j < inputMatrix.getRows(); j++)
            {
                double colElement = inputMatrix.getElement(i, i);
                double rowElement = inputMatrix.getElement(j, i);
                double[][] givensMatrix = new double[inputMatrix.getRows()][inputMatrix.getCols()];
                for (int k = 0; k < inputMatrix.getCols(); k++) 
                {
                	givensMatrix[k][k] = 1;
                }
                givensMatrix[i][i] = (colElement / Math.sqrt(rowElement * rowElement + colElement * colElement));
                givensMatrix[j][j] = (colElement / Math.sqrt(rowElement * rowElement + colElement * colElement));
                givensMatrix[i][j] = -1 * ((-1 * rowElement) / Math.sqrt(rowElement * rowElement + colElement * colElement));
                if (j > i) 
                {
                	givensMatrix[j][i] = ((-1 * rowElement) / Math.sqrt(rowElement * rowElement + colElement * colElement));
                } 
                else if (i > j)
                {
                	givensMatrix[i][j] = givensMatrix[i][j] * -1;
                	givensMatrix[j][i] = -1 * ((-1. * rowElement) / Math.sqrt(rowElement * rowElement + colElement * colElement));
                }
                Matrix returnGivens = new Matrix(givensMatrix);
                Givens.add(returnGivens.transpose());
                inputMatrix = returnGivens.times(inputMatrix);
            }
        }
        return inputMatrix;
    }

    public Matrix findQ() 
    {
        Matrix Q = Givens.get(0);
        for(int i = 0; i < Givens.size(); i++) 
        {
            if(i == 0) 
            {
                Q = Givens.get(i);
            }
            else if(i < Givens.size()-1) 
            {
                Q = Q.times(Givens.get(i + 1));
            }
        }
        return Q;
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
    	error = norm(Q.times(R).subtract(inputMatrix));
        return error;
    }
}
