package model;

import java.util.Objects;

public class Task {
    private int idTask;
    private String titleTask;
    private String descriptionTask;
    private Status statusTask;

    public Task(String titleTask, String descriptionTask) {
        this.titleTask = titleTask;
        this.descriptionTask = descriptionTask;
    }

    public Task(String titleTask, String descriptionTask, Status statusTask) {
        this.titleTask = titleTask;
        this.descriptionTask = descriptionTask;
        this.statusTask = statusTask;
    }

    public Task(int idTask, String titleTask, String descriptionTask) {
        this.idTask = idTask;
        this.titleTask = titleTask;
        this.descriptionTask = descriptionTask;
    }

    public Task(int idTask, String titleTask, String descriptionTask, Status statusTask) {
        this.idTask = idTask;
        this.titleTask = titleTask;
        this.descriptionTask = descriptionTask;
        this.statusTask = statusTask;
    }

    public Task(int idTask, TypeTask type, String titleTask, String descriptionTask, Status status) {
        this.idTask = idTask;
        this.titleTask = titleTask;
        this.descriptionTask = descriptionTask;
        this.statusTask = status;
    }

    public Task() {

    }


    public TypeTask getTypeTask() {
        return TypeTask.TASK;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public void setTitleTask(String titleTask) {
        this.titleTask = titleTask;
    }

    public String getTitleTask() {
        return titleTask;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    public Status getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(Status statusTask) {
        this.statusTask = statusTask;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return idTask == task.idTask;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idTask);
    }

    @Override
    public String toString() {
        return "\n model.Task{" +
                "Номер задачи = " + idTask +
                ", Название задачи = '" + titleTask + '\'' +
                ", Описание задачи = '" + descriptionTask + '\'' +
                ", Статус выполнения = " + statusTask +
                '}';
    }


}