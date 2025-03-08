import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> idSubTasks;


    public Epic(String titleTask, String descriptionTask, Status statusTask) {
        super(titleTask, descriptionTask, statusTask);
        this.idSubTasks = new ArrayList<>();
    }

    public ArrayList<Integer> getIdSubTasks() {
        return idSubTasks;
    }

    public void setIdSubTasks(ArrayList<Integer> idSubTasks) {
        this.idSubTasks = idSubTasks;
    }
    public void addIdSubTasks (Integer idSubTask) {
        this.idSubTasks.add(idSubTask);
    }

    @Override
    public String toString() {
        return "\n Epic{" +
                "Номер задачи = " + getIdTask() +
                ", Название задачи = '" + getTitleTask() + '\'' +
                ", Описание задачи = '" + getDescriptionTask() + '\'' +
                ", Статус выполнения = " + getStatusTask() +
                ", idSubTasks = " + idSubTasks +
                '}';
    }
}
