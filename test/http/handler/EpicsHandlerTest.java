package http.handler;

import com.google.gson.Gson;
import exeptions.NotFoundExeption;
import http.HttpTaskServer;
import manager.InMemoryTaskManager;
import manager.TaskManager;
import model.Epic;
import model.Status;
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

class EpicsHandlerTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = taskServer.getGson();

    public EpicsHandlerTest() throws IOException {
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
    public void testAddEpic() throws IOException, InterruptedException, NotFoundExeption {
        Epic epic = new Epic(1,
                TypeTask.EPIC,
                "nameTest1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now(),
                0,
                LocalDateTime.now());
        String strEpic = gson.toJson(epic);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(strEpic))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Epic> epicList = manager.getAllEpics();

        assertEquals(1, epicList.size(), "Количество Эпиков не совпадает");
        assertEquals(epic, manager.getEpicForId(1), "Эпик добавился, неправельный!");
        assertEquals(201, response.statusCode(), "Ошибка статуса!");
    }

    @Test
    public void testGetEpic() throws IOException, InterruptedException {
        Epic epic1 = new Epic(1,
                TypeTask.EPIC,
                "nameTest1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now(),
                0,
                LocalDateTime.now());
        Epic epic2 = new Epic(2,
                TypeTask.EPIC,
                "nameTest2",
                "disTest2",
                Status.NEW,
                LocalDateTime.now().plusDays(1),
                0,
                LocalDateTime.now());
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/2");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String strEpic = gson.toJson(epic2);
        String strEpicJson = gson.toJson(response.body());
        assertEquals(strEpic, gson.fromJson(strEpicJson, String.class), "Эпики не совпадают");
        assertEquals(200, response.statusCode(), "Неправильный статус при получении Эпика");
    }

    @Test
    public void testDeleteEpic() throws IOException, InterruptedException {
        Epic epic1 = new Epic(1,
                TypeTask.EPIC,
                "nameTest1",
                "disTest1",
                Status.NEW,
                LocalDateTime.now(),
                0,
                LocalDateTime.now());
        Epic epic2 = new Epic(2,
                TypeTask.EPIC,
                "nameTest2",
                "disTest2",
                Status.NEW,
                LocalDateTime.now().plusDays(1),
                0,
                LocalDateTime.now());
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
        //Количество Эпиков до удаления
        List<Epic> epicList = manager.getAllEpics();
        assertEquals(2, epicList.size(), "До удаления неправильное количество Эпиков");

        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

        List<Epic> epicListAftherDel = manager.getAllEpics();
        assertEquals(1, epicListAftherDel.size(), "количество Эпиков после удаления неправильное");
        assertEquals(200, response.statusCode());
    }
}