package piotr.kedra.adhoc.ahpproblem.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import piotr.kedra.adhoc.ahp.entity.ahpdata.AhpProblemData;
import piotr.kedra.adhoc.ahpproblem.entity.ProblemSubscriber;
import piotr.kedra.adhoc.ahpproblem.entity.SubscriberAndDataDTO;
import piotr.kedra.adhoc.ahpproblem.repo.ProblemSubscribersRepository;
import piotr.kedra.adhoc.ahpproblem.service.AhpProblemService;
import piotr.kedra.adhoc.auth.UserRepository;
import piotr.kedra.adhoc.auth.entity.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ProblemSubscriberResource {

    private final ProblemSubscribersRepository subscribersRepository;

    private final AhpProblemService ahpProblemService;

    private final UserRepository userRepository;

    @Autowired
    public ProblemSubscriberResource(ProblemSubscribersRepository subscribersRepository, AhpProblemService ahpProblemService, UserRepository userRepository) {
        this.subscribersRepository = subscribersRepository;
        this.ahpProblemService = ahpProblemService;
        this.userRepository = userRepository;
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    @RequestMapping("problems/{id}/subscribers")
    public ResponseEntity getSubscribers(@PathVariable long id){
        List<ProblemSubscriber> subscribers = subscribersRepository.getByProblemId(id);
        return ResponseEntity.ok(subscribers.stream().map(this::mapSubscriberToDTO).collect(Collectors.toList()));
    }

    private SubscriberAndDataDTO mapSubscriberToDTO(ProblemSubscriber subscriber) {
        User one = userRepository.getOne(subscriber.getUserID());
        boolean isData = !Objects.isNull(subscriber.getAhpData());
        return new SubscriberAndDataDTO(one.getEmail(), isData);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @RequestMapping("problems/{id}/subscribers/data")
    public ResponseEntity addSubscribersData(@PathVariable long id, @RequestBody AhpProblemData ahpProblemData){
        if(ahpProblemService.addSubscriberDataToProblem(id, ahpProblemData))
            return ResponseEntity.ok().build();
        return ResponseEntity.status(500).build();
    }
}
