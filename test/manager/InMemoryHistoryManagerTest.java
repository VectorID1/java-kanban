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
    InMemoryTaskManager taskManager;
    InMemoryHistoryManager historyManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
        historyManager = new InMemoryHistoryManager();
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
    public void managerTest() {
        taskManager.getEpicForId(1);
        taskManager.getSubTaskForId(2);
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);
        taskManager.getTaskForId(5);
        taskManager.getTaskForId(6);

        List<Task> tasks = historyManager.getTasks();

        //Общий список
        Assertions.assertEquals(6, tasks.size());

        //Удаление задачи
        historyManager.remove(2);
        List<Task> tasksAfterRemove = historyManager.getTasks();
        Assertions.assertEquals(5, tasksAfterRemove.size());

        //Добавление в конец списка
        taskManager.getTaskForId(3);
        List<Task> lastTaskAfther = historyManager.getTasks();
        Assertions.assertEquals(lastTaskAfther.getLast(), taskManager.getTaskForId(3));

        //Проверка на удаление повторок
        taskManager.getEpicForId(1);
        taskManager.getSubTaskForId(2);
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);
        taskManager.getTaskForId(5);
        taskManager.getTaskForId(6);
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(3);
        taskManager.getTaskForId(4);
        List<Task> newList = historyManager.getTasks();

        Assertions.assertEquals(6, newList.size());
    }


}