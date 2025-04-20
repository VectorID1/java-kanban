package model;

public enum TypeTask {
    EPIC,
    TASK,
    SUBTASK;

    public static TypeTask parseType(String type) {
        if (type.equals("EPIC")) {
            return EPIC;
        } else if (type.equals("TASK")) {
            return TASK;
        } else if (type.equals("SUBTASK")) {
            return SUBTASK;
        } else {
            return null;
        }
    }
}
