package piotr.kedra.adhoc.ahp.entity;

public class RankingPlacementDTO {

    private String objectiveName;
    private double value;

    public RankingPlacementDTO() {
    }

    public RankingPlacementDTO(String objectiveName, double value) {
        this.objectiveName = objectiveName;
        this.value = value;
    }

    public String getObjectiveName() {
        return objectiveName;
    }

    public double getValue() {
        return value;
    }
}
