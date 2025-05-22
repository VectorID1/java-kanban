package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;

import java.io.IOException;

public class PriorityHandler extends BaseHttpHandler {

    public PriorityHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            sendText(exchange, gson.toJson(taskManager.getPrioritizedTasks()), 200);
        } catch (NullPointerException e) {
            sendText(exchange, "Список пуст!", 400);
        }
    }
}
