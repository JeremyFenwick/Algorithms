import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DequeTest {
    @Test
    void addRemoveTest1() {
        var deque = new Deque<Integer>();
        deque.addFirst(12);
        var result = deque.removeFirst();
        assertEquals(12, result);
    }

    @Test
    void addRemoveTest2() {
        var deque = new Deque<Integer>();
        deque.addFirst(12);
        deque.addFirst(10);
        var result = deque.removeLast();
        assertEquals(12, result);
    }

    @Test
    void addRemoveTest3() {
        var deque = new Deque<Integer>();
        deque.addLast(12);
        var result = deque.removeFirst();
        assertEquals(12, result);
    }

    @Test
    void addRemoveTest4() {
        var deque = new Deque<Integer>();
        deque.addLast(12);
        var result = deque.removeLast();
        assertEquals(12, result);
    }

    @Test
    void addRemoveTest5() {
        var deque = new Deque<Integer>();
        deque.addLast(12);
        deque.addFirst(8);
        var result = deque.removeFirst();
        assertEquals(8, result);
    }

    @Test
    void addRemoveTest6() {
        var deque = new Deque<Integer>();
        deque.addLast(12);
        deque.addFirst(8);
        var result = deque.removeLast();
        assertEquals(12, result);
    }

    @Test
    void addRemoveTest7() {
        var deque = new Deque<Integer>();
        deque.addLast(12);
        deque.addFirst(8);
        deque.removeLast();
        deque.removeLast();
        assertTrue(deque.isEmpty());
        deque.addFirst(1);
        var result = deque.removeFirst();
        assertEquals(1, result);
    }

    @Test
    void addRemoveTest8() {
        var deque = new Deque<Integer>();
        deque.addLast(12);
        deque.addFirst(8);
        deque.removeFirst();
        deque.removeFirst();
        assertTrue(deque.isEmpty());
        deque.addFirst(1);
        var result = deque.removeLast();
        assertEquals(1, result);
    }

    @Test void Iterator() {
        var deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        for (var number : deque) {
            assertTrue(number > 0 && number < 4);
        }
    }

    @Test void StressIterator() {
        var deque = new Deque<Integer>();
        deque.addFirst(1);
        for (var number : deque) {
            System.out.print(number);
        }
        System.out.println();
        deque.addLast(2);
        for (var number : deque) {
            System.out.print(number);
        }
        System.out.println();
        deque.addFirst(3);
        for (var number : deque) {
            System.out.print(number);
        }
        System.out.println();
        deque.removeLast();
        for (var number : deque) {
            System.out.print(number);
        }
    }

    @Test void MoreInteratorStress() {
        var deque = new Deque<Integer>();
        deque.addFirst(1);
        for (var number : deque) {
            System.out.print(number);
        }
        System.out.println();
        deque.removeLast();
        for (var number : deque) {
            System.out.print(number);
        }
    }
}