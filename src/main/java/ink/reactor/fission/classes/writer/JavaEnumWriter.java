package ink.reactor.fission.classes.writer;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.classes.enums.JavaEnum;
import ink.reactor.fission.classes.enums.JavaEnumObject;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormatter;
import ink.reactor.fission.util.StringAppender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaEnumWriter extends JavaClassWriter {

    public static final JavaEnumWriter DEFAULT = new JavaEnumWriter();

    private int newFieldsBetweenEnums = 1;

    @Override
    public String getIdentifier() {
        return "enum";
    }

    @Override
    public void writeClass(JavaClass javaClass, StringBuilder builder, JavaFormatOptions options) {
        if (javaClass instanceof JavaEnum) {
            super.writeClass(javaClass, builder, options);
        }
    }

    @Override
    public void writeClassContent(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        if (!(javaClass instanceof JavaEnum javaEnum)) {
            return;
        }

        if (javaEnum.hasEnumObjects()) {
            builder.append('\n');
            writeEnumObjects(javaEnum, builder, options);
            builder.append('\n');
        }

        super.writeClassContent(javaClass, builder, options);
    }

    public void writeEnumObjects(final JavaEnum javaEnum, final StringBuilder builder, final JavaFormatOptions options) {
        final int size = javaEnum.getEnumObjects().size();

        int i = 0;
        for (final JavaEnumObject javaEnumObject : javaEnum.getEnumObjects()) {
            final StringBuilder enumBuilder = new StringBuilder();

            javaEnumObject.write(options, enumBuilder);
            enumBuilder.append((++i != size) ? ',' : ';');

            StringAppender.appendLinesWithIndentation(builder, enumBuilder.toString(), options.getSpacesInNewLine());

            if (i != size) {
                builder.repeat('\n', newFieldsBetweenEnums);
            }
        }
    }

    public void writeEnumObject(final JavaEnumObject enumObject, final StringBuilder builder, final JavaFormatOptions options) {
        builder.append(enumObject.getName());

        if (enumObject.hasParameters()) {
            builder.append('(');
            final int size = enumObject.getParameters().size();
            int i = 0;
            for (final Object parameter : enumObject.getParameters()) {
                JavaFormatter.formatObject(parameter, options, builder, null);
                if (++i != size) {
                    builder.append(',').append(' ');
                }
            }
            builder.append(')');   
        }

        if (enumObject.getClassInside() != null) {
            builder.append(' ').append('{');
            writeEnumSubClass(enumObject.getClassInside(), builder, options);
            builder.append('\n').append('}');
        }
    }

    @Override
    public void writeSubClasses(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        if (!javaClass.hasSubClasses()) {
            return;
        }

        for (final JavaClass subClass : javaClass.getSubClasses()) {
            writeEnumSubClass(subClass, builder, options);
        }
    }

    public void writeEnumSubClass(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        builder.append('\n');

        final StringBuilder subClassBuilder = new StringBuilder();
        final JavaVisibility oldVisibility = javaClass.getVisibility();
        javaClass.setVisibility(JavaVisibility.DEFAULT); // Enums don't support subclasses with visibility
        
        getWriterByClassType(javaClass.getClassType()).writeClassWithoutImportsAndPackage(javaClass, subClassBuilder, options, true);
        StringAppender.appendLinesWithIndentation(builder, subClassBuilder.toString(), options.getSpacesInNewLine());

        javaClass.setVisibility(oldVisibility);
    }
}
