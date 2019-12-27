package piotr.kedra.adhoc.ahpproblem.service.parser;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CriteriaParser {

    public String parseToString(List<String> values){
        StringBuilder result = new StringBuilder();
        values.forEach(v -> {result.append(v); result.append(';');});
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }

    public List<String> parseToList(String values){
        String[] result = values.split(";");
        return Arrays.asList(result);
    }

}
