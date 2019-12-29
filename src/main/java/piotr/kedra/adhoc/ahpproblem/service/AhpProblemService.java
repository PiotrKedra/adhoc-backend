package piotr.kedra.adhoc.ahpproblem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import piotr.kedra.adhoc.ahp.entity.ahpdata.AhpProblemData;
import piotr.kedra.adhoc.ahp.entity.ahpdata.Objective;
import piotr.kedra.adhoc.ahp.entity.ahpdata.ObjectiveComparison;
import piotr.kedra.adhoc.ahpproblem.repo.ProblemRepository;
import piotr.kedra.adhoc.ahpproblem.repo.ProblemSubscribersRepository;
import piotr.kedra.adhoc.ahpproblem.entity.Problem;
import piotr.kedra.adhoc.ahpproblem.entity.ProblemSubscriber;
import piotr.kedra.adhoc.ahpproblem.service.parser.CriteriaParser;
import piotr.kedra.adhoc.ahpproblem.service.parser.ParserService;
import piotr.kedra.adhoc.auth.UserContext;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AhpProblemService {

    @Autowired
    private CriteriaParser criteriaParser;

    @Autowired
    private ParserService parserService;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemSubscribersRepository subscribersRepository;

    @Autowired
    private UserContext userContext;

    public Problem saveProblemWithData(AhpProblemData ahpProblemData){
        String objectives = parseCriterias(ahpProblemData.getObjectives());
        String criterias = parseCriterias(ahpProblemData.getCriteriaList());
        Problem problem = new Problem(userContext.getUserID(), objectives, criterias);
        Problem savedProblem = problemRepository.save(problem);
        String ahpData = parserService.parseAHPData(ahpProblemData);
        ProblemSubscriber ownerData = new ProblemSubscriber(savedProblem, userContext.getUserID(), ahpData);
        subscribersRepository.save(ownerData);
        return savedProblem;
    }

    private String parseCriterias(List<Objective> objects) {
        List<String> criterias = objects.stream()
                .map(Objective::getName)
                .collect(Collectors.toList());
        return criteriaParser.parseToString(criterias);
    }

    public List<AhpProblemData> getProblemData(Long problemID){
        Optional<Problem> problem = problemRepository.getById(problemID);
        if(problem.isPresent()) {
            List<ProblemSubscriber> byProblemId = subscribersRepository.getByProblemId(problemID);
            return byProblemId.stream().filter(s -> Objects.nonNull(s.getAhpData())).map(s -> mapToAhpProblemData(problem.get(), s.getAhpData())).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private AhpProblemData mapToAhpProblemData(Problem problem, String ahpData) {
        Map.Entry<String[][], List<ObjectiveComparison>> listEntry = parserService.parseToAhpObjects(ahpData);
        return AhpProblemData.builder()
                .objectives(mapToObjectives(problem.getObjectives()))
                .criteriaList(mapToObjectives(problem.getCriterias()))
                .objectiveComparisons(listEntry.getValue())
                .criteriaPreferenceMatrix(listEntry.getKey())
                .build();
    }

    private List<Objective> mapToObjectives(String objectives) {
        List<String> values = criteriaParser.parseToList(objectives);
        List<Objective> objectiveList = new ArrayList<>();
        int index = 0;
        for(String obj: values){
            objectiveList.add(new Objective(obj, index));
            ++index;
        }
        return objectiveList;
    }

    public boolean addSubscriberDataToProblem(Long problemID, AhpProblemData ahpProblemData){
        Optional<ProblemSubscriber> problemSubscriber = subscribersRepository.getByProblemIdAndUserID(problemID, userContext.getUserID());
        if(problemSubscriber.isPresent()){
            ProblemSubscriber subscriber = problemSubscriber.get();
            String ahpData = parserService.parseAHPData(ahpProblemData);
            subscriber.setAhpData(ahpData);
            ProblemSubscriber updated = subscribersRepository.save(subscriber);
            return true;
        }
        return false;
    }
}
