package ink.reactor.fission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JavaVisibility {
    PUBLIC("public"),
    PROTECTED("protected"),
    PRIVATE("private"),
    DEFAULT("");

    private final String keyword;
}