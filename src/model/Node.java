package model;

public class Node<T> {
    Task task;
    Node<T> prev;
    Node<T> next;

    public Node(Node<T> prev,Task task, Node<T> next) {
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

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
