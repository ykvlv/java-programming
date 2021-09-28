package client;

import common.Request;
import common.RequestType;
import common.Response;
import common.StringDye;

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

    public boolean connect(Console console) {
        String username = console.readLine("Введите имя пользователя: ");
        String password = new String(console.readPassword("Введите пароль: "));
        return true;
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
