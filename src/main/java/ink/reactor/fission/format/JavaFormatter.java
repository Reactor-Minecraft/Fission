package ink.reactor.fission.format;

import java.util.Collection;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class JavaFormatter {

    public static String format(final Collection<?> values) {
        return format(values, JavaFormatOptions.DEFAULT_OPTIONS);
    }

    public static String format(final Collection<?> values, JavaFormatOptions options) {
        if (options.getFormateableObjects() == null) {
            options.setFormateableObjects(JavaFormatOptions.DEFAULT_OPTIONS.getFormateableObjects());
        }

        final StringBuilder builder = new StringBuilder();

        int i = 0;
        final int size = values.size();

        JavaOutputFormateable javaOutputFormateable = new JavaOutputFormateable();

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

            final JavaFormateable formateable = formatObject(object, options, builder, javaOutputFormateable);

            if (!javaOutputFormateable.getOutput().isEmpty()) {
                builder.append(format(javaOutputFormateable.getOutput(), javaOutputFormateable.getOptions()));
                javaOutputFormateable = new JavaOutputFormateable();
            }
 
            if (++i != size && formateable != null) {
                builder.append(formateable.appendNextCharacter());
            }
        }

        return builder.toString();
    }

    public static JavaFormateable formatObject(final Object object, final JavaFormatOptions options, final StringBuilder builder, final JavaOutputFormateable outputFormateable) {
        if (object == null) {
            builder.append("null");
            return null;
        }

        if (object instanceof JavaFormateable javaFormateable) {
            javaFormateable.format(object, options, builder, outputFormateable);
            return javaFormateable;
        }

        final JavaFormateable formateable = options.getFormateableObjects().get(object.getClass());

        if (formateable != null) {
            formateable.format(object, options, builder, outputFormateable);
            return formateable;
        }

        // If there no java format available for this object, transform to string
        builder.append('"');
        builder.append(object.toString());
        builder.append('"');
        return null;
    }
}