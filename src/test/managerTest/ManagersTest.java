package test.managerTest;

import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Status.NEW;

class ManagersTest {
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    @BeforeEach
    public void beforeEach (){
        Epic epic = new Epic("Test addNewEpic",
                "Test addNewEpic description");
        SubTask subTask = new SubTask("Test addSubTask",
                "Test addSubTask description",
                Status.NEW,
                1);
        Task task = new Task("Test addNewTask",
                "Test addNewTask description",
                NEW);
        taskManager.addEpic(epic);
        taskManager.addTask(task);
        taskManager.addSubTask(subTask);

    }

    @Test
            public void managerGetDefaultHistory() {
        taskManager.getTaskForId(2);
        taskManager.getEpicForId(1);
        taskManager.getSubTaskForId(3);
        HistoryManager historyManager = Managers.getDefaultHistory();
        Assertions.assertNotNull(historyManager);
    }

}