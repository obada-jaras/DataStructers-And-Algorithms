package Lists;

public class Node {
	private Node next;
	private Object data;
	

	//**constructors start**//
	public Node() {
	}
	
	
	public Node(Object data) {
		this.data = data;
	}
	//**constructors end**//

	
	
	//**setters and getters start**//
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
	//**setters and getters end**//

	
	
	@Override
	public String toString() {
		return data + "";
	}
}