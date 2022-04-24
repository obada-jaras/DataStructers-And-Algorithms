package Project;


import Heap.MaxHeap;

public class Record implements Comparable<Record>{
	private Name name;
	private MaxHeap frequency;
	
	public Record(String name, char gender, int sizeOfArray){
		this.setName(new Name(name, gender));
		setFrequency(new MaxHeap(sizeOfArray));
	}
	
	
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public MaxHeap getFrequency() {
		return frequency;
	}

	public void setFrequency(MaxHeap frequency) {
		this.frequency = frequency;
	}
	


	@Override
	public String toString() {
		return getName().toString() + "," + getFrequency();
	}


	@Override
	public int compareTo(Record o) {
		return name.compareTo(o.getName());
	}
	

}
