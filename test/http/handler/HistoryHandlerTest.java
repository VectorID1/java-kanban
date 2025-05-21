package http.handler;

import com.google.gson.Gson;
import exeptions.NotFoundExeption;
import http.HttpTaskServer;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryHandlerTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = taskServer.getGson();

    public HistoryHandlerTest() throws IOException {
    }

    @BeforeEach
    public void setUp() throws NotFoundExeption, IOException {
        manager.removeAllTasks();
        manager.removeAllEpics();
        manager.removeAllSubTasks();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void testGetHistoty() throws NotFoundExeption, IOException, InterruptedException {
        Epic epic = new Epic(1,
                TypeTask.EPIC,
                "nameHistory",
                "disHistory",
                Status.NEW,
                LocalDateTime.now(),
                100, LocalDateTime.now(),
                new ArrayList<>());
        SubTask subTask1 = new SubTask(2,
                TypeTask.SUBTASK,
                "test1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now().plusMonths(1),
                150,
                LocalDateTime.now(),
                1);
        Task task = new Task(3,
                TypeTask.TASK,
                "test2",
                "disTest3",
                Status.NEW,
                LocalDateTime.now().plusHours(3),
                150,
                LocalDateTime.now());
        manager.addEpic(epic);
        manager.addSubTask(subTask1);
        manager.addTask(task);
        manager.getEpicForId(1);
        manager.getSubTaskForId(2);
        manager.getTaskForId(3);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/history/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String str = response.body().toString();
        List<Task> str1 = manager.getHistory();
        String str2 = gson.toJson(str1);
        assertEquals(200, response.statusCode());
        assertEquals(str, str2, "Списки истории просмотренных задач отличаются!");
    }
}