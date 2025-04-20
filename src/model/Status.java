package model;

public enum Status {
    NEW,
    IN_PROGRESS,
    DONE;

    public static Status perseStatus(String status) {
        //Status parse = NEW;
        if (status.equals("NEW")) {
            return NEW;
        } else if (status.equals("IN_PROGRESS")) {
            return IN_PROGRESS;
        } else if (status.equals("DONE")) {
            return DONE;
        } else {
            return null;
        }
    }
}
