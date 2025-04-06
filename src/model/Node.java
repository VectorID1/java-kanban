package model;

public class Node<Task> {
    Task task;
    Node<Task> prev;
    Node<Task> next;

    public Node(Node<Task> prev,Task task, Node<Task> next) {
        this.task = task;
        this.prev = null;
        this.next = null;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Node<Task> getPrev() {
        return prev;
    }

    public void setPrev(Node<Task> prev) {
        this.prev = prev;
    }

    public Node<Task> getNext() {
        return next;
    }

    public void setNext(Node<Task> next) {
        this.next = next;
    }
}
