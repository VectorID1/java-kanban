package test.epicTest;

import manager.InMemoryTaskManager;
import model.Epic;
import model.Status;
import model.SubTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class EpicTest {
    static InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @BeforeEach
    public void beforeEach() {
        Epic epic = new Epic("Test addNewEpic", "Test addNewEpic description");
        taskManager.addEpic(epic);
    }

    @Test
    public void addEpic() {
        final Epic addEpic = taskManager.getEpicForId(1);

        Assertions.assertNotNull(addEpic, "Epic не найден");
    }
    @Test
    public void getSubTaskForEpic(){
        SubTask subTask = new SubTask("Test addSubTask",
                "Test addSubTask description",
                Status.NEW,
                1);

        taskManager.addSubTask(subTask);

        ArrayList<SubTask> newSubTasks = new ArrayList<>();
        newSubTasks.add(subTask);
        Assertions.assertEquals(newSubTasks,taskManager.getAllSubTasksForEpic(1),"Подзадача не равна!");
    }
}