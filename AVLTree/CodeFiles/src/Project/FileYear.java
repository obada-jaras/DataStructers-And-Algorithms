package Project;

import java.io.File;

public class FileYear {
	private File file;
	private int year;
	
	
	public FileYear(File file, int year) {
		this.file = file;
		this.year = year;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	@Override
	public String toString() {
		return file.getName() + ", " + year;
	}
}
