package ink.reactor.fission.util;

import java.util.Collection;

import ink.reactor.fission.JavaVisibility;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class StringAppender {

    public static void appendCharWithIndentation(final StringBuilder builder, final char character, final int spaces) {
        builder.repeat(' ', spaces);
        builder.append(character);
    }

    public static void appendLinesWithIndentation(final StringBuilder builder, final Collection<String> lines, final int spaces) {
        if (lines == null || lines.isEmpty()) {
            return;
        }

        final int size = lines.size();
        int i = 0;

        for (final String line : lines) {
            builder.repeat(' ', spaces);
            builder.append(line);
            if (++i != size) {
                builder.append('\n');
            }
        }
    }

    public static void appendWithIndentation(final StringBuilder builder, final String line, final int spaces) {
        builder.repeat(' ', spaces);
        builder.append(line);
    }

    public static void appendWithSpace(final StringBuilder builder, final String line) {
        if (line == null || line.isEmpty()) {
            return;
        }
        builder.append(line);
        builder.append(' ');
    }

    public static void appendVisibility(final StringBuilder builder, final JavaVisibility visibility) {
        appendWithSpace(builder, visibility.getKeyword());
    }

    public static void appendLinesWithIndentation(final StringBuilder builder, final String lines, final int indentation) {
        if (lines != null) {
            appendLinesWithIndentation(builder, StringSplitter.split(lines, '\n'), indentation);
        }
    }
}
