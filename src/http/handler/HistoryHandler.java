package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler {


    public HistoryHandler(TaskManager managers, Gson gson) {
        super(managers, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!requestMethod(exchange).equals("GET")) {
            sendText(exchange, "Данный метод не поддерживается!", 400);
        } else {
            sendText(exchange, gson.toJson(taskManager.getHistory()), 200);
        }
    }
}
