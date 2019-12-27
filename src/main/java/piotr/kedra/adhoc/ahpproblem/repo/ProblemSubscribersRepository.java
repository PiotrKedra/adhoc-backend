package piotr.kedra.adhoc.ahpproblem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import piotr.kedra.adhoc.ahpproblem.entity.ProblemSubscriber;

import java.util.List;

public interface ProblemSubscribersRepository extends JpaRepository<ProblemSubscriber, Long> {

    List<ProblemSubscriber> getByProblemId(Long id);
    List<ProblemSubscriber> getByUserID(Long id);
}
