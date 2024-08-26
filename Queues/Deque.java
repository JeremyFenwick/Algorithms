import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int length;
    private Node firstNode;
    private Node lastNode;

    public Deque() {
        firstNode = null;
        lastNode = null;
        length = 0;
    }

    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node workingNode;

        private DequeIterator() {
            workingNode = firstNode;
        }

        public boolean hasNext() {
            return workingNode != null;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            var oldNode = workingNode;
            workingNode = workingNode.next;
            return oldNode.item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public int size() {
        return length;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null item!");
        }
        var oldFirst = firstNode;
        var newNode = new Node();
        newNode.item = item;
        newNode.next = oldFirst;
        newNode.previous = null;
        firstNode = newNode;
        if (isEmpty()) {
            lastNode = firstNode;
        }
        else {
            oldFirst.previous = firstNode;
        }
        length++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null item!");
        }
        var oldLast = lastNode;
        var newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        newNode.previous = oldLast;
        lastNode = newNode;
        if (isEmpty()) {
            firstNode = lastNode;
        }
        else {
            oldLast.next = lastNode;
        }
        length++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot remove from an empty list!");
        }
        var oldFirst = firstNode;
        firstNode = firstNode.next;
        length--;
        return oldFirst.item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot remove from an empty list!");
        }
        var oldLast = lastNode;
        lastNode = lastNode.previous;
        length--;
        return oldLast.item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {

    }

}