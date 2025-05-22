package http.handler;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public abstract class BaseHttpHandler implements HttpHandler {
    protected TaskManager taskManager;
    protected Gson gson;


    public BaseHttpHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    public void sendText(HttpExchange exchange, String json, int statusCode) throws IOException {
        byte[] responce = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responce.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responce);
        }
    }

    public void sendNotFound(HttpExchange exchange, String text) throws IOException {
        byte[] responce = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(404, responce.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responce);
        }
    }

    public void sendHashInteraction(HttpExchange exchange, String text) throws IOException {
        byte[] responce = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(406, responce.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responce);
        }
    }

    public Optional<Integer> parseId(String path) {
        try {
            String[] parts = path.split("/");
            return Optional.of(Integer.parseInt(parts[2]));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public String requestMethod(HttpExchange exchange) {
        return exchange.getRequestMethod();
    }


}
