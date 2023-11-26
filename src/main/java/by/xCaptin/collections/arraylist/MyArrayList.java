package by.xCaptin.collections.arraylist;

import by.xCaptin.collections.MyList;

import java.util.Collection;
import java.util.NoSuchElementException;


/**
 * Implementation of the MyList interface using an array-based list.
 *
 * @param <T> the type of elements held in this list
 */
public class MyArrayList<T> implements MyList<T> {


    /**
     * The underlying array for storing elements.
     */
    private Object[] values;

    /**
     * The current number of elements in the list.
     */
    private int size;


    /**
     * The default capacity of the list.
     */
    private final static int DEFAULT_SIZE = 10;

    /**
     * The capacity of the list.
     */
    private int capacity = DEFAULT_SIZE;

    /**
     *
     * @return values
     */
    public Object[] getValues() {
        return values;
    }
    /**
     *
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Constructs an empty MyArrayList with the default capacity.
     */
    public MyArrayList() {
        values = new Object[DEFAULT_SIZE];
    }

    /**
     * Constructs a MyArrayList containing the elements of the specified collection,
     * in the order they are returned by the collection's iterator.
     *
     * @param c the collection whose elements are to be placed into this list
     */
    public MyArrayList(Collection<? extends T> c) {
        if (!c.isEmpty()) {
            values = c.toArray();
            size = values.length;
            capacity = size;
            resize();
        }
    }

    /**
     * Constructs an empty MyArrayList with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     */
    public MyArrayList(int initialCapacity) {
        values = new Object[initialCapacity];
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
     * @param object the element to be appended to this list
     */
    @Override
    public void add(T object) {
        resize();
        values[size] = object;
        size++;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     *
     * @param object the element to be inserted
     * @param index  the index at which the specified element is to be inserted
     */
    @Override
    public void add(T object, int index) {
        resize();
        System.arraycopy(values, index, values, index + 1, size - index);
        values[index] = object;
        size++;
    }

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index the index of the element to be removed
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    public void remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        System.arraycopy(values, index + 1, values, index, size - index - 1);
        values[--size] = null;
        resize();
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present.
     *
     * @param object the element to be removed
     * @throws NoSuchElementException if the element is not found in the list
     */
    @Override
    public void remove(T object) {
        int index = indexOf(object);

        if (index == -1) {
            throw new NoSuchElementException("Element not found: " + object);
        }

        remove(index);
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        return (T) values[index];
    }

    /**
     * Removes all elements from this list.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            values[i] = null;
        }
        size = 0;
    }


    /**
     * Sorts the elements of this list in their natural order using the Bubble Sort algorithm.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sort() {
        int n = size;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (compare(j, minIndex) < 0) {
                    minIndex = j;
                }
            }

            T temp = (T) values[i];
            values[i] = values[minIndex];
            values[minIndex] = temp;
        }
    }



    /**
     * Compares the elements at the specified indices in the list.
     *
     * @param index1 the index of the first element to compare
     * @param index2 the index of the second element to compare
     * @return a negative integer if the first element is less than the second,
     *         zero if they are equal, and a positive integer if the first element is greater
     */
    @SuppressWarnings("unchecked")
    private int compare(int index1, int index2) {
        T element1 = (T) values[index1];
        T element2 = (T) values[index2];

        return Integer.compare(element1.hashCode(), element2.hashCode());
    }


    /**
     * Resizes the underlying array if necessary.
     */
    private void resize() {
        if (size >= capacity) {
            Object[] newValues = new Object[size * 3 / 2 + 1];
            System.arraycopy(values, 0, newValues, 0, size);
            values = newValues;
        }
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list,
     * or -1 if this list does not contain the element.
     *
     * @param object the element to search for
     * @return the index of the first occurrence of the element, or -1 if not found
     */
    private int indexOf(T object) {
        for (int i = 0; i < size - 1; i++) {
            if (values[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

}