package piotr.kedra.adhoc.ahp.service;

import org.springframework.stereotype.Component;
import piotr.kedra.adhoc.ahp.entity.IncompleteEquation;


@Component
public class IncompleteEquationMapper {

    private static final String MISSING_ELEMENT = "?";

    IncompleteEquation mapIncompleteMatrix(String[][] incompleteMatrix){
        int matrixSize = incompleteMatrix.length;
        double[][] matrixM = new double[matrixSize][matrixSize];
        double[] vectorR = new double[matrixSize];

        for (int i = 0; i < incompleteMatrix.length; ++i) {
            int quantityOfMissingEleInRow = 0;
            double rValue = 0;
            for (int j = 0; j < incompleteMatrix[i].length; j++) {
                if(MISSING_ELEMENT.equals(incompleteMatrix[i][j])){
                    matrixM[i][j] = 1;
                    ++quantityOfMissingEleInRow;
                }else {
                    matrixM[i][j] = 0;
                    rValue += Math.log(Double.valueOf(incompleteMatrix[i][j]));
                }
            }
            matrixM[i][i] = matrixSize - quantityOfMissingEleInRow;
            vectorR[i] = rValue;
        }
        return buildEquation(matrixM, vectorR);
    }

    private IncompleteEquation buildEquation(double[][] matrixM, double[] vectorR) {
        return IncompleteEquation.builder()
                .matrixM(matrixM)
                .vectorR(vectorR)
                .build();
    }
}
