package manager;

import exeptions.NotFoundExeption;
import exeptions.TimeConflictExeption;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static model.Status.*;
import static org.junit.Assert.assertThrows;

class InMemoryTaskManagerTest {
    static InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void addNewTask() throws NotFoundExeption {
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

        Assertions.assertEquals(savedEpic.getName(), "Test addNewEpic", "Название Epic не совпадает.");
        Assertions.assertEquals(savedTask.getName(), "Test addNewTask", "Название задачи не совпадает.");
        Assertions.assertEquals(savedTaskSubTask.getName(), "Test subTaskName", "Название подзадачи не совпадает.");

        Assertions.assertEquals(savedEpic.getDescription(), "Test addNewEpic description", "Описание Epic не совпадает.");
        Assertions.assertEquals(savedTask.getDescription(), "Test addNewTask description", "Описание задачи не совпадает.");
        Assertions.assertEquals(savedTaskSubTask.getDescription(), "Test SubTask description", "Описание подзадачи не совпадает.");

        Assertions.assertEquals(savedEpic.getId(), 1, "id Epic не совпадает");
        Assertions.assertEquals(savedTask.getId(), 2, "id Task не совпадает");
        Assertions.assertEquals(savedTaskSubTask.getId(), 3, "id SubTask не совпадает");

        Assertions.assertEquals(savedEpic.getStatus(), DONE, "Статус Epic  не совпадает");
        Assertions.assertEquals(savedTask.getStatus(), NEW, "Статус Task  не совпадает");
        Assertions.assertEquals(savedTaskSubTask.getStatus(), DONE, "Статус SubTask  не совпадает");

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
    public void getSubTaskForEpic() throws NotFoundExeption {
        Epic epic = new Epic("Test addEpic", "Test addEpic descriprion");
        SubTask subTask = new SubTask("Test addSubTask", "Test addSubTask description", Status.NEW, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);

        ArrayList<SubTask> newSubTasks = new ArrayList<>();
        newSubTasks.add(subTask);
        Assertions.assertEquals(newSubTasks, taskManager.getAllSubTasksForEpic(1), "Подзадача не равна!");
    }

    @Test
    public void addSubTaskEpicNonExistentId() throws NotFoundExeption {
        Epic epic = new Epic(1, "Test addNewEpic", "Test addNewEpic description");
        SubTask subTask = new SubTask(2, "Test subTaskName", "Test SubTask description", Status.DONE, 2);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        NotFoundExeption exception = assertThrows(
                NotFoundExeption.class,
                () -> taskManager.getSubTaskForId(2)
        );
        Assertions.assertEquals(exception.getMessage(), "Подзадачи с таким ID нет");

    }

    @Test
    public void addTaskPreIdFrom99To1() throws NotFoundExeption {
        Task task = new Task(99, "Testid", "Test Id Task from 99 to 1", NEW);
        taskManager.addTask(task);
        Assertions.assertEquals(taskManager.getTaskForId(1), task, "id Task не совпадает в тесте PreId");
    }

    @Test
    public void newStatusEpic() throws NotFoundExeption {
        Epic epic = new Epic(1, "Test addNewEpic", "Test addNewEpic description");
        SubTask subTask = new SubTask(2, "Test subTaskName", "Test SubTask description", NEW, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        Epic newEpic = taskManager.getEpicForId(1);
        SubTask newSubTask = taskManager.getSubTaskForId(2);

        Assertions.assertEquals(newEpic.getStatus(), NEW);

        newSubTask.setStatus(DONE);
        taskManager.addSubTask(newSubTask);

        Assertions.assertEquals(newEpic.getStatus(), DONE);
    }

    @Test
    public void removeEpic() throws NotFoundExeption {
        Epic epic = new Epic(1, "Test addNewEpic", "Test addNewEpic description");
        SubTask subTask = new SubTask(2, "Test subTaskName", "Test SubTask description", NEW, 1);
        SubTask subTask1 = new SubTask(3, "Test subTaskName", "Test SubTask description", NEW, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        taskManager.addSubTask(subTask1);

        Assertions.assertNotNull(taskManager.getSubTaskForId(2));

        taskManager.removeEpicForId(1);
        NotFoundExeption exeptionEpic = assertThrows(
                NotFoundExeption.class, () ->
                        taskManager.getEpicForId(1)
        );
        NotFoundExeption exeptionSubtask = assertThrows(
                NotFoundExeption.class, () ->
                        taskManager.getSubTaskForId(2)
        );
        Assertions.assertEquals(exeptionEpic.getMessage(), "Эпика с таким ID нет");
        Assertions.assertEquals(exeptionSubtask.getMessage(), "Подзадачи с таким ID нет");
    }

    @Test
    public void statusEpicNew() throws NotFoundExeption {
        Epic epic = new Epic(1, "nameEpic", "discriptionEpic");
        SubTask subTask1 = new SubTask(2, "nameSubtask1", "discriptionSubtask1", NEW, 1);
        SubTask subTask2 = new SubTask(2, "nameSubtask2", "discriptionSubtask2", NEW, 1);
        SubTask subTask3 = new SubTask(2, "nameSubtask3", "discriptionSubtask3", NEW, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        Assertions.assertEquals(taskManager.getEpicForId(1).getStatus(), NEW, "У эпика не задаётся Статус NEW");
    }

    @Test
    public void statusEpicDone() throws NotFoundExeption {
        Epic epic = new Epic(1, "nameEpic", "discriptionEpic");
        SubTask subTask1 = new SubTask(2, "nameSubtask1", "discriptionSubtask1", DONE, 1);
        SubTask subTask2 = new SubTask(2, "nameSubtask2", "discriptionSubtask2", DONE, 1);
        SubTask subTask3 = new SubTask(2, "nameSubtask3", "discriptionSubtask3", DONE, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        Assertions.assertEquals(taskManager.getEpicForId(1).getStatus(), DONE, "У эпика не задаётся Статус DONE");
    }

    @Test
    public void statusEpicNewAndDone() throws NotFoundExeption {
        Epic epic = new Epic(1, "nameEpic", "discriptionEpic");
        SubTask subTask1 = new SubTask(2, "nameSubtask1", "discriptionSubtask1", DONE, 1);
        SubTask subTask2 = new SubTask(2, "nameSubtask2", "discriptionSubtask2", NEW, 1);
        SubTask subTask3 = new SubTask(2, "nameSubtask3", "discriptionSubtask3", NEW, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        Assertions.assertEquals(taskManager.getEpicForId(1).getStatus(), IN_PROGRESS, "У эпика не задаётся Статус");
    }

    @Test
    public void statusEpicInProgress() throws NotFoundExeption {
        Epic epic = new Epic(1, "nameEpic", "discriptionEpic");
        SubTask subTask1 = new SubTask(2, "nameSubtask1", "discriptionSubtask1", IN_PROGRESS, 1);
        SubTask subTask2 = new SubTask(2, "nameSubtask2", "discriptionSubtask2", IN_PROGRESS, 1);
        SubTask subTask3 = new SubTask(2, "nameSubtask3", "discriptionSubtask3", IN_PROGRESS, 1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        Assertions.assertEquals(taskManager.getEpicForId(1).getStatus(), IN_PROGRESS, "У эпика не задаётся Статус");
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
        TimeConflictExeption exeption = assertThrows(
                TimeConflictExeption.class, () ->
                        taskManager.addTask(task2)
        );

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
    public void endNewTaskIsTimeIntervalOfOldTask() throws NotFoundExeption {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 05, 15, 20, 00);
        LocalDateTime startTime2 = LocalDateTime.of(2025, 05, 15, 19, 00);
        Task task1 = new Task(1, TypeTask.TASK, "nameTask1", "discriptionTask1", NEW, startTime1, 100, null);
        Task task2 = new Task(1, TypeTask.TASK, "nameTask2", "discriptionTask2", NEW, startTime2, 90, null);

        taskManager.addTask(task1);
        TimeConflictExeption exeption = assertThrows(
                TimeConflictExeption.class, () ->
                        taskManager.addTask(task2)
        );
        NotFoundExeption exeption1 = assertThrows(
                NotFoundExeption.class, () ->
                        taskManager.getTaskForId(2)
        );
        Assertions.assertEquals(exeption1.getMessage(), "Задачи с ID (2) нет!");
        Assertions.assertEquals(task1, taskManager.getTaskForId(1), "Задача перезаписана! Неверно!");
        Assertions.assertEquals(1, taskManager.getPrioritizedTasks().size(), "Неверное количество задач когда " +
                "окончание новой находиться на отрезке старой");
    }

    @Test
    public void startNewTaskInTimeIntervalOfOldTask() throws NotFoundExeption {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 10, 15, 20, 00);
        LocalDateTime startTime2 = LocalDateTime.of(2025, 10, 15, 21, 00);
        Task task1 = new Task(1, TypeTask.TASK, "nameTask1", "discriptionTask1", NEW, startTime1, 100, null);
        Task task2 = new Task(1, TypeTask.TASK, "nameTask2", "discriptionTask2", NEW, startTime2, 100, null);
        taskManager.addTask(task1);
        TimeConflictExeption exeption = assertThrows(
                TimeConflictExeption.class, () ->
                        taskManager.addTask(task2)
        );
        Assertions.assertEquals(task1, taskManager.getTaskForId(1), "Задача перезаписано не верно!");
        Assertions.assertEquals(1, taskManager.getAllTask().size());
        Assertions.assertEquals(1, taskManager.getPrioritizedTasks().size(), "Неверное количество задач!" +
                "при пересечении начала новой с отрезком строй задачи!");
    }

    @Test
    public void newTaskIntirelyInTimeOldTask() throws NotFoundExeption {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 10, 15, 20, 00);
        LocalDateTime startTime2 = LocalDateTime.of(2025, 10, 16, 10, 00);
        Task task1 = new Task(1, TypeTask.TASK, "nameTask1", "discriptionTask1", NEW, startTime1, 2000, null);
        Task task2 = new Task(1, TypeTask.TASK, "nameTask2", "discriptionTask2", NEW, startTime2, 100, null);
        taskManager.addTask(task1);
        TimeConflictExeption exeption = assertThrows(
                TimeConflictExeption.class, () ->
                        taskManager.addTask(task2)
        );
        Assertions.assertEquals(task1, taskManager.getTaskForId(1), "Задача перезаписано не верно!");
        Assertions.assertEquals(1, taskManager.getAllTask().size(), "Неверное количество задач. Когда новая" +
                "полностью находиться во времени старой");
    }

    @Test
    public void oldTaskIntirelyInTimeNewTask() throws NotFoundExeption {
        LocalDateTime startTime1 = LocalDateTime.of(2025, 10, 15, 10, 00);
        LocalDateTime startTime2 = LocalDateTime.of(2025, 10, 14, 10, 00);
        Task task1 = new Task(1, TypeTask.TASK, "nameTask1", "discriptionTask1", NEW, startTime1, 100, null);
        Task task2 = new Task(1, TypeTask.TASK, "nameTask2", "discriptionTask2", NEW, startTime2, 2000, null);
        taskManager.addTask(task1);
        TimeConflictExeption exeption = assertThrows(
                TimeConflictExeption.class, () ->
                        taskManager.addTask(task2)
        );
        Assertions.assertEquals(task1, taskManager.getTaskForId(1), "Задача перезаписано не верно!");
        Assertions.assertEquals(1, taskManager.getAllTask().size(), "Неверное количество задач. Когда старая " +
                "полностью находиться во времени новой");
    }
}