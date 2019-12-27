package piotr.kedra.adhoc.ahpproblem.entity;

import piotr.kedra.adhoc.ahp.entity.ahpdata.AhpProblemData;

import java.util.List;

public class ProblemSaveAndShareDTO {

    private AhpProblemData ahpProblemData;
    private List<String> emails;

    public ProblemSaveAndShareDTO(AhpProblemData ahpProblemData, List<String> emails) {
        this.ahpProblemData = ahpProblemData;
        this.emails = emails;
    }

    public AhpProblemData getAhpProblemData() {
        return ahpProblemData;
    }

    public List<String> getEmails() {
        return emails;
    }
}
