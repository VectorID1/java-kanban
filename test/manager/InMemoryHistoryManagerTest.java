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

    @BeforeEach
    public void beforeEach() {

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
        Task task3 = new Task("Test addNewTask3",
                "Test addNewTask3 description",
                NEW);
        Task task4 = new Task("Test addNewTask4",
                "Test addNewTask4 description",
                NEW);
        Task task5 = new Task("Test addNewTask5",
                "Test addNewTask description",
                NEW);
        Task task6 = new Task("Test addNewTask5",
                "Test addNewTask description",
                NEW);
        Task task7 = new Task("Test addNewTask6",
                "Test addNewTask6 description",
                NEW);
        Task task8 = new Task("Test addNewTask6",
                "Test addNewTask6 description",
                NEW);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        taskManager.addTask(task);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.addTask(task5);
        taskManager.addTask(task6);
        taskManager.addTask(task7);
        taskManager.addTask(task8);
    }



    @Test
    public void managerGet11TasksSave10LastViewedTasksAndRemove1() {
        taskManager.getTaskForId(2);
        taskManager.getTaskForId(4);
        taskManager.getTaskForId(5);
        taskManager.getTaskForId(6);
        taskManager.getTaskForId(7);
        taskManager.getTaskForId(8);
        taskManager.getTaskForId(9);
        taskManager.getTaskForId(10);
        taskManager.getTaskForId(11);
        taskManager.getEpicForId(1);
        taskManager.getSubTaskForId(3);
        List<Task> tasks;

        tasks = taskManager.getHistory();

        Assertions.assertEquals(10, tasks.size());
    }

    @Test
    public void managerGet9TasksSave9Tasks() {
        taskManager.getTaskForId(2);
        taskManager.getTaskForId(4);
        taskManager.getTaskForId(5);
        taskManager.getTaskForId(6);
        taskManager.getTaskForId(7);
        taskManager.getTaskForId(8);
        taskManager.getTaskForId(9);
        taskManager.getTaskForId(10);
        taskManager.getTaskForId(11);
        List<Task> tasks;

        tasks = taskManager.getHistory();

        Assertions.assertEquals(9, tasks.size());
    }

    @Test
    public void managerGet1TaskSave1Task() {
        taskManager.getTaskForId(2);
        List<Task> tasks;

        tasks = taskManager.getHistory();

        Assertions.assertEquals(1, tasks.size());
    }

    @Test
    public void managerGetDefaultHistory() {
        HistoryManager defoultHistoryManager = Managers.getDefaultHistory();

        Assertions.assertNotNull(defoultHistoryManager.getHistory());

    }
}