package Heap;

public class MinHeap extends Heap {

	public MinHeap(int size) {
		super(size);
	}

	public void heapifyDown() {
		int index = 0;
		while (hasLeftChild(index)) {
			int smallerChildIndex = getLeftChildIndex(index);

//			if (hasRightChild(index) && rightChild(index) < leftChild(index)) {
			if (hasRightChild(index) && ((Comparable)rightChild(index)).compareTo(leftChild(index)) < 0) {
				smallerChildIndex = getRightChildIndex(index);
			}

//			if (items[index] < items[smallerChildIndex]) {
			if (((Comparable)items[index]).compareTo(items[smallerChildIndex]) < 0) 
				break;
			
			swap(index, smallerChildIndex);
			index = smallerChildIndex;
		}
	}

	public void heapifyUp() {
		int index = size - 1;

//		while (hasParent(index) && parent(index) > items[index]) {
		while (hasParent(index) && ((Comparable)parent(index)).compareTo(items[index]) > 0) {
			swap(getParentIndex(index), index);
			index = getParentIndex(index);
		}
	}
	
	
	
	public void reHeap() {
		MinHeap tempHeap = new MinHeap(super.getSize());
		for (Object obj: super.items)
			if (obj != null)
				tempHeap.add(obj);
		
		super.items = tempHeap.items;
	}
}