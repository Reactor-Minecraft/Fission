package ink.reactor.fission.util;

import java.util.ArrayList;
import java.util.List;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class StringSplitter {

    /**
     * Split a string by per delimiter character
     *
     * @param text the string to split
     * @param character delimiter character
     * @return a list of all strings
     */
    public static List<String> split(final String text, final char character) {
        final ArrayList<String> list = new ArrayList<>();
        int start = 0;
        int index;
        while ((index = text.indexOf(character, start)) != -1) {
            list.add(text.substring(start, index));
            start = index + 1;
        }
        if (start == 0) {
            list.add(text);
        } else {
            list.add(text.substring(start));
        }
        return list;
    }


    /**
     * Split by char (ignoring extra)
     * Example input: "       a  bcd        efg      h  i"
     * Output: [a,bcd,efg,h,i]
     *
     * @param text to split
     * @param charToFound to split and ignore if found extra
     * @return a list of all split
     */
    public static List<String> splitIgnoreExtra(final String text, final char charToFound) {
        final ArrayList<String> list = new ArrayList<>();
        int length = text.length();
        int start = -1;

        for (int i = 0; i < length; i++) {
            final char character = text.charAt(i);
            if (character != charToFound) {
                if (start == -1) {
                    start = i;
                }
                continue;
            }
            if (start != -1) {
                list.add(text.substring(start, i));
                start = -1;
            }
        }

        if (start != -1) {
            list.add(text.substring(start));
        }
        return list;
    }
}
