package ink.reactor.fission.annotation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public interface AnnotationHelper {

    Collection<JavaAnnotation> getAnnotations();
    void setAnnotations(Collection<JavaAnnotation> annotations);

    default boolean hasAnnotations() {
        return getAnnotations() != null && !getAnnotations().isEmpty();
    }

    default void addAnnotations(final @NotNull JavaAnnotation... javaAnnotations) {
        if (getAnnotations() == null) {
            setAnnotations(new ArrayList<>(javaAnnotations.length));
        }
        for (final JavaAnnotation annotation : javaAnnotations) {
            getAnnotations().add(annotation);
        }
    }

    default JavaAnnotation addAnnotation(final @NotNull JavaAnnotation javaAnnotation) {
        if (getAnnotations() == null) {
            setAnnotations(new ArrayList<>());
        }
        getAnnotations().add(javaAnnotation);
        return javaAnnotation;
    }

    default JavaAnnotation addAnnotation(final @NotNull Class<?> annotation) {
        final JavaAnnotation javaannotation = new JavaAnnotation(annotation);
        addAnnotation(javaannotation);
        return javaannotation;
    }

    default JavaAnnotation addAnnotation(final @NotNull String type) {
        final JavaAnnotation javaannotation = new JavaAnnotation(type);
        addAnnotation(javaannotation);
        return javaannotation;
    }
}