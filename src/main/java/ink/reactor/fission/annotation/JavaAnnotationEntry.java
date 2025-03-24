package ink.reactor.fission.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JavaAnnotationEntry {
    private final String key;
    private Object value;
}
