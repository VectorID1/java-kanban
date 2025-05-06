package manager;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Status.NEW;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager historyManager;
    Epic epic;
    SubTask subTask;
    Task task1;
    Task task2;
    Task task3;
    Task task4;

    @BeforeEach
    public void beforeEach() {
        historyManager = new InMemoryHistoryManager();
        epic = new Epic(1, "Test addNewEpic",
                "Test addNewEpic description");
        subTask = new SubTask(2, "Test addSubTask",
                "Test addSubTask description",
                NEW,
                1);
        task1 = new Task(3, "Test addNewTask1",
                "Test addNewTask1 description",
                NEW);
        task2 = new Task(4, "Test addNewTask2",
                "Test addNewTask2 description",
                NEW);
        task3 = new Task(5,
                "Test addNewTask3",
                "Test addNewTask3 description",
                NEW);
        task4 = new Task(6,
                "Test addNewTask4",
                "Test addNewTask4 description",
                NEW);

    }

    @Test
    public void add3Task() {
        historyManager.add(epic);
        historyManager.add(subTask);
        historyManager.add(task1);

        List<Task> tasks = historyManager.getTasks();

        Assertions.assertEquals(3, tasks.size(), "Задачи не добавляются в список");
    }

    @Test
    public void removeTask() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);


        List<Task> tasks = historyManager.getTasks();
        Assertions.assertEquals(3, tasks.size());

        historyManager.remove(3);
        List<Task> tasksAfterRemove = historyManager.getTasks();
        Assertions.assertEquals(2, tasksAfterRemove.size(), "Задача не удалилась");
    }

    @Test
    public void removeFirstTask() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);


        List<Task> tasks = historyManager.getTasks();
        Assertions.assertEquals(3, tasks.size());

        historyManager.remove(3);
        List<Task> tasksAfterRemove = new ArrayList<>();
        tasksAfterRemove.add(task2);
        tasksAfterRemove.add(task3);
        Assertions.assertEquals(2, tasksAfterRemove.size(), "Задача не удалилась");
        Assertions.assertEquals(tasksAfterRemove, historyManager.getTasks(), "1 задача не удалилась");
    }

    @Test
    public void removeLastTask() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);


        List<Task> tasks = historyManager.getTasks();
        Assertions.assertEquals(3, tasks.size());

        historyManager.remove(5);
        List<Task> tasksAfterRemove = new ArrayList<>();
        tasksAfterRemove.add(task1);
        tasksAfterRemove.add(task2);
        Assertions.assertEquals(2, tasksAfterRemove.size(), "Задача не удалилась");
        Assertions.assertEquals(tasksAfterRemove, historyManager.getTasks(), "Последняя задача не удалилась");
    }

    @Test
    public void addTaskLinkLast() {
        historyManager.add(epic);
        historyManager.add(task1);
        historyManager.add(task4);

        List<Task> tasks = historyManager.getTasks();
        Assertions.assertEquals(task4, tasks.get(2));

        historyManager.add(epic);
        List<Task> tasksAfterAddEpic = historyManager.getTasks();
        Assertions.assertEquals(epic, tasksAfterAddEpic.get(2), "Задача добавилась не в конец списка");
    }

    @Test
    public void addIdenticalTasks() {
        historyManager.add(task1);
        historyManager.add(task1);
        historyManager.add(task1);

        List<Task> tasks = historyManager.getTasks();
        Assertions.assertEquals(1, tasks.size(), "Задачи не перезаписываются");
    }
}