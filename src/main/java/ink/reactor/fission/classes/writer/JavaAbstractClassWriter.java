package ink.reactor.fission.classes.writer;

import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.method.JavaMethod;
import ink.reactor.fission.util.StringAppender;

public class JavaAbstractClassWriter extends JavaClassWriter {

    public static final JavaAbstractClassWriter DEFAULT = new JavaAbstractClassWriter();

    @Override
    public String getIdentifier() {
        return "abstract class";
    }

    @Override
    public void writeMethods(JavaClass javaClass, StringBuilder builder, JavaFormatOptions options) {     
        int i = 0;
        for (final JavaMethod method : javaClass.getMethods()) {
            if (i++ != 0) {
                builder.repeat('\n', getNewLinesBetweenMethods());
            }

            final StringBuilder methodBuilder = new StringBuilder();

            if (method.hasCode()) {
                options.getMethodWriter().writeMethod(method, methodBuilder, options);
            } else {
                if (method.getCommentary() != null) {
                    options.getCommentaryWriter().write(methodBuilder, method.getCommentary());
                    methodBuilder.append('\n');
                }
                options.getMethodWriter().writeMethodAnnotations(method, methodBuilder, options);

                StringAppender.appendWithSpace(methodBuilder, method.getVisibility().getKeyword());      
                methodBuilder.append("abstract ");     
                options.getMethodWriter().writeReturnNameAndParameters(method, methodBuilder, options);
                methodBuilder.append(';');
            }

            StringAppender.appendLinesWithIndentation(builder, methodBuilder.toString(), options.getSpacesInNewLine());
        }
    }
}