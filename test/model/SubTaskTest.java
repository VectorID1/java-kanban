package model;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubTaskTest {
    static InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    public void equalsSubTask() {
        SubTask subTask = new SubTask(1,
                "Test subTaskName",
                "Test SubTask description",
                Status.NEW,
                1);
        SubTask subTask1 = new SubTask(1,
                "Test subTaskName",
                "Test SubTask description",
                Status.NEW,
                1);

        taskManager.addSubTask(subTask);
        SubTask newSubtask = taskManager.getSubTaskForId(1);

        Assertions.assertEquals(newSubtask,subTask1,"Задачи не совпадают");
    }
}
