package ink.reactor.fission.method.writer;

import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.method.JavaMethod;
import ink.reactor.fission.method.JavaMethodParameter;

import lombok.Getter;
import lombok.Setter;

/*
 * Split parameters in new lines. Example:
 * 
 * public void testMethod(
 *     final @Nullable String line1,
 *     final @Nullable String line2,
 *     final @Nullable String line3
 * ) {
 *     System.out.println(line1);
 *     System.out.println(line2);
 * }
 * 
 * Recomended for methods with many parameters (Better than oracle style)
 */
@Getter
@Setter
public class JavaSplittedMethodWriter extends JavaMethodWriter {

    public static final JavaSplittedMethodWriter DEFAULT = new JavaSplittedMethodWriter();

    // How many parameters need to start splitting
    private int parametersToStartSplit = 3;

    @Override
    public void writeParameters(JavaMethod method, StringBuilder builder, JavaFormatOptions options) {
        if (method.getParameters() == null) {
            return;
        }

        if (method.getParameters().size() < parametersToStartSplit) {
            super.writeParameters(method, builder, options);
            return;
        }

        int i = 0;
        for (final JavaMethodParameter parameter : method.getParameters()) {
            builder.append('\n');
            builder.repeat(' ', options.getSpacesInNewLine());
            builder.append(parameter.toString());
            if (++i != method.getParameters().size()) {
                builder.append(',');
                builder.append(' ');
            }
        }
        builder.append('\n');
    }
}