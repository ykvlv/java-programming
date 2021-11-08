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
    private String login;

    public Client(DeliveryHandler deliveryHandler, ResponseHandler responseHandler) {
        this.deliveryHandler = deliveryHandler;
        this.responseHandler = responseHandler;
    }

    public String getLogin() {
        return login;
    }

    public String connect(Console console) throws IOException, ClassNotFoundException {
        String login = console.readLine("Введите имя пользователя: ").trim();
        if (login.equals("")) {
            return null;
        }
        // Существует ли пользователь?
        deliveryHandler.sendRequest(new Request(RequestType.TOUCH, login));
        Response response = deliveryHandler.receiveResponse();
        String password;
        String repeatPassword;
        if (response.getResponseType() != ResponseType.CONNECT) {
            if (response.getResponseType() == ResponseType.ERROR) {
                System.out.println((String) response.getObject());
            }
            return null;
        }

        if (response.getObject() != null && response.getObject().equals(login)) {
            // Пользователь существует
            password = new String(console.readPassword("Введите пароль (Enter — отмена): ")).trim();
            if (password.equals("")) {
                return null;
            }
        } else {
            // Регистрация нового пользовтеля
            System.out.println("Пользователь не найден! Регистрация");
            do {
                password = new String(console.readPassword("Введите пароль (Enter — отмена): ")).trim();
                if (password.equals("")) { return null; }
                repeatPassword = new String(console.readPassword("Повторите пароль: ")).trim();
                if (repeatPassword.equals("")) { return null; }
                if (!password.equals(repeatPassword)) {
                    System.out.println(StringDye.yellow("Пароли отличаются!"));
                }
            } while (!password.equals(repeatPassword));
        }

        // Отправить запрос на авторизацию
        deliveryHandler.sendRequest(new Request(RequestType.AUTH, login, password));
        response = deliveryHandler.receiveResponse();
        switch (response.getResponseType()) {
            case DONE:
                System.out.println(StringDye.green((String) response.getObject()));
                this.login = login;
                return password;
            case ERROR:
                System.out.println(StringDye.red((String) response.getObject()));
                return connect(console);
            default:
                return null;
        }
    }

    public void run(InputHandler inputHandler, String login, String password) {
        while (true) {
            System.out.print(inputHandler.isScriptMode() ? "" : "% ");
            try {
                String command = inputHandler.nextLine();
                Request request = new Request(RequestType.COMMAND, login, password, command);
                deliveryHandler.sendRequest(request);
                Response response = deliveryHandler.receiveResponse();
                responseHandler.process(response, login, password);
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
