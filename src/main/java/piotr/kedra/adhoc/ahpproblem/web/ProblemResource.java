package piotr.kedra.adhoc.ahpproblem.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import piotr.kedra.adhoc.ahpproblem.entity.*;
import piotr.kedra.adhoc.ahpproblem.repo.ProblemRepository;
import piotr.kedra.adhoc.ahpproblem.repo.ProblemSubscribersRepository;
import piotr.kedra.adhoc.ahpproblem.service.AhpProblemService;
import piotr.kedra.adhoc.ahpproblem.service.parser.CriteriaParser;
import piotr.kedra.adhoc.auth.UserContext;
import piotr.kedra.adhoc.auth.UserRepository;
import piotr.kedra.adhoc.auth.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("problems")
public class ProblemResource {

    private final UserContext userContext;

    private final ProblemRepository problemRepository;

    private final CriteriaParser criteriaParser;

    private final ProblemSubscribersRepository subscribersRepository;

    private final AhpProblemService ahpProblemService;

    private final UserRepository userRepository;

    @Autowired
    public ProblemResource(UserContext userContext, ProblemRepository problemRepository, CriteriaParser criteriaParser, ProblemSubscribersRepository subscribersRepository, AhpProblemService ahpProblemService, UserRepository userRepository) {
        this.userContext = userContext;
        this.problemRepository = problemRepository;
        this.criteriaParser = criteriaParser;
        this.subscribersRepository = subscribersRepository;
        this.ahpProblemService = ahpProblemService;
        this.userRepository = userRepository;
    }


    @GetMapping(produces = "application/json")
    public ResponseEntity getAll(){
        List<Problem> problems = problemRepository.getByOwnerID(userContext.getUserID());
        List<ProblemDTO> problemDTOs = problems.stream().map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(problemDTOs);
    }

    private ProblemDTO mapToDTO(Problem problem) {
        return ProblemDTO.builder()
                .id(problem.getId())
                .ownerID(problem.getOwnerID())
                .objectives(criteriaParser.parseToList(problem.getObjectives()))
                .criterias(criteriaParser.parseToList(problem.getCriterias()))
                .build();
    }

    @GetMapping(produces = "application/json")
    @RequestMapping("shared")
    public ResponseEntity getAllShared(){
        List<Problem> allById = problemRepository.getProblemsBySubscriberId(userContext.getUserID());

        List<ProblemDTO> shared = allById.stream().filter(problem -> !problem.getOwnerID().equals(userContext.getUserID())).map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(shared);
    }


    @PostMapping(consumes = "application/json")
    @RequestMapping("share")
    public ResponseEntity saveAndShareProblem(@RequestBody ProblemSaveAndShareDTO saveAndShareDTO){
        Problem problem = ahpProblemService.saveProblemWithData(saveAndShareDTO.getAhpProblemData());

        for(String email: saveAndShareDTO.getEmails()){
            Optional<User> user = userRepository.getByEmail(email);
            if(user.isPresent()) {
                ProblemSubscriber problemSubscriber = new ProblemSubscriber(problem, user.get().getId(), null);
                subscribersRepository.save(problemSubscriber);
            }
        }

        return ResponseEntity.ok(problem);
    }



}
