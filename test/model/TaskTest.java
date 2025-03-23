package model;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.Status.NEW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

class TaskTest {
     InMemoryTaskManager taskManager = new InMemoryTaskManager();


    @Test
            void equalsTask() {
        Task task = new Task(1,"Test addNewTask", "Test addNewTask description", NEW);
        Task task1 = new Task(1,"Test addNewTask", "Test addNewTask description", NEW);


        Assertions.assertEquals(task,task1, "Задачи не совпадают.");
    }



}