package piotr.kedra.adhoc.ahp.entity.ahpdata;

public class ObjectiveComparison {

    private String criteriaName;
    private String[][] objectiveComparisonMatrix;

    public ObjectiveComparison(String criteriaName, String[][] objectiveComparisonMatrix) {
        this.criteriaName = criteriaName;
        this.objectiveComparisonMatrix = objectiveComparisonMatrix;
    }

    public String getCriteriaName() {
        return criteriaName;
    }

    public String[][] getObjectiveComparisonMatrix() {
        return objectiveComparisonMatrix;
    }
}
