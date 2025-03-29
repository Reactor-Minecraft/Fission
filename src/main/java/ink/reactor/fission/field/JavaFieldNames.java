package ink.reactor.fission.field;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class JavaFieldNames {

    // Transform Local fields to static. Example: "testField" to "TEST_FIELD"
    public static String toFieldStaticName(final @NotNull String fieldName) {
        if (fieldName.indexOf('_') != -1) { // Is already a constant field
            return fieldName;
        }

        final int length = fieldName.length();
        if (length == 0 || Character.isUpperCase(fieldName.charAt(0))) {
            return fieldName;
        }

        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            final char currentChar = fieldName.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                result.append('_');
            }
            result.append(Character.toUpperCase(currentChar));
        }

        return result.toString();
    }

    // Transform Static field names to local. Example: "TEST_FIELD" to "testField"
    public static String toFieldLocalName(final @NotNull String fieldName) {
        final StringBuilder result = new StringBuilder();
        final int length = fieldName.length();

        for (int i = 0; i < length; i++) {
            char currentChar = fieldName.charAt(i);
            if (currentChar != '_') {
                result.append(Character.toLowerCase(currentChar));
                continue;
            }
            if (++i < length) {
                currentChar = fieldName.charAt(i);
                result.append(Character.toUpperCase(currentChar));
            }
        }

        return result.toString();
    }
}