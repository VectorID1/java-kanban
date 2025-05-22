package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exeptions.NotFoundExeption;
import exeptions.TimeConflictExeption;
import manager.TaskManager;
import model.Epic;
import model.SubTask;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class EpicsHandler extends BaseHttpHandler {
    public EpicsHandler(TaskManager managers, Gson gson) {
        super(managers, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (requestMethod(exchange)) {
                case "GET":
                    handleGet(exchange);
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

    private void handleGet(HttpExchange exchange) throws IOException, NotFoundExeption {
        Optional<Integer> epicID = parseId(exchange.getRequestURI().getPath());
        String[] subtaskUrl = exchange.getRequestURI().getPath().split("/");
        boolean subtaskRequest = subtaskUrl.length >= 4 && "subtask".equals(subtaskUrl[3]);
        if (epicID.isEmpty()) {
            sendText(exchange, gson.toJson(taskManager.getAllEpics()), 200);
        } else if (subtaskRequest) {
            List<SubTask> subTasks = taskManager.getAllSubTasksForEpic(epicID.get());
            sendText(exchange, gson.toJson(subTasks), 200);
        } else {
            Epic epic = taskManager.getEpicForId(epicID.get());
            sendText(exchange, gson.toJson(epic), 200);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        Optional<Integer> taskID = parseId(exchange.getRequestURI().getPath());
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = gson.fromJson(body, Epic.class);
        if (taskID.isPresent()) {
            taskManager.updateEpic(epic);
            sendText(exchange, "Эпик обновлен", 201);
        } else {
            taskManager.addEpic(epic);
            sendText(exchange, "Эпик добавлен", 201);
        }
    }

    private void handleDelete(HttpExchange exchange) throws IOException, NotFoundExeption {
        Optional<Integer> taskID = parseId(exchange.getRequestURI().getPath());
        if (taskID.isEmpty()) {
            sendText(exchange, "Нужно указать ID задачи", 400);
        } else {
            taskManager.removeEpicForId(taskID.get());
            sendText(exchange, "Задача удалена", 200);

        }
    }
}
