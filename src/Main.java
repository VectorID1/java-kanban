public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Epic epic = new Epic("Переезд",
                "Переехать в новый город",Status.NEW);
        taskManager.addEpic(epic);
        SubTask subTask = new SubTask("Выбрать город",
                "Сравнить города по климату",
                Status.DONE,
                epic.getIdTask());
        taskManager.addSubTask(subTask);
        Epic epic1 = new Epic("Генеральная уборка",
                "Привести квартиру в полный порядок",Status.NEW);
        taskManager.addEpic(epic1);
        SubTask subTaskForEpic1 = new SubTask("Выбросить всё лишнее",
                "Собрать мусор и ненужные вещи по квартире",
                Status.IN_PROGRESS,
                epic1.getIdTask());
        taskManager.addSubTask(subTaskForEpic1);
        SubTask subTask1ForEpic1 = new SubTask("Раздать ненужные вещи",
                "Собрать вещи и отнести в пункт приёма",
                Status.NEW,
                epic1.getIdTask());
        taskManager.addSubTask(subTask1ForEpic1);
        SubTask subTask2ForEpic1 = new SubTask("Выбросить мусор",
                "Дойти до контейнерной площадкии выкинуть мусор",
                Status.NEW,
                epic1.getIdTask());
        taskManager.addSubTask(subTask2ForEpic1);
        Task task = new Task("Поздравить жену с 8 марта",
                "Купить цветы",
                Status.NEW);
        taskManager.addTask(task);
        Task task1 = new Task("Сходить в магазин",
                "Купить продукты и не забыть пиво! =)",
                Status.NEW);
        taskManager.addTask(task1);
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubTask());
        System.out.println(taskManager .getAllTask());
        System.out.println(taskManager.getAllSubTasksForEpic(1));
        System.out.println(taskManager.getAllEpics());
        taskManager.addEpic(epic1);
        System.out.println(taskManager.getAllSubTask());

    }
}
