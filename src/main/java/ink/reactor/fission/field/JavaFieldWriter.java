package ink.reactor.fission.field;

import ink.reactor.fission.annotation.JavaAnnotation;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.util.StringAppender;
import org.jetbrains.annotations.NotNull;

public class JavaFieldWriter {

    public static final JavaFieldWriter DEFAULT = new JavaFieldWriter();

    public void write(
            final @NotNull JavaField field,
            final @NotNull StringBuilder builder,
            final @NotNull JavaFormatOptions options
    ) {
        if (field.getCommentary() != null) {
            options.getCommentaryWriter().write(builder, field.getCommentary());
            builder.append('\n');
        }

        StringAppender.appendVisibility(builder, field.getVisibility());

        if (field.isStatic()) {
            builder.append("static ");
        }
        if (field.isFinal()) {
            builder.append("final ");
        }

        writeContent(field, builder, options);
        builder.append(';');
    }

    public void writeContent(
            final @NotNull JavaField field,
            final @NotNull StringBuilder builder,
            final @NotNull JavaFormatOptions options
    ) {
        writeContentWithoutValue(field, builder, options);

        if (field.getValue() != null) {
            builder.append(" = ");
            builder.append(field.getValue());   
        }
    }

    public void writeContentWithoutValue(
            final @NotNull JavaField field,
            final @NotNull StringBuilder builder,
            final @NotNull JavaFormatOptions options
    ) {
        if (field.hasAnnotations()) {
            for (final JavaAnnotation annotation : field.getAnnotations()) {
                options.getAnnotationWriter().write(annotation, builder, options);
                builder.append(' ');
            }
        }
        builder.append(field.getType());
        builder.append(' ');
        builder.append(field.getName());
    }
}