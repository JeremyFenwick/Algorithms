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
            var item = workingNode.item;
            workingNode = workingNode.next;
            return item;
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
        var newNode = new Node();
        newNode.item = item;
        newNode.next = firstNode;
        if (isEmpty()) {
            firstNode = newNode;
            lastNode = firstNode;
        }
        else {
            firstNode.previous = newNode;
            firstNode = newNode;
        }
        length++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null item!");
        }
        var newNode = new Node();
        newNode.item = item;
        newNode.previous = lastNode;
        if (isEmpty()) {
            lastNode = newNode;
            firstNode = lastNode;
        }
        else {
            lastNode.next = newNode;
            lastNode = newNode;
        }
        length++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot remove from an empty list!");
        }
        var item = firstNode.item;
        firstNode = firstNode.next;
        if (firstNode != null) {
            firstNode.previous = null;
        }
        else {
            lastNode = firstNode;
        }
        length--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Cannot remove from an empty list!");
        }
        var item = lastNode.item;
        lastNode = lastNode.previous;
        if (lastNode != null) {
            lastNode.next = null;
        }
        else {
            firstNode = lastNode;
        }
        length--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {

    }

}