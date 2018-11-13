/**
 *
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 */
public class MyLinkedListTester {

    private static final int LONG_LIST_LENGTH = 10;

    MyLinkedList<String> shortList;
    MyLinkedList<Integer> emptyList;
    MyLinkedList<Integer> longerList;
    MyLinkedList<Integer> list1;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Feel free to use these lists, or add your own
        shortList = new MyLinkedList<String>();
        shortList.add("A");
        shortList.add("B");
        emptyList = new MyLinkedList<Integer>();
        longerList = new MyLinkedList<Integer>();
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            longerList.add(i);
        }
        list1 = new MyLinkedList<Integer>();
        list1.add(65);
        list1.add(21);
        list1.add(42);

    }

    @Test
    public void testGet() {
        //test empty list, get should throw an exception
        try {
            emptyList.get(0);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }

        // test short list, first contents, then out of bounds
        assertEquals("Check first", "A", shortList.get(0));
        assertEquals("Check second", "B", shortList.get(1));

        try {
            shortList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            shortList.get(2);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        // test longer list contents
        for (int i = 0; i < LONG_LIST_LENGTH; i++) {
            assertEquals("Check " + i + " element", (Integer) i, longerList.get(i));
        }

        // test off the end of the longer array
        try {
            longerList.get(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            longerList.get(LONG_LIST_LENGTH);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException e) {
        }

    }

    /**
     * Test adding an element into the end of the list, specifically
     * public boolean add(E element)
     */
    @Test
    public void testAddEnd() {
        assertEquals("AddAtIndex: check tail is correct ", (Integer) 42, list1.tail.data);
        list1.add(33);
        assertEquals((Integer) 42, list1.get(2));
        assertEquals((Integer) 33, list1.get(3));
        assertEquals("AddAtIndex: check tail is correct ", (Integer) 33, list1.tail.data);
    }


    /**
     * Test the size of the list
     */
    @Test
    public void testSize() {
        assertEquals((Integer) 3, (Integer) list1.size());
        assertEquals("AddAtIndex: check tail is correct ", (Integer) 42, list1.tail.data);
        list1.add(33);
        assertEquals((Integer) 4, (Integer) list1.size());
        assertEquals("AddAtIndex: check tail is correct ", (Integer) 33, list1.tail.data);
    }


    /**
     * Test adding an element into the list at a specified index,
     * specifically:
     * public void add(int index, E element)
     */
    @Test
    public void testAddAtIndex() {
        try {
            list1.add(5, 55);
            fail("Shouldn't be able to add outside the boundary!");
        } catch (IndexOutOfBoundsException e) {

        }

        assertEquals((Integer) 42, list1.get(2));
        list1.add(3, 33);
        assertEquals((Integer) 65, list1.get(0));
        assertEquals((Integer) 21, list1.get(1));
        assertEquals((Integer) 42, list1.get(2));
        assertEquals((Integer) 33, list1.get(3));
        assertEquals("AddAtIndex: check tail is correct ", (Integer) 33, list1.tail.data);
        list1.add(2, 55);
        assertEquals((Integer) 21, list1.get(1));
        assertEquals((Integer) 55, list1.get(2));
        assertEquals((Integer) 42, list1.get(3));
        list1.add(0, 44);
        assertEquals((Integer) 44, list1.get(0));
        assertEquals((Integer) 65, list1.get(1));
        assertEquals((Integer) 21, list1.get(2));
        assertEquals((Integer) 55, list1.get(3));
        assertEquals((Integer) 42, list1.get(4));
        assertEquals((Integer) 33, list1.get(5));
        assertEquals("AddAtIndex: check head is correct ", (Integer) 44, list1.head.data);
    }

    /**
     * Test setting an element in the list
     */
    @Test
    public void testSet() {
        try {
            list1.set(5, 55);
            fail("Shouldn't be able to set outside the boundary!");
        } catch (IndexOutOfBoundsException e) {

        }

        assertEquals((Integer) 65, list1.get(0));
        assertEquals((Integer) 21, list1.get(1));
        assertEquals((Integer) 42, list1.get(2));
        list1.set(1, 11);
        assertEquals((Integer) 11, list1.get(1));
    }


    /**
     * Test removing an element from the list.
     * We've included the example from the concept challenge.
     * You will want to add more tests.
     */
    @Test
    public void testRemove() {
        list1.add(33);
        list1.add(44);

        try {
            list1.remove(10);
            fail("Shouldn't be able to remove outside the boundary!");
        } catch (IndexOutOfBoundsException e) {

        }

        int a = list1.remove(0);
        assertEquals("Remove: check a is correct ", 65, a);
        assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
        assertEquals("Remove: check last element is correct ", (Integer) 44, list1.get(list1.size() - 1));
        assertEquals("Remove: check size is correct ", 4, list1.size());
        System.out.println(list1.get(0) + ":" + list1.get(1) + ":" + list1.get(2) + ":" + list1.get(3) + "\n");
        assertEquals("Remove: check head is correct ", (Integer) 21, list1.head.data);
        assertEquals("Remove: check tail is correct ", (Integer) 44, list1.tail.data);

        a = list1.remove(list1.size() - 1);
        assertEquals("Remove: check a is correct ", 44, a);
        assertEquals("Remove: check last element is correct ", (Integer) 33, list1.get(list1.size() - 1));
        assertEquals("Remove: check tail is correct ", (Integer) 33, list1.tail.data);
        assertEquals("Remove: check size is correct ", 3, list1.size());

        a = list1.remove(1);
        assertEquals("Remove: check a is correct ", 42, a);
        assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
        assertEquals("Remove: check last element is correct ", (Integer) 33, list1.get(list1.size() - 1));
        assertEquals("Remove: check head is correct ", (Integer) 21, list1.head.data);
        assertEquals("Remove: check next of head is correct ", (Integer) 33, list1.head.next.data);
        assertEquals("Remove: check tail is correct ", (Integer) 33, list1.tail.data);
        assertEquals("Remove: check prev of tail is correct ", (Integer) 21, list1.tail.prev.data);
        assertEquals("Remove: check size is correct ", 2, list1.size());
    }
}
