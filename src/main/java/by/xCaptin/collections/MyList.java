package by.xCaptin.collections;

import java.util.Iterator;

/**
 * Interface representing a generic list of elements.
 *
 * @param <T> the type of elements stored in the list.
 */
public interface MyList<T> {

    /**
     * Adds the specified element to the end of this list.
     *
     * @param object the element to be added to the list
     */
    void add(T object);

    /**
     * Inserts the specified element at the specified position in this list.
     *
     * @param object     the element to be added to the list
     * @param index the index at which the specified element is to be inserted
     */
    void add(T object, int index);

    /**
     * Removes the element at the specified position in this list.
     *
     * @param index the index of the element to be removed
     */
    void remove(int index);

    /**
     * Removes the first occurrence of the specified element from this list.
     *
     * @param object the element to be removed
     */
    void remove(T object);

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in this list
     */
    T get(int index);

    /**
     * Removes all elements from this list.
     */
    void clear();

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    int size();

    /**
     * Sorts the elements of this list in their natural order.
     */
    void sort();

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list
     */
    Iterator<T> iterator();
}
