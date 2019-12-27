package piotr.kedra.adhoc.ahpproblem.service.parser;

import org.springframework.stereotype.Component;

@Component
public class MatrixParser {

    String parseMatrix(String[][] matrix){
        StringBuilder result = new StringBuilder();
        for(String[] row: matrix){
            for(String ele: row){
                result.append(ele);
                result.append(",");
            }
            result.deleteCharAt(result.length() - 1);
            result.append(";");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    String[][] parseToMatrix(String matrixString){
        String[] rows = matrixString.split(";");
        String[][] matrix = new String[rows.length][rows.length];
        int index = 0;
        for(String row: rows){
            String[] values = row.split(",");
            matrix[index] = values;
            ++index;
        }
        return matrix;
    }
}
