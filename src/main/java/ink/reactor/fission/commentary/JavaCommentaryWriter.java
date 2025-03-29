package ink.reactor.fission.commentary;

import ink.reactor.fission.util.StringAppender;

public class JavaCommentaryWriter {

    public static final JavaCommentaryWriter DEFAULT = new JavaCommentaryWriter();

    public void write(final StringBuilder builder, final Object commentary) {
        if (commentary == null) {
            return;
        }

        switch (commentary) {
            case MultiLineCommentary multiLineCommentary:
                builder.append(multiLineCommentary);
                break;
            case SingleLineCommentary single:
                builder.append(single);
                break;
            default:
                builder.append("// ");
                builder.append(commentary);
                break;
        }
    }

    public void writeWithIndentation(final StringBuilder builder, final Object commentary, final int indentation) {
        if (commentary == null) {
            return;
        }

        switch (commentary) {
            case MultiLineCommentary multiLineCommentary:
                StringAppender.appendLinesWithIndentation(builder, multiLineCommentary.toString(), indentation);
                break;
            case SingleLineCommentary single:
                builder.repeat(' ', indentation);
                builder.append(single.toString());
                break;
            default:
                builder.repeat(' ', indentation);
                builder.append("// ");
                builder.append(commentary);
                break;
        }
    }
}
