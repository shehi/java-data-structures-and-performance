package textgen;

import java.util.AbstractList;


/**
 * A class that implements a doubly linked list
 *
 * @param <E> The type of the elements stored in the list
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    private int size;

    /**
     * Create a new empty LinkedList
     */
    public MyLinkedList() {
        size = 0;
        this.head = null;
        this.tail = null;
    }

    /**
     * Get the element at position index
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }

        LLNode<E> current = this.head;
        while (current != null) {
            if (index == 0) {
                return current.data;
            }
            index--;
            current = current.next;
        }

        throw new IndexOutOfBoundsException();
    }

    /**
     * Add an element to the list at the specified index
     *
     * @param index   The index where the element should be added
     * @param element The element to add
     */
    public void add(int index, E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        LLNode<E> newElement = new LLNode<>(element);

        if (size == 0) {
            this.head = newElement;
            this.tail = newElement;
        } else if (size > 0 && index == size) {
            newElement.prev = this.tail;
            this.tail.next = newElement;
            this.tail = newElement;
        } else {
            LLNode<E> current = this.head;
            int counter = index;
            while (current != null) {
                if (counter == 0) {
                    newElement.prev = current.prev;
                    newElement.next = current;
                    if (current.prev == null) { // In case of index = 0 where size != 0
                        this.head = newElement;
                    } else {
                        current.prev.next = newElement;
                    }
                    current.prev = newElement;
                    break;
                }
                counter--;
                current = current.next;
            }
        }
        size++;
    }

    /**
     * Appends an element to the end of the list
     *
     * @param element The element to add
     */
    public boolean add(E element) {
        add(size, element);

        return true;
    }


    /**
     * Return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     *
     * @param index The index of the element to remove
     * @return E The data element removed
     * @throws IndexOutOfBoundsException If index is outside the bounds of the list
     */
    public E remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        E deletedElement = null;

        LLNode<E> current = this.head;
        int counter = index;
        while (current != null) {
            if (counter == 0) {
                deletedElement = current.data;
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    this.head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    this.tail = current.prev;
                }
                break;
            }
            counter--;
            current = current.next;
        }
        size--;

        return deletedElement;
    }

    /**
     * Set an index position in the list to a new element
     *
     * @param index   The index of the element to change
     * @param element The new element
     * @return E
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public E set(int index, E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        LLNode<E> current = this.head;
        E currentData = null;
        int counter = index;
        while (current != null) {
            if (counter == 0) {
                currentData = current.data;
                current.data = element;
                break;
            }
            counter--;
            current = current.next;
        }

        return currentData;
    }
}

class LLNode<E> {
    LLNode<E> prev;
    LLNode<E> next;
    E data;

    // TODO: Add any other methods you think are useful here
    // E.g. you might want to add another constructor

    LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }
}
