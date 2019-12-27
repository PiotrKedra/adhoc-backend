package piotr.kedra.adhoc.ahpproblem.service.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import piotr.kedra.adhoc.ahp.entity.ahpdata.AhpProblemData;
import piotr.kedra.adhoc.ahp.entity.ahpdata.ObjectiveComparison;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ParserService {

    @Autowired
    private MatrixParser matrixParser;

    public String parseAHPData(AhpProblemData ahpProblemData){
        StringBuilder result = new StringBuilder();
        String criteriaMatrix = matrixParser.parseMatrix(ahpProblemData.getCriteriaPreferenceMatrix());

        result.append(criteriaMatrix);
        result.append("|");

        for(ObjectiveComparison comparison: ahpProblemData.getObjectiveComparisons()){
            result.append(comparison.getCriteriaName());
            result.append(">");
            String objectiveMatrix = matrixParser.parseMatrix(comparison.getObjectiveComparisonMatrix());
            result.append(objectiveMatrix);
            result.append("/");
        }
        result.deleteCharAt(result.length() - 1);
        return result.toString();
    }

    public Map.Entry<String[][], List<ObjectiveComparison>> parseToAhpObjects(String ahpData){
        String[] split = ahpData.split("\\|");
        String[][] criteriaMatrix = matrixParser.parseToMatrix(split[0]);

        String[] comparisons = split[1].split("/");

        List<ObjectiveComparison> objectiveComparisons = new ArrayList<>();
        for(String comparison: comparisons){
            String[] split2 = comparison.split(">");
            String[][] objectiveMatrix = matrixParser.parseToMatrix(split2[1]);
            ObjectiveComparison objectiveComparison = new ObjectiveComparison(split2[0], objectiveMatrix);
            objectiveComparisons.add(objectiveComparison);
        }
        return new AbstractMap.SimpleEntry<>(criteriaMatrix, objectiveComparisons);
    }
}
