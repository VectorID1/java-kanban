package manager;

import exeptions.NotFoundExeption;
import exeptions.TimeConflictExeption;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.io.IOException;
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

    @Override
    public List<Task> getPrioritizedTasks() {
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
    private void updateTimeEpic(Epic epic) throws NotFoundExeption {
        if (epic.getIdSubTasks().isEmpty()) {
            epic.setStartTime(LocalDateTime.now());
            epic.setEndTime(LocalDateTime.now());
            epic.setDuration(Duration.ZERO.toMinutes());
            return;
        }

        List<SubTask> subTasks = getAllSubTasksForEpic(epic.getId());
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
    private void updateStatusEpic(Epic epic) throws NotFoundExeption {
        int countStatusNew = 0;
        int countStatusDone = 0;
        if (epic.getIdSubTasks().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        for (SubTask subTask : getAllSubTasksForEpic(epic.getId())) {
            if (subTask.getStatus() == Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            } else if (subTask.getStatus() == Status.NEW) {
                countStatusNew++;
            } else if (subTask.getStatus() == Status.DONE) {
                countStatusDone++;
            }
        }
        if (countStatusDone == 0 && countStatusNew > 0) {
            epic.setStatus(Status.NEW);
        } else if (countStatusDone > 0 && countStatusNew == 0) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    //Добавление задач
    //___________________________________________
    @Override
    public void addTask(Task task) {
        if (task.getStartTime().isPresent()) {
            if (overlap(task)) {
                throw new TimeConflictExeption("Задача пересекается по времени!");
            } else {
                priority.add(task);
            }
        }
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        epic.setStatus(Status.NEW);
    }

    @Override
    public void addSubTask(SubTask subTask) throws NotFoundExeption {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) {
            return;
        } else {
            if (subTask.getStartTime().isPresent()) {
                if (overlap(subTask)) {
                    throw new TimeConflictExeption("Подзадача пересекается по времени");
                } else {
                    priority.add(subTask);
                }
            }
            subTask.setId(generateId());
            subTasks.put(subTask.getId(), subTask);
            epic.addIdSubTasks(subTask.getId());
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
    public void removeAllSubTasks() throws NotFoundExeption {
        subTasks.keySet()
                .forEach(taskId -> {
                    historyManager.remove(taskId);
                    priority.remove(subTasks.get(taskId));
                });
        subTasks.clear();
        epics.values()
                .forEach(epic -> {
                    epic.removeSubTask();
                    epic.setStatus(Status.NEW);
                    try {
                        updateTimeEpic(epic);
                    } catch (NotFoundExeption e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    //Получение по идентификатору
    //_____________________________________________
    @Override
    public Task getTaskForId(int id) throws NotFoundExeption {
        if (tasks.get(id) == null) {
            throw new NotFoundExeption("Задачи с ID (" + id + ") нет!");
        }
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpicForId(int id) throws NotFoundExeption {
        if (epics.get(id) == null) {
            throw new NotFoundExeption("Эпика с таким ID нет");
        }
        historyManager.add(epics.get(id));
        return epics.get(id);
    }


    @Override
    public SubTask getSubTaskForId(int id) throws NotFoundExeption {
        if (subTasks.get(id) == null) {
            throw new NotFoundExeption("Подзадачи с таким ID нет");
        }
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }


    //Обновление задачи
    //__________________________________________________
    @Override
    public void updateTask(Task task) throws TimeConflictExeption {
        if (!tasks.containsKey(task.getId())) {
            throw new TimeConflictExeption("Задачи пересекаются по времени");
        } else {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic epic1 = epics.get(epic.getId());
            epic1.setDescription(epic.getDescription());
            epic1.setName(epic.getName());
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) throws NotFoundExeption {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
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
    public void removeEpicForId(int id) throws NotFoundExeption {
        if (!epics.containsKey(id)) {
            throw new NotFoundExeption("Эпика с таким айди нет");
        }

        Epic epic = epics.get(id);
        List<Integer> subTaskIds = epic.getIdSubTasks();

        if (subTaskIds != null && !subTaskIds.isEmpty()) {
            for (Integer subTaskId : subTaskIds) {
                SubTask subTask = subTasks.get(subTaskId);
                if (subTask != null) {
                    subTasks.remove(subTaskId);
                    historyManager.remove(subTaskId);
                    if (subTask.getStartTime() != null) {
                        priority.remove(subTask);
                    }
                }
            }
        }
        epics.remove(id);
        historyManager.remove(id);
        priority.remove(epic);
    }

    @Override
    public void removeSubTaskForId(int id) throws IOException, NotFoundExeption {
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeIdSubTasks(id);
        subTasks.remove(id);
        historyManager.remove(id);
        updateStatusEpic(epic);
        if (subTask.getStartTime().isPresent()) {
            priority.remove(subTask);
            updateTimeEpic(epic);
        }
    }

    //Получение списка всех подзадач model.Epic`a
    //___________________________________________________
    @Override
    public ArrayList<SubTask> getAllSubTasksForEpic(int idEpic) throws NotFoundExeption {
        if (!epics.containsKey(idEpic)) {
            throw new NotFoundExeption("Эпика с таким ID нет.");
        }
        return epics.get(idEpic)
                .getIdSubTasks()
                .stream()
                .map(subTasks::get)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Отображение просмотренных задач
    //__________________________________________________
    @Override
    public List<Task> getHistory() {
        return historyManager.getTasks();
    }

}
