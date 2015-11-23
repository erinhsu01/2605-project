// Chad Gerhard, Erin Hsu, Michael Sharpe
// 2605 Project 11/24/15

public class Householder 
{
    double[][] factoredMatrix;
    //A simple function to turn a double array into a matrix
    public double[][] doubletoMatrix(Matrix inputMatrix)
    {
    	double[][] newMatrix = new double[inputMatrix.getRows()][inputMatrix.getCols()];
        for(int row = 0; row < inputMatrix.getRows(); row++)
        {
        	for(int col = 0; col < inputMatrix.getCols(); col++)
        	{
        		newMatrix[row][col] = inputMatrix.getElement(row, col);
        	}
        }
        return newMatrix;
    }
    
    
    public void qr_fact_househ(Matrix inputMatrix) 
    {
        factoredMatrix = doubletoMatrix(inputMatrix);
        double counter;
        double norm;
        for (int i = 0; i < inputMatrix.getCols(); i++) 
        {
            norm = 0;
            for (int j = i; j < inputMatrix.getRows(); j++)
            {
                norm = Math.sqrt(Math.pow(norm,2) + Math.pow(factoredMatrix[j][i],2));
            }
            if (norm != 0)
            {
                if (factoredMatrix[i][i] < 0) 
                {
                    norm = -1 * norm;
                }
                for (int k = i; k < inputMatrix.getRows(); k++) 
                {
                    factoredMatrix[k][i] = (factoredMatrix[k][i]/norm);
                }
                factoredMatrix[i][i]++;
                for (int l = i; l-1 < inputMatrix.getCols(); l++)
                {
                    counter = 0;
                    for (int m = i; m < inputMatrix.getRows(); m++)
                    {
                    	counter = counter + (factoredMatrix[m][i]*factoredMatrix[m][l]);
                    }
                    counter =  (counter*-1)/factoredMatrix[i][i];
                    for (int n = i; n < inputMatrix.getRows(); n++) 
                    {
                        factoredMatrix[n][l] = factoredMatrix[n][l] + (counter*factoredMatrix[n][i]);
                    }
                }
            }
        }
    }
}
