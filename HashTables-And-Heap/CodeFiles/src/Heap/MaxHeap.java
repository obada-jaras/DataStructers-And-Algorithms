package Heap;

public class MaxHeap extends Heap {

	public MaxHeap(int size) {
		super(size);
	}

	
	public void heapifyDown() {
		int index = 0;
		while (hasLeftChild(index)) {
			int biggerChildIndex = getLeftChildIndex(index);

//			if (hasRightChild(index) && rightChild(index) > leftChild(index)) {
			if (hasRightChild(index) && ((Comparable)rightChild(index)).compareTo(leftChild(index)) > 0) {
				biggerChildIndex = getRightChildIndex(index);
			}

//			if (items[index] > items[biggerChildIndex]) 
			if (((Comparable)items[index]).compareTo(items[biggerChildIndex]) > 0) 
				break;
			
			swap(index, biggerChildIndex);
			index = biggerChildIndex;
		}
	}

	public void heapifyUp() {
		int index = size - 1;

//		while (hasParent(index) && parent(index) < items[index]) {
		while (hasParent(index) && ((Comparable)parent(index)).compareTo(items[index]) < 0) {
			swap(getParentIndex(index), index);
			index = getParentIndex(index);
		}
	}
	
	
	public void reHeap() {
		MaxHeap tempHeap = new MaxHeap(super.getSize());
		for (Object obj: super.items)
			if (obj != null)
				tempHeap.add(obj);
		
		super.items = tempHeap.items;
	}
}
