package ink.reactor.fission.commentary;

import java.util.Arrays;
import java.util.Collection;

import ink.reactor.fission.util.StringSplitter;

public record MultiLineCommentary(Collection<String> lines) {
    public static MultiLineCommentary of(final String... lines) {
        return new MultiLineCommentary(Arrays.asList(lines));
    }

    public static MultiLineCommentary of(final String lines) {
        return new MultiLineCommentary(StringSplitter.split(lines, '\n'));
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
