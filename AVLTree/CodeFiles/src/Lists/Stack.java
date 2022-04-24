package Lists;


public class Stack {
	private LinkedList list;
	
	
	public Stack() {
		list = new LinkedList();
	}
	
	
	public void push(Object obj) {
		list.insertFirst(obj);
	}
	
	public Object pop() {
		return list.deleteFirst().getData();
	}
	
	public Object top() {
		return list.getFirst();
	}
	
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void clear() {
		list.clear();
	}
	
	public int size() {
		return list.size();
	}
	
	

	@Override
	public String toString() {
		String str = "";
		Stack tempStack = new Stack();


		while (!isEmpty()) {
			Object tempObj = this.pop();
			tempStack.push(tempObj);
			
			str = tempObj + "\n" + str;
		}
		
		
		while (!tempStack.isEmpty()) {
			this.push(tempStack.pop());
		}
		
		return str;
	}
}
