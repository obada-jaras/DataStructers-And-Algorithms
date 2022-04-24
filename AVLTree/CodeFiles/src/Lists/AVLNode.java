package Lists;

public class AVLNode<T> {
	private AVLNode<T> left, right;
	private T data;
	private int height;
	
	
	AVLNode(T data){
		left = right = null;
		this.data = data;
		height = 0;
	}
	
	

	public AVLNode<T> getLeft() {
		return left;
	}

	public void setLeft(AVLNode<T> left) {
		this.left = left;
	}

	public AVLNode<T> getRight() {
		return right;
	}

	public void setRight(AVLNode<T> right) {
		this.right = right;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}



	@Override
	public String toString() {
		return data + "";
	}
}
