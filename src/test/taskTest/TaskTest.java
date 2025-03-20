package test.taskTest;

import manager.InMemoryTaskManager;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static model.Status.NEW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

class TaskTest {
     InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @BeforeEach
            public  void beforeEach() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);
        taskManager.addTask(task);

    }

    @Test
    void addNewTask() {
        final Task savedTask = taskManager.getTaskForId(1);

        Assertions.assertNotNull(savedTask, "Задача не найдена.");
    }
    @Test
            void equalsTask() {
        Task task = taskManager.getTaskForId(1);
        Task newTask = new Task(1,"Test addNewTask", "Test addNewTask description", NEW);
        Assertions.assertEquals(task, newTask, "Задачи не совпадают.");
    }
    @Test
    void listTaskTest() {
        final List<Task> tasks = taskManager.getAllTask();
        Task newTask = new Task(1,"Test addNewTask", "Test addNewTask description", NEW);

        Assertions.assertNotNull(tasks,"Задачи не возвращаются.");
        Assertions.assertEquals(1, tasks.size(), "Неверное количество задач.");
        Assertions.assertEquals(newTask, taskManager.getTaskForId(1), "Задачи не совпадают.");
    }


}