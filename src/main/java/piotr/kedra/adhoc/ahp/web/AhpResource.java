package piotr.kedra.adhoc.ahp.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import piotr.kedra.adhoc.ahp.entity.RankingPlacementDTO;
import piotr.kedra.adhoc.ahp.service.AhpSolverService;
import piotr.kedra.adhoc.ahp.entity.ahpdata.AhpProblemData;
import piotr.kedra.adhoc.ahpproblem.service.AhpProblemService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("ranking")
public class AhpResource {

    private final AhpSolverService ahpSolverService;

    @Autowired
    private AhpProblemService ahpProblemService;

    @Autowired
    public AhpResource(AhpSolverService ahpSolverService) {
        this.ahpSolverService = ahpSolverService;
    }

    @PostMapping(consumes = "application/json")
    public List<RankingPlacementDTO> saveProblemAndGetRanking(@RequestBody AhpProblemData a){
        ahpProblemService.saveProblemWithData(a);
        Map<String, Double> ranking = ahpSolverService.calculate(a);
        return mapToRankingDTO(ranking);
    }

    private List<RankingPlacementDTO> mapToRankingDTO(Map<String, Double> ranking) {
        return ranking.entrySet().stream()
                .map(entry -> new RankingPlacementDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(RankingPlacementDTO::getValue).reversed())
                .collect(Collectors.toList());
    }
}
