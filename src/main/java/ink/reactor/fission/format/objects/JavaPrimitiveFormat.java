package ink.reactor.fission.format.objects;

import java.util.Map;

import ink.reactor.fission.format.JavaFormateable;
import ink.reactor.fission.util.ArrayAppender;

public final class JavaPrimitiveFormat {

    public static void loadAll(final Map<Class<?>, JavaFormateable> formateable) {
        addStringFormat(formateable);
        addArrayFormat(formateable);
        addBooleanFormat(formateable);
        addNumbersFormat(formateable);
    }

    public static void addBooleanFormat(final Map<Class<?>, JavaFormateable> formateable) {
        formateable.put(Boolean.class, (object, options, builder, values) -> builder.append((object instanceof Boolean boolean1) ? boolean1 : false));
    }

    public static void addNumbersFormat(final Map<Class<?>, JavaFormateable> formateable) {
        final JavaFormateable defaultFormateable = (object, options, builder, values) -> builder.append(object.toString());
        formateable.put(Byte.class, defaultFormateable);
        formateable.put(Short.class, defaultFormateable);
        formateable.put(Character.class, defaultFormateable);
        formateable.put(Integer.class, defaultFormateable);
        formateable.put(Long.class, defaultFormateable);
        formateable.put(Float.class, defaultFormateable);
        formateable.put(Double.class, defaultFormateable);
    }

    public static void addStringFormat(final Map<Class<?>, JavaFormateable> formateable) {
        formateable.put(String.class, (object, options, builder, values) -> {
            if (options.isAddStringQuoteMarks()) {
                builder.append('"');
                builder.append(object.toString());
                builder.append('"');
            } else {
                builder.append(object.toString());
            }
        });

        // Multiline strings
        formateable.put(String[].class, (object, options, builder, values) -> {
            builder.append('\n');
            builder.repeat('"', 3);
        
            final String[] strings = (String[])object;
            for (final String string : strings) {
                builder.append('\n');
                builder.append(string);
            }

            builder.repeat('"', 3);
        });
    }

    public static void addArrayFormat(final Map<Class<?>, JavaFormateable> formateable) {
        formateable.put(byte[].class, (object, options, builder, values) -> {
            if (options.isAddByteArrayPrefix()) {
                builder.append("new byte[] {");
                ArrayAppender.append((byte[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.append((byte[])object, builder);
            }
        });

        formateable.put(short[].class, (object, options, builder, values) -> {
            if (options.isAddShortArrayPrefix()) {
                builder.append("new short[] {");
                ArrayAppender.append((short[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.append((short[])object, builder);
            }
        });

        formateable.put(char[].class, (object, options, builder, values) -> {
            if (options.isAddCharArrayPrefix()) {
                builder.append("new char[] {");
                ArrayAppender.appendCharsToInt((char[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.appendCharsToInt((char[])object, builder);
            }
        });

        formateable.put(int[].class, (object, options, builder, values) -> {
            if (options.isAddIntArrayPrefix()) {
                builder.append("new int[] {");
                ArrayAppender.append((int[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.append((int[])object, builder);
            }
        });

        formateable.put(long[].class, (object, options, builder, values) -> {
            if (options.isAddLongArrayPrefix()) {
                builder.append("new long[] {");
                ArrayAppender.append((long[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.append((long[])object, builder);
            }
        });

        formateable.put(float[].class, (object, options, builder, values) -> {
            if (options.isAddFloatArrayPrefix()) {
                builder.append("new float[] {");
                ArrayAppender.append((float[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.append((float[])object, builder);
            }
        });

        formateable.put(double[].class, (object, options, builder, values) -> {
            if (options.isAddDoubleArrayPrefix()) {
                builder.append("new double[] {");
                ArrayAppender.append((double[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.append((double[])object, builder);
            }
        });
    }
}
