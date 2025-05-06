package manager;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected final HistoryManager historyManager;
    protected int idNumber = 1;
    private final Set<Task> priority = new TreeSet<>(Comparator.comparing(Task::getStartTimeInFormat));

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
    }

    //Генератор идонтефикатора
    private int generateId() {
        return idNumber++;
    }


    public ArrayList<Task> getPrioritizedTasks() {
        return new ArrayList<>(priority);

    }

    public boolean overlap(Task task) {
        return priority.stream()
                .anyMatch(task1 -> overlapsTime(task1, task));
    }

    public boolean overlapsTime(Task task1, Task task2) {
        return task1.getStartTime().get().isBefore(task2.getEndTime().get()) &&
                task1.getEndTime().get().isAfter(task2.getStartTime().get());
    }

    //Обновление времени Эпика
    //_________________________________________________
    private void updateTimeEpic(Epic epic) {
        if (epic.getIdSubTasks().isEmpty()) {
            epic.setStartTime(LocalDateTime.now());
            epic.setEndTime(LocalDateTime.now());
            epic.setDuration(Duration.ZERO.toMinutes());
            return;
        }

        List<SubTask> subTasks = getAllSubTasksForEpic(epic.getIdTask());
        LocalDateTime minStartTime = null;
        LocalDateTime maxEndTime = null;
        long totalDuration = 0;

        for (SubTask subTask : subTasks) {
            Optional<LocalDateTime> startTime = subTask.getStartTime();
            Optional<LocalDateTime> endTime = subTask.getEndTime();

            if (startTime.isPresent()) {
                LocalDateTime startTimeSubtask = startTime.get();
                if (minStartTime == null || minStartTime.isBefore(startTimeSubtask)) {
                    minStartTime = startTimeSubtask;
                }
            }
            if (endTime.isPresent()) {
                LocalDateTime endTimeSubtask = endTime.get();
                if (maxEndTime == null || maxEndTime.isAfter(endTimeSubtask)) {
                    maxEndTime = endTimeSubtask;
                }
            }
            totalDuration += subTask.getDuration().toMinutes();
        }
        if (minStartTime != null) {
            epic.setStartTime(minStartTime);
        }
        if (maxEndTime != null) {
            epic.setEndTime(maxEndTime);
        }
        epic.setDuration(totalDuration);
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

    //Добавление задач
    //___________________________________________
    @Override
    public void addTask(Task task) {
        if (task.getStartTime().isPresent()) {
            if (overlap(task)) {
                return;
            } else {
                priority.add(task);
            }
        }
        task.setIdTask(generateId());
        tasks.put(task.getIdTask(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setIdTask(generateId());
        epics.put(epic.getIdTask(), epic);
        epic.setStatusTask(Status.NEW);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) {
            return;
        } else {
            if (subTask.getStartTime().isPresent()) {
                if (overlap(subTask)) {
                    return;
                } else {
                    priority.add(subTask);
                }
            }
            subTask.setIdTask(generateId());
            subTasks.put(subTask.getIdTask(), subTask);
            epic.addIdSubTasks(subTask.getIdTask());
            updateStatusEpic(epic);
            updateTimeEpic(epic);
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
    public void removeAllTasks() {
        tasks.keySet()
                .forEach(taskId -> {
                    historyManager.remove(taskId);
                    priority.remove(tasks.get(taskId));
                });
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        subTasks.keySet()
                .forEach(taskId -> {
                    historyManager.remove(taskId);
                    priority.remove(subTasks.get(taskId));
                });
        subTasks.clear();
        epics.keySet()
                .forEach(taskId -> {
                    historyManager.remove(taskId);
                    priority.remove(epics.get(taskId));
                });
        epics.clear();
    }

    @Override
    public void removeAllSubTasks() {
        subTasks.keySet()
                .forEach(taskId -> {
                    historyManager.remove(taskId);
                    priority.remove(subTasks.get(taskId));
                });
        subTasks.clear();
        epics.values()
                .forEach(epic -> {
                    epic.removeSubTask();
                    epic.setStatusTask(Status.NEW);
                    updateTimeEpic(epic);
                });
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
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getIdTask())) {
            tasks.put(task.getIdTask(), task);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getIdTask())) {
            Epic epic1 = epics.get(epic.getIdTask());
            epic1.setDescriptionTask(epic.getDescriptionTask());
            epic1.setTitleTask(epic.getTitleTask());
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getIdTask())) {
            subTasks.put(subTask.getIdTask(), subTask);
            updateStatusEpic(epics.get(subTask.getEpicId()));
            updateTimeEpic(epics.get(subTask.getEpicId()));
        } else {
            System.out.println("Такой задачи нет");
        }

    }

    //Удаление задачи по ID
    //____________________________________________________
    @Override
    public void removeTaskForId(int id) {
        historyManager.remove(id);
        tasks.remove(id);
        priority.remove(tasks.get(id));
    }

    @Override
    public void removeEpicForId(int id) {
        Epic epic = epics.get(id);
        epic.getIdSubTasks()
                .forEach(subTasksId -> {
                    subTasks.remove(subTasksId);
                    historyManager.remove(subTasksId);
                    priority.remove(subTasks.get(subTasksId));
                });
        epic.removeSubTask();
        epics.remove(id);
        historyManager.remove(id);
        priority.remove(epic);

    }

    @Override
    public void removeSubTaskForId(int id) {
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeIdSubTasks(id);
        subTasks.remove(id);
        historyManager.remove(id);
        priority.remove(subTask);
        updateStatusEpic(epic);
        updateTimeEpic(epic);
    }

    //Получение списка всех подзадач model.Epic`a
    //___________________________________________________
    @Override
    public ArrayList<SubTask> getAllSubTasksForEpic(int idEpic) {
        return epics.get(idEpic)
                .getIdSubTasks()
                .stream()
                .map(subTasks::get)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Отображение просмотренных задач
    //__________________________________________________
    public List<Task> getHisory() {
        return historyManager.getTasks();
    }

}
