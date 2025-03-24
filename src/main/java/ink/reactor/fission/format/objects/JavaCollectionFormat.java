package ink.reactor.fission.format.objects;

import java.util.ArrayList;
import java.util.HashMap;

import ink.reactor.fission.format.JavaFormatOptions;
import ink.reactor.fission.format.JavaFormateable;

public final class JavaCollectionFormat {

    public static void addArrayList(final JavaFormatOptions options) {
        options.addFormat(ArrayList.class, singleGeneric("ArrayList"));
    }

    public static void addHashMap(final JavaFormatOptions options) {
        options.addFormat(HashMap.class, singleGeneric("HashMap"));
    }

    public static JavaFormateable singleGeneric(final String name) {
        return (object, options, builder, values) -> {
            final String className = "new " + name + "<>";
            builder.append(className);
            builder.append("()");
        };
    }
}