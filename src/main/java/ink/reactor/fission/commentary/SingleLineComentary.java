package ink.reactor.fission.commentary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SingleLineComentary {
    private final String line;

    public static SingleLineComentary of(final String line) {
        return new SingleLineComentary(line);
    }

    @Override
    public String toString() {
        return "// " + line;
    }
}
