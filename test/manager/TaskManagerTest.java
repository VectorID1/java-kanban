package manager;

import exeptions.NotFoundExeption;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static model.Status.DONE;
import static model.Status.NEW;
import static org.junit.Assert.assertThrows;

class TaskManagerTest {

    static InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    public void addNewTask() throws NotFoundExeption {
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

        Assertions.assertNotNull(savedEpic, "Epic не найдена.");
        Assertions.assertNotNull(savedTask, "Задача не найдена.");
        Assertions.assertNotNull(savedTaskSubTask, "Подзадача не найдена.");

        Assertions.assertEquals(savedEpic.getName(), "Test addNewEpic", "Название Epic не совпадает.");
        Assertions.assertEquals(savedTask.getName(), "Test addNewTask", "Название задачи не совпадает.");
        Assertions.assertEquals(savedTaskSubTask.getName(), "Test subTaskName",
                "Название подзадачи не совпадает.");

        Assertions.assertEquals(savedEpic.getDescription(), "Test addNewEpic description",
                "Описание Epic не совпадает.");
        Assertions.assertEquals(savedTask.getDescription(), "Test addNewTask description",
                "Описание задачи не совпадает.");
        Assertions.assertEquals(savedTaskSubTask.getDescription(), "Test SubTask description",
                "Описание подзадачи не совпадает.");

        Assertions.assertEquals(savedEpic.getId(), 1, "id Epic не совпадает");
        Assertions.assertEquals(savedTask.getId(), 2, "id Task не совпадает");
        Assertions.assertEquals(savedTaskSubTask.getId(), 3, "id SubTask не совпадает");

        Assertions.assertEquals(savedEpic.getStatus(), DONE, "Статус Epic  не совпадает");
        Assertions.assertEquals(savedTask.getStatus(), NEW, "Статус Task  не совпадает");
        Assertions.assertEquals(savedTaskSubTask.getStatus(), DONE, "Статус SubTask  не совпадает");

    }

    @Test
    void getAllTask() {
        Task task1 = new Task(1, "nameTask1", "discriptTask1", NEW);
        Task task2 = new Task(1, "nameTask2", "discriptTask2", NEW);
        Task task3 = new Task(1, "nameTask3", "discriptTask3", NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        ArrayList<Task> newListTask = new ArrayList<>();
        newListTask.add(task1);
        newListTask.add(task2);
        newListTask.add(task3);
        Assertions.assertEquals(newListTask, taskManager.getAllTask(), "Списки Задач не совпадают");
    }

    @Test
    void getAllEpics() {
        Epic epic1 = new Epic(1, "nameEpic1", "discriptEpic1");
        Epic epic2 = new Epic(1, "nameEpic2", "discriptEpic2");
        Epic epic3 = new Epic(1, "nameEpic3", "discriptEpic3");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addEpic(epic3);
        ArrayList<Epic> newListEpic = new ArrayList<>();
        newListEpic.add(epic1);
        newListEpic.add(epic2);
        newListEpic.add(epic3);
        Assertions.assertEquals(newListEpic, taskManager.getAllEpics(), "Списки Задач не совпадают");
    }

    @Test
    void getAllSubTaskandGetAllSubtaskForEpicId() throws NotFoundExeption {
        Epic epic1 = new Epic(1, "nameEpic1", "discriptEpic1");
        SubTask subTask1 = new SubTask(1, "nameSubtask1", "discriptSubtaks1", NEW, 1);
        SubTask subTask2 = new SubTask(1, "nameSubTask2", "discriptSubtask2", NEW, 1);
        SubTask subTask3 = new SubTask(1, "nameSubTask3", "discriptSubtaks3", NEW, 1);
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        ArrayList<SubTask> newListSubtask = new ArrayList<>();
        newListSubtask.add(subTask1);
        newListSubtask.add(subTask2);
        newListSubtask.add(subTask3);
        Assertions.assertEquals(newListSubtask, taskManager.getAllSubTask(), "Списки Подзадач не совпадают");
        Assertions.assertEquals(newListSubtask, taskManager.getAllSubTasksForEpic(1), "Задачи Эпика не совпадают");
    }


    @Test
    void removeAllTasks() {
        Task task1 = new Task(1, "nameTask1", "discriptTask1", NEW);
        Task task2 = new Task(1, "nameTask2", "discriptTask2", NEW);
        Task task3 = new Task(1, "nameTask3", "discriptTask3", NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        Assertions.assertNotNull(taskManager.getAllTask(), "Список задач до удаления пустой");
        taskManager.removeAllTasks();
        ArrayList<Task> EmptyList = new ArrayList<>(); //Пустой лист для проверки
        Assertions.assertEquals(EmptyList, taskManager.getAllTask(), "Задачи не удлились");
    }

    @Test
    void removeAllEpics() {
        Epic epic1 = new Epic(1, "nameEpic1", "discriptEpic1");
        Epic epic2 = new Epic(1, "nameEpic2", "discriptEpic2");
        Epic epic3 = new Epic(1, "nameEpic3", "discriptEpic3");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addEpic(epic3);
        Assertions.assertNotNull(taskManager.getAllEpics(), "Список Эпиков до удаления пустой");
        taskManager.removeAllEpics();
        ArrayList<Epic> EmptyList = new ArrayList<>();
        Assertions.assertEquals(EmptyList, taskManager.getAllEpics(), "Эпики не удалились");
    }

    @Test
    void removeAllSubTasks() throws NotFoundExeption {
        Epic epic1 = new Epic(1, "nameEpic1", "discriptEpic1");
        SubTask subTask1 = new SubTask(1, "nameSubtask1", "discriptSubtaks1", NEW, 1);
        SubTask subTask2 = new SubTask(1, "nameSubTask2", "discriptSubtask2", NEW, 1);
        SubTask subTask3 = new SubTask(1, "nameSubTask3", "discriptSubtaks3", NEW, 1);
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        ArrayList<SubTask> EmptyList = new ArrayList<>();
        Assertions.assertNotNull(taskManager.getAllSubTask(), "Список подзадач до удаления пустой");
        taskManager.removeAllSubTasks();
        Assertions.assertEquals(EmptyList, taskManager.getAllSubTask(), "Подзадачи не удалились");
    }

    @Test
    void getTaskForId() throws NotFoundExeption {
        Task task1 = new Task(1, "nameTask1", "discriptTask1", NEW);
        Task task2 = new Task(1, "nameTask2", "discriptTask2", NEW);
        Task task3 = new Task(1, "nameTask3", "discriptTask3", NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        Assertions.assertEquals(task1, taskManager.getTaskForId(1), "1 задача не добавилось");
        Assertions.assertEquals(task2, taskManager.getTaskForId(2), "2 задача не добавилось");
        Assertions.assertEquals(task3, taskManager.getTaskForId(3), "3 задача не добавилось");
    }

    @Test
    void getEpicForId() throws NotFoundExeption {
        Epic epic1 = new Epic(1, "nameEpic1", "discriptEpic1");
        Epic epic2 = new Epic(1, "nameEpic2", "discriptEpic2");
        Epic epic3 = new Epic(1, "nameEpic3", "discriptEpic3");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addEpic(epic3);
        Assertions.assertEquals(epic1, taskManager.getEpicForId(1), "1 Эпик не добавился");
        Assertions.assertEquals(epic2, taskManager.getEpicForId(2), "2 Эпик не добавился");
        Assertions.assertEquals(epic3, taskManager.getEpicForId(3), "3 Эпик не добавился");
    }

    @Test
    void getSubTaskForId() throws NotFoundExeption {
        Epic epic1 = new Epic(1, "nameEpic1", "discriptEpic1");
        SubTask subTask1 = new SubTask(1, "nameSubtask1", "discriptSubtaks1", NEW, 1);
        SubTask subTask2 = new SubTask(1, "nameSubTask2", "discriptSubtask2", NEW, 1);
        SubTask subTask3 = new SubTask(1, "nameSubTask3", "discriptSubtaks3", NEW, 1);
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        Assertions.assertEquals(subTask1, taskManager.getSubTaskForId(2), "Подзадач 1 не добавилась");
        Assertions.assertEquals(subTask2, taskManager.getSubTaskForId(3), "Подзадач 2 не добавилась");
        Assertions.assertEquals(subTask3, taskManager.getSubTaskForId(4), "Подзадач 3 не добавилась");
    }

    @Test
    void updateTask() throws NotFoundExeption {
        Task task1 = new Task(1, "nameTask1", "discriptTask1", NEW);
        Task updateTask = new Task(1, "newNameTask1", "NewDiscriptTask1", NEW);
        taskManager.addTask(task1);
        taskManager.updateTask(updateTask);
        Assertions.assertEquals(updateTask, taskManager.getTaskForId(1), "Задача не обновилась");
    }

    @Test
    void updateEpic() throws NotFoundExeption {
        Epic epic1 = new Epic(1, "nameEpic1", "discriptEpic1");
        Epic updateEpic = new Epic(1, "NewNameEpic1", "NewDiscriptEpic1");
        taskManager.addEpic(epic1);
        taskManager.updateEpic(updateEpic);
        Assertions.assertEquals(updateEpic, taskManager.getEpicForId(1), "Эпик не обновился");

    }

    @Test
    void updateSubTaskAndUpdateStatusEpic() throws NotFoundExeption {
        Epic epic1 = new Epic(1, "nameEpic1", "discriptEpic1");
        SubTask subTask1 = new SubTask(2, "nameSubtask1", "discriptSubtaks1", NEW, 1);
        SubTask updateSubTask = new SubTask(2, "NewNameSubtask1", "NewDiscriptSubtaks1", DONE, 1);
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1);
        taskManager.updateSubTask(updateSubTask);
        Assertions.assertEquals(updateSubTask, taskManager.getSubTaskForId(2), "Подзадача не обновилась");
        Assertions.assertEquals(epic1.getStatus(), DONE, "Статус Эпика не обновился при обновлении Подзадачи");

    }

    @Test
    void removeTaskForId() throws NotFoundExeption {
        Task task1 = new Task(1, "nameTask1", "discriptTask1", NEW);
        Task task2 = new Task(1, "nameTask2", "discriptTask2", NEW);
        Task task3 = new Task(1, "nameTask3", "discriptTask3", NEW);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.removeTaskForId(2);
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task3);
        NotFoundExeption exception = assertThrows(
                NotFoundExeption.class,
                () -> taskManager.getTaskForId(2)
        );
        Assertions.assertEquals(exception.getMessage(), "Задачи с ID (2) нет!");
        Assertions.assertEquals(taskList, taskManager.getAllTask(), "Списки не совпадают после удаления 1 задачи");
    }

    @Test
    void removeEpicForId() throws NotFoundExeption {
        Epic epic1 = new Epic(1, "nameEpic1", "discriptEpic1");
        Epic epic2 = new Epic(1, "nameEpic2", "discriptEpic2");
        Epic epic3 = new Epic(1, "nameEpic3", "discriptEpic3");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addEpic(epic3);
        taskManager.removeEpicForId(3);
        ArrayList<Epic> epicList = new ArrayList<>();
        epicList.add(epic1);
        epicList.add(epic2);
        NotFoundExeption exception = assertThrows(
                NotFoundExeption.class,
                () -> taskManager.getEpicForId(3)
        );
        Assertions.assertEquals(exception.getMessage(), "Эпика с таким ID нет");
        Assertions.assertEquals(epicList, taskManager.getAllEpics(), "Список Эпиков не совпадает после удаления 1 Эпика");
    }

    @Test
    void removeSubTaskForId() throws NotFoundExeption, IOException {
        Epic epic1 = new Epic(1, "nameEpic1", "discriptEpic1");
        SubTask subTask1 = new SubTask(2, "nameSubtask1", "discriptSubtaks1", NEW, 1);
        SubTask subTask2 = new SubTask(3, "nameSubTask2", "discriptSubtask2", NEW, 1);
        SubTask subTask3 = new SubTask(4, "nameSubTask3", "discriptSubtaks3", NEW, 1);
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        taskManager.removeSubTaskForId(2);
        ArrayList<SubTask> subtaskList = new ArrayList<>();
        subtaskList.add(subTask2);
        subtaskList.add(subTask3);
        NotFoundExeption exception = assertThrows(
                NotFoundExeption.class,
                () -> taskManager.getSubTaskForId(2)
        );
        Assertions.assertEquals(exception.getMessage(), "Подзадачи с таким ID нет");
        Assertions.assertEquals(subtaskList, taskManager.getAllSubTasksForEpic(1), "Подзадача " +
                "не удалилась и осталась в эпике");
    }

    @Test
    void getAllSubTasksForEpic() throws NotFoundExeption {
        Epic epic1 = new Epic(1, "nameEpic1", "discriptEpic1");
        SubTask subTask1 = new SubTask(2, "nameSubtask1", "discriptSubtaks1", NEW, 1);
        SubTask subTask2 = new SubTask(3, "nameSubTask2", "discriptSubtask2", NEW, 1);
        SubTask subTask3 = new SubTask(4, "nameSubTask3", "discriptSubtaks3", NEW, 1);
        taskManager.addEpic(epic1);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        ArrayList<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(subTask1);
        subTaskList.add(subTask2);
        subTaskList.add(subTask3);
        Assertions.assertEquals(subTaskList, taskManager.getAllSubTasksForEpic(1), "Подзадачи эпика не совпадают");
    }
}