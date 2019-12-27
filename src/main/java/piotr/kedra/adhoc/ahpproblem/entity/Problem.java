package piotr.kedra.adhoc.ahpproblem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "problem")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerID;
    private String objectives;
    private String criterias;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    private List<ProblemSubscriber> problemSubscribers;

    public Problem() {
    }

    public Problem(Long ownerID, String objectives, String criterias) {
        this.ownerID = ownerID;
        this.objectives = objectives;
        this.criterias = criterias;
    }

    public Problem(Long ownerID, String objectives, String criterias, List<ProblemSubscriber> problemSubscribers) {
        this.ownerID = ownerID;
        this.objectives = objectives;
        this.criterias = criterias;
        this.problemSubscribers = problemSubscribers;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerID() {
        return ownerID;
    }

    public String getObjectives() {
        return objectives;
    }

    public String getCriterias() {
        return criterias;
    }

    public List<ProblemSubscriber> getProblemSubscribers() {
        return problemSubscribers;
    }
}
