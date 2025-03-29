package ink.reactor.fission.method;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.classes.JavaClassType;
import ink.reactor.fission.field.JavaField;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public final class JavaGetterAndSetters {

    public static JavaGetterAndSetters DEFAULT = new JavaGetterAndSetters();

    private boolean setterFinalMethod = false, getterFinalMethod = false;

    private boolean setStatic = false;
    private String prefix = "this.";

    public void addGetters(final @NotNull JavaClass javaClass) {
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

    public void addSetters(final @NotNull JavaClass javaClass) {
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

    public void add(final @NotNull JavaClass javaClass) {
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

    public JavaMethod createSetter(final @NotNull JavaField field) {
        return createSetter(field, JavaVisibility.PUBLIC);
    }

    public JavaMethod createGetter(final @NotNull JavaField field) {
        return createGetter(field, JavaVisibility.PUBLIC);
    }

    public JavaMethod createSetter(final @NotNull JavaField field, final @NotNull JavaVisibility visibility) {
        final JavaMethod setter = new JavaMethod("set" + firstCharacterUppercase(field.getName()));
        setter.addParameterFinal(field.getType(), field.getName());
        setter.setStatic(field.isStatic() || this.setStatic);
        setter.setVisibility(visibility);
        setter.setCodeBlock(this.prefix + field.getName() + " = " + field.getName() + ';');
        setter.setFinal(setterFinalMethod);
        return setter;
    }

    public JavaMethod createGetter(final @NotNull JavaField field, final @NotNull JavaVisibility visibility) {
        final JavaMethod getter = new JavaMethod("get" + firstCharacterUppercase(field.getName()));
        getter.setStatic(field.isStatic() || this.setStatic);
        getter.setVisibility(visibility);
        getter.setCodeBlock("return " + this.prefix + field.getName() + ';');
        getter.setReturnObjectType(field.getType());
        getter.setFinal(getterFinalMethod);
        return getter;
    }

    private static String firstCharacterUppercase(final @NotNull String name) {
        if (name.length() == 1) {
            return String.valueOf(Character.toUpperCase(name.charAt(0)));
        }
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    public static boolean supportClassType(final @NotNull JavaClassType classType) {
        return classType != JavaClassType.INTERFACE && classType != JavaClassType.RECORD;
    }
}