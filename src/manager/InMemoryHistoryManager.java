package manager;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> taskHistory = new ArrayList<>();
    private static final int MAX_HISTORY_SIZE = 10;

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(taskHistory);
    }

    @Override
    public void add(Task task) {
        if (taskHistory.size() == MAX_HISTORY_SIZE) {
            taskHistory.removeFirst();
        }
        taskHistory.add(task);
    }
}
