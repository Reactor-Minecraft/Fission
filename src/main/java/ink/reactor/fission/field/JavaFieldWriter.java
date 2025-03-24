package ink.reactor.fission.field;

import ink.reactor.fission.annotation.JavaAnnotation;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.util.StringAppender;

public class JavaFieldWriter {

    public static final JavaFieldWriter DEFAULT = new JavaFieldWriter();

    public void write(final JavaField field, final StringBuilder builder, final JavaFormatOptions options) {
        if (field.getComentary() != null) {
            options.getComentaryWriter().write(builder, field.getComentary());
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

    public void writeContent(final JavaField field, final StringBuilder builder, final JavaFormatOptions options) {
        writeContentWithoutValue(field, builder, options);
        builder.append(" = ");
        builder.append(field.getValue());
    }

    public void writeContentWithoutValue(final JavaField field, final StringBuilder builder, final JavaFormatOptions options) {
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
