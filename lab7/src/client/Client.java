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
        String password;
        String repeatPassword;
        if (response.getResponseType() != ResponseType.CONNECT) {
            if (response.getResponseType() == ResponseType.ERROR) {
                System.out.println((String) response.getObject());
            }
            return false;
        }

        if (response.getObject() != null && response.getObject().equals(username)) {
            // Пользователь существует
            password = new String(console.readPassword("Введите пароль (Enter — отмена): "));
        } else {
            // Регистрация нового пользовтеля
            System.out.println("Пользователь не найден! Регистрация");
            do {
                password = new String(console.readPassword("Введите пароль (Enter — отмена): ")).trim();
                if (password.equals("")) { return false; }
                repeatPassword = new String(console.readPassword("Повторите пароль: ")).trim();
                if (repeatPassword.equals("")) { return false; }

                if (!password.equals(repeatPassword)) {
                    System.out.println(StringDye.yellow("Пароли отличаются!"));
                }
            } while (!password.equals(repeatPassword));
        }

        // Отправить запрос на авторизацию
        deliveryHandler.sendRequest(new Request(RequestType.AUTH, username, password));
        response = deliveryHandler.receiveResponse();
        switch (response.getResponseType()) {
            case DONE:
                System.out.println(StringDye.green((String) response.getObject()));
                return true;
            case ERROR:
                System.out.println(StringDye.red((String) response.getObject()));
                 return connect(console);
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
