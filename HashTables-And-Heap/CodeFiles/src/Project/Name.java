package Project;


public class Name implements Comparable<Name> {
	private String name;
	private char gender;
	
	public Name(String name, char gender){
		this.name = name.trim();
		if (gender == 'm' || gender == 'M')
			this.gender = 'M';
		
		else if (gender == 'f' || gender == 'F')
			this.gender = 'F';
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}

	

	@Override
	public int compareTo(Name o) {
		return (name+gender).compareTo((o.name+o.gender));
	}
	

	public boolean equals(Name o) {
		return (name+gender).equals(o.name+o.gender);
	}
	

	
	@Override
	public String toString() {
		return name + "," + gender;
	}
}
