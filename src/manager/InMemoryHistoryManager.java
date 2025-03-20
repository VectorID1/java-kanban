package manager;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> historyOfViewedIssues = new ArrayList<>();


    @Override
    public List<Task> getHistory() {
        return historyOfViewedIssues;
    }

    @Override
    public void add(Task task) {
        if (historyOfViewedIssues.size() == 10) {
            historyOfViewedIssues.removeFirst();
        }
        historyOfViewedIssues.add(task);
    }
}
