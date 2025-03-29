package ink.reactor.fission.annotation;

import java.util.ArrayList;
import java.util.Collection;

import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormateable;
import ink.reactor.fission.format.JavaOutputFormateable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaAnnotation implements JavaFormateable {

    private String type;
    private Collection<JavaAnnotationEntry> entries;

    public JavaAnnotation(String type) {
        this.type = type;
    }

    public JavaAnnotation(Class<?> type) {
        this.type = type.getSimpleName();
    }

    public <T> JavaAnnotation addEntry(final String key, final Object value) {
        if (this.entries == null) {
            this.entries = new ArrayList<>();
        }
        this.entries.add(new JavaAnnotationEntry(key, value));
        return this;
    }

    public JavaAnnotation addEntry(JavaAnnotationEntry... javaannotationEntries) {
        if (this.entries == null) {
            this.entries = new ArrayList<>();
        }
        for (final JavaAnnotationEntry entry : javaannotationEntries) {
            this.entries.add(entry);
        }
        return this;
    }

    @Override
    public void format(Object object, JavaFormatOptions options, StringBuilder builder, JavaOutputFormateable outputFormateable) {
        write(builder, options);
    }

    public void write(final StringBuilder builder, final JavaFormatOptions options) {
        options.getAnnotationWriter().write(this, builder, options);
    }

    public JavaAnnotation copy() {
        final JavaAnnotation annotation = new JavaAnnotation(type);
        annotation.entries = this.entries;
        return annotation;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        write(builder, JavaFormatOptions.DEFAULT_OPTIONS);
        return builder.toString();
    }

    public boolean hasEntries() {
        return entries != null && !entries.isEmpty();
    }
}