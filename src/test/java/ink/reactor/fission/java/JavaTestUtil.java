package ink.reactor.fission.java;

import ink.reactor.fission.JavaVisibility;
import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.classes.JavaClassType;
import ink.reactor.fission.classes.JavaConstructor;
import ink.reactor.fission.commentary.MultiLineComentary;
import ink.reactor.fission.commentary.SingleLineComentary;
import ink.reactor.fission.field.JavaFields;
import ink.reactor.fission.method.JavaGetterAndSetters;
import ink.reactor.fission.method.JavaMethod;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JavaTestUtil {

    public static JavaClass registerDefaultData(final JavaClassType classType) {
        final JavaClass javaClass = new JavaClass("ink.reactor.fission.test", "TestClass");
        javaClass.setClassType(classType);
        registerDefaultData(javaClass);
        return javaClass;
    }

    public static JavaClass registerDefaultData(final JavaClass javaClass) {

        javaClass.addImports(
            "ink.reactor.fission.JavaVisibility",
            "lombok.NonNull"
        );
        javaClass.setComentary(MultiLineComentary.of(
            """
                Example class for test fission
                Line2 :)"""
        ));

        final JavaMethod method = new JavaMethod("isPublic");
        method
            .addParameterFinal(JavaVisibility.class, "visibility")
            .addAnnotation(NonNull.class);

        method.setExceptionThrows("Exception");
        method.setReturnObjectType("boolean");

        method.addAnnotation(Deprecated.class)
            .addEntry("since", "2025")
            .addEntry("forRemoval", true);

        javaClass.addFields(
            JavaFields.DEFAULT.toConstant(JavaFields.DEFAULT.ofShort("testConstant", (short)1)),
            JavaFields.DEFAULT.ofInt("instanceField", 1),
            JavaFields.DEFAULT.of(String.class, "stringField", "Example")
        );

        method.setCodeBlock(
            """
            if (visibility == JavaVisibility.PRIVATE) {
                throw new Exception("This is the opposite of public >:("); 
            }
            return visibility == JavaVisibility.PUBLIC;"""
        );

        final JavaClass subClass = new JavaClass("SubClass");
        subClass.addAnnotation(Deprecated.class);
        subClass.setComentary(SingleLineComentary.of("Test"));

        javaClass.addSubclass(subClass);

        javaClass.addMethods(
            method,
            new JavaMethod("emptyMethod")
        );

        JavaConstructor.add(javaClass);
        JavaGetterAndSetters.add(javaClass);

        return javaClass;
    }
}