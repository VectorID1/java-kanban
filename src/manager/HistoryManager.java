package manager;

import exeptions.TimeConflictExeption;
import model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task) throws TimeConflictExeption;

    void remove(int id);

    List<Task> getTasks();

}
