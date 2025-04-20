package manager;

import model.Epic;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.util.ArrayList;

public interface TaskManager {

    //Добавление задач
    //___________________________________________
    void addTask(Task task) throws IOException;

    void addEpic(Epic epic) throws IOException;

    void addSubTask(SubTask subTask) throws IOException;

    // Получение списка задач
    //___________________________________________
    ArrayList<Task> getAllTask();

    ArrayList<Epic> getAllEpics();

    ArrayList<SubTask> getAllSubTask();

    //Удаление всех задач
    //_____________________________________________
    void removeAllTasks() throws IOException;

    void removeAllEpics() throws IOException;

    void removeAllSubTasks() throws IOException;

    //Получение по идентификатору
    //_____________________________________________
    Task getTaskForId(int id);

    Epic getEpicForId(int id);

    SubTask getSubTaskForId(int id);

    //Обновление задачи
    //__________________________________________________
    void updateTask(Task task) throws IOException;

    void updateEpic(Epic epic) throws IOException;

    void updateSubTask(SubTask subTask) throws IOException;

    //Удаление задачи по ID
    //____________________________________________________
    void removeTaskForId(int id) throws IOException;

    void removeEpicForId(int id) throws IOException;

    void removeSubTaskForId(int id) throws IOException;

    //Получение списка всех подзадач model.Epic`a
    //___________________________________________________
    ArrayList<SubTask> getAllSubTasksForEpic(int idEpic);

    //Отображение последних 10 просмотренных задач
    //___________________________________________________
    // List<Task> getHistory();


}
