package ink.reactor.fission.format;

public final class JavaFormatConstants {
    public static final JavaFormateable EMPTY_LINE = new JavaFormateable() {
        public void format(Object object, JavaFormatOptions options, StringBuilder builder, JavaOutputFormateable outputFormateable) {}
        public char appendNextCharacter() {
            return '\n';
        };
    };
}
