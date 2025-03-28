package ink.reactor.fission.classes;

import java.util.Collection;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.classes.enums.JavaEnum;
import ink.reactor.fission.field.JavaField;
import ink.reactor.fission.method.JavaMethod;
import ink.reactor.fission.method.JavaMethodParameter;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@Getter
@Setter
@UtilityClass
public final class JavaConstructor {

    public static void add(final JavaEnum javaEnum) {
        add(javaEnum, JavaVisibility.PRIVATE);
    }

    public static void add(final JavaClass javaClass) {
        add(javaClass, javaClass.getVisibility());
    }

    public static void add(final JavaClass javaClass, final JavaVisibility visibility) {
        if (!supportConstructor(javaClass)) {
            return;
        }
        final int instanceFields = javaClass.getInstanceFields();
        if (instanceFields == 0) {
            return;
        }
    
        final JavaMethod constructor = new JavaMethod(javaClass.getClassName());
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
            content.append("this.").append(field.getName()).append(" = ").append(field.getName()).append(';');
            if (++i != instanceFields) {
                content.append('\n');
            }
        }
        constructor.setCodeBlock(content.toString());
        javaClass.addMethods(constructor);
    }

    public static boolean supportConstructor(JavaClass javaClass) {
        return javaClass.getClassType() != JavaClassType.RECORD && javaClass.getClassType() != JavaClassType.INTERFACE;
    } 
}
