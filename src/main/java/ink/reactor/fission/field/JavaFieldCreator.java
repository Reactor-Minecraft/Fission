package ink.reactor.fission.field;

import java.util.List;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.format.JavaFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JavaFieldCreator {

    public static JavaField toConstant(final JavaField javaField) {
        javaField.setName(JavaFieldNames.toFieldStaticName(javaField.getName()));
        javaField.setVisibility(JavaVisibility.PUBLIC);
        javaField.setFinal(true);
        javaField.setStatic(true);
        return javaField;
    }

    public static <T> JavaField constant(final Class<T> type, final String name, final T value) {
        return toConstant(of(type, name, value));
    }

    public static <T> JavaField ofFinal(final Class<T> type, final String name, final T value) {
        final JavaField javaField = of(type, name, value);
        javaField.setFinal(true);
        return javaField;
    }

    public static <T> JavaField of(final Class<T> type, final String name, final T value) {
        return new JavaField(type.getSimpleName(), name, JavaFormatter.format(List.of(value)));
    }

    public static <T> JavaField of(final Class<T> type, final String name, final T value, final JavaVisibility visibility) {
        final JavaField javaField = new JavaField(type.getSimpleName(), name, JavaFormatter.format(List.of(value)));
        javaField.setVisibility(visibility);
        return javaField;
    }

    public static JavaField constant(final String name, final int value) {
        return toConstant(primitive(name, value));
    }
    public static JavaField constant(final String name, final long value) {
        return toConstant(primitive(name, value));
    }
    public static JavaField constant(final String name, final float value) {
        return toConstant(primitive(name, value));
    }
    public static JavaField constant(final String name, final double value) {
        return toConstant(primitive(name, value));
    }
    public static JavaField constant(final String name, final short value) {
        return toConstant(primitive(name, value));
    }
    public static JavaField constant(final String name, final char value) {
        return toConstant(primitive(name, value));
    }
    public static JavaField constant(final String name, final byte value) {
        return toConstant(primitive(name, value));
    }

    public static JavaField primitive(final String name, final int value) {
        return new JavaField("int", name, String.valueOf(value));
    }
    public static JavaField primitive(final String name, final long value) {
        return new JavaField("long", name, String.valueOf(value));
    }
    public static JavaField primitive(final String name, final float value) {
        return new JavaField("float", name, String.valueOf(value));
    }
    public static JavaField primitive(final String name, final double value) {
        return new JavaField("double", name, String.valueOf(value));
    }
    public static JavaField primitive(final String name, final short value) {
        return new JavaField("short", name, String.valueOf(value));
    }
    public static JavaField primitive(final String name, final char value) {
        return new JavaField("char", name, String.valueOf(value));
    }
    public static JavaField primitive(final String name, final byte value) {
        return new JavaField("byte", name, String.valueOf(value));
    }
}
