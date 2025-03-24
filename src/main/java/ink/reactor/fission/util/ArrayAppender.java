package ink.reactor.fission.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ArrayAppender {

    public static void append(final Object[] array, final StringBuilder builder) {
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(',');
            }
        }
    }

    public static void append(final char[] array, final StringBuilder builder) {
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(',');
            }
        }
    }

    public static void appendCharsToInt(final char[] array, final StringBuilder builder) {
        for (int i = 0; i < array.length; i++) {
            builder.append((int)array[i]);
            if (i != array.length - 1) {
                builder.append(',');
            }
        }
    }

    public static void append(final int[] array, final StringBuilder builder) {
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(',');
            }
        }
    }

    public static void append(final long[] array, final StringBuilder builder) {
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(',');
            }
        }
    }

    public static void append(final short[] array, final StringBuilder builder) {
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(',');
            }
        }
    }

    public static void append(final byte[] array, final StringBuilder builder) {
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(',');
            }
        }
    }

    public static void append(final double[] array, final StringBuilder builder) {
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(',');
            }
        }
    }

    public static void append(final float[] array, final StringBuilder builder) {
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(',');
            }
        }
    }
}