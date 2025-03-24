package ink.reactor.fission;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import ink.reactor.fission.template.GenericTemplate;
import ink.reactor.fission.template.loader.TemplateLoader;

public class GenericTemplateTest {

    private static final String INPUT_STRING =
        """
        amount: $ANIMALS_COUNT

        animals: [
          {MONKEY}
          {DRAGON}
          {ELEPHANT}]""";

    private static final String EXPECTED_STRING =
        """
        amount: 3
  
        animals: [
          "Simon"
          "Kratos"
          "Luffy"]""";
  
    @Test
    public void check() {
        final GenericTemplate genericTemplate = new GenericTemplate();
        genericTemplate.setTemplate(TemplateLoader.load("generic", getClass().getClassLoader(), ".template"));

        Assert.assertEquals(INPUT_STRING, genericTemplate.getTemplate());

        final Map<String, Object> placeholders = new HashMap<>();
        placeholders.put("$ANIMALS_COUNT", 3);
        placeholders.put("{MONKEY}", "\"Simon\"");
        placeholders.put("{DRAGON}", "\"Kratos\"");
        placeholders.put("{ELEPHANT}", "\"Luffy\"");
        genericTemplate.setPlaceholdersToReplace(placeholders);

        Assert.assertEquals(EXPECTED_STRING, genericTemplate.replace());
    }
}
