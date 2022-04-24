package Project;

public class DeHuffNode {
	private char character;
	private String code;
	private DeHuffNode left;
	private DeHuffNode right;
	
	
	
	public DeHuffNode() {
	}

	public DeHuffNode(char character, String code, DeHuffNode left, DeHuffNode right) {
		this.character = character;
		this.code = code;
		this.left = left;
		this.right = right;
	}

	public DeHuffNode(char character) {
		this.character = character;
	}

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public DeHuffNode getLeft() {
		return left;
	}

	public void setLeft(DeHuffNode left) {
		this.left = left;
	}

	public DeHuffNode getRight() {
		return right;
	}

	public void setRight(DeHuffNode right) {
		this.right = right;
	}
	
	
}
