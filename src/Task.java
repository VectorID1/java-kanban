import java.util.Objects;

public class Task {
    private  int idTask;
    private final String titleTask;
    private final String descriptionTask;
    private Status statusTask;

    public Task(String titleTask, String descriptionTask, Status statusTask) {
        this.titleTask = titleTask;
        this.descriptionTask = descriptionTask;
        this.statusTask = statusTask;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getTitleTask() {
        return titleTask;
    }

    public String getDescriptionTask() {
        return descriptionTask;
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
        return "\n Task{" +
                "Номер задачи = " + idTask +
                ", Название задачи = '" + titleTask + '\'' +
                ", Описание задачи = '" + descriptionTask + '\'' +
                ", Статус выполнения = " + statusTask +
                '}';
    }
}