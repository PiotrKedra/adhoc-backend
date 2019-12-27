package piotr.kedra.adhoc.ahp.entity.ahpdata;

import java.util.List;

public class AhpProblemData {

    private List<Objective> objectives;
    private List<ObjectiveComparison> objectiveComparisons;
    private List<Objective> criteriaList;
    private String[][] criteriaPreferenceMatrix;

    public AhpProblemData() {
    }

    public AhpProblemData(Builder builder) {
        this.objectives = builder.objectives;
        this.objectiveComparisons = builder.objectiveComparisons;
        this.criteriaList = builder.criteriaList;
        this.criteriaPreferenceMatrix = builder.criteriaPreferenceMatrix;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public List<ObjectiveComparison> getObjectiveComparisons() {
        return objectiveComparisons;
    }

    public List<Objective> getCriteriaList() {
        return criteriaList;
    }

    public String[][] getCriteriaPreferenceMatrix() {
        return criteriaPreferenceMatrix;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private List<Objective> objectives;
        private List<ObjectiveComparison> objectiveComparisons;
        private List<Objective> criteriaList;
        private String[][] criteriaPreferenceMatrix;

        public Builder objectives(List<Objective> objectives) {
            this.objectives = objectives;
            return this;
        }

        public Builder objectiveComparisons(List<ObjectiveComparison> objectiveComparisons) {
            this.objectiveComparisons = objectiveComparisons;
            return this;
        }

        public Builder criteriaList(List<Objective> criteriaList) {
            this.criteriaList = criteriaList;
            return this;
        }

        public Builder criteriaPreferenceMatrix(String[][] criteriaPreferenceMatrix) {
            this.criteriaPreferenceMatrix = criteriaPreferenceMatrix;
            return this;
        }

        public AhpProblemData build(){
            return new AhpProblemData(this);
        }
    }
}
