package practica6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

//IMPLEMENTS A PRIORITY QUEUE USING A MINHEAP

public class EDPriorityQueue<E>  {

	private static final int DEFAULT_INITIAL_CAPACITY = 11;
	
	E [] data;
    int size;

	private Comparator<E> comparator;

	
	public EDPriorityQueue() {
		this(DEFAULT_INITIAL_CAPACITY, null);
	}
	public EDPriorityQueue(Comparator<E> comp) {
		this(DEFAULT_INITIAL_CAPACITY, comp);
	}

	public EDPriorityQueue(int capacity, Comparator<E> comp) {
		if (capacity < 1)
			throw new IllegalArgumentException();
		this.data = (E[]) new Object[capacity];
		this.comparator = comp;
	}
	
	public EDPriorityQueue(Collection<E> c) {
		this(c.size()+1, null);
		size = c.size();
		int i=0;
		for (E elem: c) {data[i]=elem; i++;}
		heapify();
	}

	public EDPriorityQueue(Collection<E> c, Comparator<E> comp) {
		this(c.size(), comp);
		size = c.size();
		int i=0;
		for (E elem: c) {data[i]=elem; i++;}
		heapify();
	}
	
	//private methods
	/** compares two objects and returns a negative number if object
	 * left is less than object right, zero if are equal, and a positive number if
	 * object left is greater than object right
	 */
	 int compare(E left, E right) {
		if (comparator != null) { //A comparator is defined
			return comparator.compare(left,right);
		}
		else {
			return (((Comparable<E>) left).compareTo(right));
		}
	}
	
	/** Exchanges the object references in the data field at indexes i and j
	 * 
	 * @param i  index of firt object in data
	 * @param j  index of second objet in data
	 */
	private void swap (int i, int j) {
		E elem_i= this.data[i];
		this.data[i]=data[j];
		this.data[j]=elem_i;
	}
	
	private void sink (int parent) {
		//TODO
		int minor = parent;
		//Comparo con la izquierda
		if(2*parent+1 < size) {
			if (compare(data[parent], data[2*parent+1]) > 0) {
				minor = 2*parent+1;
			}
			//Comparo con la derecha
			if (2*parent+2 < size) {
				if (compare(data[minor], data[2*parent+2]) > 0) {
					minor = 2*parent+2;
				}
			}
		}
		//Si son distintos, cambio y llamo
		if (minor != parent) {
			swap(minor, parent);
			sink(minor);
		}
	}
	
	private void floating (int child) {
		//TODO
		while (child > 0) {
			int parent = (child - 1) / 2;
			if (compare(data[parent], data[child]) > 0) {
				swap(parent, child);
				child = parent;
			} else {
				break;
			}
		}
	}
	
	/** Duplicates the size of the array storing the elements*/
	private void grow () {
		int initial_capacity = data.length;
		E[] old = this.data;
		data = (E[]) new Object[initial_capacity*2];
		for (int i=0; i<size; i++) {
			this.data[i]=old[i];
		}
	}
	
	private void heapify() {
		//TODO
		for (int i=(data.length/2)-1; i>=0; i--) {
			sink(i);
			//heapifyAdv(data, i, data.length);
		}
	}
	/*
	private void heapifyAdv(E v[], int parent, int size) {
		while ((2*parent + 1) <= size) {
			int left = 2*parent + 1;
			int right = 2*parent + 2;
			if (right < size && compare(v[left], v[right]) > 0) {
				if (left < size && compare(v[parent], v[left]) < 0) {
					swap(parent, right);
					parent = left;
				} else {
					break;
				}
			} else if (right < size && compare(v[left], v[right]) < 0) {
				if (right < size && compare(v[parent], v[right]) < 0) {
					swap(parent, right);
					parent = right;
				} else {
					break;
				}
			} else if (left < size && compare(v[parent], v[left]) < 0) {
				swap(parent, left);
				parent = left;
			} else {
				break;
			}
		}
	}
	 */

	private int indexOf(E item, int current) {
	    //TODO
		int i = current;
		while (i < this.size && compare(data[i],item) !=0 ) i++;
		if (i == this.size) return -1;
		return i;
    }

	public boolean contains(E item) {
		int pos = indexOf(item, 0);

		return (pos != -1);
	}

	public boolean isEmpty() {

	    return (this.size == 0);
	}

	public int size() {
	    return size;
    }
	
	/** Inserts an item into the priority_queue. Returns true if successful;
	 * returns false if the item is not inserted
	 * @param item Item to be inserted in the priority queue
	 * @return True if the element have been added or false if not (in practice always true)
	 */
	public boolean add(E item) {
		//TODO
		this.data[size] = item;
		size++;
		grow();
		floating(size-1);
		return true;
	}
	
		

	/** Removes the front of the queue if  the priority queue is not empty.
	 * If the queue is empty, returns NoSuchElementException
	 * @return E smallest element in the queue
	 */
	public E remove() throws NoSuchElementException {
		if (isEmpty()) throw new NoSuchElementException();
			E result = data[0]; //the root of the heap
			
			if (size > 1) {
				//remove the last item in the array and put it in the root position
				data[0] = data[size-1];  //puts last item in the root position
				sink(0);
			}
			size = size -1;  //deletes last element
			return result;
		
	}

	/** Returns the smallest entry, WITHOUT REMOVING IT.
	 * If the queue is empty, returns NoSuchElementException
	 * @return E smallest entry
	 */
	public E element() throws NoSuchElementException {
		if (!isEmpty()) {
			return data[0];
		}
		throw new NoSuchElementException();
	}

    /** Removes the first occurrence of the element E in the queue.
     *
     * @param item The element to be removed
     * @return True is the element was found and removed, false if not.
     */
	public boolean remove (E item) {
		//TODO
		if (this.isEmpty() || item == null) {
			return false;
		}
		int firstIndex = indexOf(item, 0);
		if (firstIndex < 0 || firstIndex >= size) return false;
		else {
			swap(0, firstIndex);
			remove();
			heapify();
			return true;
		}
	}


    String toStringHeap() {
        StringBuilder s = new StringBuilder();
        int enNivel = 1;
        int finNivel = 1;
        for (int i = 0; i < size; i++) {
            s.append(data[i]);

            if (i != size -1)
                if (i == finNivel-1) {
                    s.append("] [");
                    enNivel *= 2;
                    finNivel += enNivel;
                } else
                    s.append(", ");
        }
        s.append("]");
        return s.toString();
    }

    public String toString() {
        return "EDPriorityQueue: [" + this.toStringHeap() + " - size: " + size;
    }
}
