package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> idSubTasks;
    LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
        this.idSubTasks = new ArrayList<>();
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
        this.idSubTasks = new ArrayList<>();
    }


    public Epic(int id, TypeTask typeTask, String name, String description, Status status, ArrayList<Integer> idSubTasks) {
        super(id, typeTask, name, description, status);
        this.idSubTasks = idSubTasks;
    }

    public Epic(int id, TypeTask typeTask, String name, String description, Status status, LocalDateTime startTime, long duration, LocalDateTime endTime, ArrayList<Integer> idSubTasks) {
        super(id, typeTask, name, description, status, startTime, duration, endTime);
        this.idSubTasks = idSubTasks;
        endTime = getEndTimeEpic();
    }

    public Epic(int id, TypeTask typeTask, String name, String description, Status status, LocalDateTime startTime, long duration, LocalDateTime endTime) {
        super(id, typeTask, name, description, status, startTime, duration, endTime);
        this.idSubTasks = idSubTasks;
        endTime = getEndTimeEpic();
    }


    public Epic() {
    }

    public Epic(int id, TypeTask typeTask, String name, String description, Status status) {
        super(id, typeTask, name, description, status);
    }

    public ArrayList<Integer> getIdSubTasks() {
        return idSubTasks;
    }

    public void setIdSubTasks(ArrayList<Integer> idSubTasks) {
        this.idSubTasks = idSubTasks;
    }

    public void addIdSubTasks(Integer idSubTask) {
        this.idSubTasks.add(idSubTask);
    }

    public void removeIdSubTasks(int idSubtask) {
        this.idSubTasks.remove(Integer.valueOf(idSubtask));
    }

    public void removeSubTask() {
        this.idSubTasks.clear();
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


    public LocalDateTime getEndTimeEpic() {
        return this.endTime;
    }

    @Override
    public TypeTask getTypeTask() {
        return TypeTask.EPIC;
    }

    @Override
    public String toString() {
        return "\n model.Epic{" +
                "Номер задачи = " + getId() +
                ", Название задачи = '" + getName() + '\'' +
                ", Описание задачи = '" + getDescription() + '\'' +
                ", Статус выполнения = " + getStatus() +
                ", idSubTasks = " + idSubTasks +
                '}';
    }

}
