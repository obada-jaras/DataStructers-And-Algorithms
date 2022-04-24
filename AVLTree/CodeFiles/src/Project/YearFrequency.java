package Project;

public class YearFrequency implements Comparable<YearFrequency>{
	private int year;
	private int frequency;
	
	public YearFrequency(int year, int frequency) {
		this.year = year;
		this.frequency = frequency;
	}

	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}


	@Override
	public String toString() {
		return year + ":" + frequency;
	}

	
	@Override
	public int compareTo(YearFrequency o) {
		return o.year - year;
	}
}
