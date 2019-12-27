package piotr.kedra.adhoc.ahp.service;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.util.Precision;

// TODO: 03.11.2019 remove if not use in future
class EigenvectorCalculator {

    private static final String EIGENVECTOR_CALCULATING_INFO = "Break at: %dth iteration (eigenvector)";

    private static final int MAX_ITERATION = 10;
    private static final double EPSILON = 1e-5;

    double[] calculate(RealMatrix matrix){
        double[] eigenvector = getPossibleEigenvector(matrix);
        for (int i=0; i<MAX_ITERATION; ++i) {
            matrix = matrix.multiply(matrix);
            double[] nextEigenvector = getPossibleEigenvector(matrix);
            if (vectorsAreSame(eigenvector, nextEigenvector)){
                System.out.println(String.format(EIGENVECTOR_CALCULATING_INFO, i));
                break;
            }
            eigenvector = nextEigenvector;
        }
        return eigenvector;
    }

    private double[] getPossibleEigenvector(RealMatrix matrix){
        double[] eigenvector = new double[matrix.getRowDimension()];
        for (int i = 0; i < matrix.getRowDimension(); ++i) {
            eigenvector[i] = sumUp(matrix.getRow(i));
        }
        normalize(eigenvector);
        return eigenvector;
    }

    private double sumUp(double[] vector){
        double sum = 0;
        for (double number : vector) {
            sum += number;
        }
        return sum;
    }

    private void normalize(double[] vector){
        double sum = sumUp(vector);
        for (int i=0; i<vector.length; ++i){
            vector[i] = vector[i]/sum;
        }
    }

    private boolean vectorsAreSame(double[] eigenvector, double[] nextEigenvector) {
        for (int i=0; i<eigenvector.length; ++i) {
            if (!Precision.equals(eigenvector[i], nextEigenvector[i], EPSILON)){
                return false;
            }
        }
        return true;
    }
}
