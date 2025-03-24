package ink.reactor.fission.format;

import java.util.ArrayDeque;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public final class JavaOutputFormateable {
    private Collection<?> output;
    private JavaFormatOptions options;

    public JavaOutputFormateable() {
        output = new ArrayDeque<>();
        options = JavaFormatOptions.DEFAULT_OPTIONS;
    }
}
