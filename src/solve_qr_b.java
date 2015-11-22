// Chad Gerhard, Erin Hsu, Michael Sharpe
// 2605 Project 11/24/15

public class solve_qr_b 
{
    double[] diagonal;
    double[][] QRmatrix;

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
    
    public double norm(Matrix inputMatrix) 
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
    
    public void qr_fact_househ(Matrix inputMatrix) 
    {
        QRmatrix = getArrayCopy(inputMatrix);
        diagonal = new double[inputMatrix.getCols()];
        double norm;
        for (int k = 0; k < inputMatrix.getCols(); k++) 
        {
            norm = 0;
            for (int i = k; i < inputMatrix.getRows(); i++) 
            {
                norm = Math.hypot(norm, QRmatrix[i][k]);
            }

            if (norm != 0)
            {
                if (QRmatrix[k][k] < 0) 
                {
                    norm *= -1;
                }
                for (int i = k; i < inputMatrix.getRows(); i++) 
                {
                    QRmatrix[i][k] = QRmatrix[i][k]/norm;
                }
                QRmatrix[k][k] += 1;

                for (int j = k+1; j < inputMatrix.getCols(); j++) 
                {
                    double x = 0;
                    for (int i = k; i < inputMatrix.getRows(); i++) 
                    {
                        x += QRmatrix[i][k]*QRmatrix[i][j];
                    }
                    x = -x/QRmatrix[k][k];
                    for (int i = k; i < inputMatrix.getRows(); i++) 
                    {
                        QRmatrix[i][j] += x*QRmatrix[i][k];
                    }
                }
            }
            diagonal[k] = -norm;
        }

    }

    public Matrix getQMatrix(Matrix inputMatrix) 
    {
        Matrix qMatrix = new Matrix(inputMatrix.getRows(),inputMatrix.getCols());
        double summation = 0;
        for (int k = inputMatrix.getCols()-1; k >= 0; k--) 
        {
            for (int i = 0; i < inputMatrix.getRows(); i++) 
            {
                qMatrix = set(qMatrix,i,k,0);
            }
            qMatrix = set(qMatrix,k,k,1);
            for (int j = k; j < inputMatrix.getCols(); j++) 
            {
                if (QRmatrix[k][k] != 0) 
                {
                    for (int i = k; i < inputMatrix.getRows(); i++)
                    {
                        summation = summation + QRmatrix[i][k]*qMatrix.getElement(i,j);
                    }
                    summation = (-1 * summation)/QRmatrix[k][k];
                    for (int i = k; i < inputMatrix.getRows(); i++)
                    {
                        qMatrix = set(qMatrix,i,j,qMatrix.getElement(i, j) + summation*QRmatrix[i][k]);
                    }
                }
            }
        }
        
        for (int i = 0; i < qMatrix.getRows(); i++)
        {
            for (int j = 0; j < qMatrix.getCols(); j++) 
            {
                if (qMatrix.getElement(i,j) != 0) 
                {
                    qMatrix = set(qMatrix,i,j,qMatrix.getElement(i,j) * -1);
                }
            }
        }
        return qMatrix;
    }

    public Matrix getRMatrix(Matrix inputMatrix) 
    {
        Matrix rMatrix = new Matrix(inputMatrix.getRows(),inputMatrix.getCols());
        for (int i = 0; i < inputMatrix.getRows(); i++) 
        {
            for (int j = 0; j < inputMatrix.getCols(); j++) 
            {
                if (i < j) 
                {
                	rMatrix = set(rMatrix,i,j,QRmatrix[i][j] * -1);
                } 
                else if (i > j)
                {
                	rMatrix = set(rMatrix,i,j,0);
                }
                else rMatrix = set(rMatrix,i,j,-diagonal[i]);
            }
        }
        return rMatrix;
    }
    
    public Matrix houseSolve(Matrix inputMatrix) 
    {
    	//The matrix is first broken into an A and B matrices, for the ensuing Ax=B calculation
    	//The A coefficient matrix is the same matrix, minus the rightmost column
        double[][] coefficientMatrix = new double[inputMatrix.getRows()][inputMatrix.getCols() - 1];
        
        //The B matrix is the rightmost column of the input matrix
        double[][] constantMatrix = new double[inputMatrix.getRows()][1];
        
        //This iterates through the input matrix, populating the coefficient and constant matrices
        for(int i = 0; i < inputMatrix.getRows(); i++)
        {
            for(int j = 0; j < inputMatrix.getCols(); j++)
            {
                if(j != inputMatrix.getCols()-1)
                {
                	coefficientMatrix[i][j] = inputMatrix.getElement(i, j);
                }
                else
                {
                	constantMatrix[i][0] = inputMatrix.getElement(i, j);
                }
            }
        }
        //This turns the coefficient and constant array matrices into Matrix objects, then uses backward substitution to solve
        Matrix aMatrix = new Matrix(coefficientMatrix);
        Matrix bMatrix = new Matrix(constantMatrix);
        return Substitution.backwardSubstitution(getRMatrix(aMatrix), getQMatrix(aMatrix).transpose()).times(bMatrix);
    }
    
    public Matrix set(Matrix inputMatrix, int i, int j, double x)
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
        
    public static Matrix givensSolve(Matrix inputMatrix) 
    {
    	//The matrix is first broken into an A and B matrices, for the ensuing Ax=B calculation
    	//The A coefficient matrix is the same matrix, minus the rightmost column
        double[][] coefficientMatrix = new double[inputMatrix.getRows()][inputMatrix.getCols() - 1];
        
        //The B matrix is the rightmost column of the input matrix
        double[][] constantMatrix = new double[inputMatrix.getRows()][1];
        
        //This iterates through the input matrix, populating the coefficient and constant matrices
        for(int i = 0; i < inputMatrix.getRows(); i++)
        {
            for(int j = 0; j < inputMatrix.getCols(); j++)
            {
                if(j != inputMatrix.getCols()-1)
                {
                	coefficientMatrix[i][j] = inputMatrix.getElement(i, j);
                }
                else
                {
                	constantMatrix[i][0] = inputMatrix.getElement(i, j);
                }
            }
        }
        //This turns the coefficient and constant array matrices into Matrix objects, then uses backward substitution to solve
        GivensQR qrA = new GivensQR(new Matrix(coefficientMatrix));
        Matrix QTb = qrA.Q.transpose().times(new Matrix(constantMatrix));
        return Substitution.backwardSubstitution(qrA.R, QTb);
    }
}
