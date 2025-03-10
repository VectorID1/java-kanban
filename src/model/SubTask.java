package model;

public class SubTask extends Task {
    private Integer epicId;

    public SubTask(String titleTask, String descriptionTask, Status statusTask, Integer epicId) {
        super(titleTask, descriptionTask, statusTask);
        this.epicId = epicId;
    }

    public SubTask(int idTask, String titleTask, String descriptionTask, Status statusTask, Integer epicId) {
        super(idTask, titleTask, descriptionTask, statusTask);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "\n model.SubTask{" +
                "Номер подзадачи = " + getIdTask() +
                ", Название задачи = '" + getTitleTask() + '\'' +
                ", Описание задачи = '" + getDescriptionTask() + '\'' +
                ", Статус выполнения = " + getStatusTask() +
                ", Номер основной задачи = " + getEpicId() +
                '}';
    }
}
