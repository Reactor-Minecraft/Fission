package ink.reactor.fission.annotation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JavaAnnotationEntry {
    private @NonNull String key;
    private Object value;

    public JavaAnnotationEntry copy() {
        return new JavaAnnotationEntry(key, value);
    }
}
