package ink.reactor.fission.format;

public final class JavaFormatConstants {
    public static final JavaFormalizable EMPTY_LINE = new JavaFormalizable() {
        public void format(Object object, JavaFormatOptions options, StringBuilder builder, JavaOutputFormalizable outputFormalizable) {}
        public char appendNextCharacter() {
            return '\n';
        };
    };
}
