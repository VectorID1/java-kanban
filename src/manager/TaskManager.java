package manager;

import exeptions.NotFoundExeption;
import exeptions.TimeConflictExeption;
import model.Epic;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    //Добавление задач
    //___________________________________________
    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubTask(SubTask subTask) throws NotFoundExeption;

    // Получение списка задач
    //___________________________________________
    ArrayList<Task> getAllTask();

    ArrayList<Epic> getAllEpics();

    ArrayList<SubTask> getAllSubTask();

    //Удаление всех задач
    //_____________________________________________
    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubTasks() throws NotFoundExeption;

    //Получение по идентификатору
    //_____________________________________________
    Task getTaskForId(int id) throws NotFoundExeption;

    Epic getEpicForId(int id) throws NotFoundExeption;

    SubTask getSubTaskForId(int id) throws NotFoundExeption;

    //Обновление задачи
    //__________________________________________________
    void updateTask(Task task) throws TimeConflictExeption;

    void updateEpic(Epic epic) throws TimeConflictExeption;

    void updateSubTask(SubTask subTask) throws TimeConflictExeption, NotFoundExeption;

    //Удаление задачи по ID
    //____________________________________________________
    void removeTaskForId(int id) throws IOException;

    void removeEpicForId(int id) throws NotFoundExeption;

    void removeSubTaskForId(int id) throws IOException, NotFoundExeption;

    //Получение списка всех подзадач model.Epic`a
    //___________________________________________________
    ArrayList<SubTask> getAllSubTasksForEpic(int idEpic) throws NotFoundExeption;

    // Отображение просмотренных задач
    //__________________________________________________
    List<Task> getHistory();


    List<Task> getPrioritizedTasks();
}
