package model;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class EpicTest {
    static InMemoryTaskManager taskManager = new InMemoryTaskManager();


    @Test
    public void equalsEpic() {
        Epic epic = new Epic(1,"Test addNewEpic", "Test addNewEpic description");
        Epic epic1 = new Epic(1,"Test addNewEpic", "Test addNewEpic description");


        Assertions.assertEquals(epic,epic1,"Задачи не совпадают.");
    }

}