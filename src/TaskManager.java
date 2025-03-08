import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static final HashMap<Integer, Task> tasks = new HashMap<>();
    private static final HashMap<Integer, Epic> epics = new HashMap<>();
    private static final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private static int idNumber = 1;

    //Генератор идонтефикатора
    private int increment() {
        return idNumber++;
    }

    //Добавление задач
    //___________________________________________
    public void addTask(Task task) {
        task.setIdTask(increment());
        tasks.put(task.getIdTask(), task);
    }

    public void addEpic(Epic epic) {
        epic.setIdTask(increment());
        epics.put(epic.getIdTask(), epic);
    }

    public void addSubTask(SubTask subTask) {
        subTask.setIdTask(increment());
        subTasks.put(subTask.getIdTask(), subTask);
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            epic.addIdSubTasks(subTask.getIdTask());
            epic.setStatusTask(updateStatusEpic(epic));
        }

    }

    // Получение списка задач
    //___________________________________________
    ArrayList<Object> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    ArrayList<Object> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    ArrayList<Object> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    //Удаление всех задач
    //_____________________________________________
    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
    }

    public void removeAllSubTasks() {
        subTasks.clear();
    }

    //Получение по идентификатору
    //_____________________________________________
    public Task getTaskForId(int id) {
        for (Task task : tasks.values()) {
            if (task.getIdTask() == id) {
                return task;
            }
        }
        return null;
    }

    public Epic getEpicForId(int id) {
        for (Epic epic : epics.values()) {
            if (epic.getIdTask() == id) {
                return epic;
            }
        }
        return null;
    }

    public SubTask getSubTaskForId(int id) {
        for (SubTask subTask : subTasks.values()) {
            if (subTask.getIdTask() == id) {
                return subTask;
            }
        }
        return null;
    }

    //Обновление задачи
    //__________________________________________________
    public void updateTask(Task task) {
        for (int key : tasks.keySet()) {
            if (task.getIdTask() == key) {
                tasks.put(task.getIdTask(), task);
                return;
            } else
                System.out.println("Такой задачи нет");
        }
    }

    public void updateEpic(Epic epic) {
        for (int key : epics.keySet()) {
            if (epic.getIdTask() == key) {
                epics.put(epic.getIdTask(), epic);
                return;
            } else
                System.out.println("Такой задачи нет");
            return;
        }
    }

    public void updateSubTask(SubTask subTask) {
        for (int key : subTasks.keySet()) {
            if (subTask.getIdTask() == key) {
                subTasks.put(subTask.getIdTask(), subTask);
                return;
            } else
                System.out.println("Такой задачи нет");
        }
    }

    //Удаление задачи по ID
    //____________________________________________________
    public void removeTaskForId(int id) {
        tasks.remove(id);
    }

    public void removeEpicForId(int id) {
        epics.remove(id);
    }

    public void removeSubTaskForId(int id) {
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
    public Status updateStatusEpic(Epic epic) {
        for (int id : epic.getIdSubTasks()) {
            SubTask subTask = subTasks.get(id);
            if (subTask.getStatusTask() == Status.DONE && subTask.getStatusTask() != null &&
                    subTask.getStatusTask() != Status.NEW && subTask.getStatusTask() != Status.IN_PROGRESS) {
                return Status.DONE;
            } else if (subTask.getStatusTask() == null || subTask.getStatusTask() == Status.NEW) {
                return Status.NEW;
            } else
                return Status.IN_PROGRESS;
        }

        return null;
    }
}

