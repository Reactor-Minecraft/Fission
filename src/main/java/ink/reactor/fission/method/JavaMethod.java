package ink.reactor.fission.method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.annotation.AnnotationHelper;
import ink.reactor.fission.annotation.JavaAnnotation;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormalizable;
import ink.reactor.fission.format.JavaOutputFormalizable;
import ink.reactor.fission.method.writer.JavaMethodWriter;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class JavaMethod implements JavaFormalizable, AnnotationHelper {

    // Header
    private Object commentary;
    private Collection<JavaAnnotation> annotations;
    private @NonNull JavaVisibility visibility = JavaVisibility.PUBLIC;
    private boolean isStatic = false;
    private boolean isFinal = false;

    // Header content
    private @NonNull String name;
    private Collection<JavaMethodParameter> parameters;
    private String exceptionThrows;

    // Body content
    private String codeBlock;
    private String returnObjectType = "void";

    public JavaMethod(@NonNull String name) {
        this.name = name;
    }

    public JavaMethod(@NonNull String name, String codeBlock) {
        this.name = name;
        this.codeBlock = codeBlock;
    }

    public JavaMethod(@NonNull String name, JavaMethodParameter... parameters) {
        this.name = name;
        this.parameters = Arrays.asList(parameters);
    }

    public JavaMethod(@NonNull String name, String codeBlock, JavaMethodParameter... parameters) {
        this.name = name;
        this.codeBlock = codeBlock;
        this.parameters = Arrays.asList(parameters);
    }

    @Override
    public void format(final Object object, final JavaFormatOptions options, final StringBuilder builder, final JavaOutputFormalizable outputFormalizable) {
        writeMethod(builder, options);
    }

    public void writeMethod(final @NotNull StringBuilder builder, final @NotNull JavaFormatOptions options) {
        writeMethod(options.getMethodWriter(), builder, options);
    }

    public void writeMethod(final @NotNull JavaMethodWriter writer, final @NotNull StringBuilder builder, final @NotNull JavaFormatOptions options) {
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

    public boolean hasReturn() {
        return returnObjectType != null && !returnObjectType.equals("void");
    }

    public JavaMethod addParameters(final @NonNull JavaMethodParameter... javaParameters) {
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        for (final JavaMethodParameter methodParameter : javaParameters) {
            parameters.add(methodParameter);
        }
        return this;
    }

    public JavaMethodParameter addParameter(final @NotNull Class<?> type, final @NotNull String name) {
        final JavaMethodParameter parameter = new JavaMethodParameter(type, name);
        addParameters(parameter);
        return parameter;
    }

    public JavaMethodParameter addParameter(final @NotNull String type, final @NotNull String name) {
        final JavaMethodParameter parameter = new JavaMethodParameter(type, name);
        addParameters(parameter);
        return parameter;
    }

    public JavaMethodParameter addParameterFinal(final @NotNull Class<?> type, final @NotNull String name) {
        final JavaMethodParameter parameter = new JavaMethodParameter(type, name, true);
        addParameters(parameter);
        return parameter;
    }

    public JavaMethodParameter addParameterFinal(final @NotNull String type, final @NotNull String name) {
        final JavaMethodParameter parameter = new JavaMethodParameter(type, name, true);
        addParameters(parameter);
        return parameter;
    }

    public <T> void addParametersFinal(final @NonNull Class<?> type, final @NonNull String... names) {
        for (final String name : names) {
            addParameterFinal(type, name);
        }
    }

    public <T> void addParameters(final @NonNull Class<?> type, final @NonNull String... names) {
        for (final String name : names) {
            addParameter(type, name);
        }
    }

    public JavaMethod copy() {
        final JavaMethod javaMethod = new JavaMethod(name);
        javaMethod.isFinal = this.isFinal;
        javaMethod.isStatic = this.isStatic;
        javaMethod.visibility = this.visibility;
        javaMethod.exceptionThrows = this.exceptionThrows;
        javaMethod.returnObjectType = this.returnObjectType;
        javaMethod.annotations = this.annotations;
        javaMethod.codeBlock = this.codeBlock;
        javaMethod.parameters = this.parameters;
        javaMethod.commentary = this.commentary;
        return javaMethod;
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
            && Objects.equals(method.exceptionThrows, this.exceptionThrows)
            && Objects.equals(method.returnObjectType, this.returnObjectType)
            && Objects.equals(method.annotations, this.annotations)
            && Objects.equals(method.codeBlock, this.codeBlock)
            && Objects.equals(method.parameters, this.parameters)
            && Objects.equals(method.commentary, this.commentary);
    }
}