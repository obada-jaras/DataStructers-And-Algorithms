public class Node {
	private Node next;
	private Object data;
	

	//**start constructors**//
	public Node() {
	}
	
	
	public Node(Object data) {
		this.data = data;
	}
	//**end constructors**//

	
	
	//**start setters and getters**//
	public Node getNext() {
		return next;
	}
	
	public void setNext(Node next) {
		this.next = next;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	//**end setters and getters**//

	
	
	@Override
	public String toString() {
		return data + "";
	}
}