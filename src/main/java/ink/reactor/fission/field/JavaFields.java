package ink.reactor.fission.field;

import java.util.Arrays;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.format.JavaFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public @Builder class JavaFields {

    public static final JavaFields DEFAULT = JavaFields.builder().build();
    public static final JavaFields FINAL = JavaFields.builder().makeFinals(true).build();
    public static final JavaFields STATIC_CONSTANTS = JavaFields.builder()
        .makeFinals(true)
        .visibility(JavaVisibility.PUBLIC)
        .statics(true)
        .constantStaticNames(true).build();

    private @Builder.Default boolean makeFinals = false;
    private @Builder.Default JavaVisibility visibility = JavaVisibility.PRIVATE;
    private @Builder.Default boolean statics = false;
    private @Builder.Default boolean constantStaticNames = false;

    public JavaFields copy() {
        return new JavaFields(makeFinals, visibility, statics, constantStaticNames);
    }

    public JavaField applyOptions(final JavaField field) {
        field.setFinal(makeFinals);
        field.setVisibility(visibility);

        if (statics) {
            field.setStatic(true);
        }
        if (constantStaticNames) {
            field.setName(JavaFieldNames.toFieldStaticName(field.getName()));
        }

        return field;
    }

    public <T> JavaField of(final Class<T> type, final String name, final T value) {
        return applyOptions(new JavaField(type.getSimpleName(), name, JavaFormatter.format(Arrays.asList(value))));
    }

    public <T> JavaField of(final Class<T> type, final String name, final T value, final JavaVisibility visibility) {
        final JavaField javaField = new JavaField(type.getSimpleName(), name, JavaFormatter.format(Arrays.asList(value)));
        applyOptions(javaField);
        javaField.setVisibility(visibility);
        return javaField;
    }

    public JavaField of(final String type, final String name, final String value) {
        return applyOptions(new JavaField(type, name, JavaFormatter.format(Arrays.asList(value))));
    }

    public JavaField ofInt(final String name, final int value) {
        return applyOptions(new JavaField("int", name, String.valueOf(value)));
    }
    public JavaField ofLong(final String name, final long value) {
        return applyOptions(new JavaField("long", name, String.valueOf(value)));
    }
    public JavaField ofFloat(final String name, final float value) {
        return applyOptions(new JavaField("float", name, String.valueOf(value)));
    }
    public JavaField ofDouble(final String name, final double value) {
        return applyOptions(new JavaField("double", name, String.valueOf(value)));
    }
    public JavaField ofShort(final String name, final short value) {
        return applyOptions(new JavaField("short", name, String.valueOf(value)));
    }
    public JavaField ofChar(final String name, final char value) {
        return applyOptions(new JavaField("char", name, String.valueOf(value)));
    }
    public JavaField ofByte(final String name, final byte value) {
        return applyOptions(new JavaField("byte", name, String.valueOf(value)));
    }

    public void addFields(final JavaClass javaClass, final String type, final String... names) {
        final JavaField[] fields = new JavaField[names.length];
        for (int i = 0; i < names.length; i++) {
            fields[i] = applyOptions(new JavaField(type, names[i]));
        }
        javaClass.addFields(fields);
    }
    public void addFields(final JavaClass javaClass, final Class<?> type, final String... names) {
        addFields(javaClass, type.getSimpleName(), names);
    }

    public void addInts(final JavaClass javaClass, final String... names) {
        addFields(javaClass, "int", names);
    }
    public void addLongs(final JavaClass javaClass, final String... names) {
        addFields(javaClass, "long", names);
    }
    public void addFloats(final JavaClass javaClass, final String... names) {
        addFields(javaClass, "float", names);
    }
    public void addDoubles(final JavaClass javaClass, final String... names) {
        addFields(javaClass, "double", names);
    }
    public void addShorts(final JavaClass javaClass, final String... names) {
        addFields(javaClass, "short", names);
    }
    public void addChars(final JavaClass javaClass, final String... names) {
        addFields(javaClass, "char", names);
    }
    public void addBytes(final JavaClass javaClass, final String... names) {
        addFields(javaClass, "byte", names);
    }
}