package ink.reactor.fission.format;

import java.util.Collection;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class JavaFormatter {

    public static String format(final Collection<?> values) {
        return format(values, JavaFormatOptions.DEFAULT_OPTIONS);
    }

    public static String format(final Collection<?> values, JavaFormatOptions options) {
        if (options.getFormalizableObjects() == null) {
            options.setFormalizableObjects(JavaFormatOptions.DEFAULT_OPTIONS.getFormalizableObjects());
        }

        final StringBuilder builder = new StringBuilder();

        int i = 0;
        final int size = values.size();

        JavaOutputFormalizable javaOutputFormalizable = new JavaOutputFormalizable();

        for (final Object object : values) {

            if (object == null && !options.isAppendNullable()) {
                if (++i != size) {
                    builder.append(',');
                }
                continue;
            }

            if (options.isStartInNewLine()) {
                builder.append('\n');
                builder.repeat(' ', options.getSpacesInNewLine());
            }

            final JavaFormalizable formateable = formatObject(object, options, builder, javaOutputFormalizable);

            if (!javaOutputFormalizable.getOutput().isEmpty()) {
                builder.append(format(javaOutputFormalizable.getOutput(), javaOutputFormalizable.getOptions()));
                javaOutputFormalizable = new JavaOutputFormalizable();
            }
 
            if (++i != size && formateable != null) {
                builder.append(formateable.appendNextCharacter());
            }
        }

        return builder.toString();
    }

    public static JavaFormalizable formatObject(final Object object, final JavaFormatOptions options, final StringBuilder builder, final JavaOutputFormalizable outputFormalizable) {
        if (object == null) {
            builder.append("null");
            return null;
        }

        if (object instanceof JavaFormalizable javaFormalizable) {
            javaFormalizable.format(object, options, builder, outputFormalizable);
            return javaFormalizable;
        }

        final JavaFormalizable formalizable = options.getFormalizableObjects().get(object.getClass());

        if (formalizable != null) {
            formalizable.format(object, options, builder, outputFormalizable);
            return formalizable;
        }

        // If there is no java format available for this object, transform to string
        builder.append('"');
        builder.append(object.toString());
        builder.append('"');
        return null;
    }
}