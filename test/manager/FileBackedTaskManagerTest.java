package manager;

import model.Status;
import model.Task;
import model.TypeTask;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {
    File tempFile = File.createTempFile("testManager", "csv");
    FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(tempFile.toPath());

    public FileBackedTaskManagerTest() throws IOException {
    }


    @Test
    public void saveEmptyFile() throws IOException {

        fileBackedTaskManager.save();
        List<Task> newMap = new ArrayList<>();  //Создали пустой лист!

        assertEquals(newMap, fileBackedTaskManager.getAllTask());  //Сравнили пустой лист и сохранение пустого манагера.

    }

    @Test
    public void loadEmptyFile() throws IOException {

        fileBackedTaskManager.loadFromFile(tempFile.toPath());
        List<Task> newMap = new ArrayList<>();

        assertEquals(newMap, fileBackedTaskManager.getAllTask());
    }

    @Test
    public void save2NewTaskInList() throws IOException {

        Task task1 = new Task(1, "NameTask1",
                "DiscriprioinTask1",
                Status.NEW);
        Task task2 = new Task(2, "NameTask2",
                "DiscriprioinTask2",
                Status.NEW);
        fileBackedTaskManager.addTask(task1);
        fileBackedTaskManager.addTask(task1);


        assertEquals(task1, fileBackedTaskManager.getTaskForId(1), "Ошибка при сохранении задачи 1");
        assertEquals(task2, fileBackedTaskManager.getTaskForId(2), "Ошибка при сохранении задачи 2");
        assertEquals(2, fileBackedTaskManager.getAllTask().size(), "Количество сохраненых" +
                " задач не совпадает");

    }

    @Test
    public void load2TaskInFile() throws IOException {
        Path path = Paths.get("SaveListTask.txt");
        FileBackedTaskManager fileBackedTaskManager1 = new FileBackedTaskManager(path);
        fileBackedTaskManager1.loadFromFile(path);

        Task task1 = fileBackedTaskManager1.tasks.get(1);
        Task task2 = fileBackedTaskManager1.tasks.get(2);
        Task task3 = new Task(1, TypeTask.TASK, "nameTask1", "discriprionTask1", Status.NEW);
        Task task4 = new Task(2, TypeTask.TASK, "nameTask2", "discriprionTask2", Status.NEW);

        assertEquals(task1, task3, "Задачи 1 и 3 при загрузке не равны!");
        assertEquals(task2, task4, "Задани 2 и 4 при загрузке не равны!");
    }

}