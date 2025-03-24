package ink.reactor.fission.java;

import org.junit.Assert;
import org.junit.Test;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.method.JavaMethod;
import lombok.NonNull;

public class JavaClassTest {

    @Test
    public void checkSimpleClass() {
        final JavaClass javaClass = new JavaClass("ink.reactor.fission.test", "TestClass");
        javaClass.addImports(
            "ink.reactor.fission.JavaVisibility",
            "lombok.NonNull"
        );

        final JavaMethod method = new JavaMethod("isPublic");
        method
            .addParameterFinal(JavaVisibility.class, "visibility")
            .addAnnotation(NonNull.class);
     
        method.addAnnotation(Deprecated.class)
            .addEntry("since", "2025")
            .addEntry("forRemoval", true);

        method.setCodeBlock(
            """
            if (visibility == JavaVisibility.PRIVATE) {
                throw new Exception("This is the opposite of public >:("); 
            }
            return visibility == JavaVisibility.PUBLIC;"""
        );
        javaClass.addMethods(method);

        Assert.assertEquals(
        """
        package ink.reactor.fission.test;

        import ink.reactor.fission.JavaVisibility;
        import lombok.NonNull;

        public class TestClass {
            @Deprecated(since = "2025", forRemoval = true)
            public void isPublic(final @NonNull JavaVisibility visibility) {
                if (visibility == JavaVisibility.PRIVATE) {
                    throw new Exception("This is the opposite of public >:(");
                }
                return visibility == JavaVisibility.PUBLIC;
            }
        }""", javaClass.toString());
    }
}
