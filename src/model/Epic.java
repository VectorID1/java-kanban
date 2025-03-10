package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> idSubTasks;

    public Epic(String titleTask, String descriptionTask) {
        super(titleTask, descriptionTask);
        this.idSubTasks = new ArrayList<>();
    }

    public Epic(int idTask, String titleTask, String descriptionTask) {
        super(idTask, titleTask, descriptionTask);
        this.idSubTasks = new ArrayList<>();
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
