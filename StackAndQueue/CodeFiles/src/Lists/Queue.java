package Lists;


public class Queue {
	private LinkedList list;

	
	public Queue() {
		list = new LinkedList();
	}
	
	public void enqueue(Object obj) {
		list.insertLast(obj);
	}
	
	public Object dequeue() {
		return list.deleteFirst().getData();
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
		return list.toString();
	}
}
