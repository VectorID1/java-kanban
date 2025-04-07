package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class EpicTest {

    @Test
    public void equalsEpic() {
        Epic epic = new Epic(1, "Test addNewEpic", "Test addNewEpic description");
        Epic epic1 = new Epic(1, "Test addNewEpic", "Test addNewEpic description");


        Assertions.assertEquals(epic, epic1, "Задачи не совпадают.");
    }

}