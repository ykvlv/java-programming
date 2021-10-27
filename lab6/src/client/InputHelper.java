package client;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class InputHelper {
    private Scanner scanner;
    private boolean scriptMode;
    private final ArrayDeque<Scanner> deque = new ArrayDeque<>();

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public String requestString(boolean mayBeNull) {
        String string;
        do {
            string = scanner.nextLine().trim();
            if (string.equals("")) {
                if (mayBeNull) {
                    return null;
                } else {
                    System.out.println("Поле не может быть null");
                }
            }
        } while (string.equals(""));
        return string;
    }

    public Long requestLong(boolean mayBeNull) {
        long l;
        while (true) {
            try {
                l = Long.parseLong(scanner.nextLine().trim());
                return l;
            } catch (NumberFormatException e) {
                if (mayBeNull) {
                    return null;
                }
                System.out.println("Ошибка парсинга числа");
            }
        }
    }

    public float requestFloat() {
        float f;
        while (true) {
            try {
                f = Float.parseFloat(scanner.nextLine().trim());
                return f;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка парсинга числа");
            }
        }
    }

    public int requestInt() {
        int i;
        while (true) {
            try {
                i = Integer.parseInt(scanner.nextLine().trim());
                return i;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка парсинга числа");
            }
        }
    }

    public String chooseFromArray(ArrayList<String> a, boolean mayBeNull) {
        String string;
        while (true) {
            string = scanner.nextLine().trim().toUpperCase();
            if (string.equals("") && mayBeNull) {
                return null;
            }
            if (a.contains(string)) {
                return string;
            } else {
                System.out.printf("Нужно выбрать значение из предложенных %s%s%n", a.toString(), mayBeNull ? " или пропустить" : "");
            }
        }
    }

    public void endScriptMode() {
        scriptMode = false;
        scanner = deque.removeLast();
    }

    public boolean isScriptMode() {
        return scriptMode;
    }

    public void setScriptMode(Scanner scanner) {
        deque.add(this.scanner);
        this.scanner = scanner;
        scriptMode = true;
    }

    public boolean hasNext() {
        return scanner.hasNext();
    }
}
