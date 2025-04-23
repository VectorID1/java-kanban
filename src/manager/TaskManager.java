package manager;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;

public interface TaskManager {

    //Добавление задач
    //___________________________________________
    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask);

    // Получение списка задач
    //___________________________________________
    ArrayList<Task> getAllTask();

    ArrayList<Epic> getAllEpics();

    ArrayList<SubTask> getAllSubTask();

    //Удаление всех задач
    //_____________________________________________
    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubTasks();

    //Получение по идентификатору
    //_____________________________________________
    Task getTaskForId(int id);

    Epic getEpicForId(int id);

    SubTask getSubTaskForId(int id);

    //Обновление задачи
    //__________________________________________________
    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(SubTask subTask);

    //Удаление задачи по ID
    //____________________________________________________
    void removeTaskForId(int id);

    void removeEpicForId(int id);

    void removeSubTaskForId(int id);

    //Получение списка всех подзадач model.Epic`a
    //___________________________________________________
    ArrayList<SubTask> getAllSubTasksForEpic(int idEpic);

    //Отображение последних 10 просмотренных задач
    //___________________________________________________
    // List<Task> getHistory();


}
