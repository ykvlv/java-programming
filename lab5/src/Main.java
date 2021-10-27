import client.*;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            FlatHashMap flatHashMap = new FlatHashMap(LocalDateTime.now(), args);

            Scanner scanner = new Scanner(System.in);
            InputHelper inputHelper = new InputHelper(scanner);


            FlatCreator flatCreator = new FlatCreator(inputHelper, flatHashMap);
            CommandRegister cr = new CommandRegister(flatHashMap, flatCreator, inputHelper);

            String request;
            do {
                System.out.print("% ");
                request = inputHelper.nextLine();
                cr.decryptAndRun(request);
            } while (!request.equals("exit"));
        } catch (Exception e) {
            System.out.println("Завершение работы.");
        }
    }
}
