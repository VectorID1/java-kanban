package manager;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected final HistoryManager historyManager;
    protected int idNumber = 1;

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
    }

    //Генератор идонтефикатора
    private int generateId() {
        return idNumber++;
    }


    //Добавление задач
    //___________________________________________
    @Override
    public void addTask(Task task) throws IOException {
        task.setIdTask(generateId());
        tasks.put(task.getIdTask(), task);
    }

    @Override
    public void addEpic(Epic epic) throws IOException {
        epic.setIdTask(generateId());
        epics.put(epic.getIdTask(), epic);
        epic.setStatusTask(Status.NEW);
    }

    @Override
    public void addSubTask(SubTask subTask) throws IOException {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) {
            return;
        } else {
            subTask.setIdTask(generateId());
            subTasks.put(subTask.getIdTask(), subTask);
            epic.addIdSubTasks(subTask.getIdTask());
            updateStatusEpic(epic);
        }
    }

    // Получение списка задач
    //___________________________________________
    @Override
    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    //Удаление всех задач
    //_____________________________________________
    @Override
    public void removeAllTasks() throws IOException {
        for (int id : tasks.keySet()) {
            historyManager.remove(id);
        }
        tasks.clear();
    }

    @Override
    public void removeAllEpics() throws IOException {
        for (int id : subTasks.keySet()) {
            historyManager.remove(id);
        }
        subTasks.clear();
        for (int id : epics.keySet()) {
            historyManager.remove(id);
        }
        epics.clear();
    }

    @Override
    public void removeAllSubTasks() throws IOException {
        for (int id : subTasks.keySet()) {
            historyManager.remove(id);
        }
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.removeSubTask();
            epic.setStatusTask(Status.NEW);
        }
    }

    //Получение по идентификатору
    //_____________________________________________
    @Override
    public Task getTaskForId(int id) {
        if (tasks.get(id) != null) {
            historyManager.add(tasks.get(id));
            return tasks.get(id);
        }
        return null;
    }

    @Override
    public Epic getEpicForId(int id) {
        if (epics.get(id) != null) {
            historyManager.add(epics.get(id));
            return epics.get(id);
        }
        return null;
    }

    @Override
    public SubTask getSubTaskForId(int id) {
        if (subTasks.get(id) != null) {
            historyManager.add(subTasks.get(id));
            return subTasks.get(id);
        }
        return null;
    }

    //Обновление задачи
    //__________________________________________________
    @Override
    public void updateTask(Task task) throws IOException {
        if (tasks.containsKey(task.getIdTask())) {
            tasks.put(task.getIdTask(), task);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void updateEpic(Epic epic) throws IOException {
        if (epics.containsKey(epic.getIdTask())) {
            Epic epic1 = epics.get(epic.getIdTask());
            epic1.setDescriptionTask(epic.getDescriptionTask());
            epic1.setTitleTask(epic.getTitleTask());
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) throws IOException {
        if (subTasks.containsKey(subTask.getIdTask())) {
            subTasks.put(subTask.getIdTask(), subTask);
            updateStatusEpic(epics.get(subTask.getEpicId()));
        } else {
            System.out.println("Такой задачи нет");
        }

    }

    //Удаление задачи по ID
    //____________________________________________________
    @Override
    public void removeTaskForId(int id) throws IOException {
        historyManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public void removeEpicForId(int id) throws IOException {
        Epic epic = epics.get(id);
        for (int i : epic.getIdSubTasks()) {
            subTasks.remove(i);
            historyManager.remove(i);
        }
        epic.removeSubTask();
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeSubTaskForId(int id) throws IOException {
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeIdSubTasks(id);
        updateStatusEpic(epic);
        subTasks.remove(id);
        historyManager.remove(id);
    }

    //Получение списка всех подзадач model.Epic`a
    //___________________________________________________
    @Override
    public ArrayList<SubTask> getAllSubTasksForEpic(int idEpic) {
        ArrayList<SubTask> subTasksForEpicId = new ArrayList<>();
        Epic epic = epics.get(idEpic);
        for (int subTaskId : epic.getIdSubTasks()) {
            subTasksForEpicId.add(subTasks.get(subTaskId));
        }
        return subTasksForEpicId;
    }

    // Обновление статуса model.Epic`a
    //____________________________________________________


    private void updateStatusEpic(Epic epic) {
        int countStatusNew = 0;
        int countStatusDone = 0;
        if (epic.getIdSubTasks().isEmpty()) {
            epic.setStatusTask(Status.NEW);
            return;
        }
        for (SubTask subTask : getAllSubTasksForEpic(epic.getIdTask())) {
            if (subTask.getStatusTask() == Status.IN_PROGRESS) {
                epic.setStatusTask(Status.IN_PROGRESS);
                return;
            } else if (subTask.getStatusTask() == Status.NEW) {
                countStatusNew++;
            } else if (subTask.getStatusTask() == Status.DONE) {
                countStatusDone++;
            }
        }
        if (countStatusDone == 0 && countStatusNew > 0) {
            epic.setStatusTask(Status.NEW);
        } else if (countStatusDone > 0 && countStatusNew == 0) {
            epic.setStatusTask(Status.DONE);
        } else {
            epic.setStatusTask(Status.IN_PROGRESS);
        }
    }

    // Отображение просмотренных задач
    //__________________________________________________
    public List<Task> getHisory() {
        return historyManager.getTasks();
    }

}
