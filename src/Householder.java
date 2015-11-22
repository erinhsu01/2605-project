// Chad Gerhard, Erin Hsu, Michael Sharpe
// 2605 Project 11/24/15

public class Householder 
{
    double[][] QR;
    double[][] qVals;
    double[][] rVals;
    
    //A simple function to turn a double array into a matrix
    public double[][] getArrayCopy(Matrix inputMatrix)
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
        QR = getArrayCopy(inputMatrix);
        double counter;
        double norm;
        for (int i = 0; i < inputMatrix.getCols(); i++) 
        {
            norm = 0;
            for (int j = i; j < inputMatrix.getRows(); j++)
            {
                norm = Math.hypot(norm, QR[j][i]);
            }
            if (norm != 0) 
            {
                if (QR[i][i] < 0) 
                {
                    norm = -1 * norm;
                }
                for (int k = i; k < inputMatrix.getRows(); k++) 
                {
                    QR[k][i] = (QR[k][i]/norm);
                }
                QR[i][i]++;
                for (int l = i+1; l < inputMatrix.getCols(); l++)
                {
                    counter = 0;
                    for (int m = i; m < inputMatrix.getRows(); m++)
                    {
                    	counter = counter + (QR[m][i]*QR[m][l]);
                    }
                    counter =  (counter*-1)/QR[i][i];
                    for (int n = i; n < inputMatrix.getRows(); n++) 
                    {
                        QR[n][l] = QR[n][l] + (counter*QR[n][i]);
                    }
                }
            }
        }
    }
}
