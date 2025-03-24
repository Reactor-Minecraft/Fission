package ink.reactor.fission.template.loader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class TemplateLoader {

    public static String load(final File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    public static String load(File file, final String suffix) throws IOException {
        final String name = file.getName();
        if (!name.endsWith(suffix)) {
            file = new File(file.getParentFile(), name+suffix);
        }
        return load(file);
    }

    public static String load(String fileName, final ClassLoader classLoader) {
        final InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            return null;
        }

        try (final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            return new String(bufferedInputStream.readAllBytes());    
        } catch (Exception e) {
            return null;
        }
    }

    public static String load(String fileName, final ClassLoader classLoader, final String suffix) {
        if (!fileName.endsWith(suffix)) {
            fileName += suffix;
        }
        return load(fileName, classLoader);
    }
}