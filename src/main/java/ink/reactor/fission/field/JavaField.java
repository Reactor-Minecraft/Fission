package ink.reactor.fission.field;

import java.util.Collection;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.annotation.AnnotationHelper;
import ink.reactor.fission.annotation.JavaAnnotation;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormateable;
import ink.reactor.fission.format.JavaOutputFormateable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JavaField implements JavaFormateable, AnnotationHelper {

    // Header
    private Object comentary;
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
    public void format(final Object object, final JavaFormatOptions options, final StringBuilder builder, final JavaOutputFormateable outputFormateable) {
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
            && (this.comentary != null && this.comentary.equals(javaField.comentary)); 
    }
}