package client;

import common.Request;
import common.RequestType;
import common.Response;

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
                System.out.println("Превышено время ожидания от сервера");
            } catch (InterruptedIOException e) {
                return;
            } catch (IOException e) {
                System.err.println("Ошибка приема/передачи.");
            } catch (ClassNotFoundException e) {
                System.err.println("Ошибка распаковки ответа.");
            }
        }
    }
}
