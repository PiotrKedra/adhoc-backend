package piotr.kedra.adhoc.ahpproblem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import piotr.kedra.adhoc.ahpproblem.entity.Problem;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query(value = "select * from problem where id in (select s.problem_id from problem_subscriber s where s.userid = ?1)", nativeQuery = true)
    List<Problem> getProblemsBySubscriberId(Long userid);

    Optional<Problem> getById(Long id);
    List<Problem> getByOwnerID(Long id);
}
