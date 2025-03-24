package ink.reactor.fission.format;

import java.util.HashMap;
import java.util.Map;

import ink.reactor.fission.annotation.JavaAnnotationWriter;
import ink.reactor.fission.classes.writer.JavaClassWriter;
import ink.reactor.fission.commentary.JavaComentaryWriter;
import ink.reactor.fission.field.JavaFieldWriter;
import ink.reactor.fission.format.objects.JavaPrimitiveFormat;
import ink.reactor.fission.method.writer.JavaMethodWriter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaFormatOptions {

    public static final JavaFormatOptions DEFAULT_OPTIONS = withDefaultOptions();

    public static JavaFormatOptions withDefaultOptions() {
        final JavaFormatOptions formatOptions = new JavaFormatOptions();
        formatOptions.formateableObjects = new HashMap<>();
        JavaPrimitiveFormat.loadAll(formatOptions.formateableObjects);
        return formatOptions;
    }

    private Map<Class<?>, JavaFormateable> formateableObjects;

    private boolean
        appendNullable = true,
        addStringQuoteMarks = true,
        startInNewLine = false;

    private int spacesInNewLine = 4;

    private boolean
        addCharArrayPrefix = true, // new char[] { }
        addIntArrayPrefix = true, // new int[] { }
        addLongArrayPrefix = true,
        addShortArrayPrefix = true,
        addByteArrayPrefix = true,
        addDoubleArrayPrefix = true,
        addFloatArrayPrefix = true;

    private JavaMethodWriter methodWriter = JavaMethodWriter.DEFAULT;
    private JavaClassWriter classWriter = JavaClassWriter.DEFAULT;
    private JavaFieldWriter fieldWriter = JavaFieldWriter.DEFAULT;
    private JavaComentaryWriter comentaryWriter = JavaComentaryWriter.DEFAULT;
    private JavaAnnotationWriter annotationWriter = JavaAnnotationWriter.DEFAULT;

    public void addFormat(final Class<?> clazz, final JavaFormateable javaFormateable) {
        if (formateableObjects == null) {
            formateableObjects = new HashMap<>();
        }
        formateableObjects.put(clazz, javaFormateable);
    }
}