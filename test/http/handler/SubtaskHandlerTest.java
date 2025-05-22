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

class SubtaskHandlerTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = taskServer.getGson();

    public SubtaskHandlerTest() throws IOException {
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
    public void testAddSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic(1,
                TypeTask.EPIC,
                "nameEpic",
                "disEpic",
                Status.NEW,
                LocalDateTime.now(),
                100, LocalDateTime.now(),
                new ArrayList<>());
        SubTask subTask = new SubTask(2,
                TypeTask.SUBTASK,
                "test1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now(),
                150,
                LocalDateTime.now(),
                1);
        manager.addEpic(epic);

        String subTaskToJson = gson.toJson(subTask);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(subTaskToJson))
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<SubTask> subtaskList = manager.getAllSubTask();

        assertNotNull(subtaskList, "Подзадача не добавлена в лист.");
        assertEquals(1, subtaskList.size(), "Некоректное количество подзадач");
        assertEquals("test1", subtaskList.get(0).getName());
    }

    @Test
    public void testGetSubtask() throws IOException, InterruptedException, NotFoundExeption {
        Epic epic = new Epic(1,
                TypeTask.EPIC,
                "nameEpic",
                "disEpic",
                Status.NEW,
                LocalDateTime.now(),
                100, LocalDateTime.now(),
                new ArrayList<>());
        SubTask subTask1 = new SubTask(2,
                TypeTask.SUBTASK,
                "test1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now(),
                150,
                LocalDateTime.now(),
                1);
        SubTask subTask2 = new SubTask(3,
                TypeTask.SUBTASK,
                "test2",
                "disTest2",
                Status.NEW,
                LocalDateTime.now().plusMonths(2),
                180,
                LocalDateTime.now(),
                1);
        manager.addEpic(epic);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String strSubtask = gson.toJson(response.body());
        String stsSub = gson.fromJson(strSubtask, String.class);
        SubTask subTask = gson.fromJson(stsSub, SubTask.class);
        assertEquals(subTask, subTask1, "Подзадачи не равны!");
        assertEquals(200, response.statusCode(), "Ошибка статуса");
    }

    @Test
    public void testGetAllSubtask() throws IOException, InterruptedException, NotFoundExeption {
        Epic epic = new Epic(1,
                TypeTask.EPIC,
                "nameEpic",
                "disEpic",
                Status.NEW,
                LocalDateTime.now(),
                100, LocalDateTime.now(),
                new ArrayList<>());
        SubTask subTask1 = new SubTask(2,
                TypeTask.SUBTASK,
                "test1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now(),
                150,
                LocalDateTime.now(),
                1);
        SubTask subTask2 = new SubTask(3,
                TypeTask.SUBTASK,
                "test2",
                "disTest2",
                Status.NEW,
                LocalDateTime.now().plusMonths(2),
                180,
                LocalDateTime.now(),
                1);
        manager.addEpic(epic);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String allSubtask = gson.toJson(response.body());
        String allSubtaskInmanager = gson.toJson(manager.getAllSubTask());
        assertEquals(200, response.statusCode(), "Ошибка статуса");
        assertEquals(allSubtask, gson.toJson(allSubtaskInmanager, String.class), "Списки подзадач не равны!");
    }

    @Test
    public void testDeleteSubtask() throws IOException, InterruptedException, NotFoundExeption {
        Epic epic = new Epic(1,
                TypeTask.EPIC,
                "nameEpic",
                "disEpic",
                Status.NEW,
                LocalDateTime.now(),
                100, LocalDateTime.now(),
                new ArrayList<>());
        SubTask subTask1 = new SubTask(2,
                TypeTask.SUBTASK,
                "test1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now(),
                150,
                LocalDateTime.now(),
                1);
        SubTask subTask2 = new SubTask(3,
                TypeTask.SUBTASK,
                "test2",
                "disTest2",
                Status.NEW,
                LocalDateTime.now().plusMonths(2),
                180,
                LocalDateTime.now(),
                1);
        manager.addEpic(epic);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/3");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(1, manager.getAllSubTask().size(), "Количество Подазадач после удаления неправильное!");
    }
}