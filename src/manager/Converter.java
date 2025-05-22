package manager;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static model.TypeTask.*;

public class Converter {

    protected static Task fromString(String value) {
        int idEpic = 0;
        String[] arrayIsTask = value.split(",");
        if (arrayIsTask.length > 6) {
            if (TypeTask.valueOf(arrayIsTask[1]) == TASK) {
                return new Task(Integer.parseInt(arrayIsTask[0]),
                        TypeTask.valueOf(arrayIsTask[1]),
                        arrayIsTask[2],
                        arrayIsTask[3],
                        Status.valueOf(arrayIsTask[4]),
                        LocalDateTime.parse(arrayIsTask[5]),
                        Long.parseLong(arrayIsTask[6]),
                        LocalDateTime.parse(arrayIsTask[7]));

            } else if (TypeTask.valueOf(arrayIsTask[1]) == EPIC) {
                ArrayList<Integer> listIsSubtask = new ArrayList<>();
                return new Epic(Integer.parseInt(arrayIsTask[0]),
                        TypeTask.valueOf(arrayIsTask[1]),
                        arrayIsTask[2],
                        arrayIsTask[3],
                        Status.valueOf(arrayIsTask[4]),
                        LocalDateTime.parse(arrayIsTask[5]),
                        Long.parseLong(arrayIsTask[6]),
                        LocalDateTime.parse(arrayIsTask[7]),
                        listIsSubtask);
            } else if (TypeTask.valueOf(arrayIsTask[1]) == SUBTASK) {
                idEpic = Integer.parseInt(arrayIsTask[8]);
                return new SubTask(
                        Integer.parseInt(arrayIsTask[0]),
                        TypeTask.valueOf(arrayIsTask[1]),
                        arrayIsTask[2],
                        arrayIsTask[3],
                        Status.valueOf(arrayIsTask[4]),
                        LocalDateTime.parse(arrayIsTask[5]),
                        Long.parseLong(arrayIsTask[6]),
                        LocalDateTime.parse(arrayIsTask[7]),
                        idEpic);
            }
        }
        if (arrayIsTask.length == 6) {
            idEpic = Integer.parseInt(arrayIsTask[5]);
        }
        if (TypeTask.valueOf(arrayIsTask[1]) == TASK) {
            return new Task(Integer.parseInt(arrayIsTask[0]),
                    TypeTask.valueOf(arrayIsTask[1]),
                    arrayIsTask[2],
                    arrayIsTask[3],
                    Status.valueOf(arrayIsTask[4]));

        } else if (TypeTask.valueOf(arrayIsTask[1]) == EPIC) {
            ArrayList<Integer> listIsSubtask = new ArrayList<>();
            return new Epic(Integer.parseInt(arrayIsTask[0]),
                    TypeTask.valueOf(arrayIsTask[1]),
                    arrayIsTask[2],
                    arrayIsTask[3],
                    Status.valueOf(arrayIsTask[4]),
                    listIsSubtask);

        } else if (TypeTask.valueOf(arrayIsTask[1]) == SUBTASK) {
            return new SubTask(
                    Integer.parseInt(arrayIsTask[0]),
                    TypeTask.valueOf(arrayIsTask[1]),
                    arrayIsTask[2],
                    arrayIsTask[3],
                    Status.valueOf(arrayIsTask[4]),
                    idEpic);
        } else {
            return null;
        }
    }

    protected static String fromTaskToString(Task task) {
        if (task.getStartTime().isPresent() && task.getEndTime().isPresent()) {
            if (task.getTypeTask() == SUBTASK) {
                SubTask subTask = (SubTask) task;

                return subTask.getId() + ","
                        + subTask.getTypeTask() + ","
                        + subTask.getName() + ","
                        + subTask.getDescription() + ","
                        + subTask.getStatus() + ","
                        + subTask.getEpicId() + ","
                        + subTask.getStartTime().map(LocalDateTime::toString).orElse(null) + ","
                        + subTask.getDuration().toMinutes() + ","
                        + subTask.getEndTime().map(LocalDateTime::toString).orElse(null) + ",\n";
            } else {
                return task.getId() + ","
                        + task.getTypeTask() + ","
                        + task.getName() + ","
                        + task.getDescription() + ","
                        + task.getStatus() + ","
                        + task.getStartTime().map(LocalDateTime::toString).orElse(null) + ","
                        + task.getDuration().toMinutes() + ","
                        + task.getEndTime().map(LocalDateTime::toString).orElse(null) + ",\n";
            }
        } else if (task.getTypeTask() == SUBTASK) {
            SubTask subTask = (SubTask) task;
            return subTask.getId() + ","
                    + subTask.getTypeTask() + ","
                    + subTask.getName() + ","
                    + subTask.getDescription() + ","
                    + subTask.getStatus() + ","
                    + subTask.getEpicId() + ",\n";

        } else {
            return task.getId() + ","
                    + task.getTypeTask() + ","
                    + task.getName() + ","
                    + task.getDescription() + ","
                    + task.getStatus() + ",\n";
        }
    }
}
