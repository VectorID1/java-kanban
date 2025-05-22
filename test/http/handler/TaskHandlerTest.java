package http.handler;

import com.google.gson.Gson;
import exeptions.NotFoundExeption;
import http.HttpTaskServer;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import model.Status;
import model.Task;
import model.TypeTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskHandlerTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = taskServer.getGson();

    public TaskHandlerTest() throws IOException {
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
    public void testAddTask() throws IOException, InterruptedException {
        Task task = new Task(1,
                TypeTask.TASK,
                "test1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now(),
                150,
                LocalDateTime.now());

        String taskToJson = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskToJson))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Task> taskList = manager.getAllTask();

        assertNotNull(taskList, "Задача не добавлена в лист.");
        assertEquals(1, taskList.size(), "Некоректное количество задач");
        assertEquals("test1", taskList.get(0).getName());
    }

    @Test
    public void testGetTask() throws IOException, InterruptedException {
        Task task = new Task(1,
                TypeTask.TASK,
                "test1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now(),
                150,
                LocalDateTime.now());

        String taskJson = gson.toJson(task);
        manager.addTask(task);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String newTaskJson = gson.toJson(response.body());

        assertEquals(200, response.statusCode());

        assertEquals(taskJson, gson.fromJson(newTaskJson, String.class), "Задачи не равны");
    }

    @Test
    public void testGetAllTasks() throws IOException, InterruptedException {
        Task task1 = new Task(1,
                TypeTask.TASK,
                "test1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now(),
                150,
                LocalDateTime.now());
        Task task2 = new Task(1,
                TypeTask.TASK,
                "test2",
                "disTest2",
                Status.NEW,
                LocalDateTime.now().plusDays(1),
                100,
                LocalDateTime.now());
        manager.addTask(task1);
        manager.addTask(task2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Task> listTask = manager.getAllTask();
        String stringListTask1 = gson.toJson(listTask);
        String stringListTask2 = gson.toJson(response.body());


        assertEquals(stringListTask1, gson.fromJson(stringListTask2, String.class), "Списки отличатся! При получении всех задач");
        assertEquals(2, listTask.size(), "Количество задач разное! При получении всех задач.");
    }

    @Test
    public void testDeleteTask() throws IOException, InterruptedException, NotFoundExeption {
        Task task1 = new Task(1,
                TypeTask.TASK,
                "test1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now(),
                150,
                LocalDateTime.now());
        Task task2 = new Task(1,
                TypeTask.TASK,
                "test2",
                "disTest2",
                Status.NEW,
                LocalDateTime.now().plusDays(1),
                100,
                LocalDateTime.now());
        manager.addTask(task1);
        manager.addTask(task2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        // Проверка количества задач до запроса.
        List<Task> listTask = manager.getAllTask();
        assertEquals(2, listTask.size(), "Количество задач отличатся!В тесте Удаления");

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Task> listTaskAfther = manager.getAllTask();
        Task newTask = manager.getTaskForId(2);
        assertEquals(1, listTaskAfther.size(), "Количество задач полсе удаление не совпадает!");
        assertEquals(task2, newTask, "Задачи не совпадают после удаления!(не так удиалилась!)");
    }

}