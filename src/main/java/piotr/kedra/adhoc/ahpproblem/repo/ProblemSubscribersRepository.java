package piotr.kedra.adhoc.ahpproblem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import piotr.kedra.adhoc.ahpproblem.entity.ProblemSubscriber;

import java.util.List;
import java.util.Optional;

public interface ProblemSubscribersRepository extends JpaRepository<ProblemSubscriber, Long> {

    List<ProblemSubscriber> getByProblemId(Long id);
    Optional<ProblemSubscriber> getByProblemIdAndUserID(Long problemId, Long userID);
}
