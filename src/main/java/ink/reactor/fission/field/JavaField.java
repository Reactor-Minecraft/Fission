package ink.reactor.fission.field;

import java.util.Collection;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.annotation.AnnotationHelper;
import ink.reactor.fission.annotation.JavaAnnotation;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormalizable;
import ink.reactor.fission.format.JavaOutputFormalizable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JavaField implements JavaFormalizable, AnnotationHelper {

    // Header
    private Object commentary;
    private Collection<JavaAnnotation> annotations;
    private JavaVisibility visibility = JavaVisibility.PRIVATE;
    private boolean isStatic = false;
    private boolean isFinal = false;

    // Content
    private String type;
    private String name;
    private String value;

    public JavaField(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public JavaField(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    @Override
    public void format(final Object object, final JavaFormatOptions options, final StringBuilder builder, final JavaOutputFormalizable outputFormalizable) {
        write(builder, options);
    }

    public void write(final StringBuilder builder, final JavaFormatOptions options) {
        options.getFieldWriter().write(this, builder, options);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        write(builder, JavaFormatOptions.DEFAULT_OPTIONS);
        return builder.toString();
    }

    @Override
    public char appendNextCharacter() {
        return '\n';
    }

    public JavaField copy() {
        final JavaField javaField = new JavaField(type, name);
        javaField.isFinal = this.isFinal;
        javaField.isStatic = this.isStatic;
        javaField.visibility = this.visibility;
        javaField.commentary = this.commentary;
        return javaField;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        return obj instanceof JavaField javaField
            && javaField.isFinal == this.isFinal
            && javaField.isStatic == this.isStatic
            && javaField.visibility == this.visibility
            && javaField.type.equals(this.type)
            && javaField.name.equals(this.name)
            && (this.commentary != null && this.commentary.equals(javaField.commentary));
    }
}