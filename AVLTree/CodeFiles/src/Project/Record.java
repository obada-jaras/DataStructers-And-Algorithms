package Project;

import Lists.LinkedList;

public class Record implements Comparable<Record>{
	private Name name;
	private LinkedList frequency;
	
	public Record(String name, char gender){
		this.setName(new Name(name, gender));
		setFrequency(new LinkedList());
	}
	
	
	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public LinkedList getFrequency() {
		return frequency;
	}

	public void setFrequency(LinkedList frequency) {
		this.frequency = frequency;
	}
	


	@Override
	public String toString() {
		return getName().toString() + "," + getFrequency().getTotalFrequencies() + "\n";
	}


	@Override
	public int compareTo(Record o) {
		return name.compareTo(o.getName());
	}
	

}
