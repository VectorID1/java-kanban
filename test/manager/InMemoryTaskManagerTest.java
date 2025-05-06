package manager;

import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static model.Status.*;

class InMemoryTaskManagerTest {
    static InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void addNewTask() {
        Epic epic = new Epic(1, "Test addNewEpic", "Test addNewEpic description");
        Task task = new Task(2, "Test addNewTask", "Test addNewTask description", NEW);
        SubTask subTask = new SubTask(3, "Test subTaskName", "Test SubTask description", Status.DONE, 1);

        taskManager.addEpic(epic);
        taskManager.addTask(task);
        taskManager.addSubTask(subTask);

        final Epic savedEpic = taskManager.getEpicForId(1);
        final Task savedTask = taskManager.getTaskForId(2);
        final SubTask savedTaskSubTask = taskManager.getSubTaskForId(3);

        Assertions.assertNotNull(savedEpic, "Epic не найдена.");
        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertNotNull(savedTaskSubTask, "Подзадача не найдена.");

        Assertions.assertEquals(savedEpic.getTitleTask(), "Test addNewEpic", "Название Epic не совпадает.");
        Assertions.assertEquals(savedTask.getTitleTask(), "Test addNewTask", "Название задачи не совпадает.");
        Assertions.assertEquals(savedTaskSubTask.getTitleTask(), "Test subTaskName", "Название подзадачи не совпадает.");

        Assertions.assertEquals(savedEpic.getDescriptionTask(), "Test addNewEpic description", "Описание Epic не совпадает.");
        Assertions.assertEquals(savedTask.getDescriptionTask(), "Test addNewTask description", "Описание задачи не совпадает.");
        Assertions.assertEquals(savedTaskSubTask.getDescriptionTask(), "Test SubTask description", "Описание подзадачи не совпадает.");

        Assertions.assertEquals(savedEpic.getIdTask(), 1, "id Epic не совпадает");
        Assertions.assertEquals(savedTask.getIdTask(), 2, "id Task не совпадает");
        Assertions.assertEquals(savedTaskSubTask.getIdTask(), 3, "id SubTask не совпадает");

        Assertions.assertEquals(savedEpic.getStatusTask(), DONE, "Статус Epic  не совпадает");
        Assertions.assertEquals(savedTask.getStatusTask(), NEW, "Статус Task  не совпадает");
        Assertions.assertEquals(savedTaskSubTask.getStatusTask(), DONE, "Статус SubTask  не совпадает");

    }

    @Test
    void listTaskTest() {
        Task task = new Task(1, "Test addNewTask", "Test addNewTask description", NEW);
        Task task1 = new Task(2, "Test addNewTask1", "Test addNewTask1 description", NEW);
        taskManager.addTask(task);
        taskManager.addTask(task1);
        final List<Task> tasks = taskManager.getAllTask();

        Assertions.assertNotNull(tasks, "Задачи не возвращаются.");
        Assertions.assertEquals(2, tasks.size(), "Неверное количество задач.");
    }

    @Test
    public void getSubTaskForEpic() {
        Epic epic = new Epic("Test addEpic", "Test addEpic descriprion");
        SubTask subTask = new SubTask("Test addSubTask", "Test addSubTask description", Status.NEW, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);

        ArrayList<SubTask> newSubTasks = new ArrayList<>();
        newSubTasks.add(subTask);
        Assertions.assertEquals(newSubTasks, taskManager.getAllSubTasksForEpic(1), "Подзадача не равна!");
    }

    @Test
    public void addSubTaskEpicNonExistentId() {
        Epic epic = new Epic(1, "Test addNewEpic", "Test addNewEpic description");
        SubTask subTask = new SubTask(2, "Test subTaskName", "Test SubTask description", Status.DONE, 2);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        Assertions.assertNull(taskManager.getSubTaskForId(2));

    }

    @Test
    public void addTaskPreIdFrom99To1() {
        Task task = new Task(99, "TestIdTask", "Test Id Task from 99 to 1", NEW);
        taskManager.addTask(task);
        Assertions.assertEquals(taskManager.getTaskForId(1), task, "id Task не совпадает в тесте PreId");
    }

    @Test
    public void newStatusEpic() {
        Epic epic = new Epic(1, "Test addNewEpic", "Test addNewEpic description");
        SubTask subTask = new SubTask(2, "Test subTaskName", "Test SubTask description", NEW, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        Epic newEpic = taskManager.getEpicForId(1);
        SubTask newSubTask = taskManager.getSubTaskForId(2);

        Assertions.assertEquals(newEpic.getStatusTask(), NEW);

        newSubTask.setStatusTask(DONE);
        taskManager.addSubTask(newSubTask);

        Assertions.assertEquals(newEpic.getStatusTask(), DONE);
    }

    @Test
    public void removeEpic() {
        Epic epic = new Epic(1, "Test addNewEpic", "Test addNewEpic description");
        SubTask subTask = new SubTask(2, "Test subTaskName", "Test SubTask description", NEW, 1);
        SubTask subTask1 = new SubTask(3, "Test subTaskName", "Test SubTask description", NEW, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        taskManager.addSubTask(subTask1);

        Assertions.assertNotNull(taskManager.getSubTaskForId(2));

        taskManager.removeEpicForId(1);

        Assertions.assertNull(taskManager.getEpicForId(1));
        Assertions.assertNull(taskManager.getSubTaskForId(2));
        Assertions.assertNull(taskManager.getSubTaskForId(3));
    }

    @Test
    public void statusEpicNew() {
        Epic epic = new Epic(1, "nameEpic", "discriptionEpic");
        SubTask subTask1 = new SubTask(2, "nameSubtask1", "discriptionSubtask1", NEW, 1);
        SubTask subTask2 = new SubTask(2, "nameSubtask2", "discriptionSubtask2", NEW, 1);
        SubTask subTask3 = new SubTask(2, "nameSubtask3", "discriptionSubtask3", NEW, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        Assertions.assertEquals(taskManager.getEpicForId(1).getStatusTask(), NEW, "У эпика не задаётся Статус NEW");
    }

    @Test
    public void statusEpicDone() {
        Epic epic = new Epic(1, "nameEpic", "discriptionEpic");
        SubTask subTask1 = new SubTask(2, "nameSubtask1", "discriptionSubtask1", DONE, 1);
        SubTask subTask2 = new SubTask(2, "nameSubtask2", "discriptionSubtask2", DONE, 1);
        SubTask subTask3 = new SubTask(2, "nameSubtask3", "discriptionSubtask3", DONE, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        Assertions.assertEquals(taskManager.getEpicForId(1).getStatusTask(), DONE, "У эпика не задаётся Статус DONE");
    }

    @Test
    public void statusEpicNewAndDone() {
        Epic epic = new Epic(1, "nameEpic", "discriptionEpic");
        SubTask subTask1 = new SubTask(2, "nameSubtask1", "discriptionSubtask1", DONE, 1);
        SubTask subTask2 = new SubTask(2, "nameSubtask2", "discriptionSubtask2", NEW, 1);
        SubTask subTask3 = new SubTask(2, "nameSubtask3", "discriptionSubtask3", NEW, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        Assertions.assertEquals(taskManager.getEpicForId(1).getStatusTask(), IN_PROGRESS, "У эпика не задаётся Статус");
    }

    @Test
    public void statusEpicInProgress() {
        Epic epic = new Epic(1, "nameEpic", "discriptionEpic");
        SubTask subTask1 = new SubTask(2, "nameSubtask1", "discriptionSubtask1", IN_PROGRESS, 1);
        SubTask subTask2 = new SubTask(2, "nameSubtask2", "discriptionSubtask2", IN_PROGRESS, 1);
        SubTask subTask3 = new SubTask(2, "nameSubtask3", "discriptionSubtask3", IN_PROGRESS, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        Assertions.assertEquals(taskManager.getEpicForId(1).getStatusTask(), IN_PROGRESS, "У эпика не задаётся Статус");
    }

    @Test
    public void intersectionTimeTask() {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 5, 3, 20, 10);
        Task task1 = new Task(1, TypeTask.TASK, "nameTask1", "discriptionTask1",
                NEW, startTime1, 150, null);
        LocalDateTime startTime2 = LocalDateTime.of(2025, 5, 3, 21, 10);
        Task task2 = new Task(1, TypeTask.TASK, "nameTask2", "discriptionTask2",
                NEW, startTime2, 100, null);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        List<Task> newListTask = new ArrayList<>();
        newListTask.add(task1);
        Assertions.assertEquals(1, taskManager.getPrioritizedTasks().size(), "Неверное количесто задач");
        Assertions.assertEquals(newListTask, taskManager.getPrioritizedTasks(), "Задача с пересекающимся " +
                "временем добавилась!");
    }

    @Test
    public void validTestTimeTaskAndSort() {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 05, 15, 20, 00);
        LocalDateTime startTime2 = LocalDateTime.of(2025, 06, 15, 20, 00);
        LocalDateTime startTime3 = LocalDateTime.of(2024, 06, 15, 20, 00);
        Task task1 = new Task(1, TypeTask.TASK, "nameTask1", "discriptionTask1", NEW, startTime1, 100, null);
        Task task2 = new Task(1, TypeTask.TASK, "nameTask2", "discriptionTask2", NEW, startTime2, 99, null);
        Task task3 = new Task(1, TypeTask.TASK, "nameTask3", "discriptionTask3", NEW, startTime3, 60, null);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        List<Task> newListTask = new ArrayList<>();
        newListTask.add(task3);     //Добавление задач по возрастанию времени начала!
        newListTask.add(task1);     //Добавление задач по возрастанию времени начала!
        newListTask.add(task2);     //Добавление задач по возрастанию времени начала!
        Assertions.assertEquals(3, taskManager.getPrioritizedTasks().size(), "Неверное количесто задач");
        Assertions.assertEquals(newListTask, taskManager.getPrioritizedTasks(), "Задачи не сортируются по дате!!!");
    }

    @Test
    public void endNewTaskIsTimeIntervalOfOldTask() {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 05, 15, 20, 00);
        LocalDateTime startTime2 = LocalDateTime.of(2025, 05, 15, 19, 00);
        Task task1 = new Task(1, TypeTask.TASK, "nameTask1", "discriptionTask1", NEW, startTime1, 100, null);
        Task task2 = new Task(1, TypeTask.TASK, "nameTask2", "discriptionTask2", NEW, startTime2, 90, null);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        Assertions.assertNull(taskManager.getTaskForId(2), "Добавилась задача, которая не должна добавиться");
        Assertions.assertEquals(task1, taskManager.getTaskForId(1), "Задача перезаписана! Неверно!");
        Assertions.assertEquals(1, taskManager.getPrioritizedTasks().size(), "Неверное количество задач когда " +
                "окончание новой находиться на отрезке старой");
    }

    @Test
    public void startNewTaskInTimeIntervalOfOldTask() {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 10, 15, 20, 00);
        LocalDateTime startTime2 = LocalDateTime.of(2025, 10, 15, 21, 00);
        Task task1 = new Task(1, TypeTask.TASK, "nameTask1", "discriptionTask1", NEW, startTime1, 100, null);
        Task task2 = new Task(1, TypeTask.TASK, "nameTask2", "discriptionTask2", NEW, startTime2, 100, null);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        Assertions.assertEquals(task1, taskManager.getTaskForId(1), "Задача перезаписано не верно!");
        Assertions.assertEquals(1, taskManager.getAllTask().size());
        Assertions.assertEquals(1, taskManager.getPrioritizedTasks().size(), "Неверное количество задач!" +
                "при пересечении начала новой с отрезком строй задачи!");
    }

    @Test
    public void newTaskIntirelyInTimeOldTask() {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 10, 15, 20, 00);
        LocalDateTime startTime2 = LocalDateTime.of(2025, 10, 16, 10, 00);
        Task task1 = new Task(1, TypeTask.TASK, "nameTask1", "discriptionTask1", NEW, startTime1, 2000, null);
        Task task2 = new Task(1, TypeTask.TASK, "nameTask2", "discriptionTask2", NEW, startTime2, 100, null);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        Assertions.assertEquals(task1, taskManager.getTaskForId(1), "Задача перезаписано не верно!");
        Assertions.assertEquals(1, taskManager.getAllTask().size(), "Неверное количество задач. Когда новая" +
                "полностью находиться во времени старой");
    }

    @Test
    public void oldTaskIntirelyInTimeNewTask() {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 10, 15, 10, 00);
        LocalDateTime startTime2 = LocalDateTime.of(2025, 10, 14, 10, 00);
        Task task1 = new Task(1, TypeTask.TASK, "nameTask1", "discriptionTask1", NEW, startTime1, 100, null);
        Task task2 = new Task(1, TypeTask.TASK, "nameTask2", "discriptionTask2", NEW, startTime2, 2000, null);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        Assertions.assertEquals(task1, taskManager.getTaskForId(1), "Задача перезаписано не верно!");
        Assertions.assertEquals(1, taskManager.getAllTask().size(), "Неверное количество задач. Когда старая " +
                "полностью находиться во времени новой");
    }
}