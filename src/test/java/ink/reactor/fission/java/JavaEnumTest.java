package ink.reactor.fission.java;

import org.junit.Assert;
import org.junit.Test;

import ink.reactor.fission.classes.JavaConstructor;
import ink.reactor.fission.classes.enums.JavaEnum;
import ink.reactor.fission.classes.enums.JavaEnumObject;
import ink.reactor.fission.field.JavaFields;
import ink.reactor.fission.method.JavaGetterAndSetters;

public class JavaEnumTest {

    @Test
    public void check() {
        Assert.assertEquals(
            """
            package ink.reactor.fission.enum.test;

            public enum TestEnum {
                IDK(2, new char[] {2,3,4});

                private final int testInt;
                private final char[] testChars;

                private TestEnum(int testInt, char[] testChars) {
                    this.testInt = testInt;
                    this.testChars = testChars;
                }

                public int getTestInt() {
                    return this.testInt;
                }

                public char[] getTestChars() {
                    return this.testChars;
                }
            }""", createEnum().toString());
    }

    private static JavaEnum createEnum() {
        final JavaEnum javaEnum = new JavaEnum("ink.reactor.fission.enum.test", "TestEnum");
    
        final JavaEnumObject javaEnumObject = new JavaEnumObject("IDK");
        javaEnumObject.addParameters(2, new char[] { 2, 3, 4} );
        javaEnum.addEnumObjects(javaEnumObject);

        JavaFields.FINAL.addInts(javaEnum, "testInt");
        JavaFields.FINAL.addFields(javaEnum, char[].class, "testChars");

        JavaConstructor.add(javaEnum);
        JavaGetterAndSetters.add(javaEnum);

        return javaEnum;
    }
}
