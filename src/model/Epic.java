package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> idSubTasks;
    LocalDateTime endTime;

    public Epic(String titleTask, String descriptionTask) {
        super(titleTask, descriptionTask);
        this.idSubTasks = new ArrayList<>();
    }

    public Epic(int idTask, String titleTask, String descriptionTask) {
        super(idTask, titleTask, descriptionTask);
        this.idSubTasks = new ArrayList<>();
    }


    public Epic(int idTask, TypeTask type, String titleTask, String descriptionTask, Status status, ArrayList<Integer> idSubTasks) {
        super(idTask, type, titleTask, descriptionTask, status);
        this.idSubTasks = idSubTasks;
    }

    public Epic(int idTask, TypeTask type, String titleTask, String descriptionTask, Status statusTask, LocalDateTime startTime, long duration, LocalDateTime endTime, ArrayList<Integer> idSubTasks) {
        super(idTask, type, titleTask, descriptionTask, statusTask);
        this.idSubTasks = idSubTasks;
        endTime = getEndTimeEpic();
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
                "Номер задачи = " + getIdTask() +
                ", Название задачи = '" + getTitleTask() + '\'' +
                ", Описание задачи = '" + getDescriptionTask() + '\'' +
                ", Статус выполнения = " + getStatusTask() +
                ", idSubTasks = " + idSubTasks +
                '}';
    }

}
