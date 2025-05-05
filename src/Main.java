//  public static void main(String[] args) {
//    InMemoryTaskManager taskManager = new InMemoryTaskManager();
//  HistoryManager historyManager = Managers.getDefaultHistory();
//        //1 Epic____________________________________
//        Epic epic1 = new Epic("Переезд",
//                "Переехать в новый город");
//        taskManager.addEpic(epic1);
//        //1 Subtask_________________________________
//        SubTask subTask1ForEpic1 = new SubTask("Выбрать город",
//                "Сравнить города по климату",
//                Status.NEW,
//                epic1.getIdTask());
//        taskManager.addSubTask(subTask1ForEpic1);
//        //2 SubTask__________________________________
//        SubTask subTask2ForEpic1 = new SubTask("Выбросить всё лишнее",
//                "Собрать мусор и ненужные вещи по квартире",
//                Status.DONE,
//                epic1.getIdTask());
//        taskManager.addSubTask(subTask2ForEpic1);
//
//        //2 Epic_____________________________________
//        Epic epic2 = new Epic("Генеральная уборка",
//                "Привести квартиру в полный порядок");
//        taskManager.addEpic(epic2);
//        //3 SubTask___________________________________
//        SubTask subTask1ForEpic2 = new SubTask("Раздать ненужные вещи",
//                "Собрать вещи и отнести в пункт приёма",
//                Status.NEW,
//                epic2.getIdTask());
//        taskManager.addSubTask(subTask1ForEpic2);
//        //1 Task______________________________________
//        Task task1 = new Task("Поздравить жену с 8 марта",
//                "Купить цветы",
//                Status.NEW);
//        taskManager.addTask(task1);
//        //2 Task_______________________________________
//        Task task2 = new Task("Сходить в магазин",
//                "Купить продукты и не забыть пиво! =)",
//                Status.NEW);
//        taskManager.addTask(task2);
//        System.out.println("\nВывод всех задач");
//        System.out.println(taskManager.getAllTask());
//        System.out.println("\nВывод всех Эпиков");// Вывод всех задач задачи
//        System.out.println(taskManager.getAllEpics());
//        System.out.println("\nВывод всех подзадач\n");// Вывод всех эпиков эпики
//        System.out.println(taskManager.getAllSubTask()); //Вывод всех подзадач
//        System.out.println("_______________________________________________");
//        //Новая задача для обновления
//        Task newStatusTask = new Task(7, "Сходить в магазин",
//                "Купить продукты и не забыть пиво! =)",
//                Status.DONE);
//
//
//        //Обновление задачи с существующим индексом
//        taskManager.updateTask(newStatusTask);
//        System.out.println("\nОбновленные Задачи");
//        System.out.println(taskManager.getAllTask());
//        System.out.println("________________________________________________");
//
//
//        // Новая подзадача для обновления
//        SubTask newStatusSubTask = new SubTask(2, "Выбрать город",
//                "Сравнить города по климату",
//                Status.IN_PROGRESS,
//                epic1.getIdTask());
//
//        //Обновление подлзадачи с существующим индексом
//        taskManager.updateSubTask(newStatusSubTask);
//
//        System.out.println("\nОбновленные подзадачи и Епики");
//        System.out.println(taskManager.getAllSubTask()); // Вывод
//        System.out.println(taskManager.getAllEpics());  // Вывод
//        System.out.println("________________________________________________");
//
//
//        //Удаление задач
//        System.out.println("\nПодзадачи и Эпики до удаления");
//        System.out.println("_________________________________________________");
//        System.out.println(taskManager.getAllSubTask());
//        System.out.println(taskManager.getAllEpics());
//
//        taskManager.removeSubTaskForId(2);
//
//        System.out.println("\nПосле удаления подзадачи");
//        System.out.println("_________________________________________________");
//        System.out.println(taskManager.getAllSubTask());
//        System.out.println(taskManager.getAllEpics());
//
//        //taskManager.removeEpicForId(1);
//
//        System.out.println("\nПосле удаления Эпика");
//        System.out.println("__________________________________________________");
//
//        System.out.println(taskManager.getAllSubTask());
//        System.out.println(taskManager.getAllEpics());
//
//        System.out.println("\nВывод Сабтасок по индексу Эпика");
//        System.out.println("___________________________________________________");
//
//        System.out.println(taskManager.getAllSubTasksForEpic(4));
//
//        System.out.println("\n Вывод Задачи по индексу");
//        System.out.println("___________________________________________________");
//
//        System.out.println(taskManager.getTaskForId(6));
//
//        System.out.println("\n Обновление Эпика по айди");
//        System.out.println("____________________________________________________");
//        Epic newEpic = new Epic(4, "Забить на всё.", "Немного отдохнуть!!!");
//
//        taskManager.updateEpic(newEpic);
//
//        System.out.println(taskManager.getEpicForId(4));
//        System.out.println();
//
//        System.out.println("\nУдаление эпика и вывод Эпиков и сабтасок");
//        System.out.println("____________________________________________________");
//
//        // taskManager.removeAllEpics();
//
//        // System.out.println(taskManager.getAllEpics());
//        // System.out.println(taskManager.getAllSubTask());
//
//        System.out.println(historyManager.getTasks());
//
//        taskManager.getSubTaskForId(3);
//
//        //  System.out.println(taskManager.getHistory());
//        historyManager.remove(4);
//        System.out.println(historyManager.getTasks());


import manager.InMemoryTaskManager;
import model.Status;
import model.Task;
import model.TypeTask;

import java.time.LocalDateTime;

//1 Task______________________________________
//        Task task1 = new Task("Поздравить жену с 8 марта",
//                "Купить цветы",
//                Status.NEW);
//        taskManager.addTask(task1);
//        //2 Task_______________________________________
//        Task task2 = new Task("Сходить в магазин",
//                "Купить продукты и не забыть пиво! =)",
//                Status.NEW);
//        taskManager.addTask(task2);
//        //3 Task_______________________________________
//        Task task3 = new Task("Новая 3 задача",
//                "Купить пиво! =)",
//                Status.NEW);
//        taskManager.addTask(task3);
//        //4 Task_______________________________________
//        Task task4 = new Task("Новая 4 задача",
//                "Новое описание 4!",
//                Status.NEW);
//        taskManager.addTask(task4);
//        taskManager.getTaskForId(4);
//        taskManager.getTaskForId(4);
//
//        taskManager.getTaskForId(1);
//
//        taskManager.getTaskForId(4);
//        taskManager.getTaskForId(4);
//        taskManager.getTaskForId(3);
//        taskManager.getTaskForId(1);
//        taskManager.getTaskForId(1);
//
//        taskManager.getTaskForId(1);
//
//
//        System.out.println(taskManager.getHisory());
//    }
//}
//public class Main {
//    public static void main(String[] args) {
//        InMemoryTaskManager taskManager = new InMemoryTaskManager();
//        LocalDateTime startTime = LocalDateTime.of(2026,10,22,18,45);
//        LocalDateTime startTime1 = LocalDateTime.of(2025,11,15,18,45);
//        LocalDateTime startTime2 = LocalDateTime.of(2025,11,15,18,50);
//        Task task = new Task(1,
//                TypeTask.TASK,
//                "nameTask1",
//                "opicanie1",
//                Status.NEW,
//                startTime,
//                50,
//                null);
//        Task task1 = new Task(1,
//                TypeTask.TASK,
//                "nameTask2",
//                "opicanie2",
//                Status.NEW,
//                startTime1,
//                50,
//                null);
//        Task task2 = new Task(1,
//                TypeTask.TASK,
//                "nameTask2",
//                "opicanie3",
//                Status.NEW,
//                startTime2,
//                50,
//                null);
//        taskManager.addTask(task);
//        taskManager.addTask(task1);
//        taskManager.addTask(task2);
//        System.out.println(taskManager.getPrioritizedTasks());
//        System.out.println(taskManager.getAllTask());
//    }
//}