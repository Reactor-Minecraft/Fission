package ink.reactor.fission.method;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.classes.JavaClassType;
import ink.reactor.fission.field.JavaField;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JavaGetterAndSetters {

    public static void addGetters(final JavaClass javaClass) {
        if (!supportClassType(javaClass.getClassType())) {
            return;
        }

        final int instanceFields = javaClass.getInstanceFields();
        if (instanceFields < 0) {
            return;
        }

        for (final JavaField field : javaClass.getFields()) {
            if (!field.isStatic()) {
                javaClass.addMethods(createGetter(field));
            }
        }
    }

    public static void addSetters(final JavaClass javaClass) {
        if (!supportClassType(javaClass.getClassType())) {
            return;
        }

        final int instanceFields = javaClass.getInstanceFields();
        if (instanceFields < 0) {
            return;
        }

        for (final JavaField field : javaClass.getFields()) {
            if (!field.isStatic() && !field.isFinal()) {
                javaClass.addMethods(createSetter(field), createGetter(field));
            }
        }
    }

    public static void add(final JavaClass javaClass) {
        if (!supportClassType(javaClass.getClassType())) {
            return;
        }

        final int instanceFields = javaClass.getInstanceFields();
        if (instanceFields < 0) {
            return;
        }
        for (final JavaField field : javaClass.getFields()) {
            if (field.isStatic()) {
                continue;
            }
            javaClass.addMethods(createGetter(field));
            if (!field.isFinal()) {
                javaClass.addMethods(createSetter(field));
            }
        }
    }

    public static JavaMethod createSetter(final JavaField field) {
        return createSetter(field, JavaVisibility.PUBLIC);
    }

    public static JavaMethod createGetter(final JavaField field) {
        return createGetter(field, JavaVisibility.PUBLIC);
    }

    public static JavaMethod createSetter(final JavaField field, final JavaVisibility visibility) {
        final JavaMethod setter = new JavaMethod("set" + firstCharacterUppercase(field.getName()));
        setter.addParameterFinal(field.getType(), field.getName());
        setter.setStatic(field.isStatic());
        setter.setVisibility(visibility);
        setter.setCodeBlock("this." + field.getName() + " = " + field.getName() + ';');
        return setter;
    }

    public static JavaMethod createGetter(final JavaField field, final JavaVisibility visibility) {
        final JavaMethod getter = new JavaMethod("get" + firstCharacterUppercase(field.getName()));
        getter.setStatic(field.isStatic());
        getter.setVisibility(visibility);
        getter.setCodeBlock("return this." + field.getName() + ';');
        getter.setReturnObjectType(field.getType());
        return getter;
    }

    private static String firstCharacterUppercase(final String name) {
        if (name.length() == 1) {
            return String.valueOf(Character.toUpperCase(name.charAt(0)));
        }
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    public static boolean supportClassType(final JavaClassType classType) {
        return classType != JavaClassType.INTERFACE && classType != JavaClassType.RECORD;
    }
}