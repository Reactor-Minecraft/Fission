package ink.reactor.fission.annotation;

import java.util.ArrayList;
import java.util.Collection;

import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormalizable;
import ink.reactor.fission.format.JavaOutputFormalizable;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class JavaAnnotation implements JavaFormalizable {

    private @NonNull String type;
    private Collection<JavaAnnotationEntry> entries;

    public JavaAnnotation(@NonNull String type) {
        this.type = type;
    }

    public JavaAnnotation(@NonNull Class<?> type) {
        this.type = type.getSimpleName();
    }

    public <T> JavaAnnotation addEntry(final @NonNull String key, final Object value) {
        if (this.entries == null) {
            this.entries = new ArrayList<>();
        }
        this.entries.add(new JavaAnnotationEntry(key, value));
        return this;
    }

    public JavaAnnotation addEntry(@NonNull JavaAnnotationEntry... javaAnnotationEntries) {
        if (this.entries == null) {
            this.entries = new ArrayList<>();
        }
        for (final JavaAnnotationEntry entry : javaAnnotationEntries) {
            this.entries.add(entry);
        }
        return this;
    }

    @Override
    public void format(Object object, JavaFormatOptions options, StringBuilder builder, JavaOutputFormalizable outputFormalizable) {
        write(builder, options);
    }

    public void write(final @NotNull StringBuilder builder, final @NotNull JavaFormatOptions options) {
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