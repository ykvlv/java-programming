package common;

public final class StringDye {
    private static final String START_OF_PATTERN = (char)27 + "[";
    private static final String END_OF_PATTERN = (char)27 + "[0m";
    private static final String RED = "31m";
    private static final String GREEN = "32m";
    private static final String YELLOW = "33m";

    private StringDye() {}

    public static String red(String string) {
        return START_OF_PATTERN + RED + string + END_OF_PATTERN;
    }

    public static String green(String string) {
        return START_OF_PATTERN + GREEN + string + END_OF_PATTERN;
    }

    public static String yellow(String string) {
        return START_OF_PATTERN + YELLOW + string + END_OF_PATTERN;
    }
}
