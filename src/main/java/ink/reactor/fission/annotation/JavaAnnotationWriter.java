package ink.reactor.fission.annotation;

import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormatter;

public class JavaAnnotationWriter {

    public static final JavaAnnotationWriter DEFAULT = new JavaAnnotationWriter();

    public void write(final JavaAnnotation annotation, final StringBuilder builder, final JavaFormatOptions options) {
        builder.append('@').append(annotation.getType());

        if (!annotation.hasEntries()) {
            return;
        }

        builder.append('(');

        int i = 0;
        for (final JavaAnnotationEntry entry : annotation.getEntries()) {
            if (i++ != 0) {
                builder.append(',');
                builder.append(' ');
            }
            builder.append(entry.getKey());
            builder.append(" = ");
            JavaFormatter.formatObject(entry.getValue(), options, builder, null);
        }

        builder.append(')');
    }
}
