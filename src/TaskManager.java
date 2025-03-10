import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private static int idNumber = 1;

    //Генератор идонтефикатора
    private int generateId() {
        return idNumber++;
    }

    //Добавление задач
    //___________________________________________
    public void addTask(Task task) {
        task.setIdTask(generateId());
        tasks.put(task.getIdTask(), task);
    }

    public void addEpic(Epic epic) {
        epic.setIdTask(generateId());
        epics.put(epic.getIdTask(), epic);
        epic.setStatusTask(Status.NEW);
    }

    public void addSubTask(SubTask subTask) {
        subTask.setIdTask(generateId());
        subTasks.put(subTask.getIdTask(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            epic.addIdSubTasks(subTask.getIdTask());
            updateStatusEpic(epic);
        } else {
            subTasks.remove(subTask.getIdTask());
        }

    }

    // Получение списка задач
    //___________________________________________
    ArrayList<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    //Удаление всех задач
    //_____________________________________________
    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    public void removeAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.removeSubTask();
            epic.setStatusTask(Status.NEW);
        }
    }

    //Получение по идентификатору
    //_____________________________________________
    public Task getTaskForId(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            return task;
        } else {
            return null;
        }
    }

    public Epic getEpicForId(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            return epic;
        } else {
            return null;

        }
    }

    public SubTask getSubTaskForId(int id) {
        SubTask subTask = subTasks.get(id);
        if (subTask != null) {
            return subTask;
        } else {
            return null;

        }
    }

    //Обновление задачи
    //__________________________________________________
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getIdTask())) {
            tasks.put(task.getIdTask(), task);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getIdTask())) {
            Epic epic1 = epics.get(epic.getIdTask());
            epic1.setDescriptionTask(epic.getDescriptionTask());
            epic1.setTitleTask(epic.getTitleTask());
            epics.put(epic.getIdTask(), epic1);
            updateStatusEpic(epic);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    public void updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getIdTask())) {
            subTasks.put(subTask.getIdTask(), subTask);
            updateStatusEpic(epics.get(subTask.getEpicId()));
        } else {
            System.out.println("Такой задачи нет");
        }

    }

    //Удаление задачи по ID
    //____________________________________________________
    public void removeTaskForId(int id) {
        tasks.remove(id);
    }

    public void removeEpicForId(int id) {
        Epic epic = epics.get(id);
        for (int i : epic.getIdSubTasks()) {
            subTasks.remove(i);
        }
        epic.removeSubTask();
        epics.remove(id);
    }

    public void removeSubTaskForId(int id) {
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeIdSubTasks(id);
        updateStatusEpic(epic);
        subTasks.remove(id);
    }

    //Получение списка всех подзадач Epic`a
    //___________________________________________________
    public ArrayList<SubTask> getAllSubTasksForEpic(int idEpic) {
        ArrayList<SubTask> subTasksForEpicId = new ArrayList<>();
        Epic epic = epics.get(idEpic);
        for (int subTaskId : epic.getIdSubTasks()) {
            subTasksForEpicId.add(subTasks.get(subTaskId));
        }
        return subTasksForEpicId;
    }

    // Обновление статуса Epic`a
    //____________________________________________________

    public void updateStatusEpic(Epic epic) {
        int countStatusNew = 0;
        int countStatusDone = 0;
        for (int id : epic.getIdSubTasks()) {
            SubTask subTask = subTasks.get(id);
            if (subTask == null) {
                epic.setStatusTask(Status.NEW);
                return;
            }
            if (subTask.getStatusTask() == Status.IN_PROGRESS) {
                epic.setStatusTask(Status.IN_PROGRESS);
                return;
            } else if (subTask.getStatusTask() == Status.NEW) {
                countStatusNew++;
            } else if (subTask.getStatusTask() == Status.DONE) {
                countStatusDone++;
            }
        }
        if (countStatusDone == 0) {
            epic.setStatusTask(Status.NEW);
        } else if (countStatusDone > 0) {
            epic.setStatusTask(Status.DONE);
        }
    }
}

