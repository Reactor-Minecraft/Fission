package ink.reactor.fission.java;

import org.junit.Assert;
import org.junit.Test;

import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.classes.JavaClassType;

public class JavaRecordTest {

    @Test
    public void checkSimpleClass() {
        final JavaClass javaClass = JavaTestUtil.registerDefaultData(JavaClassType.RECORD);
        Assert.assertEquals(
            """
            /**
            * Example class for test fission
            * Line2 :)
            */
            package ink.reactor.fission.test;

            import ink.reactor.fission.JavaVisibility;
            import lombok.NonNull;

            public record TestClass(
                int instanceField,
                String stringField
            ) {
                public static final short TEST_CONSTANT = 1;
                @Deprecated(since = "2025", forRemoval = true)
                public boolean isPublic(final @NonNull JavaVisibility visibility) throws Exception {
                    if (visibility == JavaVisibility.PRIVATE) {
                        throw new Exception("This is the opposite of public >:(");
                    }
                    return visibility == JavaVisibility.PUBLIC;
                }

                public void emptyMethod() {}

                // Test
                @Deprecated
                public class SubClass {}
            }""", javaClass.toString());
    }
}