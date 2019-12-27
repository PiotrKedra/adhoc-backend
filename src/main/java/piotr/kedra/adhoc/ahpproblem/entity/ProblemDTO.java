package piotr.kedra.adhoc.ahpproblem.entity;


import java.util.List;

public class ProblemDTO {

    private Long id;
    private Long ownerID;
    private List<String> objectives;
    private List<String> criterias;

    public ProblemDTO() {
    }

    public ProblemDTO(Builder builder) {
        this.id = builder.id;
        this.ownerID = builder.ownerID;
        this.objectives = builder.objectives;
        this.criterias = builder.criterias;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerID() {
        return ownerID;
    }

    public List<String> getObjectives() {
        return objectives;
    }

    public List<String> getCriterias() {
        return criterias;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Long id;
        private Long ownerID;
        private List<String> objectives;
        private List<String> criterias;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder ownerID(Long ownerID) {
            this.ownerID = ownerID;
            return this;
        }

        public Builder objectives(List<String> objectives) {
            this.objectives = objectives;
            return this;
        }

        public Builder criterias(List<String> criterias) {
            this.criterias = criterias;
            return this;
        }

        public ProblemDTO build(){
            return new ProblemDTO(this);
        }
    }
}
