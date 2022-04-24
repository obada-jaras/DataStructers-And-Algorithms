public class TRecord{
	private long seatNumber;
	private float grade;
	private String branch;
	
	//**start constructors**//  
	public TRecord() {
	}
	
	
	public TRecord(long seatNumber, float grade, String branch) {
		this.seatNumber = seatNumber;
		this.grade = grade;
		this.branch = branch;
	}


	public TRecord(String line) {
		int firstComma = line.indexOf(",");
		int secondComma = line.lastIndexOf(",");
		
		seatNumber = Long.parseLong(line.substring(0, firstComma).trim());
		branch = line.substring(firstComma+1, secondComma).trim();
		grade = Float.parseFloat(line.substring(secondComma+1).trim());
	}
	//**end constructors**//  

	
	
	
	//**start setters and getters**//
	public long getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(long seatNumber) {
		this.seatNumber = seatNumber;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
	//**end setters and getters**//


	
	
	public boolean isLiterary() {
		return branch.toLowerCase().equals("literary");
	}
	
	public boolean isScientific() {
		return branch.toLowerCase().equals("scientific");
	}
	
	
	

	//this method used LinkedList.insertRecordSorted() method
	//it returns 1 if selected record is greater than the new record passed as parameter
	public int greaterThan(TRecord record) {
		if (this.grade > record.grade)
			return 1;
		
		else 
			return 0;
	}
	
	
	@Override
	public String toString() {
		return "Seat Number: " + seatNumber + "     Grade: " + grade/* + "     Branch: " + branch*/;
	}
}
