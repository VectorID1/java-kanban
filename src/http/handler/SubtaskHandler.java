package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exeptions.NotFoundExeption;
import exeptions.TimeConflictExeption;
import manager.TaskManager;
import model.SubTask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class SubtaskHandler extends BaseHttpHandler {
    public SubtaskHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (requestMethod(exchange)) {
                case "GET":
                    handlGet(exchange);
                    break;
                case "POST":
                    handlePost(exchange);
                    break;
                case "DELETE":
                    handleDelete(exchange);
                    break;
                default:
                    sendText(exchange, "Метод не поддерживается", 500);
            }
        } catch (NotFoundExeption e) {
            sendNotFound(exchange, e.getMessage());
        } catch (TimeConflictExeption e) {
            sendHashInteraction(exchange, e.getMessage());
        }
    }

    private void handlGet(HttpExchange exchange) throws IOException, NotFoundExeption {
        Optional<Integer> taskID = parseId(exchange.getRequestURI().getPath());
        if (taskID.isPresent()) {
            sendText(exchange, gson.toJson(taskManager.getSubTaskForId(taskID.get())), 200);
        } else {
            sendText(exchange, gson.toJson(taskManager.getAllSubTask()), 200);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException, TimeConflictExeption, NotFoundExeption {
        Optional<Integer> taskID = parseId(exchange.getRequestURI().getPath());
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        SubTask subTask = gson.fromJson(body, SubTask.class);
        if (taskID.isPresent()) {
            taskManager.updateSubTask(subTask);
            sendText(exchange, "Задача обновлена", 201);
        } else {
            taskManager.addSubTask(subTask);
            sendText(exchange, "Задача добавлена", 201);
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        Optional<Integer> taskID = parseId(exchange.getRequestURI().getPath());
        if (taskID.isEmpty()) {
            sendText(exchange, "Нужно указать ID задачи", 400);
        } else {
            try {
                taskManager.removeSubTaskForId(taskID.get());
            } catch (Exception ignored) {
            }
        }
        sendText(exchange, "Задача удалена", 200);
    }
}
