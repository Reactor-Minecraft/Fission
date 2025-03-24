package ink.reactor.fission.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericTemplate {

    private Map<String, Object> placeholdersToReplace = new HashMap<>();
    private String template;

    public void addPlaceholder(final String toSearch, final Object toReplace) {
        placeholdersToReplace.put(toSearch, toReplace);
    }

    public String replace() {
        String formattedTemplate = template;
        for (final Entry<String, Object> entry : placeholdersToReplace.entrySet()) {
            formattedTemplate = formattedTemplate.replace(entry.getKey(), entry.getValue().toString());
        }
        return formattedTemplate;
    }
}
