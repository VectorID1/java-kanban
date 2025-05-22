package http;

import com.google.gson.*;
import com.sun.net.httpserver.HttpServer;
import exeptions.NotFoundExeption;
import http.handler.*;
import http.handler.adapter.DurationAdapter;
import http.handler.adapter.LocalDateTimeAdapter;
import manager.Managers;
import manager.TaskManager;
import model.Status;
import model.Task;
import model.TypeTask;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private HttpServer server;
    protected TaskManager taskManager;
    protected Gson gson;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(long.class, new DurationAdapter())
                .create();

        this.server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler(taskManager, gson));
        server.createContext("/subtasks", new SubtaskHandler(taskManager, gson));
        server.createContext("/epics", new EpicsHandler(taskManager, gson));
        server.createContext("/history", new HistoryHandler(taskManager, gson));
        server.createContext("/priorityzed", new PriorityHandler(taskManager, gson));

    }

    public Gson getGson() {
        return this.gson;
    }


    public void start() {
        server.start();
        System.out.println("Сервер запущен на порте " + PORT);


    }

    public void stop() {
        server.stop(1);
        System.out.println("Сервер остановлен.");
    }

    public static void main(String[] args) throws IOException, NotFoundExeption {

        TaskManager manager = Managers.getDefault();
        new HttpTaskServer(manager).start();
        Task task = new Task(1, TypeTask.TASK,"name","dis", Status.NEW);
        manager.addTask(task);
    }

}

