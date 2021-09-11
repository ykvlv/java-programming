package client;

import java.io.InterruptedIOException;
import java.util.Arrays;
import java.util.Scanner;

public class InputHandler {
    public static final String InterruptCommand = "exit";

    private final Scanner scanner;
    private final boolean scriptMode;

    public InputHandler(Scanner scanner, boolean scriptMode) {
        this.scriptMode = scriptMode;
        this.scanner = scanner;
    }

    public String nextLine() throws InterruptedIOException {
        String string = scanner.nextLine().trim();
        if (string.equals(InterruptCommand)) {
            throw new InterruptedIOException();
        }
        return string;
    }

    public String requestString(boolean mayBeNull) throws InterruptedIOException {
        while (true) {
            String string = nextLine();
            if (string.equals("")) {
                if (mayBeNull) {
                    return null;
                } else {
                    System.err.println("Не может быть null.");
                }
            } else {
                return string;
            }
        }
    }

    public Long requestLong(boolean mayBeNull) throws InterruptedIOException {
        while (true) {
            try {
                String string = requestString(mayBeNull);
                if (string == null) {
                    return null;
                } else {
                    return Long.parseLong(string);
                }
            } catch (NumberFormatException e) {
                System.err.println("Ошибка парсинга Integer.");
            }
        }
    }

    public Float requestFloat(boolean mayBeNull) throws InterruptedIOException {
        while (true) {
            try {
                String string = requestString(mayBeNull);
                if (string == null) {
                    return null;
                } else {
                    return Float.parseFloat(string);
                }
            } catch (NumberFormatException e) {
                System.err.println("Ошибка парсинга Float.");
            }
        }
    }

    public Integer requestInteger(boolean mayBeNull) throws InterruptedIOException {
        while (true) {
            try {
                String string = requestString(mayBeNull);
                if (string == null) {
                    return null;
                } else {
                    return Integer.parseInt(string);
                }
            } catch (NumberFormatException e) {
                System.err.println("Ошибка парсинга Integer.");
            }
        }
    }

    public String chooseFromEnumValues(Enum<?>[] values, boolean mayBeNull) throws InterruptedIOException {
        while (true) {
            try {
                String string = requestString(mayBeNull).toUpperCase();
                if (Arrays.stream(values).anyMatch(value -> value.toString().equals(string))) {
                    return string;
                } else {
                    System.err.println("Значение не найдено");
                }
            } catch (NullPointerException e) {
                return null;
            }
        }
    }

    public boolean isScriptMode() {
        return scriptMode;
    }
}
