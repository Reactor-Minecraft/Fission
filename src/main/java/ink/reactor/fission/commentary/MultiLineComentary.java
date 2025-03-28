package ink.reactor.fission.commentary;

import java.util.Arrays;
import java.util.Collection;

import ink.reactor.fission.util.StringSplittler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MultiLineComentary {
    private final Collection<String> lines;

    public static MultiLineComentary of(final String... lines) {
        return new MultiLineComentary(Arrays.asList(lines));
    }

    public static MultiLineComentary of(final String lines) {
        return new MultiLineComentary(StringSplittler.split(lines, '\n'));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("/**");
        for (final String line : lines) {
            builder.append("\n* ");
            builder.append(line);
        }
        builder.append("\n*/");
        return builder.toString();
    }
}
