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
import piotr.kedra.adhoc.ahp.service.AhpSolverService;
import piotr.kedra.adhoc.ahpproblem.service.AhpProblemService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class AhpProblemResource {

    @Autowired
    private AhpSolverService ahpSolverService;

    @Autowired
    private AhpProblemService ahpProblemService;

    @GetMapping
    @RequestMapping("problem/{id}/ranking")
    public ResponseEntity getRanking(@PathVariable Long id){

        List<AhpProblemData> problemData = ahpProblemService.getProblemData(id);
        if(!problemData.isEmpty()) {
            Map<String, Double> result = calculateForAll(problemData);
            return ResponseEntity.ok(mapToRankingDTO(result));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private Map<String, Double> calculateForAll(List<AhpProblemData> problemData) {
        List<Map<String, Double>> results = problemData.stream().map(data -> ahpSolverService.calculate(data)).collect(Collectors.toList());


        Map<String, Double> first = results.get(0);
        for (int i = 1; i < results.size(); i++) {
            for(Map.Entry<String, Double> entry: results.get(i).entrySet()){
                Double value = first.get(entry.getKey());
                value += entry.getValue();
                first.replace(entry.getKey(), value);
            }
        }
        int quantityOfResults = results.size();
        for(Map.Entry<String, Double> entry: first.entrySet()){
            first.replace(entry.getKey(), entry.getValue()/quantityOfResults);
        }
        return first;
    }

    private List<RankingPlacementDTO> mapToRankingDTO(Map<String, Double> ranking) {
        return ranking.entrySet().stream()
                .map(entry -> new RankingPlacementDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(RankingPlacementDTO::getValue).reversed())
                .collect(Collectors.toList());
    }
}
