package manager;

import model.*;

import java.io.*;

import static manager.Converter.*;
import static model.TypeTask.*;


public class FileBackedTaskManager extends InMemoryTaskManager {
    File file;

    public FileBackedTaskManager(File file) {
        super();
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                try {
                    Task task = fromString(line);

                    if (task.getIdTask() > fileBackedTaskManager.idNumber) {
                        fileBackedTaskManager.idNumber = task.getIdTask() + 1;
                    }
                    if (task.getTypeTask() == TASK) {
                        fileBackedTaskManager.tasks.put(task.getIdTask(), task);
                    } else if (task.getTypeTask() == EPIC) {
                        fileBackedTaskManager.epics.put(task.getIdTask(), (Epic) task);
                    } else if (task.getTypeTask() == SUBTASK) {
                        fileBackedTaskManager.subTasks.put(task.getIdTask(), (SubTask) task);
                    }
                } catch (ManagerSaveException e) {
                    System.out.println("Ошибка обработки строки: " + line);
                }
            }
            return fileBackedTaskManager;
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки файла!");
        }
    }

    public void save() throws ManagerSaveException {
        try (FileWriter writer = new FileWriter(String.valueOf(this.file))) {
            writer.write("id,type,name,discribtion,status,startTime,durations,endTime,epic\n");
            for (Task task : tasks.values()) {
                writer.write(fromTaskToString(task));
            }
            for (Epic epic : epics.values()) {
                writer.write(fromTaskToString(epic));
            }
            for (SubTask subTask : subTasks.values()) {
                writer.write(fromTaskToString(subTask));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения задачи!");
        }

    }


    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void removeTaskForId(int id) {
        super.removeTaskForId(id);
        save();
    }

    @Override
    public void removeEpicForId(int id) {
        super.removeEpicForId(id);
        save();
    }

    @Override
    public void removeSubTaskForId(int id) {
        super.removeSubTaskForId(id);
        save();
    }

}


