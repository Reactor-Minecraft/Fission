package ink.reactor.fission.method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.annotation.AnnotationHelper;
import ink.reactor.fission.annotation.JavaAnnotation;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormateable;
import ink.reactor.fission.format.JavaOutputFormateable;
import ink.reactor.fission.method.writer.JavaMethodWriter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaMethod implements JavaFormateable, AnnotationHelper {

    // Header
    private Object comentary;
    private Collection<JavaAnnotation> annotations;
    private JavaVisibility visibility = JavaVisibility.PUBLIC;
    private boolean isStatic = false;
    private boolean isFinal = false;

    // Header content
    private final String name;
    private Collection<JavaMethodParameter> parameters;

    // Body content
    private String codeBlock;
    private String returnObjectType;
    private Object returnValue;

    public JavaMethod(String name) {
        this.name = name;
    }

    public JavaMethod(String name, String codeBlock) {
        this.name = name;
        this.codeBlock = codeBlock;
    }

    public JavaMethod(String name, JavaMethodParameter... parameters) {
        this.name = name;
        this.parameters = Arrays.asList(parameters);
    }

    public JavaMethod(String name, String codeBlock, JavaMethodParameter... parameters) {
        this.name = name;
        this.codeBlock = codeBlock;
        this.parameters = Arrays.asList(parameters);
    }

    @Override
    public void format(final Object object, final JavaFormatOptions options, final StringBuilder builder, final JavaOutputFormateable outputFormateable) {
        writeMethod(builder, options);
    }

    public void writeMethod(final StringBuilder builder, final JavaFormatOptions options) {
        writeMethod(options.getMethodWriter(), builder, options);
    }

    public void writeMethod(final JavaMethodWriter writer, final StringBuilder builder, final JavaFormatOptions options) {
        writer.writeMethod(this, builder, options);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        writeMethod(builder, JavaFormatOptions.DEFAULT_OPTIONS);
        return builder.toString();
    }

    @Override
    public char appendNextCharacter() {
        return '\n';
    }

    public boolean hasCode() {
        return codeBlock != null && !codeBlock.isEmpty();
    }

    public <T> JavaMethod setReturn(final Class<T> type, final T returnObject) {
        returnObjectType = type.getSimpleName();
        returnValue = returnObject;
        return this;
    }

    public JavaMethod setReturn(final String type, final Object returnObject) {
        returnObjectType = type;
        returnValue = returnObject;
        return this;
    }

    public JavaMethod addParameters(final JavaMethodParameter... javaParameters) {
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        for (final JavaMethodParameter methodParameter : javaParameters) {
            parameters.add(methodParameter);
        }
        return this;
    }

    public JavaMethodParameter addParameter(final Class<?> type, final String name) {
        final JavaMethodParameter parameter = new JavaMethodParameter(type, name);
        addParameters(parameter);
        return parameter;
    }

    public JavaMethodParameter addParameter(final String type, final String name) {
        final JavaMethodParameter parameter = new JavaMethodParameter(type, name);
        addParameters(parameter);
        return parameter;
    }

    public JavaMethodParameter addParameterFinal(final Class<?> type, final String name) {
        final JavaMethodParameter parameter = new JavaMethodParameter(type, name, true);
        addParameters(parameter);
        return parameter;
    }

    public JavaMethodParameter addParameterFinal(final String type, final String name) {
        final JavaMethodParameter parameter = new JavaMethodParameter(type, name, true);
        addParameters(parameter);
        return parameter;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        return obj instanceof JavaMethod method
            && method.isFinal == this.isFinal
            && method.isStatic == this.isStatic
            && method.visibility == this.visibility
            && method.name.equals(this.name)
            && Objects.equals(method.codeBlock, this.codeBlock)
            && Objects.equals(method.parameters, parameters)
            && Objects.equals(comentary, method.comentary); 
    }
}