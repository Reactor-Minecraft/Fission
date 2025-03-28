package ink.reactor.fission.java;

import org.junit.Assert;
import org.junit.Test;

import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.classes.JavaClassType;

public class JavaAbstractClassTest {

    @Test
    public void checkSimpleClass() {
        final JavaClass javaClass = JavaTestUtil.registerDefaultData(JavaClassType.ABSTRACT_CLASS);

        Assert.assertEquals(
            """
            /**
            * Example class for test fission
            * Line2 :)
            */
            package ink.reactor.fission.test;

            import ink.reactor.fission.JavaVisibility;
            import lombok.NonNull;

            public abstract class TestClass {
                public static final short TEST_CONSTANT = 1;

                private int instanceField = 1;
                private String stringField = "Example";

                @Deprecated(since = "2025", forRemoval = true)
                public boolean isPublic(final @NonNull JavaVisibility visibility) throws Exception {
                    if (visibility == JavaVisibility.PRIVATE) {
                        throw new Exception("This is the opposite of public >:(");
                    }
                    return visibility == JavaVisibility.PUBLIC;
                }

                public abstract void emptyMethod();

                public TestClass(int instanceField, String stringField) {
                    this.instanceField = instanceField;
                    this.stringField = stringField;
                }

                public void setInstanceField(final int instanceField) {
                    this.instanceField = instanceField;
                }

                public int getInstanceField() {
                    return this.instanceField;
                }

                public void setStringField(final String stringField) {
                    this.stringField = stringField;
                }

                public String getStringField() {
                    return this.stringField;
                }

                // Test
                @Deprecated
                public class SubClass {}
            }""", javaClass.toString());
    }
}
