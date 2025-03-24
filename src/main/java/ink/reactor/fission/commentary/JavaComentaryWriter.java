package ink.reactor.fission.commentary;

import ink.reactor.fission.util.StringAppender;

public class JavaComentaryWriter {

    public static final JavaComentaryWriter DEFAULT = new JavaComentaryWriter();

    public void write(final StringBuilder builder, final Object comentary) {
        if (comentary == null) {
            return;
        }

        switch (comentary) {
            case MultiLineComentary multiLineComentary:
                builder.append(multiLineComentary.toString());
                break;
            case SingleLineComentary single:
                builder.append(single.toString());
                break;
            default:
                builder.append("// ");
                builder.append(comentary);
                break;
        }
    }

    public void writeWithIndentation(final StringBuilder builder, final Object comentary, final int indentation) {
        if (comentary == null) {
            return;
        }

        switch (comentary) {
            case MultiLineComentary multiLineComentary:
                StringAppender.appendLinesWithIndentation(builder, multiLineComentary.toString(), indentation);
                break;
            case SingleLineComentary single:
                builder.repeat(' ', indentation);
                builder.append(single.toString());
                break;
            default:
                builder.repeat(' ', indentation);
                builder.append("// ");
                builder.append(comentary);
                break;
        }
    }
}
