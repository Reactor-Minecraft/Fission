package ink.reactor.fission.format;

public interface JavaFormateable {
    void format(Object object, JavaFormatOptions options, StringBuilder builder, JavaOutputFormateable outputFormateable);

    default char appendNextCharacter() {
        return ',';
    }
}
