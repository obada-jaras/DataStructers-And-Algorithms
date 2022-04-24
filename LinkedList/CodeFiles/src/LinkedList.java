public class LinkedList {
	private Node first;
	private Node last;
	private int count;
	
	

	
	//**start constructors**//
	public LinkedList() {
	}
	//**end constructors**//
	
	
	
	
	//number of elements in the LinkedList
	public int size() {
		return count;
	}
	
	
	//returns true if the LinkedList is empty
	public boolean isEmpty() {
		return count==0;
	}
	
	
	//clear the LinkedList
	public void clear() {
		first = null;
		last = null;
		count = 0;
	}
	
	
	
	//insert object in the beginning
	//this method returns 1 if inserting process succeed
	public int insertFirst(Object object) {
		Node newNode = new Node(object);
		
		//if the insertFirst() method calls for an empty LinkedList, the inserted object will set as first and last
		if (count == 0) {
			first = newNode;
			last = newNode;
			count++;
		}
		
		//if the LinkedList isn't empty, the inserted object will set as the first Node, and its next will set as the previous first 
		else {
			newNode.setNext(first);
			first = newNode;
			count++;
		}
		
		return 1;		//if the method reaches the end without any problem, it returns 1
	}
	
	
	
	
	//insert more than one object an the first at the same time (with one method call)
	//this method returns 1 if inserting process succeed
	public int insertAllFirst(Object... objects) {			//all parameters saved in an array called objects
//		for (int i = 0; i < objects.length; i++) {
//			insertAt(objects[i], i)
//		}
		
//		for (int i = objects.length-1; i >= 0; i--) {
//			insertFirst(objects[i])
//		}

		
		if (objects.length == 0) return 0;		//if no arguments, it will return 0 without doing anything
		
		
		int initialSize = count;				//this flag used to determine if the initial status of the LinkedList is empty, if so, last will set as the last parameter
		Node current, temp;
		
		temp = first;							//saving the first item to put it after the last added item
		first = new Node(objects[0]);			//change first to the first parameter
		current = first;
		count++;								//increment LinkedList size counter (because adding the first item)
		
		//adding all parameters after first
		for (int i = 1; i < objects.length; i++) {
			current.setNext(new Node(objects[i]));
			current = current.getNext();
			count++;
		}
		
		//if the LinkedList was empty before adding items, last set as the last parameter
		if (initialSize == 0) {
			last = current;
		}
		
		//if the LinkedList wasn't empty  before adding items, the next of the last added items set as the initial first item that saved in temp
		else {
			current.setNext(temp);
		}
		
		return 1;		//if the method reaches the end without any problem, it returns 1
	}
	
	
	
	
	
	//delete object from the beginning
	//this method returns deleted Node if deleting process succeed
	public Node deleteFirst() {
		//if the LinkedList is already have no items, the method just returns null
		if (count == 0) {
			return null;
		}
		
		//if the LinkedList has just one object, it will return it and set first and last to null
		else if (count == 1){
			Node temp = first;	//to return it
			first = null;
			last = null;
			count--;
			return temp;
		}
		
		//if the LinkedList has more than one object, first node changes to the second Node, and the first one will return
		else {
			Node temp = first;	//to return it
			first = first.getNext();
			count--;
			return temp;
		}
	}
	
	
	
	
	//insert object at the end
	//this method returns 1 if inserting process succeed
	public int insertLast(Object object) {
		Node newNode = new Node(object);
		
		//if the insertLast() method calls for an empty LinkedList, the inserted object will set as first and last
		if (count == 0) {
			first = newNode;
			last = newNode;
			count++;
		}
		
		//if the LinkedList isn't empty, the last node will point to the inserted node, and the inserted node sets as last
		else {				
			last.setNext(newNode);
			last = newNode;
			count++;
		}
		
		return 1;		//if the method reaches the end without any problem, it returns 1
	}
	
	
	
	
	//insert more than one object at the end
	//this method returns 1 if inserting process succeed
	public int insertAllLast(Object... objects) { 			//all parameters saved in an array called objects
//		for (int i = 0; i < objects.length; i++) {
//			insertLast(objects[i])
//		}
		
		if (objects.length == 0) return 0;		//if no arguments, it will return 0 without doing anything
		
		
		//if the LinkedList is empty, it will insert parameters at the first
		if (count == 0) {
			return insertAllFirst(objects);
		}
		

		Node current = last;
		
		//inserting all parameters after the last node
		for (int i = 0; i < objects.length; i++) {
			current.setNext(new Node(objects[i]));
			current = current.getNext();
			count++;
		}
		
		last = current;							//set value of last to the last inserted item

		return 1;	//if the method reaches the end without any problem, it returns 1
	}
	
	
	
	

	//delete object from the end
	//this method returns deleted Node if deleting process succeed
	public Node deleteLast() {
		
		//if the LinkedList is already have no items, the method just returns null
		if (count == 0) {
			return null;
		}
		
		//if the LinkedList has just one object, it will return it and set first and last to null
		else if (count == 1) {
			Node temp = last;	//to return it
			first = null;
			last = null;
			count--;
			return temp;
		}
		
		//if the LinkedList has more than one object, last become the previous of the last
		else {
			Node temp = last;	//to return it
			
			Node current = first;
			while (current.getNext().getNext() != null) {	//it will stop when current become the previous of the last node
				current = current.getNext();
			}
			
			temp = current.getNext();
			current.setNext(null);
			last = current;
			count--;
			return temp;
		}
	}
	
	
	
	
	//insert object after a specific index
	//this method returns 1 if inserting process succeed, and 0 if failed
	public int insertAt(int index, Object object) {
		
		//if selected index less than 0, the method just returns 0
		if (index < 0) {
			return 0;
		}
		
		
		//if selected index is 0, its the first, so it will inserted at the first
		if (index == 0) {
			return insertFirst(object);
		}
		
		//if the selected index is more than or equal the last index, it will inserted at the last
		if (index > count) {
			return insertLast(object);
		}
		
		
		//if the selected index is between the first and last
		Node newNode = new Node(object);
		Node current = first;
		
		for (int i = 0; i < index-1; i++) {		//it will stop when current become the previous of the selected index
			current = current.getNext();
		}
		
		newNode.setNext(current.getNext());
		current.setNext(newNode);
		count++;
		
		return 1;	//if the method reaches the end without any problem, it returns 1
	}
	
	
	
	
	//insert more than one object after a specific index
	//this method returns 1 if inserting process succeed, and 0 if failed
	public int insertAllAt(int index, Object... objects) {			//all parameters saved in an array called objects
//		for (int i = 0; i < objects.length; i++, index++) {
//			insertAt(objects[i], index);
//		}
		
		
		//return 0 (failed) if the selected index is less than 0 or no the method has no arguments
		if (index < 0 || objects.length == 0) {
			return 0;
		}
		
		//if the LinkedList is empty, it will insert parameters at the first
		if (count == 0) {
			return insertAllFirst(objects);
		}
		
		//if the selected index is 0, it will insert parameters at the first
		if (index == 0) {
			return insertAllFirst(objects);
		}
		
		//if the selected index is greater than or equal the last index, it will insert parameters at the end
		if (index >= count-1) {
			return insertAllLast(objects);
		}
		
		
		//if the selected index is between the first and last
		Node current, temp;		

		current = first;
		for (int i = 0; i < index-1; i++) {		//it will stop when current equals the previous of selected index
			current = current.getNext();
		}
		
		temp = current.getNext();				//saving it to make it as the next of the last item added

		for (int i = 0; i < objects.length; i++) {		//adding parameters after selected index
			current.setNext(new Node(objects[i]));
			current = current.getNext();
			count++;
		}
		
		current.setNext(temp);
		
		return 1;	//if the method reaches the end without any problem, it returns 1
	}
	
	
	
	
	//get index of an object, it returns -1 if not found
	public int indexOf(Object object) {
		int index = 0, flag = 0;
		Node current = first;
		
		while (current != null) {		//it will check all nodes until found object
			if (current.getData().equals(object)) {		//when an object found, while loop breaks and the flag value become 1
				flag = 1;
				break;
			}
			
			current = current.getNext();
			index++;		//to return it when find the object
		}
		
		if (flag == 0) {	//if the flag value doesn't change, that means there is no object found
			return -1;
		}
		
		return index;
	}
	
	
	
	
	
	
	//delete object from specific index
	//this method returns deleted Node if deleting process succeed
	public Node deleteAt(int index) {
		
		//if the selected index is less than 0 or greater than the last index, it will return null
		if (index < 0 || index >= count) {
			return null;
		}
		
		//if selected index is zero, it means it's the first index and will call deleteFirst() method
		if (index == 0) {
			return deleteFirst();
		}
		
		//if selected index is last index, it will call deleteLast() method
		if (index == count-1) {
			return deleteLast();
		}
		
		//if the selected index is between the first and last
		Node current = first;
		Node temp;
		
		for (int i = 0; i < index-1; i++) {		//it stops when current is the previous of the selected index
			current = current.getNext();
		}
		
		temp = current.getNext();	//to return it at the end
		current.setNext(current.getNext().getNext());
		count--;
		
		return temp;
	}
	
	
	
	
	
	//delete specific object
	//this method returns the index of deleted Node, and -1 if no objects deleted
	public int delete(Object object) {
//		int index = indexOf(object);
//		
//		if (index == -1)
//			return -1;
//		
//		deleteAt(index);
//		return index;
		
		
		//if the object is the first item in the LinkedList, deleteFirst() called
		if (first.getData().equals(object)) {
			deleteFirst();
			return 0;
		}
		
		
		Node current = first;
		
		for (int i = 0; i < count-1; i++) {
			if (current.getNext().getData().equals(object)) {
				
				current.setNext(current.getNext().getNext());	//deleting process 
				count--;
				
				if (i == count-1)		//if the object is the last one, the value of last set as the previous of it
					last = current;
				
				return i+1;
			}
			
			current = current.getNext();
		}
		
		return -1;		//return -1 if no object found
	}
	
	
	
	
	//return the first object in the LinkedList
	public Object getFirst() {
		if (count == 0) {		//if the LinkedList is null, it will return null
			return null;
		}
		
		return first.getData();
	}
	
	
	//return the last object in the LinkedList
	public Object getLast() {
		if (count == 0) {		//if the LinkedList is null, it will return null
			return null;
		}
		
		return last.getData();
	}

	
	//return the object at a specific index
	public Object getAt(int index) {
		
		//if the entered index is less than 0 or greater than the last index, it will return null
		if (index < 0 || index >= count) {
			return null;
		}
		
		
		Node current = first;
		
		for (int i = 0; i < index; i++) {	//will stop at the selected index
			current = current.getNext();
		}
		
		return current.getData();
	}
	
	
	
	
	
	//replace the first item with a new one and return the replaced (deleted) item
	public Object replaceFirst(Object object) {
//		deleteFirst();
//		insertFirst(object);
		
		
		//if the LinkedList is empty, it will return null and insert the object
		if (count == 0) {
			insertFirst(object);
			return null;
		}
	
		
		Object temp = first.getData();	//to return it
		first.setData(object);
		return temp;
	}
	
	
	
	//replace the last item with a new one and return the replaced (deleted) item
	public Object replaceLast(Object object) {	

		//if the LinkedList is empty, it will return null and insert the object
		if (count == 0) {
			insertFirst(object);
			return null;
		}
		
		Object temp = last.getData();	//to return it
		last.setData(object);
		return temp;
	}
	
	
	
	//replace item in a specific index with a new one and return the replaced (deleted) item
	public Object replaceAt(int index, Object object) {
		
		//if the index in out of range or the LinkedList has not have any item to replace, the method will just return null
		if (index < 0 || index >= count || count == 0) {
			return null;
		}
		
		//if the index selected is zero, replaceFirst() method called
		if (index == 0) {
			return replaceFirst(object);
		}
		
		//if the index selected is the last index, replaceLast() method called
		if (index == count-1) {
			return replaceLast(object);
		}
		
		//if the index between first and last
		Node current = first;
		for (int i = 0; i < index; i++) {	//will stop at the selected index
			current = current.getNext();
		}
		
		Object temp = current.getData();	//to return it
		current.setData(object);
		return temp;
	}
	
	
	
	//swap two objects in specific indices and return 1 if succeed or -1 if failed
	public int swap(int index1, int index2) {
		
		//always make index2 greater than index1
		if (index1 > index2) {
			int temp = index1;
			index1 = index2;
			index2 = temp;
		}
		
		
		//fail cases:
		//index1 is the same as index2
		//at least one index less than 0
		//at least one index is greater than the last index
		if (index1 == index2 || index1 < 0 || index2 > count-1) {
			return -1;
		}
		
		
		Node current = first;
		
		int i;	//declared before the for loop because it used also in the next for loop

		//catch the first object
		for (i = 0; i < index1; i++) {
			current = current.getNext();
		}
		Object temp1 = current.getData();
		//after reaching the first object, store it in a temp1
		
		
		//continue to catch the second object
		for (; i < index2; i++) {
			current = current.getNext();
		}
		Object temp2 = current.getData();
		//after reaching the second object, store it in a temp2

		current.setData(temp1);	//replace second object with first one that stored in temp1
		
		replaceAt(index1, temp2);	//go back from the first to reach the first one and replace it with the second object stored in temp2
		
		return 1;	//if the method reaches the end without any problem, it returns 1
	}
	
	
	
	
	
	//**
	//**
	//**
	//**methods below are just for LinkedLists that contains TRecords
	//**
	
	

	//insert a new record to LinkedList in sorted way (Progressive), return 1 if inserting process succeed
	public int insertRecordSorted(TRecord record) {
		
		//if the LinkedList is empty, call insertFirst() method
		if (count == 0) {
			return insertFirst(record);
		}
		
		
		//if the grade of inserted record is greater than the first grade, it will added to the first
		int compareWithFirst = (record.greaterThan((TRecord)(first.getData())));
		if (compareWithFirst == 1) {				//object.grade > this.grade
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
			compareWithNext = (record.greaterThan((TRecord)(current.getNext().getData())));
			if (compareWithNext == 1) {
				newNode.setNext(current.getNext());
				current.setNext(newNode);
				
				count++;
				return 1;
			}
			
			current = current.getNext();
		}
		
		
		//if the grade of inserted record is the lowest, it added to the last
		current.setNext(newNode);
		last = newNode;
		count++;
		return 1;	
	}
	
	
	
	//delete a record by the seat number and return the index of deleted record
	public int deleteBySeat(long sNumber) {
	//see delete() method
		
		
		//if the seat number entered is for the first record
		if (((TRecord)(first.getData())).getSeatNumber() == sNumber) {
			deleteFirst();
			return 0;
		}
		
		
		Node current = first;
		
		for (int i = 0; i < count-1; i++) {
			if (((TRecord)(current.getNext().getData())).getSeatNumber() == sNumber) {
				
				current.setNext(current.getNext().getNext());
				count--;
				
				
				//change the value of last if the deleted record is the last one
				if (i == count-1)
					last = current;	
				
				return i;
			}
			
			current = current.getNext();
		}
		
		
		return -1;	//return -1 if no record deleted
	}

	
	
	//get the record of a seat number
	public TRecord getBySeat(long sNumber) {
		
		Node current = first;

		while (current != null) {
			if (((TRecord)current.getData()).getSeatNumber() == sNumber) {
				return (TRecord)current.getData();
			}
			
			current = current.getNext();
		}
		
		return null;	//return null if no record found
	}
	
	
	//return a string that includes info of the top n students in the LinkedList
	public String topStudentsString(int numberOfTop) {
		
		if (numberOfTop <= 0) {
			return null;
		}
		
		
		double previousGrade = ((TRecord)first.getData()).getGrade();
		String str = "";
		Node current = first;
		
		for (int i=0, rank=0; true; i++) {
			
			double currentGrade = ((TRecord)current.getData()).getGrade();
			
			if (i >= numberOfTop && currentGrade != previousGrade) {		//break when reach number of students want to show, taking into consideration the repetitive grades
				break;
			}
			
			
			if (currentGrade != previousGrade) {	//when moving to a new grade, rank value changes
				rank = i;
			}
			
			str += rank+1 + ")\t" + current + "\n";	//add the rank and record info to str

			if (i >= count-1) {		//break if reach end of the list without print the selected number of students
				break;
			}
			
			previousGrade = ((TRecord)current.getData()).getGrade();
			current = current.getNext();
		}
		
		return str;
	}
	
	
	
	//return average of all students grades
	public double average() {
		
		//if the LinkedList is empty then average become 0
		if (count == 0) {
			return 0;
		}
		
		
		double sum = 0;
		Node current = first;
		
		for (int i = 0; i < count; i++) {
			sum += ((TRecord)current.getData()).getGrade();		//add the grade of each record to the sum
			
			current = current.getNext();
		}
		
		
		return sum/count;	//divide summation of all grade by the number of all students
	}
	
	
	
	//return mode of all students grades
	public String mode() {
		
		//mode is 0 if the LinkedList is empty
		if (count == 0) {
			return "0";
		}
		
		
		int nOfDupForSelNum = 1;	//number of duplication for selected number
		int tempCounter = 1;
		double numberSelected = -1;	//grade
		String str = "";
		
		
		Node current = first;
		
		while (current.getNext() != null) {
			//count number of duplicates for each grade and save it the number in tempCounter
			if (((TRecord)current.getData()).getGrade() == ((TRecord)current.getNext().getData()).getGrade()) {
				tempCounter++;
			}
			
			else { //if the grade duplicates end
				if (tempCounter > nOfDupForSelNum) {		//if tempCounter greater than the most duplicated grade, change it with the current grade, and add grade to str
					nOfDupForSelNum = tempCounter;
					numberSelected = ((TRecord)current.getData()).getGrade();
					str = String.format("%.3g", numberSelected);
				}
				
				else if (tempCounter == nOfDupForSelNum) {		//if current grade is same as the most duplicated grade, append it to str
					numberSelected = ((TRecord)current.getData()).getGrade();
					str += ", " + String.format("%.3g", numberSelected);
				}
				tempCounter = 1;	//reset tempCounter to start counting for the next grade
			}
			
			current = current.getNext();
		}
		
		
		//for the last number in the list
		if (tempCounter > nOfDupForSelNum) {
			nOfDupForSelNum = tempCounter;
			numberSelected = ((TRecord)current.getData()).getGrade();
			tempCounter = 1;
		}
	
		
		if (tempCounter == nOfDupForSelNum) {
			numberSelected = ((TRecord)current.getData()).getGrade();
			str += ", " + String.format("%.3g", numberSelected);
			tempCounter = 1;
		}
		//*//
		
		
		
		//try to enter inputs like : 40, 50, 60 and see why this added
		if (str.charAt(0) == ',') {
			str = str.substring(2);
		}
		
		return str;
	}

	
	
	//return variance of all students grades
	public double variance() {	
		double avg = average();
		double sum = 0;
		
		Node current = first;
		
		while (current != null) {
			double x = ((TRecord)current.getData()).getGrade();
			sum += Math.pow((x-avg), 2);	//(grade-average)^2
			
			current = current.getNext();
		}
		
		return sum/count;
	}

	
	//return standard deviation of all students grades
	public double stdDeviation() {
		return Math.sqrt(variance());
	}
	
	
	//return median of all students grades
	public double median() {
		if (count == 0) {
			return 0;
		}
		
		//if the LinkedList has odd number of records, return the grade in the middle 
		if (count % 2 == 1) {
			return ((TRecord)getAt(count/2)).getGrade();
		}
		
		
		else {		//if the LinkedList has even number of records, return average of the two grades in the middle
			
//			double x1 = ((TRecord)getAt(count/2-1)).getGrade();
//			double x2 = ((TRecord)getAt(count/2)).getGrade();
//			return (x1+x2)/2;
			
			Node current = first;
			for (int i = 0; i < count/2-1; i++) {	//stop when current equal the first of the two middles
				current = current.getNext();
			}
			
			double x1 = ((TRecord)current.getData()).getGrade();
			double x2 = ((TRecord)current.getNext().getData()).getGrade();
			return (x1+x2)/2;
		}
	}
	
	
	
	//return number of students above or equal a specific grade
	public int numberOfStudentsAboveOrEqual(double grade) {
		int total = 0;
		Node current = first;
		
		while (current != null) {
			
			if (((TRecord)current.getData()).getGrade() < grade) {
				break;		//note that our LinkedList is sorted
			}
			
			total++;
			current = current.getNext();
		}

		return total;
	}
	
	
	//return percentage of students above or equal a specific grade
	public double percentageOfStudentsAboveOrEqual(double grade) {
		if (count == 0) {
			return 0;
		}
		
		return numberOfStudentsAboveOrEqual(grade)*1.0/count;
	}


	
	//**
	//**end of methods for LinkedLists that contains TRecords
	//**
	//**
	//**
	
	
	
	
	
	
	@Override
	public String toString() {
		if (count == 0) {
			return null;
		}
		
		else {
			String str = "";
			int number = 1;
			Node current = first;
			
			while (current != null) {
				str += (number + ")\t" + current.getData() + "\n");
				current = current.getNext();
				number++;
			}
			
			return str;
		}
	}
}
