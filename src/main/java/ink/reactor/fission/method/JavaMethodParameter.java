package ink.reactor.fission.method;

import java.util.Collection;

import ink.reactor.fission.annotation.AnnotationHelper;
import ink.reactor.fission.annotation.JavaAnnotation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JavaMethodParameter implements AnnotationHelper {
    private Collection<JavaAnnotation> annotations;
    private String type, name;
    private boolean isFinal = false;

    public JavaMethodParameter(String clazz, String name) {
        this.type = clazz;
        this.name = name;
    }

    public JavaMethodParameter(Class<?> clazz, String name) {
        this.type = clazz.getSimpleName();
        this.name = name;
    }

    public JavaMethodParameter(Class<?> clazz, String name, boolean isFinal) {
        this.type = clazz.getSimpleName();
        this.name = name;
        this.isFinal = isFinal;
    }

    public JavaMethodParameter(String clazz, String name, boolean isFinal) {
        this.type = clazz;
        this.name = name;
        this.isFinal = isFinal;
    }

    @Override
    public String toString() {
        if (!hasAnnotations()) {
            return (isFinal)
                ? "final " + type + ' ' + name
                : type + ' ' + name;
        }

        final StringBuilder builder = new StringBuilder();

        if (isFinal) {
            builder.append("final ");
        }

        for (final JavaAnnotation annotation : annotations) {
            builder.append(annotation);
            builder.append(' ');
        }

        builder.append(type).append(' ').append(name);

        return builder.toString();
    }

    public JavaMethodParameter copy() {
        final JavaMethodParameter javaMethodParameter = new JavaMethodParameter(type, name);
        javaMethodParameter.annotations = this.annotations;
        javaMethodParameter.isFinal = this.isFinal;
        return javaMethodParameter;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof JavaMethodParameter parameter)
            && parameter.isFinal == this.isFinal
            && parameter.name.equals(this.name)
            && parameter.type.equals(this.type);
    }
}