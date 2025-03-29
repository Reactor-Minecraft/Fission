package ink.reactor.fission.format.objects;

import java.util.Map;

import ink.reactor.fission.format.JavaFormalizable;
import ink.reactor.fission.util.ArrayAppender;
import org.jetbrains.annotations.NotNull;

public final class JavaPrimitiveFormat {

    public static void loadAll(final @NotNull Map<Class<?>, JavaFormalizable> formalizableMap) {
        addStringFormat(formalizableMap);
        addArrayFormat(formalizableMap);
        addBooleanFormat(formalizableMap);
        addNumbersFormat(formalizableMap);
    }

    public static void addBooleanFormat(final @NotNull Map<Class<?>, JavaFormalizable> formalizableMap) {
        formalizableMap.put(Boolean.class, (object, options, builder, values) -> {
            if (object instanceof Boolean boolean1) {
                builder.append(boolean1.booleanValue());
            } else {
                builder.append(false);
            }
        });
    }

    public static void addNumbersFormat(final @NotNull Map<Class<?>, JavaFormalizable> formalizableMap) {
        final JavaFormalizable defaultFormalizable = (object, options, builder, values) -> builder.append(object.toString());
        formalizableMap.put(Byte.class, defaultFormalizable);
        formalizableMap.put(Short.class, defaultFormalizable);
        formalizableMap.put(Character.class, defaultFormalizable);
        formalizableMap.put(Integer.class, defaultFormalizable);
        formalizableMap.put(Long.class, defaultFormalizable);
        formalizableMap.put(Float.class, defaultFormalizable);
        formalizableMap.put(Double.class, defaultFormalizable);
    }

    public static void addStringFormat(final @NotNull Map<Class<?>, JavaFormalizable> formalizableMap) {
        formalizableMap.put(String.class, (object, options, builder, values) -> {
            if (options.isAddStringQuoteMarks()) {
                builder.append('"');
                builder.append(object.toString());
                builder.append('"');
            } else {
                builder.append(object.toString());
            }
        });

        // Multiline strings
        formalizableMap.put(String[].class, (object, options, builder, values) -> {
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

    public static void addArrayFormat(final @NotNull Map<Class<?>, JavaFormalizable> formalizableMap) {
        formalizableMap.put(byte[].class, (object, options, builder, values) -> {
            if (options.isAddByteArrayPrefix()) {
                builder.append("new byte[] {");
                ArrayAppender.append((byte[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.append((byte[])object, builder);
            }
        });

        formalizableMap.put(short[].class, (object, options, builder, values) -> {
            if (options.isAddShortArrayPrefix()) {
                builder.append("new short[] {");
                ArrayAppender.append((short[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.append((short[])object, builder);
            }
        });

        formalizableMap.put(char[].class, (object, options, builder, values) -> {
            if (options.isAddCharArrayPrefix()) {
                builder.append("new char[] {");
                ArrayAppender.appendCharsToInt((char[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.appendCharsToInt((char[])object, builder);
            }
        });

        formalizableMap.put(int[].class, (object, options, builder, values) -> {
            if (options.isAddIntArrayPrefix()) {
                builder.append("new int[] {");
                ArrayAppender.append((int[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.append((int[])object, builder);
            }
        });

        formalizableMap.put(long[].class, (object, options, builder, values) -> {
            if (options.isAddLongArrayPrefix()) {
                builder.append("new long[] {");
                ArrayAppender.append((long[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.append((long[])object, builder);
            }
        });

        formalizableMap.put(float[].class, (object, options, builder, values) -> {
            if (options.isAddFloatArrayPrefix()) {
                builder.append("new float[] {");
                ArrayAppender.append((float[])object, builder);
                builder.append('}');
            } else {
                ArrayAppender.append((float[])object, builder);
            }
        });

        formalizableMap.put(double[].class, (object, options, builder, values) -> {
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
