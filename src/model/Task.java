package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class Task {
    private int idTask;
    private String titleTask;
    private String descriptionTask;
    private Status statusTask;
    private long duration;
    private LocalDateTime startTime;


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

    public Task(int idTask, TypeTask type, String titleTask, String descriptionTask, Status statusTask, LocalDateTime startTime, long duration, LocalDateTime endTime) {
        this.idTask = idTask;
        this.titleTask = titleTask;
        this.descriptionTask = descriptionTask;
        this.statusTask = statusTask;
        this.duration = duration;
        this.startTime = startTime;
    }


    public TypeTask getTypeTask() {
        return TypeTask.TASK;
    }

    public Optional<LocalDateTime> getEndTime() {
        if (startTime == null) {
            return Optional.empty();
        } else {
            return Optional.of(startTime.plus(Duration.ofMinutes(duration)));
        }
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

    public Optional<LocalDateTime> getStartTime() {
        return Optional.ofNullable(startTime);
    }

    public LocalDateTime getStartTimeInFormat() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return Duration.ofMinutes(this.duration);
    }

    public void setDuration(long duration) {
        this.duration = duration;
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
        if (getStartTime().isPresent() && getEndTime().isPresent()) {
            return "\n model.Task{" +
                    "Номер задачи = " + idTask +
                    ", Название задачи = '" + titleTask + '\'' +
                    ", Описание задачи = '" + descriptionTask + '\'' +
                    ", Статус выполнения = " + statusTask +
                    ", Время начала задания = " + getStartTime().get() +
                    ", Время выполнения задания = " + getDuration().toMinutes() + " минут" +
                    ", Время окончания задания = " + getEndTime().get() +
                    '}';
        } else {
            return "\n model.Task{" +
                    "Номер задачи = " + idTask +
                    ", Название задачи = '" + titleTask + '\'' +
                    ", Описание задачи = '" + descriptionTask + '\'' +
                    ", Статус выполнения = " + statusTask +
                    '}';
        }
    }


}