package manager;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class ManagersTest {
    @Test
    public void managerGetDefault() {
        TaskManager taskManagerDefault = Managers.getDefault();
        Assertions.assertNotNull(taskManagerDefault);
    }

    @Test
    public void managerGetDefaultHistory() {
        HistoryManager defoultHistoryManager = Managers.getDefaultHistory();

        Assertions.assertNotNull(defoultHistoryManager.getTasks());

    }

}