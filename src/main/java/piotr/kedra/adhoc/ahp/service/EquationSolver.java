package piotr.kedra.adhoc.ahp.service;

import org.apache.commons.math3.linear.*;
import org.springframework.stereotype.Component;

@Component
public class EquationSolver {

    double[] solve(double[][] matrixM, double[] vectorR){
        double[] vectorWideHat = solveLinearEquation(matrixM, vectorR);
        double[] vectorW = calculateVectorW(vectorWideHat);
        normalize(vectorW);
        return vectorW;
    }

    private double[] solveLinearEquation(double[][] matrixM, double[] vectorR){
        RealMatrix coefficients = new Array2DRowRealMatrix(matrixM, false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
        RealVector constants = new ArrayRealVector(vectorR, false);
        RealVector solution = solver.solve(constants);
        return solution.toArray();
    }

    private double[] calculateVectorW(double[] vectorWideHat) {
        double[] vectorW = new double[vectorWideHat.length];
        for (int i = 0; i < vectorWideHat.length; ++i) {
            vectorW[i] = Math.exp(vectorWideHat[i]);
        }
        return vectorW;
    }

    private void normalize(double[] vector){
        double sum = sumUp(vector);
        for (int i=0; i<vector.length; ++i){
            vector[i] = vector[i]/sum;
        }
    }

    private double sumUp(double[] vector){
        double sum = 0;
        for (double number : vector) {
            sum += number;
        }
        return sum;
    }
}
