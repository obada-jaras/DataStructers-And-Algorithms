package Heap;

import java.util.Arrays;


public abstract class Heap {
	
	protected int capacity;
	protected int size;
	protected Object[] items;

	
	public Heap(int size) {
		this.capacity = size+1;
		this.size = 0;
		this.items = new Object[capacity];
	}

	
	
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Object[] getItems() {
		Object[] array = new Object[size];
		
		for (int i = 0; i < size; i++) 
			array[i] = items[i];
		
		return array;
	}
	




	public int getLeftChildIndex(int parentIndex) {
		return 2 * parentIndex + 1;
	}

	public int getRightChildIndex(int parentIndex) {
		return 2 * parentIndex + 2;
	}

	public int getParentIndex(int childIndex) {
		return (childIndex - 1) / 2;
	}

	
	
	public boolean hasLeftChild(int index) {
		return getLeftChildIndex(index) < size;
	}

	public boolean hasRightChild(int index) {
		return getRightChildIndex(index) < size;
	}

	public boolean hasParent(int index) {
		return getParentIndex(index) >= 0;
	}
	

	public Object leftChild(int index) {
		return items[getLeftChildIndex(index)];
	}

	public Object rightChild(int index) {
		return items[getRightChildIndex(index)];
	}

	public Object parent(int index) {
		return items[getParentIndex(index)];
	}

	

	public void swap(int indexOne, int indexTwo) {
		Object temp = items[indexOne];
		items[indexOne] = items[indexTwo];
		items[indexTwo] = temp;
	}

	public void ensureCapacity() {
		if (size == capacity) {
			capacity = capacity << 1;
			items = Arrays.copyOf(items, capacity);
		}
	}

	
	
	//return the top element without remove it
	public Object peek() {
		isEmpty("peek");
		return items[0];
	}

	public void isEmpty(String methodName) {
		if (size == 0) {
			throw new IllegalStateException("You cannot perform '" + methodName + "' on an empty Heap.");
		}
	}

	
	public Object poll() {
		if (size > 0) {
			// Else, not empty
			Object item = items[0];
			items[0] = items[size - 1];
			size--;
			heapifyDown();
			return item;
		}
		
		return null;
	}

	public void add(Object item) {
		// Resize underlying array if it's not large enough for insertion
		ensureCapacity();

		items[size] = item;
		size++;

		heapifyUp();
	}

	public abstract void heapifyDown();

	public abstract void heapifyUp();
	
	public abstract void reHeap();
	
	
	
	public Object[] sortedArrayOfHeap() {
		reHeap();

		int tempSize = size;
		Object[] copiedArray = items.clone();
		Object[] tempArray = new Object[size];
		
		for (int i = 0; items[i] != null; i++) {
			tempArray[i] = poll();
		}
		
		items = copiedArray;
		size = tempSize;
		return tempArray;
	}
	
	

	public String toString() {
		String str = "";
		
		for (Object obj: items)
			str += obj + " | ";
		
		
		return str;
	}
}