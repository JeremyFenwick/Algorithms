import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class RandomizedQueueTest {
    @Test
    void addRemoveTest1() {
        var queue = new RandomizedQueue<Integer>();
        queue.enqueue(12);
        var result = queue.dequeue();
        assertEquals(12, result);
    }

    @Test
    void addRemoveTest2() {
        var queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        var result1 = queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        assertTrue(result1 == 1 || result1 == 2 || result1 == 3);
    }

    @Test
    void addRemoveTest3() {
        var queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(1);
    }

    @Test
    void iterator() {
        var queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        for (var number : queue) {
            assertTrue(number >= 1 && number <= 3);
        }
    }
}

