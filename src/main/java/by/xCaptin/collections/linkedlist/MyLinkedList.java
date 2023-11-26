package by.xCaptin.collections.linkedlist;

import by.xCaptin.collections.MyList;

import java.util.Collection;
import java.util.Iterator;


/**
 * A custom implementation of a linked list that implements the MyList interface.
 *
 * @param <T> the type of elements held in this linked list
 */
public class MyLinkedList<T> implements MyList<T> {


    /**
     * The size of the linked list.
     */
    private int size;

    /**
     * The header node of the linked list.
     */
    private Node<T> header;

    /**
     * Constructs an empty linked list.
     */
    public MyLinkedList() {
        this.header = new Node<>(null, header, header);
        header.first = header.last = header;
        size = 0;
    }

    /**
     * Constructs a linked list containing the elements of the specified collection,
     * in the order they are returned by the collection's iterator.
     *
     * @param c the collection whose elements are to be added to the linked list
     */
    public MyLinkedList(Collection<? extends T> c) {
        this();
        addAll(c);
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param object the element to be added to the list
     */
    @Override
    public void add(T object) {
        add(object, header);
    }

    /**
     * Inserts the specified element at the specified position in this list.
     *
     * @param object the element to be added to the list
     * @param index  the index at which the specified element is to be inserted
     */
    @Override
    public void add(T object, int index) {
        if (index == size) {
            add(object, header);
        } else {
            Node<T> indexNode = node(index);
            add(object, indexNode);
        }
    }

    /**
     * Adds the specified element before the given node.
     *
     * @param object the element to be added
     * @param node   the node before which the element is to be added
     */
    private void add(T object, Node<T> node) {
        Node<T> newNode = new Node<>(object, node, node.last);
        newNode.last.first = newNode;
        newNode.first.last = newNode;
        size++;
    }

    /**
     * Adds all elements of the specified collection to the end of this list.
     *
     * @param c the collection containing elements to be added to this list
     */
    @SuppressWarnings("unchecked")
    public void addAll(Collection<? extends T> c) {
        if (!c.isEmpty()) {
            for (Iterator iterator = c.iterator(); iterator.hasNext(); ) {
                add((T) iterator.next(), header);
            }
        }
    }


    /**
     * Removes the first occurrence of the specified element from this list, if it is present.
     *
     * @param object the element to be removed from this list, if present
     */
    @Override
    public void remove(Object object) {
        Node<T> n = header;
        for (int i = 0; i < size; i++) {
            n = n.first;
            if (n.element.equals(object)) {
                remove(n);
                break;
            }
        }
    }

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index the index of the element to be removed
     */
    @Override
    public void remove(int index) {
        Node<T> node = node(index);
        remove(node);
    }

    /**
     * Removes the specified node from this list.
     *
     * @param n the node to be removed
     */
    private void remove(Node<T> n) {
        n.last.first = n.first;
        n.first.last = n.last;

        n.first = null;
        n.last = null;
        n.element = null;
        size--;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index the index of the element to be returned
     * @return the element at the specified position in this list
     */
    @Override
    public T get(int index) {
        return node(index).element;
    }

    /**
     * Removes all elements from this list.
     */
    @Override
    public void clear() {
        header.last = null;
        header.first = null;
        size = 0;
    }


    /**
     * Sorts the elements of this list in their natural order.
     * Sorts the linked listbased on the natural ordering of its elements, i.e., assuming that the elements implement the Comparable interface.
     */
    @Override
    public void sort() {
        if (size <= 1) {
            return;
        }

        Node<T> current = header.first;
        while (current != header) {
            Node<T> minNode = current;
            Node<T> next = current.first;

            while (next != header) {
                if (((Comparable<T>) next.element).compareTo(minNode.element) < 0) {
                    minNode = next;
                }
                next = next.first;
            }

            swap(current, minNode);
            current = current.first;
        }
    }

    private void swap(Node<T> node1, Node<T> node2) {
        T temp = node1.element;
        node1.element = node2.element;
        node2.element = temp;
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence (from first to last element).
     *
     * @return an array containing all of the elements in this list in proper sequence
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = node(i).element;
        }
        return array;
    }

    /**
     * Returns the node at the specified position in this list.
     *
     * @param index the index of the node to be returned
     * @return the node at the specified position in this list
     */
    @SuppressWarnings("unchecked")
    private Node<T> node(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node n = header;
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++) {
                n = n.first;
            }
        } else {
            for (int i = size; i > index; i--) {
                n = n.last;
            }
        }
        return n;
    }


    /**
     * Represents a node in the linked list.
     *
     * @param <T> the type of the element held by the node
     */
    private class Node<T> {
        T element;
        Node<T> first;
        Node<T> last;

        Node(T element, Node<T> first, Node<T> last) {
            this.element = element;
            this.first = first;
            this.last = last;
        }

    }
}
