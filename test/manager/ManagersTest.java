package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    public void managerGetDefault() {
        TaskManager taskManagerDefault = Managers.getDefault();
        Assertions.assertNotNull(taskManagerDefault);
    }

    @Test
    public void managerGetDefaultHistory() {
        HistoryManager defoultHistoryManager = Managers.getDefaultHistory();

        Assertions.assertNotNull(defoultHistoryManager.getHistory());

    }

}