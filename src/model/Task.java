package model;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class Task {
    private int id;
    private String name;
    private TypeTask typeTask;
    private String description;
    private Status status;
    private LocalDateTime startTime;
    private long duration;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(int id, TypeTask typeTask, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.typeTask = getTypeTask();
        this.description = description;
        this.status = status;
    }

    public Task() {

    }

    public Task(int id, TypeTask typeTask, String name, String description, Status status, LocalDateTime startTime, long duration, LocalDateTime endTime) {
        this.id = id;
        this.name = name;
        this.typeTask = getTypeTask();
        this.description = description;
        this.status = status;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        if (getStartTime().isPresent() && getEndTime().isPresent()) {
            return "\n model.Task{" +
                    "Номер задачи = " + id +
                    ", Название задачи = '" + name + '\'' +
                    ", Описание задачи = '" + description + '\'' +
                    ", Статус выполнения = " + status +
                    ", Время начала задания = " + getStartTime().get() +
                    ", Время выполнения задания = " + getDuration().toMinutes() + " минут" +
                    ", Время окончания задания = " + getEndTime().get() +
                    '}';
        } else {
            return "\n model.Task{" +
                    "Номер задачи = " + id +
                    ", Название задачи = '" + name + '\'' +
                    ", Описание задачи = '" + description + '\'' +
                    ", Статус выполнения = " + status +
                    '}';
        }
    }

}