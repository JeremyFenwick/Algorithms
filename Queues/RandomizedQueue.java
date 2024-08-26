import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_CAPACITY = 8;

    private Item[] data;
    private int length;


    public RandomizedQueue() {
        data = (Item[]) new Object[INITIAL_CAPACITY];
        length = 0;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int index;
        private int[] randomIndexes;

        private RandomizedQueueIterator() {
            index = 0;
            randomIndexes = new int[length];
            for (int i = 0; i < length; i++) {
                randomIndexes[i] = i;
            }
            StdRandom.shuffle(randomIndexes);
        }

        public boolean hasNext() {
            return index < length;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            var randomizedIndex = randomIndexes[index];
            var item = data[randomizedIndex];
            index++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int capacity) {
        var copy = (Item[]) new Object[capacity];
        for (int i = 0; i < length; i++) {
            copy[i] = data[i];
        }
        data = copy;
    }

    private void swap(int firstIndex, int secondIndex) {
        var firstData = data[firstIndex];
        data[firstIndex] = data[secondIndex];
        data[secondIndex] = firstData;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public int size() {
        return length;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null!");
        }
        if (length == data.length) {
            resize(2 * data.length);
        }
        data[length] = item;
        length++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        var randomIndex = StdRandom.uniformInt(length);
        var randomItem = data[randomIndex];
        data[randomIndex] = null;
        swap(randomIndex, length - 1);
        length --;
        if (length > 0 && length <= data.length / 4) {
            resize(data.length / 2);
        }
        return randomItem;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        var randomIndex = StdRandom.uniformInt(length);
        return data[randomIndex];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {

    }
}
