package ink.reactor.fission.classes;

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
}