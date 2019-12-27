package piotr.kedra.adhoc.ahp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import piotr.kedra.adhoc.ahp.entity.RankingPlacementDTO;
import piotr.kedra.adhoc.ahp.entity.ahpdata.AhpProblemData;
import piotr.kedra.adhoc.ahp.service.AhpService;
import piotr.kedra.adhoc.ahpproblem.service.AhpProblemService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class AhpProblemResource {

    @Autowired
    private AhpService ahpService;

    @Autowired
    private AhpProblemService ahpProblemService;

    @GetMapping
    @RequestMapping("problem/{id}/ranking")
    public ResponseEntity getRanking(@PathVariable Long id){

        Optional<AhpProblemData> problemData = ahpProblemService.getProblemData(id);
        if(problemData.isPresent()) {
            Map<String, Double> calculate = ahpService.calculate(problemData.get());
            return ResponseEntity.ok(mapToRankingDTO(calculate));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private List<RankingPlacementDTO> mapToRankingDTO(Map<String, Double> ranking) {
        return ranking.entrySet().stream()
                .map(entry -> new RankingPlacementDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(RankingPlacementDTO::getValue).reversed())
                .collect(Collectors.toList());
    }
}
