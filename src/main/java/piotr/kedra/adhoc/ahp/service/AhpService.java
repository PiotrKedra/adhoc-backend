package piotr.kedra.adhoc.ahp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import piotr.kedra.adhoc.ahp.entity.IncompleteEquation;
import piotr.kedra.adhoc.ahp.entity.ahpdata.AhpProblemData;
import piotr.kedra.adhoc.ahp.entity.ahpdata.Eigenvector;
import piotr.kedra.adhoc.ahp.entity.ahpdata.Objective;
import piotr.kedra.adhoc.ahp.entity.ahpdata.ObjectiveComparison;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class AhpService {

    private final IncompleteEquationMapper incompleteEquationMapper;
    private final EquationSolver solver;

    @Autowired
    public AhpService(IncompleteEquationMapper incompleteEquationMapper, EquationSolver solver) {
        this.incompleteEquationMapper = incompleteEquationMapper;
        this.solver = solver;
    }

    public Map<String, Double> calculate(AhpProblemData problemData){
        Map<String, Double> criteriaWeights = getCriteriaWeights(problemData);
        List<Eigenvector> criteriaEigenvectors = calculateCriteriaEigenvectors(problemData);
        return getRanking(problemData.getObjectives(), criteriaWeights, criteriaEigenvectors);
    }

    private Map<String, Double> getCriteriaWeights(AhpProblemData data){
        double[] preferenceEigenvector = getEigenvector(data.getCriteriaPreferenceMatrix());
        return data.getCriteriaList().stream()
                .collect(Collectors.toMap(Objective::getName, c -> preferenceEigenvector[c.getIndex()]));
    }

    private double[] getEigenvector(String[][] matrix) {
        IncompleteEquation equation = incompleteEquationMapper.mapIncompleteMatrix(matrix);
        return solver.solve(equation.getMatrixM(), equation.getVectorR());
    }

    private List<Eigenvector> calculateCriteriaEigenvectors(AhpProblemData problemData) {
        return problemData.getObjectiveComparisons().stream()
                .map(this::mapToEigenvector)
                .collect(Collectors.toList());
    }

    private Eigenvector mapToEigenvector(ObjectiveComparison objectiveComparison) {
        double[] eigenvector = getEigenvector(objectiveComparison.getObjectiveComparisonMatrix());
        return new Eigenvector(objectiveComparison.getCriteriaName(), eigenvector);
    }

    private Map<String, Double> getRanking(List<Objective> objectives, Map<String, Double> criteriaWeights, List<Eigenvector> criteriaEigenvectors) {
        Map<String, Double> result = new HashMap<>();
        for(Objective objective: objectives){
            double value = calculateRankingValue(criteriaWeights, criteriaEigenvectors, objective);
            result.put(objective.getName(), value);
        }
        return sortRanking(result);
    }

    private double calculateRankingValue(Map<String, Double> criteriaWeights, List<Eigenvector> criteriaEigenvectors, Objective objective) {
        double value = 0;
        for(Eigenvector criteria: criteriaEigenvectors){
            value += criteria.getEigenvector()[objective.getIndex()] * criteriaWeights.get(criteria.getName());
        }
        return value;
    }

    private Map<String, Double> sortRanking(Map<String, Double> result) {
        return result.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

//    public static void main(String[] args) {
//        AhpService ahp = new AhpService(new IncompleteEquationMapper(), new EquationSolver());
//
//        List<Objective> objectives = Arrays.asList(new Objective("Skoda", 0), new Objective("VW", 1), new Objective("Mercedes", 2));
//
//        String[][] price = {{"1","2","7"},{"0.5","1","5"},{"0.1428","0.2","1"}};
//        String[][] apperance = {{"1","1","0.3333"},{"1","1","0.5"},{"3","2","1"}};
//        String[][] equipment = {{"1","0.5","0.2"},{"2","1","0.3333"},{"5","3","1"}};
//
//        List<ObjectiveComparison> objectiveComparisons = Arrays.asList(new ObjectiveComparison("Price", price),
//                new ObjectiveComparison("Apperance", apperance),
//                new ObjectiveComparison("Equipment", equipment));
//
//        List<Objective> criterias = Arrays.asList(new Objective("Price", 0), new Objective("Apperance", 1), new Objective("Equipment", 2));
//
//        String[][] weight = {{"1","5","7"},{"0.2","1","2"},{"0.1428","0.5","1"}};
//
//        AhpProblemData data = new AhpProblemData(objectives, objectiveComparisons, criterias, weight);
//
//        System.out.println(ahp.calculate(data));
//    }
}