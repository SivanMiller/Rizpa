package engine;

import java.util.Locale;

public class Utilities {

    public static boolean checkUpperCase(String str) {
        String temp = str.toUpperCase(Locale.ROOT);

        return (temp.equals(str));
    }
}
