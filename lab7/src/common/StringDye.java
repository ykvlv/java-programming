package common;

public final class StringDye {
    private static final String START_OF_PATTERN = (char)27 + "[";
    private static final String END_OF_PATTERN = (char)27 + "[0m";

    private StringDye() {}

    public static String red(String string) {
        return START_OF_PATTERN + 31 + "m" + string + END_OF_PATTERN;
    }

    public static String green(String string) {
        return START_OF_PATTERN + 32 + "m" + string + END_OF_PATTERN;
    }

    public static String yellow(String string) {
        return START_OF_PATTERN + 33 + "m" + string + END_OF_PATTERN;
    }
}
