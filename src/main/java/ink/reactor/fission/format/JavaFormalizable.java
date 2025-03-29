package ink.reactor.fission.format;

public interface JavaFormalizable {
    void format(Object object, JavaFormatOptions options, StringBuilder builder, JavaOutputFormalizable outputFormalizable);

    default char appendNextCharacter() {
        return ',';
    }
}
