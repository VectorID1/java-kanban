package model;

import java.time.LocalDateTime;

public class SubTask extends Task {
    private Integer epicId;

    public SubTask(String name, String description, Status status, Integer epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public SubTask(int id, String name, String description, Status status, Integer epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public SubTask(int id, TypeTask typeTask, String name, String discription, Status status, int epicId) {
        super(id, typeTask, name, discription, status);
        this.epicId = epicId;
    }

    public SubTask(int id, TypeTask typeTask, String name, String description, Status status, LocalDateTime startTime, long duration, LocalDateTime endTime, Integer epicId) {
        super(id, typeTask, name, description, status, startTime, duration, endTime);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public TypeTask getTypeTask() {
        return TypeTask.SUBTASK;
    }

    @Override
    public String toString() {
        return "\n model.SubTask{" +
                "Номер подзадачи = " + getId() +
                ", Название задачи = '" + getName() + '\'' +
                ", Описание задачи = '" + getDescription() + '\'' +
                ", Статус выполнения = " + getStatus() +
                ", Номер основной задачи = " + getEpicId() +
                '}';
    }
}
