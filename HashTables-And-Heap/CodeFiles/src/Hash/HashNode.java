package Hash;

import Project.Record;


public class HashNode {
	private Record value;
	private int status; // insert: 1, delete: 2, empty: 0

	
	HashNode(Record value, int status) {
		this.value = value;
		this.status = status;
	}

	public Record getValue() {
		return value;
	}

	public int getStatus() {
		return status;
	}

	public void setDeleteStatus() {
		status = 2;
	}

	@Override
	public String toString() {
		return "HashNode [value=" + value + ", status=" + status + "]";
	}
}