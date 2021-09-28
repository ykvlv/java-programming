package client;

import common.*;

import java.io.Console;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;

public class Client {
    private final DeliveryHandler deliveryHandler;
    private final ResponseHandler responseHandler;

    public Client(DeliveryHandler deliveryHandler, ResponseHandler responseHandler) {
        this.deliveryHandler = deliveryHandler;
        this.responseHandler = responseHandler;
    }

    public boolean connect(Console console) throws IOException, ClassNotFoundException {
        String username = console.readLine("Введите имя пользователя: ").trim();
        if (username.equals("")) {
            return false;
        }
        // Существует ли пользователь?
        deliveryHandler.sendRequest(new Request(RequestType.TOUCH, username));
        Response response = deliveryHandler.receiveResponse();
        String password = "";
        String repeatPassword;
        if (response.getResponseType().equals(ResponseType.CONNECT)) {
            if (response.getObject().equals(username)) {
                // Пользователь существует
                password = new String(console.readPassword("Введите пароль: "));
            } else {
                // Регистрация нового пользовтеля
                System.out.printf("Регистрация нового пользователя %s%n", username);
                do {
                    password = new String(console.readPassword("Введите пароль: "));
                    repeatPassword = new String(console.readPassword("Повторите пароль: "));
                    if (!password.equals(repeatPassword)) {
                        System.out.println(StringDye.yellow("Пароли отличаются!"));
                    }
                } while (!password.equals(repeatPassword));
            }
        }
        if (password.equals("")) {
            return false;
        }

        // Отправить запрос на авторизацию
        deliveryHandler.sendRequest(new Request(RequestType.AUTH, username, password));
        response = deliveryHandler.receiveResponse();
        switch (response.getResponseType()) {
            case DONE:
                System.out.printf(StringDye.green("Добро пожаловать %s!%n"), username);
                return true;
            case ERROR:
                System.out.println(StringDye.red("Неверный пароль. Повторие попытку позже."));
            default:
                return false;
        }
    }

    public void run(InputHandler inputHandler) {
        while (true) {
            System.out.print(inputHandler.isScriptMode() ? "" : "% ");
            try {
                String command = inputHandler.nextLine();
                Request request = new Request(RequestType.COMMAND, command);
                deliveryHandler.sendRequest(request);
                Response response = deliveryHandler.receiveResponse();
                responseHandler.process(response);
            } catch (NoSuchElementException e) {
                inputHandler.switchScript();
            } catch (SocketTimeoutException e) {
                System.out.println(StringDye.yellow("Превышено время ожидания от сервера"));
            } catch (InterruptedIOException e) {
                return;
            } catch (IOException e) {
                System.out.println(StringDye.yellow("Ошибка приема/передачи."));
            } catch (ClassNotFoundException e) {
                System.out.println(StringDye.yellow("Ошибка распаковки ответа."));
            }
        }
    }
}
