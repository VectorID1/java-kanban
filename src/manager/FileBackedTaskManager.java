package manager;

import model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static model.TypeTask.*;


public class FileBackedTaskManager extends InMemoryTaskManager {
    Path file;

    public FileBackedTaskManager(Path file) {
        super();
        this.file = file;
    }


    public void save() throws IOException {

        try (FileWriter writer = new FileWriter(String.valueOf(this.file))) {
            for (Task task : tasks.values()) {
                String taskLine = toString(task);
                writer.write(taskLine);
            }
            for (Epic epic : epics.values()) {
                String epicLine = toString(epic);
                writer.write(epicLine);
            }
            for (SubTask subTask : subTasks.values()) {
                String subtaskLine = toString(subTask);
                writer.write(subtaskLine);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения задачи!");
        }

    }

    private Task fromString(String value) {
        List<Integer> listIsSubtask = new ArrayList<>();
        int id;
        TypeTask type;
        String name;
        String discription;
        Status status;
        int idEpic = 0;
        String[] arrayIsTask = value.split(",");
        id = Integer.parseInt(arrayIsTask[0]);
        type = TypeTask.parseType(arrayIsTask[1]);
        name = arrayIsTask[2];
        discription = arrayIsTask[3];
        status = Status.perseStatus(arrayIsTask[4]);
        if (arrayIsTask.length == 6) {
            idEpic = Integer.parseInt(arrayIsTask[5]);
        }
        if (type == TASK) {
            return new Task(id, type, name, discription, status);

        } else if (type == EPIC) {
            return new Epic(id, type, name, discription, status, (ArrayList<Integer>) listIsSubtask);

        } else if (type == SUBTASK) {
            return new SubTask(id, type, name, discription, status, idEpic);

        } else {
            return null;
        }

    }

    public FileBackedTaskManager loadFromFile(Path file) throws IOException {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        List<String> list = Files.readAllLines(file);
        for (String list1 : list) {
            Task task = fromString(list1);

            if (task.getTypeTask() == TASK) {
                super.addTask(task);
            } else if (task.getTypeTask() == EPIC) {
                super.addEpic((Epic) task);
            } else if (task.getTypeTask() == SUBTASK) {
                super.addSubTask((SubTask) task);
            }
        }
        return fileBackedTaskManager;
    }


    @Override
    public void addTask(Task task) throws IOException {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) throws IOException {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) throws IOException {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void removeAllTasks() throws IOException {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() throws IOException {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubTasks() throws IOException {
        super.removeAllSubTasks();
        save();
    }

    @Override
    public void updateTask(Task task) throws IOException {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) throws IOException {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) throws IOException {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public void removeTaskForId(int id) throws IOException {
        super.removeTaskForId(id);
        save();
    }

    @Override
    public void removeEpicForId(int id) throws IOException {
        super.removeEpicForId(id);
        save();
    }

    @Override
    public void removeSubTaskForId(int id) throws IOException {
        super.removeSubTaskForId(id);
        save();
    }


    String toString(Task task) {
        String taskLine =
                task.getIdTask() + ","
                        + task.getTypeTask() + ","
                        + task.getTitleTask() + ","
                        + task.getDescriptionTask() + ","
                        + task.getStatusTask() + ",\n";
        return taskLine;

    }
}


