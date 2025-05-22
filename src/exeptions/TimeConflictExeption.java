package exeptions;

public class TimeConflictExeption extends RuntimeException {
    public TimeConflictExeption(String message) {
        super(message);
    }
}
