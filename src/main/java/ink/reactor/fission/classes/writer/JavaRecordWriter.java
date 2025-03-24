package ink.reactor.fission.classes.writer;

import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.field.JavaField;
import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.util.StringAppender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaRecordWriter extends JavaClassWriter {

    public static final JavaRecordWriter DEFAULT = new JavaRecordWriter();

    @Override
    public String getIdentifier() {
        return "record";
    }

    @Override
    public void writeFields(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        final int staticFields = javaClass.getStaticFields();

        if (staticFields > 0) {
            writeFieldsPredicate(javaClass, builder, options, JavaField::isStatic);
        }
    }

    @Override
    public void writeClassStart(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        StringAppender.appendVisibility(builder, javaClass.getVisibility());

        if (javaClass.isStatic()) {
            builder.append("static ");
        }
        if (javaClass.isFinal()) {
            builder.append("final ");
        }

        builder.append("record ");
        builder.append(javaClass.getClassName());
        builder.append('(');

        if (javaClass.hasFields()) {
            writeRecordFields(javaClass, builder, options);
        }

        builder.append(')');
    }

    public void writeRecordFields(final JavaClass javaClass, final StringBuilder builder, final JavaFormatOptions options) {
        if (javaClass.getFields() == null) {
            return;
        }
        final int size = javaClass.getInstanceFields();
        int i = 0;
        for (final JavaField javaField : javaClass.getFields()) {
            if (javaField.isStatic()) {
                continue;
            }

            if (i != 0) {
                builder.repeat('\n', getNewLinesBetweenFields());
            } else {
                builder.append('\n');
            }

            if (javaField.getComentary() != null) {
                options.getComentaryWriter().writeWithIndentation(builder, javaField.getComentary(), options.getSpacesInNewLine());
                builder.append('\n');
            }

            builder.repeat(' ', options.getSpacesInNewLine());
            options.getFieldWriter().writeContent(javaField, builder, options);

            if (++i != size) {
                builder.append(',');
            } else {
                builder.append('\n');
            }
        }
    }
}
