package model;

import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static model.Status.NEW;

class NodeTest {

    @Test
    public void addNode() {
        Task task = new Task(1,
                "Test addNewTask",
                "Test addNewTask description",
                NEW);
        Node<Task> newNode1 = new Node<>(null, task, null);
        Node<Task> newNode2 = new Node<>(null, task, null);

        Assertions.assertEquals(newNode1.getTask(), newNode2.getTask(), "Узлы не равны");

    }
}