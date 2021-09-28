package client;

import common.StringDye;

import java.io.InterruptedIOException;
import java.util.*;

public class InputHandler {
    public static final String InterruptCommand = "exit";

    private final LinkedList<Scanner> scanners = new LinkedList<>();
    private final LinkedList<String> scannersNames = new LinkedList<>();
    private Scanner scanner;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public String nextLine() throws InterruptedIOException, NoSuchElementException {
        String string = scanner.nextLine().trim();
        if (string.equals(InterruptCommand)) {
            throw new InterruptedIOException();
        } else {
            return string;
        }
    }

    public String requestString(boolean mayBeNull) throws InterruptedIOException, NoSuchElementException {
        while (true) {
            String string = nextLine();
            if (string.equals("")) {
                if (mayBeNull) {
                    return null;
                } else {
                    System.out.println(StringDye.yellow("Не может быть null."));
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
                System.out.println(StringDye.yellow("Ошибка парсинга Integer."));
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
                System.out.println(StringDye.yellow("Ошибка парсинга Float."));
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
                System.out.println(StringDye.yellow("Ошибка парсинга Integer."));
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
                    System.out.println(StringDye.yellow("Значение не найдено"));
                }
            } catch (NullPointerException e) {
                return null;
            }
        }
    }

    public boolean isScriptMode() {
        return !scanners.isEmpty();
    }

    public void scriptFrom(Scanner scanner, String fileName) {
        scanners.add(this.scanner);
        scannersNames.add(fileName);
        this.scanner = scanner;
    }

    public void switchScript() {
        scanner = scanners.removeLast();
        scannersNames.remove();
    }

    public LinkedList<String> getScannersNames() {
        return scannersNames;
    }
}
