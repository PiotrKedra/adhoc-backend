package piotr.kedra.adhoc.ahpproblem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "problem_subscriber")
public class ProblemSubscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private Long userID;

    @Column(length = 8000)
    private String ahpData;

    public ProblemSubscriber() {
    }

    public ProblemSubscriber(Problem problem, Long userID, String ahpData) {
        this.problem = problem;
        this.userID = userID;
        this.ahpData = ahpData;
    }

    public Long getId() {
        return id;
    }

    public Problem getProblem() {
        return problem;
    }

    public Long getUserID() {
        return userID;
    }

    public String getAhpData() {
        return ahpData;
    }
}
