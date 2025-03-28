package ink.reactor.fission.field;

import java.util.Arrays;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.format.JavaFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JavaFields {

    public static JavaField toConstant(final JavaField javaField) {
        javaField.setName(JavaFieldNames.toFieldStaticName(javaField.getName()));
        javaField.setVisibility(JavaVisibility.PUBLIC);
        javaField.setFinal(true);
        javaField.setStatic(true);
        return javaField;
    }

    public static <T> JavaField toConstant(final Class<T> type, final String name, final T value) {
        return toConstant(of(type, name, value));
    }

    public static <T> JavaField ofFinal(final Class<T> type, final String name, final T value) {
        final JavaField javaField = of(type, name, value);
        javaField.setFinal(true);
        return javaField;
    }

    public static <T> JavaField of(final Class<T> type, final String name, final T value) {
        return new JavaField(type.getSimpleName(), name, JavaFormatter.format(Arrays.asList(value)));
    }

    public static <T> JavaField of(final Class<T> type, final String name, final T value, final JavaVisibility visibility) {
        final JavaField javaField = new JavaField(type.getSimpleName(), name, JavaFormatter.format(Arrays.asList(value)));
        javaField.setVisibility(visibility);
        return javaField;
    }

    public static JavaField ofInt(final String name, final int value) {
        return new JavaField("int", name, String.valueOf(value));
    }
    public static JavaField ofLong(final String name, final long value) {
        return new JavaField("long", name, String.valueOf(value));
    }
    public static JavaField ofFloat(final String name, final float value) {
        return new JavaField("float", name, String.valueOf(value));
    }
    public static JavaField ofDouble(final String name, final double value) {
        return new JavaField("double", name, String.valueOf(value));
    }
    public static JavaField ofShort(final String name, final short value) {
        return new JavaField("short", name, String.valueOf(value));
    }
    public static JavaField ofChar(final String name, final char value) {
        return new JavaField("char", name, String.valueOf(value));
    }
    public static JavaField ofByte(final String name, final byte value) {
        return new JavaField("byte", name, String.valueOf(value));
    }

    public static void addFields(final String type, final JavaClass javaClass, final String... names) {
        final JavaField[] fields = new JavaField[names.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i++] = new JavaField(type, names[i]);
        }
        javaClass.addFields(fields);
    }

    public static void addFields(final Class<?> type, final JavaClass javaClass, final String... names) {
        addFields(type.getSimpleName(), javaClass, names);
    }
    public static void addInts(final JavaClass javaClass, final String... names) {
        addFields("int", javaClass, names);
    }
    public static void addLongs(final JavaClass javaClass, final String... names) {
        addFields("long", javaClass, names);
    }
    public static void addFloats(final JavaClass javaClass, final String... names) {
        addFields("float", javaClass, names);
    }
    public static void addDoubles(final JavaClass javaClass, final String... names) {
        addFields("double", javaClass, names);
    }
    public static void addShorts(final JavaClass javaClass, final String... names) {
        addFields("short", javaClass, names);
    }
    public static void addChars(final JavaClass javaClass, final String... names) {
        addFields("char", javaClass, names);
    }
    public static void addBytes(final JavaClass javaClass, final String... names) {
        addFields("byte", javaClass, names);
    }
}