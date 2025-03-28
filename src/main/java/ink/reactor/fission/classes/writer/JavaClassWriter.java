package ink.reactor.fission.classes.writer;

import java.util.function.Predicate;

import ink.reactor.fission.annotation.JavaAnnotation;
import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.classes.JavaClassType;
import ink.reactor.fission.field.JavaField;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.method.JavaMethod;
import ink.reactor.fission.util.StringAppender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaClassWriter {

    public static final JavaClassWriter DEFAULT = new JavaClassWriter();

    private int newLinesBetwenImportsAndClass = 1;
    private int newLinesBetwenPackage = 2;
    private int newLinesBetweenMethods = 2;
    private int newLinesBetweenFields = 1;

    private int newLinesBetwenStaticAndInstaceFields = 2;

    public String getIdentifier() {
        return "class";
    }

    public static JavaClassWriter getWriterByClassType(final JavaClassType classType) {
        return switch (classType) {
            case RECORD -> JavaRecordWriter.DEFAULT;
            case ABSTRACT_CLASS -> JavaAbstractClassWriter.DEFAULT;
            case INTERFACE -> JavaInterfaceWriter.DEFAULT;
            case ENUM -> JavaEnumWriter.DEFAULT;
            default -> DEFAULT;
        };
    }

    public void writeClass(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        writeImportAndPackage(javaClass, builder, options);
        if (javaClass.hasImports()) {
            builder.repeat('\n', newLinesBetwenImportsAndClass);
        }
        writeClassWithoutImportsAndPackage(javaClass, builder, options, false);
    }

    public void writeImportAndPackage(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        if (javaClass.hasComentary()) {
            options.getComentaryWriter().write(builder, javaClass.getComentary());
            builder.append('\n');
        }

        builder.append("package ").append(javaClass.getPackageName()).append(';');
        builder.repeat('\n', newLinesBetwenPackage);

        writeImports(javaClass, builder);
    }

    public void writeImports(final JavaClass javaClass, final StringBuilder builder) {
        if (!javaClass.hasImports()) {
            return;
        }
        for (final String classImport : javaClass.getImports()) {
            builder.append("import ").append(classImport).append(';').append('\n');
        }
    }

    public void writeClassWithoutImportsAndPackage(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options, final boolean writeComentary) {
        if (writeComentary && javaClass.getComentary() != null) {
            options.getComentaryWriter().write(builder, javaClass.getComentary());
            builder.append('\n');
        }

        if (javaClass.hasAnnotations()) {
            for (final JavaAnnotation annotation : javaClass.getAnnotations()) {
                options.getAnnotationWriter().write(annotation, builder, options);
                builder.append('\n');
            }
        }

        writeClassStart(javaClass, builder, options);

        builder.append(" {");

        writeClassContent(javaClass, builder, options);

        builder.append('}');
    }

    public void writeClassContent(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        if (javaClass.hasFields()) {
            builder.append('\n');
            writeFields(javaClass, builder, options);
        }

        if (javaClass.hasMethods()) {
            builder.append('\n');
            writeMethods(javaClass, builder, options);
            builder.append('\n');
        }

        if (javaClass.hasSubClasses()) {
            writeSubClasses(javaClass, builder, options);
            builder.append('\n');
        }
    }

    public void writeClassStart(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        StringAppender.appendVisibility(builder, javaClass.getVisibility());

        if (javaClass.isStatic()) {
            builder.append("static ");
        }
        if (javaClass.isFinal()) {
            builder.append("final ");
        }
        builder.append(getIdentifier());
        builder.append(' ');
        builder.append(javaClass.getClassName());
    }

    public void writeFields(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        if (!javaClass.hasFields()) {
            return;
        }

        final int staticFields = javaClass.getStaticFields();

        if (staticFields > 0) {
            writeFieldsPredicate(javaClass, builder, options, JavaField::isStatic);
        }

        final int instanceFields = javaClass.getInstanceFields();

        if (instanceFields != 0) {
            if (staticFields > 0) {
                builder.repeat('\n', newLinesBetwenStaticAndInstaceFields); 
            }
            writeFieldsPredicate(javaClass, builder, options, (field) -> !field.isStatic());
        }

        builder.append('\n');
    }

    public void writeFieldsPredicate(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options, final Predicate<JavaField> predicate) {
        int i = 0;
        for (final JavaField javaField : javaClass.getFields()) {
            if (!predicate.test(javaField)) {
                continue;
            }

            if (i++ != 0) {
                builder.repeat('\n', newLinesBetweenFields);
            }

            final StringBuilder fieldBuilder = new StringBuilder();
            options.getFieldWriter().write(javaField, fieldBuilder, options);
            StringAppender.appendLinesWithIndentation(builder, fieldBuilder.toString(), options.getSpacesInNewLine());
        }
    }

    public void writeMethods(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        if (!javaClass.hasMethods()) {
            return;
        }
        
        int i = 0;
        for (final JavaMethod method : javaClass.getMethods()) {
            if (i++ != 0) {
                builder.repeat('\n', newLinesBetweenMethods);
            }
            final StringBuilder methodBuilder = new StringBuilder();
            options.getMethodWriter().writeMethod(method, methodBuilder, options);
            StringAppender.appendLinesWithIndentation(builder, methodBuilder.toString(), options.getSpacesInNewLine());
        }
    }

    public void writeSubClasses(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        if (!javaClass.hasSubClasses()) {
            return;
        }

        for (final JavaClass subClass : javaClass.getSubClasses()) {
            builder.append('\n');

            final StringBuilder subClassBuilder = new StringBuilder();
            getWriterByClassType(subClass.getClassType()).writeClassWithoutImportsAndPackage(subClass, subClassBuilder, options, true);
            StringAppender.appendLinesWithIndentation(builder, subClassBuilder.toString(), options.getSpacesInNewLine());
        }
    }
}