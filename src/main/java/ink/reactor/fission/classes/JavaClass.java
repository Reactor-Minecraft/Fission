package ink.reactor.fission.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.annotation.AnnotationHelper;
import ink.reactor.fission.annotation.JavaAnnotation;
import ink.reactor.fission.classes.writer.JavaClassWriter;
import ink.reactor.fission.field.JavaField;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormateable;
import ink.reactor.fission.format.JavaOutputFormateable;
import ink.reactor.fission.method.JavaMethod;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClass implements JavaFormateable, AnnotationHelper {

    // Header
    private Collection<JavaAnnotation> annotations;
    private Object comentary;
    private final String packageName;
    private JavaVisibility visibility = JavaVisibility.PUBLIC;
    private boolean isStatic = false;
    private boolean isFinal = false;
    private JavaClassType classType = JavaClassType.CLASS;
    private final String className;

    // Content
    private Collection<JavaField> fields;
    private Collection<JavaMethod> methods;
    private Collection<String> imports;
    private Collection<JavaClass> subClasses;

    public JavaClass(String className) {
        this.packageName = null;
        this.className = className;
    }

    public JavaClass(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public void addFields(final JavaField... javaFields) {
        if (javaFields.length == 0) {
            return;
        }
        if (fields == null) {
            fields = new ArrayList<>(javaFields.length);
        }
        for (final JavaField field : javaFields) {
            this.fields.add(field);
        }
    }

    public void addMethods(final JavaMethod... javaMethods) {
        if (javaMethods.length == 0) {
            return;
        }
        if (methods == null) {
            methods = new ArrayList<>(javaMethods.length);
        }
        for (final JavaMethod method : javaMethods) {
            this.methods.add(method);
        }
    }

    public void addImports(final String... javaImports) {
        if (javaImports.length == 0) {
            return;
        }
        if (imports == null) {
            imports = new ArrayList<>(javaImports.length);
        }
        for (final String javaImport : javaImports) {
            this.imports.add(javaImport);
        }
    }

    public void addSubclass(final JavaClass... javaSubClasses) {
        if (javaSubClasses.length == 0) {
            return;
        }
        if (subClasses == null) {
            subClasses = new ArrayList<>(javaSubClasses.length);
        }
        for (final JavaClass javaClass : javaSubClasses) {
            this.subClasses.add(javaClass);
        }
    }

    @Override
    public void format(final Object object, final JavaFormatOptions options, final StringBuilder builder, final JavaOutputFormateable outputFormateable) {
        write(builder, options);
    }

    public void write(final StringBuilder builder, final JavaFormatOptions options) {
        options.getClassWriter().writeClass(this, builder, options);   
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        JavaClassWriter.getWriterByClassType(classType).writeClass(this, builder, JavaFormatOptions.DEFAULT_OPTIONS);
        return builder.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof JavaClass javaClass)
            && this.isFinal == javaClass.isFinal
            && this.classType == javaClass.classType
            && this.visibility == javaClass.visibility
            && this.className.equals(javaClass.className)
            && Objects.equals(this.packageName, javaClass.packageName)
            && this.fields.equals(javaClass.fields)
            && this.methods.equals(javaClass.methods)
            && this.subClasses.equals(javaClass.subClasses);
    }

    public boolean hasFields() {
        return fields != null && !fields.isEmpty();
    }

    public boolean hasMethods() {
        return methods != null && !methods.isEmpty();
    }

    public boolean hasSubClasses() {
        return subClasses != null && !subClasses.isEmpty();
    }

    public boolean hasImports() {
        return imports != null && !imports.isEmpty();
    }

    public boolean hasComentary() {
        return comentary != null;
    }

    public int getStaticFields() {
        if (!hasFields()) {
            return 0;
        }
        int fields = 0;
        for (final JavaField field : this.fields) {
            if (field.isStatic()) {
                fields++;
            }
        }
        return fields;
    }

    public int getInstanceFields() {
        if (!hasFields()) {
            return 0;
        }
        int fields = 0;
        for (final JavaField field : this.fields) {
            if (!field.isStatic()) {
                fields++;
            }
        }
        return fields;
    }
}