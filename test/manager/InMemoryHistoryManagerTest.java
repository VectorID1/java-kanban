package manager;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.Status.NEW;

class InMemoryHistoryManagerTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @BeforeEach
    public void beforeEach() {
        Epic epic = new Epic(1, "Test addNewEpic",
                "Test addNewEpic description");
        SubTask subTask = new SubTask(2, "Test addSubTask",
                "Test addSubTask description",
                NEW,
                1);
        Task task1 = new Task(3, "Test addNewTask1",
                "Test addNewTask1 description",
                NEW);
        Task task2 = new Task(4, "Test addNewTask2",
                "Test addNewTask2 description",
                NEW);
        Task task3 = new Task(5,
                "Test addNewTask3",
                "Test addNewTask3 description",
                NEW);
        Task task4 = new Task(6,
                "Test addNewTask4",
                "Test addNewTask4 description",
                NEW);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
    }

    @Test
    public void managerGet1TaskSave1Task() {
        taskManager.getTaskForId(3);

        final List<Task> tasks = historyManager.getTasks();

        Assertions.assertEquals(1, tasks.size());
    }

    @Test
    public void addTaskEndOfTheList() {
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);
        taskManager.getTaskForId(5);
        taskManager.getTaskForId(6);
        taskManager.getTaskForId(4);

        final List<Task> listTask = historyManager.getTasks();

        Assertions.assertEquals(4, listTask.size());
        Assertions.assertEquals(taskManager.getTaskForId(4), listTask.get(3));

    }

    @Test
    public void removeTask() {
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);
        taskManager.getTaskForId(5);
        taskManager.getTaskForId(6);

        final List<Task> historyTask = historyManager.getTasks();
        Assertions.assertEquals(4, historyTask.size());
        historyManager.remove(3);
        final List<Task> newHistoryTask = historyManager.getTasks();
        Assertions.assertEquals(3, newHistoryTask.size());

    }

    @Test
    public void managerGet6TasksSave4TaskInHistory() {
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);
        taskManager.getTaskForId(5);
        taskManager.getTaskForId(6);
        taskManager.getTaskForId(3); //Повторный просмотр.
        taskManager.getTaskForId(6); //Повторный просмотр.

        final List<Task> historyTask = historyManager.getTasks();
        Assertions.assertEquals(4, historyTask.size(), "Количество задач не совпадает");

    }

    @Test
    public void managerGet4TasksSave4Tasks() {
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);
        taskManager.getTaskForId(5);
        taskManager.getTaskForId(6);

        final List<Task> tasks1 = historyManager.getTasks();


        Assertions.assertEquals(4, tasks1.size());
    }
//    @Test
//    public void addOtherTask() {
//        taskManager.getEpicForId(1);
//        taskManager.getSubTaskForId(2);
//        taskManager.getTaskForId(3);
//        taskManager.getTaskForId(4);
//        taskManager.getTaskForId(5);
//        taskManager.getTaskForId(6);
//
//
//       final List<Task> historyList = historyManager.getTasks();
//        Assertions.assertEquals(6, historyList.size(), "Разное количесво задач");
//
//    }

}