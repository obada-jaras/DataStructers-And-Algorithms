package Lists;

import Project.YearFrequency;

public class LinkedList {
	private Node first;
	private Node last;
	private int count;

	// **start constructors**//
	public LinkedList() {
	}
	// **end constructors**//

	// number of elements in the LinkedList
	public int size() {
		return count;
	}

	// returns true if the LinkedList is empty
	public boolean isEmpty() {
		return count == 0;
	}

	// clear the LinkedList
	public void clear() {
		first = null;
		last = null;
		count = 0;
	}

	// insert object in the beginning
	// this method returns 1 if inserting process succeed
	public int insertFirst(Object object) {
		Node newNode = new Node(object);

		// if the insertFirst() method calls for an empty LinkedList, the inserted
		// object will set as first and last
		if (count == 0) {
			first = newNode;
			last = newNode;
			count++;
		}

		// if the LinkedList isn't empty, the inserted object will set as the first
		// Node, and its next will set as the previous first
		else {
			newNode.setNext(first);
			first = newNode;
			count++;
		}

		return 1; // if the method reaches the end without any problem, it returns 1
	}

	// insert more than one object an the first at the same time (with one method
	// call)
	// this method returns 1 if inserting process succeed
	public int insertAllFirst(Object... objects) { // all parameters saved in an array called objects

		if (objects.length == 0)
			return 0; // if no arguments, it will return 0 without doing anything

		int initialSize = count; // this flag used to determine if the initial status of the LinkedList is empty,
									// if so, last will set as the last parameter
		Node current, temp;

		temp = first; // saving the first item to put it after the last added item
		first = new Node(objects[0]); // change first to the first parameter
		current = first;
		count++; // increment LinkedList size counter (because adding the first item)

		// adding all parameters after first
		for (int i = 1; i < objects.length; i++) {
			current.setNext(new Node(objects[i]));
			current = current.getNext();
			count++;
		}

		// if the LinkedList was empty before adding items, last set as the last
		// parameter
		if (initialSize == 0) {
			last = current;
		}

		// if the LinkedList wasn't empty before adding items, the next of the last
		// added items set as the initial first item that saved in temp
		else {
			current.setNext(temp);
		}

		return 1; // if the method reaches the end without any problem, it returns 1
	}

	// delete object from the beginning
	// this method returns deleted Node if deleting process succeed
	public Node deleteFirst() {
		// if the LinkedList is already have no items, the method just returns null
		if (count == 0) {
			return null;
		}

		// if the LinkedList has just one object, it will return it and set first and
		// last to null
		else if (count == 1) {
			Node temp = first; // to return it
			first = null;
			last = null;
			count--;
			return temp;
		}

		// if the LinkedList has more than one object, first node changes to the second
		// Node, and the first one will return
		else {
			Node temp = first; // to return it
			first = first.getNext();
			count--;
			return temp;
		}
	}

	// insert object at the end
	// this method returns 1 if inserting process succeed
	public int insertLast(Object object) {
		Node newNode = new Node(object);

		// if the insertLast() method calls for an empty LinkedList, the inserted object
		// will set as first and last
		if (count == 0) {
			first = newNode;
			last = newNode;
			count++;
		}

		// if the LinkedList isn't empty, the last node will point to the inserted node,
		// and the inserted node sets as last
		else {
			last.setNext(newNode);
			last = newNode;
			count++;
		}

		return 1; // if the method reaches the end without any problem, it returns 1
	}

	// insert more than one object at the end
	// this method returns 1 if inserting process succeed
	public int insertAllLast(Object... objects) { // all parameters saved in an array called objects

		if (objects.length == 0)
			return 0; // if no arguments, it will return 0 without doing anything

		// if the LinkedList is empty, it will insert parameters at the first
		if (count == 0) {
			return insertAllFirst(objects);
		}

		Node current = last;

		// inserting all parameters after the last node
		for (int i = 0; i < objects.length; i++) {
			current.setNext(new Node(objects[i]));
			current = current.getNext();
			count++;
		}

		last = current; // set value of last to the last inserted item

		return 1; // if the method reaches the end without any problem, it returns 1
	}

	// delete object from the end
	// this method returns deleted Node if deleting process succeed
	public Node deleteLast() {

		// if the LinkedList is already have no items, the method just returns null
		if (count == 0) {
			return null;
		}

		// if the LinkedList has just one object, it will return it and set first and
		// last to null
		else if (count == 1) {
			Node temp = last; // to return it
			first = null;
			last = null;
			count--;
			return temp;
		}

		// if the LinkedList has more than one object, last become the previous of the
		// last
		else {
			Node temp = last; // to return it

			Node current = first;
			while (current.getNext().getNext() != null) { // it will stop when current become the previous of the last
															// node
				current = current.getNext();
			}

			temp = current.getNext();
			current.setNext(null);
			last = current;
			count--;
			return temp;
		}
	}

	// insert object after a specific index
	// this method returns 1 if inserting process succeed, and 0 if failed
	public int insertAt(int index, Object object) {

		// if selected index less than 0, the method just returns 0
		if (index < 0) {
			return 0;
		}

		// if selected index is 0, its the first, so it will inserted at the first
		if (index == 0) {
			return insertFirst(object);
		}

		// if the selected index is more than or equal the last index, it will inserted
		// at the last
		if (index > count) {
			return insertLast(object);
		}

		// if the selected index is between the first and last
		Node newNode = new Node(object);
		Node current = first;

		for (int i = 0; i < index - 1; i++) { // it will stop when current become the previous of the selected index
			current = current.getNext();
		}

		newNode.setNext(current.getNext());
		current.setNext(newNode);
		count++;

		return 1; // if the method reaches the end without any problem, it returns 1
	}

	// insert more than one object after a specific index
	// this method returns 1 if inserting process succeed, and 0 if failed
	public int insertAllAt(int index, Object... objects) { // all parameters saved in an array called objects

		// return 0 (failed) if the selected index is less than 0 or no the method has
		// no arguments
		if (index < 0 || objects.length == 0) {
			return 0;
		}

		// if the LinkedList is empty, it will insert parameters at the first
		if (count == 0) {
			return insertAllFirst(objects);
		}

		// if the selected index is 0, it will insert parameters at the first
		if (index == 0) {
			return insertAllFirst(objects);
		}

		// if the selected index is greater than or equal the last index, it will insert
		// parameters at the end
		if (index >= count - 1) {
			return insertAllLast(objects);
		}

		// if the selected index is between the first and last
		Node current, temp;

		current = first;
		for (int i = 0; i < index - 1; i++) { // it will stop when current equals the previous of selected index
			current = current.getNext();
		}

		temp = current.getNext(); // saving it to make it as the next of the last item added

		for (int i = 0; i < objects.length; i++) { // adding parameters after selected index
			current.setNext(new Node(objects[i]));
			current = current.getNext();
			count++;
		}

		current.setNext(temp);

		return 1; // if the method reaches the end without any problem, it returns 1
	}
	
	
	
	//insert a new record to LinkedList in sorted way (Progressive), return 1 if inserting process succeed
	public int insertRecordSorted(YearFrequency record) {
		
		//if the LinkedList is empty, call insertFirst() method
		if (count == 0) {
			return insertFirst(record);
		}
		
		
		//if the year of inserted record is greater than the first year, it will added to the first
		int compareWithFirst = (record.compareTo((YearFrequency)(first.getData())));
		if (compareWithFirst > 0) {				//object.grade > this.grade
			return insertFirst(record);
		}
		
		
		//if the LinkedList has just one record, and the inserted record doesn't inserted in the first, it will inserted in the last
		if (count == 1) {
			return insertLast(record);
		}
		
		
		int compareWithNext;
		Node current = first;
		Node newNode = new Node(record);
		
		for (int i = 0; i < count-1; i++) {
			compareWithNext = (record.compareTo((YearFrequency)(current.getNext().getData())));
			if (compareWithNext > 0) {
				newNode.setNext(current.getNext());
				current.setNext(newNode);
				
				count++;
				return 1;
			}
			
			current = current.getNext();
		}
		
		
		//if the year of inserted record is the lowest, it added to the last
		current.setNext(newNode);
		last = newNode;
		count++;
		return 1;	
	}
		
		
		

	// get index of an object, it returns -1 if not found
	public int indexOf(Object object) {
		int index = 0, flag = 0;
		Node current = first;

		while (current != null) { // it will check all nodes until found object
			if (current.getData().equals(object)) { // when an object found, while loop breaks and the flag value become
													// 1
				flag = 1;
				break;
			}

			current = current.getNext();
			index++; // to return it when find the object
		}

		if (flag == 0) { // if the flag value doesn't change, that means there is no object found
			return -1;
		}

		return index;
	}

	// delete object from specific index
	// this method returns deleted Node if deleting process succeed
	public Node deleteAt(int index) {

		// if the selected index is less than 0 or greater than the last index, it will
		// return null
		if (index < 0 || index >= count) {
			return null;
		}

		// if selected index is zero, it means it's the first index and will call
		// deleteFirst() method
		if (index == 0) {
			return deleteFirst();
		}

		// if selected index is last index, it will call deleteLast() method
		if (index == count - 1) {
			return deleteLast();
		}

		// if the selected index is between the first and last
		Node current = first;
		Node temp;

		for (int i = 0; i < index - 1; i++) { // it stops when current is the previous of the selected index
			current = current.getNext();
		}

		temp = current.getNext(); // to return it at the end
		current.setNext(current.getNext().getNext());
		count--;

		return temp;
	}

	// delete specific object
	// this method returns the index of deleted Node, and -1 if no objects deleted
	public int delete(Object object) {

		// if the object is the first item in the LinkedList, deleteFirst() called
		if (first.getData().equals(object)) {
			deleteFirst();
			return 0;
		}

		Node current = first;

		for (int i = 0; i < count - 1; i++) {
			if (current.getNext().getData().equals(object)) {

				current.setNext(current.getNext().getNext()); // deleting process
				count--;

				if (i == count - 1) // if the object is the last one, the value of last set as the previous of it
					last = current;

				return i + 1;
			}

			current = current.getNext();
		}

		return -1; // return -1 if no object found
	}

	// return the first object in the LinkedList
	public Object getFirst() {
		if (count == 0) { // if the LinkedList is null, it will return null
			return null;
		}

		return first.getData();
	}

	// return the last object in the LinkedList
	public Object getLast() {
		if (count == 0) { // if the LinkedList is null, it will return null
			return null;
		}

		return last.getData();
	}

	// return the object at a specific index
	public Object getAt(int index) {

		// if the entered index is less than 0 or greater than the last index, it will
		// return null
		if (index < 0 || index >= count) {
			return null;
		}

		Node current = first;

		for (int i = 0; i < index; i++) { // will stop at the selected index
			current = current.getNext();
		}

		return current.getData();
	}

	// replace the first item with a new one and return the replaced (deleted) item
	public Object replaceFirst(Object object) {

		// if the LinkedList is empty, it will return null and insert the object
		if (count == 0) {
			insertFirst(object);
			return null;
		}

		Object temp = first.getData(); // to return it
		first.setData(object);
		return temp;
	}

	// replace the last item with a new one and return the replaced (deleted) item
	public Object replaceLast(Object object) {

		// if the LinkedList is empty, it will return null and insert the object
		if (count == 0) {
			insertFirst(object);
			return null;
		}

		Object temp = last.getData(); // to return it
		last.setData(object);
		return temp;
	}

	// replace item in a specific index with a new one and return the replaced
	// (deleted) item
	public Object replaceAt(int index, Object object) {

		// if the index in out of range or the LinkedList has not have any item to
		// replace, the method will just return null
		if (index < 0 || index >= count || count == 0) {
			return null;
		}

		// if the index selected is zero, replaceFirst() method called
		if (index == 0) {
			return replaceFirst(object);
		}

		// if the index selected is the last index, replaceLast() method called
		if (index == count - 1) {
			return replaceLast(object);
		}

		// if the index between first and last
		Node current = first;
		for (int i = 0; i < index; i++) { // will stop at the selected index
			current = current.getNext();
		}

		Object temp = current.getData(); // to return it
		current.setData(object);
		return temp;
	}

	// swap two objects in specific indices and return 1 if succeed or -1 if failed
	public int swap(int index1, int index2) {

		// always make index2 greater than index1
		if (index1 > index2) {
			int temp = index1;
			index1 = index2;
			index2 = temp;
		}

		// fail cases:
		// index1 is the same as index2
		// at least one index less than 0
		// at least one index is greater than the last index
		if (index1 == index2 || index1 < 0 || index2 > count - 1) {
			return -1;
		}

		Node current = first;

		int i; // declared before the for loop because it used also in the next for loop

		// catch the first object
		for (i = 0; i < index1; i++) {
			current = current.getNext();
		}
		Object temp1 = current.getData();
		// after reaching the first object, store it in a temp1

		// continue to catch the second object
		for (; i < index2; i++) {
			current = current.getNext();
		}
		Object temp2 = current.getData();
		// after reaching the second object, store it in a temp2

		current.setData(temp1); // replace second object with first one that stored in temp1

		replaceAt(index1, temp2); // go back from the first to reach the first one and replace it with the second
									// object stored in temp2

		return 1; // if the method reaches the end without any problem, it returns 1
	}

	
	public int getFrequencyForYear(int year) {

		Node current = first;

		while (current != null) {
			if (((YearFrequency)current.getData()).getYear() == year) {
				return ((YearFrequency) current.getData()).getFrequency();
			}

			current = current.getNext();
		}

		return 0; // return 0 if no record found
	}
	
	
	
	public int getTotalFrequencies() {
		Node current = first;
		
		int sum = 0;
		while (current != null) {
			sum += ((YearFrequency)current.getData()).getFrequency();
			current = current.getNext();
		}
		
		return sum;
	}
	
	
	
	public double getAvgFrequencies() {
		return getTotalFrequencies()*1.0/count;
	}
	
	
	
	

	public Stack toStack() {

		if (isEmpty()) {
			return new Stack();
		}

		Node current = first;
		Stack tempStack = new Stack();

		while (current != null) {
			tempStack.push(current.getData());
			current = current.getNext();
		}

		return tempStack;
	}

	public Queue toQueue() {
		if (isEmpty()) {
			return new Queue();
		}

		Node current = first;
		Queue tempQueue = new Queue();

		while (current != null) {
			tempQueue.enqueue(current.getData());
			current = current.getNext();
		}

		return tempQueue;
	}

	@Override
	public String toString() {
		if (count == 0) {
			return null;
		}

		else {
			String str = "";
			Node current = first;

			while (current != null) {
				str += current.getData() + "\n";
				current = current.getNext();
			}

			return str;
		}
	}
}
