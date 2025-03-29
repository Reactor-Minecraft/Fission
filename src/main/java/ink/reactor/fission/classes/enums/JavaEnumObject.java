package ink.reactor.fission.classes.enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import ink.reactor.fission.annotation.AnnotationHelper;
import ink.reactor.fission.annotation.JavaAnnotation;
import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.classes.writer.JavaEnumWriter;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormalizable;
import ink.reactor.fission.format.JavaOutputFormalizable;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JavaEnumObject implements JavaFormalizable, AnnotationHelper {

    // Header
    private Collection<JavaAnnotation> annotations;
    private Object commentary;

    // Content
    private @NonNull String name;
    private Collection<Object> parameters;
    private JavaClass classInside;

    @Override
    public void format(final Object object, final JavaFormatOptions options, final StringBuilder builder, final JavaOutputFormalizable outputFormalizable) {
        write(options, builder);
    }

    public void write(final JavaFormatOptions options, final StringBuilder builder) {
        JavaEnumWriter.DEFAULT.writeEnumObject(this, builder, options);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        write(JavaFormatOptions.DEFAULT_OPTIONS, builder);
        return builder.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof JavaEnumObject enumObject)
            && this.name.equals(enumObject.name)
            && Objects.equals(this.parameters, enumObject.parameters)
            && Objects.equals(this.classInside, enumObject.classInside);
    }

    public boolean hasParameters() {
        return parameters != null && !parameters.isEmpty();
    }

    public void addParameters(final Object... constructorParameters) {
        if (parameters == null) {
            parameters = new ArrayList<>(constructorParameters.length);
        }
        for (final Object parameter : constructorParameters) {
            parameters.add(parameter);
        }
    }
}