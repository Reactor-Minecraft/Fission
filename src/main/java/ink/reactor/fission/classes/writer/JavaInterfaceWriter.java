package ink.reactor.fission.classes.writer;

import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.field.JavaField;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.method.JavaMethod;
import ink.reactor.fission.util.StringAppender;

public class JavaInterfaceWriter extends JavaClassWriter {

    public static final JavaInterfaceWriter DEFAULT = new JavaInterfaceWriter();

    @Override
    public String getIdentifier() {
        return "interface";
    }

    @Override
    public void writeFields(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        if (!javaClass.hasFields()) {
            return;
        }
        int i = 0;

        for (final JavaField field : javaClass.getFields()) {
            if (!field.isStatic()) {
                continue;
            }
            if (i++ != 0) {
                builder.repeat('\n', getNewLinesBetweenFields());
            }
            if (field.getCommentary() != null) {
                options.getCommentaryWriter().writeWithIndentation(builder, field.getCommentary(), options.getSpacesInNewLine());
                builder.append('\n');
            }
            builder.repeat(' ', options.getSpacesInNewLine());
            options.getFieldWriter().writeContent(field, builder, options);
            builder.append(';');
        }
        builder.append('\n');
    }

    @Override
    public void writeMethods(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        if (!javaClass.hasMethods()) {
            return;
        }
        
        int i = 0;
        for (final JavaMethod method : javaClass.getMethods()) {
            if (i++ != 0) {
                builder.repeat('\n', getNewLinesBetweenMethods());
            }

            final StringBuilder methodBuilder = new StringBuilder();
            if (method.getCommentary() != null) {
                options.getCommentaryWriter().write(methodBuilder, method.getCommentary());
                methodBuilder.append('\n');
            }

            options.getMethodWriter().writeMethodAnnotations(method, methodBuilder, options);

            if (method.hasCode()) {
                methodBuilder.append("default ");
                options.getMethodWriter().writeReturnNameAndParameters(method, methodBuilder, options);

                methodBuilder.append(" {\n");
                options.getMethodWriter().writeContent(method, methodBuilder, options);
                methodBuilder.append('\n').append('}');
            } else {
                StringAppender.appendWithSpace(methodBuilder, method.getVisibility().getKeyword());
                options.getMethodWriter().writeReturnNameAndParameters(method, methodBuilder, options);
                methodBuilder.append(';');
            }

            StringAppender.appendLinesWithIndentation(builder, methodBuilder.toString(), options.getSpacesInNewLine());
        }
    }
}