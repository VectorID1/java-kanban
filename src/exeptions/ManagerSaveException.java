package exeptions;


public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(String messege) {
        super(messege);
    }
}
