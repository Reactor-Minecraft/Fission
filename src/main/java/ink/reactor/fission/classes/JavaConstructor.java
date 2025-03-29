package ink.reactor.fission.classes;

import java.util.Collection;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.classes.enums.JavaEnum;
import ink.reactor.fission.field.JavaField;
import ink.reactor.fission.method.JavaMethod;
import ink.reactor.fission.method.JavaMethodParameter;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public final class JavaConstructor {

    public static final JavaConstructor DEFAULT = new JavaConstructor();

    private String prefixField = "this.";

    public JavaMethod add(final @NotNull JavaEnum javaEnum) {
        return add(javaEnum, JavaVisibility.PRIVATE);
    }

    public JavaMethod add(final @NotNull JavaClass javaClass) {
        return add(javaClass, javaClass.getVisibility());
    }

    public JavaMethod add(final @NotNull JavaClass javaClass, final @NotNull JavaVisibility visibility) {
        return add(javaClass, javaClass.getClassName(), visibility);
    }

    public JavaMethod add(final @NotNull JavaClass javaClass, final @NotNull String name, final @NotNull JavaVisibility visibility) {
        if (!supportConstructor(javaClass)) {
            return null;
        }
        final int instanceFields = javaClass.getInstanceFields();
        if (instanceFields == 0) {
            return null;
        }
    
        final JavaMethod constructor = new JavaMethod(name);
        constructor.setVisibility((javaClass instanceof JavaEnum) ? JavaVisibility.PRIVATE : visibility);

        final StringBuilder content = new StringBuilder();
        constructor.setReturnObjectType(null);

        int i = 0;
        final Collection<JavaField> fields = javaClass.getFields();
        for (final JavaField field : fields) {
            if (field.isStatic()) {
                continue;
            }

            constructor.addParameters(new JavaMethodParameter(field.getType(), field.getName()));
            if (prefixField != null) {
                content.append(prefixField);
            }
            content.append(field.getName()).append(" = ").append(field.getName()).append(';');
            if (++i != instanceFields) {
                content.append('\n');
            }
        }
        constructor.setCodeBlock(content.toString());
        javaClass.addMethods(constructor);
        return constructor;
    }

    public static boolean supportConstructor(@NotNull JavaClass javaClass) {
        return javaClass.getClassType() != JavaClassType.RECORD && javaClass.getClassType() != JavaClassType.INTERFACE;
    } 
}
