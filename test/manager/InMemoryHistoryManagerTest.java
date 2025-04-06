package manager;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.Status.NEW;

class InMemoryHistoryManagerTest {
    private InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    public void addTaskEndOfTheList() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Test addNewTask1",
                "Test addNewTask1 description",
                NEW);
        Task task2 = new Task("Test addNewTask2",
                "Test addNewTask2 description",
                NEW);
        Task task3 = new Task("Test addNewTask3",
                "Test addNewTask3 description",
                NEW);
        Task task4 = new Task("Test addNewTask4",
                "Test addNewTask4 description",
                NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.getTaskForId(1);
        taskManager.getTaskForId(2);
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);
        taskManager.getTaskForId(2);
        List<Task> listTask = historyManager.getTasks();
        Assertions.assertEquals(4, listTask.size());
        Assertions.assertEquals(task2, listTask.get(3));

    }

    @Test
    public void removeTask() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Test addNewTask1",
                "Test addNewTask1 description",
                NEW);
        Task task2 = new Task("Test addNewTask2",
                "Test addNewTask2 description",
                NEW);
        Task task3 = new Task("Test addNewTask3",
                "Test addNewTask3 description",
                NEW);
        Task task4 = new Task("Test addNewTask4",
                "Test addNewTask4 description",
                NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.getTaskForId(1);
        taskManager.getTaskForId(2);
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);

        List<Task> historyTask = historyManager.getTasks();
        Assertions.assertEquals(4, historyTask.size());
        historyManager.remove(3);
        List<Task> newHistoryTask = historyManager.getTasks();
        Assertions.assertEquals(3, newHistoryTask.size());

    }

    @Test
    public void addOtherTask() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Epic epic = new Epic("Test addNewEpic",
                "Test addNewEpic description");
        SubTask subTask = new SubTask("Test addSubTask",
                "Test addSubTask description",
                NEW,
                1);
        Task task = new Task("Test addNewTask",
                "Test addNewTask description",
                NEW);
        Task task1 = new Task("Test addNewTask1",
                "Test addNewTask1 description",
                NEW);
        Task task2 = new Task("Test addNewTask2",
                "Test addNewTask2 description",
                NEW);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        taskManager.addTask(task);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.getEpicForId(1);
        taskManager.getSubTaskForId(2);
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);
        taskManager.getTaskForId(5);

        List<Task> historyList = historyManager.getTasks();
        Assertions.assertEquals(5, historyList.size(), "Разное количесво задач");

    }

    @Test
    public void managerGet6TasksSave4TaskInHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Test addNewTask1",
                "Test addNewTask1 description",
                NEW);
        Task task2 = new Task("Test addNewTask2",
                "Test addNewTask2 description",
                NEW);
        Task task3 = new Task("Test addNewTask3",
                "Test addNewTask3 description",
                NEW);
        Task task4 = new Task("Test addNewTask4",
                "Test addNewTask4 description",
                NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.getTaskForId(1);
        taskManager.getTaskForId(2);
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);
        taskManager.getTaskForId(2);
        taskManager.getTaskForId(1);

        List<Task> historyTask = historyManager.getTasks();
        Assertions.assertEquals(4, historyTask.size(), "Количество задач не совпадает");

    }

    @Test
    public void managerGet4TasksSave4Tasks() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        // InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Test addNewTask1",
                "Test addNewTask1 description",
                NEW);
        Task task2 = new Task("Test addNewTask2",
                "Test addNewTask2 description",
                NEW);
        Task task3 = new Task("Test addNewTask3",
                "Test addNewTask3 description",
                NEW);
        Task task4 = new Task("Test addNewTask4",
                "Test addNewTask4 description",
                NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.getTaskForId(1);
        taskManager.getTaskForId(2);
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);

        List<Task> tasks;

        tasks = historyManager.getTasks();

        Assertions.assertEquals(4, tasks.size());
    }

    @Test
    public void managerGet1TaskSave1Task() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        Task task = new Task(1, "Название задачи 1", "Описание задачи 1", NEW);
        taskManager.addTask(task);
        taskManager.getTaskForId(1);

        List<Task> tasks = historyManager.getTasks();

        Assertions.assertEquals(1, tasks.size());
    }

}