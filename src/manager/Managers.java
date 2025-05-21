package manager;

import java.io.File;

public class Managers {
    private static final String FILE = "SaveListTask.csv";

    public static TaskManager getDefault() {
        return new FileBackedTaskManager(new File(FILE));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
