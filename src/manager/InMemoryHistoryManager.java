package manager;
import model.Node;
import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private  HashMap<Integer, Node<Task>> historyTask = new HashMap<>();
    private  Node<Task> first = null;
    private  Node<Task> last = null;

    public void linkLast(Task task) {
        Node<Task> newNode = new Node<>(last, task, null);
        if (last == null) {
            first = newNode;
            last = newNode;
        } else {
            Node<Task> oldNode = last;
            oldNode.setNext(newNode);
            last = newNode;
            last.setPrev(oldNode);
            last.setNext(null);
        }

        historyTask.put(task.getIdTask(), last);

    }

    @Override
    public void add(Task task) {
        if (historyTask.containsKey(task.getIdTask())) {
            removeNode(historyTask.get(task.getIdTask()));
        }
        linkLast(task);
    }

    @Override
    public List<Task> getTasks() {
        ArrayList<Task> listTask = new ArrayList<>();
        Node<Task> current = first;
        while (current != null) {
            listTask.add(current.getTask());
            current = current.getNext();
        }
        return listTask;
    }

    public void removeNode(Node<Task> node) {
        Task task = node.getTask();
            Node<Task> removeNode = historyTask.get(task.getIdTask());
            if (removeNode.getNext() != null && removeNode.getPrev() != null) {
                Node<Task> nextNode = removeNode.getNext();
                Node<Task> prevNode = removeNode.getPrev();
                nextNode.setPrev(prevNode);
                prevNode.setNext(nextNode);
            } else if (removeNode.getNext() == null && removeNode.getPrev() != null) {
                Node<Task> prevNode = removeNode.getPrev();
                prevNode.setNext(null);
                last = prevNode;
            } else if (removeNode.getPrev() == null && removeNode.getNext() != null) {
                Node<Task> firstNode = removeNode.getNext();
                firstNode.setPrev(null);
                first = firstNode;
            } else {
                last = null;
                first = null;
            }
        historyTask.remove(task.getIdTask());
    }

    @Override
    public void remove(int id) {
        if (historyTask.containsKey(id)) {
            removeNode(historyTask.get(id));
            historyTask.remove(id);
        }
        return;
    }
}



