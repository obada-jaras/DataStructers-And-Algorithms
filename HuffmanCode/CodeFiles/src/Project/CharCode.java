package Project;

public class CharCode {
	private char character;
	private String code;
	
	
	public CharCode(char character, String code) {
		this.character = character;
		this.code = code;
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


	@Override
	public String toString() {
		return "CharFreq [character=" + character + ", frequency=" + code + "]\n";
	}


}
