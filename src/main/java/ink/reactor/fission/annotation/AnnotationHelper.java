package ink.reactor.fission.annotation;

import java.util.ArrayList;
import java.util.Collection;

public interface AnnotationHelper {

    Collection<JavaAnnotation> getAnnotations();
    void setAnnotations(Collection<JavaAnnotation> annotations);

    default boolean hasAnnotations() {
        return getAnnotations() != null && !getAnnotations().isEmpty();
    }

    default void addAnnotations(final JavaAnnotation... javaannotations) {
        if (getAnnotations() == null) {
            setAnnotations(new ArrayList<>(javaannotations.length));
        }
        for (final JavaAnnotation annotation : javaannotations) {
            getAnnotations().add(annotation);
        }
    }

    default JavaAnnotation addAnnotation(final JavaAnnotation javaannotation) {
        if (getAnnotations() == null) {
            setAnnotations(new ArrayList<>());
        }
        getAnnotations().add(javaannotation);
        return javaannotation;
    }

    default JavaAnnotation addAnnotation(final Class<?> annotation) {
        final JavaAnnotation javaannotation = new JavaAnnotation(annotation);
        addAnnotation(javaannotation);
        return javaannotation;
    }

    default JavaAnnotation addAnnotation(final String type) {
        final JavaAnnotation javaannotation = new JavaAnnotation(type);
        addAnnotation(javaannotation);
        return javaannotation;
    }
}