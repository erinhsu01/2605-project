// Chad Gerhard, Erin Hsu, Michael Sharpe
// 2605 Project 11/24/15

public class solve_qr_b 
{
    double[] diagonal;
    Matrix QRmatrix;

    //Simple function to turn a double array into a matrix
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
    
    //Simple function to find the norm of a matrix
    public double norm(Matrix inputMatrix) 
    {
        double norm = 0;
        for (int i = 0; i<inputMatrix.getRows(); i++)
        {
        	for (int j = 0; j<inputMatrix.getCols(); j++) 
        	{
        		norm = norm + Math.pow(inputMatrix.getElement(i,j), 2);
        	}
        }
        norm = Math.pow(norm, 0.5);
        return norm;
    }
    
    public void qr_fact_househ(Matrix inputMatrix) 
    {
        QRmatrix = inputMatrix;
        diagonal = new double[inputMatrix.getCols()];
        double norm;
        double counter;
        for (int i = 0; i < inputMatrix.getCols(); i++) 
        {
            norm = 0;
            for (int j = i; j < inputMatrix.getRows(); j++) 
            {
                norm = Math.sqrt(Math.pow(norm,2)+ Math.pow(QRmatrix.getElement(j,i),2));
            }

            if (norm != 0)
            {
                if (QRmatrix.getElement(i,i) < 0) 
                {
                    norm *= -1;
                }
                for (int k = i; k < inputMatrix.getRows(); k++) 
                {
                    QRmatrix = set(QRmatrix,k,i,QRmatrix.getElement(k, i)/norm);
                }
                QRmatrix = set(QRmatrix,i,i,QRmatrix.getElement(i, i)+1);

                for (int l = i; l-1 < inputMatrix.getCols(); l++) 
                {
                    counter = 0;
                    for (int m = i; m < inputMatrix.getRows(); m++) 
                    {
                        counter += QRmatrix.getElement(m, i)*QRmatrix.getElement(m, l);
                    }
                    counter *= -1/QRmatrix.getElement(i,i);
                    for (int n = i; n < inputMatrix.getRows(); n++) 
                    {
                        QRmatrix = set(QRmatrix,n,l,QRmatrix.getElement(n, l)+(counter*QRmatrix.getElement(n,i)));
                    }
                }
            }
            diagonal[i] = norm * -1;
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
                if (QRmatrix.getElement(k, k) != 0) 
                {
                    for (int i = k; i < inputMatrix.getRows(); i++)
                    {
                        summation = summation + QRmatrix.getElement(i,k)*qMatrix.getElement(i,j);
                    }
                    summation = (-1 * summation)/QRmatrix.getElement(k, k);
                    
                    for (int i = k; i < inputMatrix.getRows(); i++)
                    {
                        qMatrix = set(qMatrix,i,j,qMatrix.getElement(i, j) + summation*QRmatrix.getElement(i,k));
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
                	rMatrix = set(rMatrix,i,j,QRmatrix.getElement(i, j) * -1);
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
        Matrix coefficientMatrix = new Matrix(inputMatrix.getRows(),inputMatrix.getCols() - 1);
        
        //The B matrix is the rightmost column of the input matrix
        Matrix constantMatrix = new Matrix(inputMatrix.getRows(),1);
        
        //This iterates through the input matrix, populating the coefficient and constant matrices
        for(int i = 0; i < inputMatrix.getRows(); i++)
        {
            for(int j = 0; j < inputMatrix.getCols(); j++)
            {
                if(j != inputMatrix.getCols()-1)
                {
                	coefficientMatrix= set(coefficientMatrix,i,j,inputMatrix.getElement(i, j));
                }
                else
                {
                	constantMatrix = set(constantMatrix,i,0,inputMatrix.getElement(i, j));
                }
            }
        }
        //This turns the coefficient and constant array matrices into Matrix objects, then uses backward substitution to solve
        return Substitution.backwardSubstitution(getRMatrix(coefficientMatrix), getQMatrix(coefficientMatrix).transpose()).times(constantMatrix);
    }
    
    public static Matrix set(Matrix inputMatrix, int i, int j, double x)
    {
    	Matrix newMatrix = new Matrix(inputMatrix.getRows(),inputMatrix.getCols());
        for(int row = 0; row < inputMatrix.getRows(); row++)
        {
        	for(int col = 0; col < inputMatrix.getCols(); col++)
        	{
        		if(row == i && col == j)
        		{
        			newMatrix = set(newMatrix,row,col,x);
        		}
        		else newMatrix = set(newMatrix,row,col,inputMatrix.getElement(row, col));
        	}
        }
        return newMatrix;
    }
        
    
    //Currently broken with improvements to givensQR class. NEEDS FIX
    public static Matrix givensSolve(Matrix inputMatrix) 
    {
    	//The matrix is first broken into an A and B matrices, for the ensuing Ax=B calculation
    	//The A coefficient matrix is the same matrix, minus the rightmost column
        Matrix coefficientMatrix = new Matrix(inputMatrix.getRows(),inputMatrix.getCols() - 1);
        
        //The B matrix is the rightmost column of the input matrix
        Matrix constantMatrix = new Matrix(inputMatrix.getRows(),1);
        
        //This iterates through the input matrix, populating the coefficient and constant matrices
        for(int i = 0; i < inputMatrix.getRows(); i++)
        {
            for(int j = 0; j < inputMatrix.getCols(); j++)
            {
                if(j != inputMatrix.getCols()-1)
                {
                	coefficientMatrix = set(coefficientMatrix,i,j,inputMatrix.getElement(i, j));
                }
                else
                {
                	constantMatrix = set(constantMatrix,i,0,inputMatrix.getElement(i, j));
                }
            }
        }
        //This turns the coefficient and constant array matrices into Matrix objects, then uses backward substitution to solve
        
        //Following section is broken with some impovements to the QR code, requires fixing
      //  GivensQR qrA = new GivensQR(new Matrix(coefficientMatrix));
    //    Matrix qrB = qrA.Q.transpose().times(new Matrix(constantMatrix));
        return inputMatrix;// Substitution.backwardSubstitution(qrA.R, qrB);
    }
}
