package ink.reactor.fission.format.objects;

import java.util.ArrayList;
import java.util.HashMap;

import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormalizable;
import org.jetbrains.annotations.NotNull;

public final class JavaCollectionFormat {

    public static void addArrayList(final @NotNull JavaFormatOptions options) {
        options.addFormat(ArrayList.class, singleGeneric("ArrayList"));
    }

    public static void addHashMap(final @NotNull JavaFormatOptions options) {
        options.addFormat(HashMap.class, singleGeneric("HashMap"));
    }

    public static JavaFormalizable singleGeneric(final @NotNull String name) {
        return (object, options, builder, values) -> {
            final String className = "new " + name + "<>";
            builder.append(className);
            builder.append("()");
        };
    }
}