package Lists;


public class AVLT<T> {
	private AVLNode<T> root;

	public AVLT() {
		setRoot(null);
	}


	public void clear() {
		setRoot(null);
	}
	
	
	
	public AVLNode<T> getRoot() {
		return root;
	}

	public void setRoot(AVLNode<T> root) {
		this.root = root;
	}
	
	
	

	private void updateHeight(AVLNode<T> node) {
		node.setHeight(Math.max(getHeight(node.getLeft()), getHeight(node.getRight())) + 1);
	}

	private int getHeight(AVLNode<T> node) {
		return node == null ? -1 : node.getHeight();
	}



	private AVLNode<T> singleRotateLeft(AVLNode<T> y) {
		AVLNode<T> x = y.getRight();
		AVLNode<T> z = x.getLeft();
		x.setLeft(y);
		y.setRight(z);
		updateHeight(y);
		updateHeight(x);
		return x;
	}

	private AVLNode<T> singleRotateRight(AVLNode<T> y) {
		AVLNode<T> x = y.getLeft();
		AVLNode<T> z = x.getRight();
		x.setRight(y);
		y.setLeft(z);
		updateHeight(y);
		updateHeight(x);
		return x;
	}
	
	
	public AVLNode<T> doubleRotateLeft(AVLNode<T> k3) {
		k3.setLeft(singleRotateRight(k3.getLeft()));
		return (singleRotateLeft(k3));
	}

	public AVLNode<T> doubleRotateRight(AVLNode<T> k3) {
		k3.setRight(singleRotateLeft(k3.getLeft()));
		return (singleRotateRight(k3));
	}

	
	
	
	

	public void insert(T data) {
		setRoot(privateInsert(getRoot(), data));
	}

	private AVLNode<T> privateInsert(AVLNode<T> node, T data) {
		if (node == null)
			return new AVLNode<T>(data);

		else if (((Comparable) data).compareTo(node.getData()) < 0)
			node.setLeft(privateInsert(node.getLeft(), data));

		else
			node.setRight(privateInsert(node.getRight(), data));

		return rebalance(node);
	}
	
	
	
	
	
	
	public void delete(T data) {
		setRoot(privateDelete(root, data));
	}

	private AVLNode<T> privateDelete(AVLNode<T> node, T data) {
		if (node == null)
			return node;

		else if (((Comparable) data).compareTo(node.getData()) < 0)
			node.setLeft(privateDelete(node.getLeft(), data));

		else if (((Comparable) data).compareTo(node.getData()) > 0)
			node.setRight(privateDelete(node.getRight(), data));

		else {
			if (node.getLeft() == null || node.getRight() == null)
				node = (node.getLeft() == null) ? node.getRight() : node.getLeft();

			else {
				AVLNode<T> minNode = minValueNode(node.getRight());
				node.setData(minNode.getData());
				node.setRight(privateDelete(node.getRight(), node.getData()));
			}
		}

		if (node != null)
			node = rebalance(node);

		return node;
	}

	
	
	
	
	public T find(T data) {
		AVLNode<T> current = root;
		
		while (current != null) {
			
			if (((Comparable) current.getData()).compareTo(data) == 0) {
				break;
			}
			
			current = ((Comparable) current.getData()).compareTo(data) < 0 ? current.getRight() : current.getLeft();
		}
		
		if (current == null)
			return null;
		
		return current.getData();
	}

	
	
	
	
	
	
	private AVLNode<T> rebalance(AVLNode<T> node) {
		updateHeight(node);

		int HeightDiff = heightDiff(node);
		if (HeightDiff == -2) {
			if (heightDiff(node.getRight()) > 0)
				node.setRight(singleRotateRight(node.getRight()));

			return singleRotateLeft(node);

		} else if (HeightDiff == 2) {
			if (heightDiff(node.getLeft()) < 0)
				node.setLeft(singleRotateLeft(node.getLeft()));

			return singleRotateRight(node);
		}

		return node;
	}

	private int heightDiff(AVLNode<T> node) {
		if (node == null)
			return 0;
		return getHeight(node.getLeft()) - getHeight(node.getRight());
	}

	private AVLNode<T> minValueNode(AVLNode<T> node) {
		AVLNode<T> minNode = node;

		/* loop down to find the leftmost leaf */
		while (minNode.getLeft() != null)
			minNode = minNode.getLeft();

		return minNode;
	}
	

	
	
	public void printPreOrder() {
		privatePreOrder(getRoot());
	}

	private void privatePreOrder(AVLNode<T> node) {

		if (node == null)
			return;

		System.out.println(node);
		privatePreOrder(node.getLeft());
		privatePreOrder(node.getRight());
	}

	public void printInOrder() {
		privateInOrder(getRoot());
	}

	private void privateInOrder(AVLNode<T> node) {
		if (node == null)
			return;

		privateInOrder(node.getLeft());
		System.out.println(node);
		privateInOrder(node.getRight());
	}

	public void printPostOrder() {
		privatePostOrder(getRoot());
	}

	private void privatePostOrder(AVLNode<T> node) {
		if (node == null)
			return;

		privatePostOrder(node.getLeft());
		privatePostOrder(node.getRight());
		System.out.println(node);
	}

	
	
	public String getLevelOrder() {
		if (getRoot() == null)
			return "";

		String str = "";
		Queue q = new Queue();

		q.enqueue(getRoot());
		q.enqueue(null);

		while (!q.isEmpty()) {

			AVLNode<T> curr = (AVLNode<T>)q.dequeue();

			if (curr == null) {
				if (!q.isEmpty()) {
					q.enqueue(null);
					str += "\n";
				}
				
			} else {
				if (curr.getLeft() != null)
					q.enqueue(curr.getLeft());

				if (curr.getRight() != null)
					q.enqueue(curr.getRight());

				str += curr.getData() + " ";
			}
		}
		return str;
	}
	
}
