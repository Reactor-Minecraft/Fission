package ink.reactor.fission.commentary;

public record SingleLineCommentary(String line) {
    public static SingleLineCommentary of(final String line) {
        return new SingleLineCommentary(line);
    }

    @Override
    public String toString() {
        return "// " + line;
    }
}
