package manager;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Status.DONE;
import static model.Status.NEW;

class InMemoryTaskManagerTest {
    static InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @BeforeEach
    public void beforeEach() {
         taskManager = new InMemoryTaskManager();
    }
    @Test
    public void addNewTask() {
        Epic epic = new Epic(
                1,
                "Test addNewEpic",
                "Test addNewEpic description");
        Task task = new Task(
                2,
                "Test addNewTask",
                "Test addNewTask description",
                NEW);
        SubTask subTask = new SubTask(
                3,
                "Test subTaskName",
                "Test SubTask description",
                Status.DONE,
                1);

        taskManager.addEpic(epic);
        taskManager.addTask(task);
        taskManager.addSubTask(subTask);

        final Epic savedEpic = taskManager.getEpicForId(1);
        final Task savedTask = taskManager.getTaskForId(2);
        final SubTask savedTaskSubTask = taskManager.getSubTaskForId(3);

        Assertions.assertNotNull(savedTask,"Epic не найдена.");
        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertNotNull(savedTaskSubTask,"Подзадача не найдена.");

        Assertions.assertEquals(savedEpic.getTitleTask(),"Test addNewEpic","Название Epic не совпадает.");
        Assertions.assertEquals(savedTask.getTitleTask(),"Test addNewTask","Название задачи не совпадает.");
        Assertions.assertEquals(savedTaskSubTask.getTitleTask(),"Test subTaskName",
                "Название подзадачи не совпадает.");

        Assertions.assertEquals(savedEpic.getDescriptionTask(),"Test addNewEpic description",
                "Описание Epic не совпадает.");
        Assertions.assertEquals(savedTask.getDescriptionTask(),"Test addNewTask description",
                "Описание задачи не совпадает.");
        Assertions.assertEquals(savedTaskSubTask.getDescriptionTask(),"Test SubTask description",
                "Описание подзадачи не совпадает.");

        Assertions.assertEquals(savedEpic.getIdTask(),1,"id Epic не совпадает");
        Assertions.assertEquals(savedTask.getIdTask(),2,"id Task не совпадает");
        Assertions.assertEquals(savedTaskSubTask.getIdTask(),3,"id SubTask не совпадает");

        Assertions.assertEquals(savedEpic.getStatusTask(),DONE,"Статус Epic  не совпадает");
        Assertions.assertEquals(savedTask.getStatusTask(),NEW,"Статус Task  не совпадает");
        Assertions.assertEquals(savedTaskSubTask.getStatusTask(),DONE,"Статус SubTask  не совпадает");

    }
    @Test
    void listTaskTest() {
        Task task = new Task(1,"Test addNewTask", "Test addNewTask description", NEW);
        Task task1 = new Task(2,"Test addNewTask1", "Test addNewTask1 description", NEW);
        taskManager.addTask(task);
        taskManager.addTask(task1);
        final List<Task> tasks = taskManager.getAllTask();

        Assertions.assertNotNull(tasks,"Задачи не возвращаются.");
        Assertions.assertEquals(2, tasks.size(), "Неверное количество задач.");
    }

    @Test
    public void getSubTaskForEpic(){
        Epic epic = new Epic(
                "Test addEpic",
                "Test addEpic descriprion"
        );
        SubTask subTask = new SubTask(
                "Test addSubTask",
                "Test addSubTask description",
                Status.NEW,
                1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);

        ArrayList<SubTask> newSubTasks = new ArrayList<>();
        newSubTasks.add(subTask);
        Assertions.assertEquals(newSubTasks,taskManager.getAllSubTasksForEpic(1),"Подзадача не равна!");
    }

    @Test
    public void addSubTaskEpicNonExistentId() {
        Epic epic = new Epic(
                1,
                "Test addNewEpic",
                "Test addNewEpic description");
        SubTask subTask = new SubTask(
                2,
                "Test subTaskName",
                "Test SubTask description",
                Status.DONE,
                2);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        Assertions.assertNull(taskManager.getSubTaskForId(2));

    }

    @Test
    public void addTaskPreIdFrom99To1() {
        Task task = new Task(
                99,
                "TestIdTask",
                "Test Id Task from 99 to 1",
                NEW
        );
        taskManager.addTask(task);
        Assertions.assertEquals(taskManager.getTaskForId(1),task,"id Task не совпадает в тесте PreId");
    }

    @Test
    public void newStatusEpic() {
        Epic epic = new Epic(
                1,
                "Test addNewEpic",
                "Test addNewEpic description");
        SubTask subTask = new SubTask(
                2,
                "Test subTaskName",
                "Test SubTask description",
                NEW,
                1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        Epic newEpic = taskManager.getEpicForId(1);
        SubTask newSubTask = taskManager.getSubTaskForId(2);

        Assertions.assertEquals(newEpic.getStatusTask(),NEW);

        newSubTask.setStatusTask(DONE);
        taskManager.addSubTask(newSubTask);

        Assertions.assertEquals(newEpic.getStatusTask(),DONE);
    }


}