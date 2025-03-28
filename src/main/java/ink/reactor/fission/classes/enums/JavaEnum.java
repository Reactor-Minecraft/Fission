package ink.reactor.fission.classes.enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import ink.reactor.fission.classes.JavaClass;
import ink.reactor.fission.classes.JavaClassType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JavaEnum extends JavaClass {

    private Collection<JavaEnumObject> enumObjects;

    public JavaEnum(String className) {
        super(className);
    }

    public JavaEnum(String packageName, String className) {
        super(packageName, className);
    }

    public void addEnumObjects(final JavaEnumObject... javaEnumObjects) {
        if (javaEnumObjects.length == 0) {
            return;
        }
        if (enumObjects == null) {
            enumObjects = new ArrayList<>(javaEnumObjects.length);
        }
        for (final JavaEnumObject method : javaEnumObjects) {
            this.enumObjects.add(method);
        }
    }

    public boolean hasEnumObjects() {
        return enumObjects != null && !enumObjects.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JavaEnum javaEnum)) {
            return false;
        }
        return super.equals(obj) && Objects.equals(this.enumObjects, javaEnum.enumObjects);
    }

    @Override
    public JavaClassType getClassType() {
        return JavaClassType.ENUM;
    }
}