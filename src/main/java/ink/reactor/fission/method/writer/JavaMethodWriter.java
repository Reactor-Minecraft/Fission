package ink.reactor.fission.method.writer;

import ink.reactor.fission.annotation.JavaAnnotation;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.method.JavaMethod;
import ink.reactor.fission.method.JavaMethodParameter;
import ink.reactor.fission.util.StringAppender;
import ink.reactor.fission.util.StringSplitter;

import lombok.Getter;
import lombok.Setter;

/*
 * Classic method writer. Example:
 * 
 * public void testMethod(final @Nullable String line1, final @Nullable String line2) {
 *     System.out.println(line1);
 *     System.out.println(line2);
 * }
 */
@Getter
@Setter
public class JavaMethodWriter {

    public static final JavaMethodWriter DEFAULT = new JavaMethodWriter();

    private boolean codeBlockIndentation = true;
    private int codeExtraIndentation = 0;

    public void writeMethod(final JavaMethod method, final StringBuilder builder, final JavaFormatOptions options) {
        writeHeader(method, builder, options);

        builder.append(' ');
        builder.append('{');

        if (method.getCodeBlock() != null) {
            builder.append('\n');
            writeContent(method, builder, options);
            builder.append('\n');
        }
        builder.append('}');
    }

    public void writeHeader(final JavaMethod method, final StringBuilder builder, final JavaFormatOptions options) {
        if (method.getCommentary() != null) {
            options.getCommentaryWriter().write(builder, method.getCommentary());
            builder.append('\n');
        }
    
        writeMethodAnnotations(method, builder, options);

        StringAppender.appendWithSpace(builder, method.getVisibility().getKeyword());
        if (method.isStatic()) {
            builder.append("static ");
        }
        if (method.isFinal()) {
            builder.append("final ");
        }
        writeReturnNameAndParameters(method, builder, options);
    }

    public void writeMethodAnnotations(final JavaMethod method, final StringBuilder builder, final JavaFormatOptions options) {
        if (method.hasAnnotations()) {
            for (final JavaAnnotation annotation : method.getAnnotations()) {
                options.getAnnotationWriter().write(annotation, builder, options);
                builder.append('\n');
            }
        }
    }

    public void writeReturnNameAndParameters(final JavaMethod method, final StringBuilder builder, final JavaFormatOptions options) {
        if (method.getReturnObjectType() != null) {
            builder.append(method.getReturnObjectType());
            builder.append(' ');
        }
        builder.append(method.getName());
        builder.append('(');

        writeParameters(method, builder, options);

        builder.append(')');

        if (method.getExceptionThrows() != null) {
            builder.append(" throws ");
            builder.append(method.getExceptionThrows());
        }
    }

    public void writeParameters(final JavaMethod method, final StringBuilder builder, final JavaFormatOptions options) {
        if (method.getParameters() == null) {
            return;
        }

        int i = 0;
        for (final JavaMethodParameter parameter : method.getParameters()) {
            builder.append(parameter.toString());
            if (++i != method.getParameters().size()) {
                builder.append(',');
                builder.append(' ');
            }
        }
    }

    public void writeContent(final JavaMethod method, final StringBuilder builder, final JavaFormatOptions options) {
        if (method.getCodeBlock() == null) {
            return;
        }

        if (codeBlockIndentation) {
            StringAppender.appendLinesWithIndentation(
                builder,
                StringSplitter.split(method.getCodeBlock(), '\n'),
                options.getSpacesInNewLine() + codeExtraIndentation);
        } else {
            builder.append(method.getCodeBlock());
        }
    }
}