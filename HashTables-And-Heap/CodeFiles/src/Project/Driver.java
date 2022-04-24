package Project;

import java.util.Scanner;

import Hash.Hash;
import Lists.LinkedList;

public class Driver {
	private static Hash hash = new Hash(200);
	static int numberOfInsertedRecords = 0;
	static int numberOfInsertedNames = 0;
	public static int sizeOfHeap;

	
	public static Hash getHash() {
		return hash;
	}


	public static String readFiles(LinkedList files) {
		sizeOfHeap = files.size() + 1;
		
		try {
			Scanner sc;
			
			for (int i = 0; i < files.size(); i++) {
				
				FileYear fileYear = ((FileYear)files.getAt(i));				
				sc = new Scanner(fileYear.getFile());
				int year = fileYear.getYear();
				
				if (!(sc.hasNext())) {
					sc.close();
					throw new Exception("Cannot read file " + ((FileYear)files.getAt(i)).getFile().getName());
				}
				while (sc.hasNext()) {
					try {
						String line = sc.nextLine();
						int firstComma = line.indexOf(",");
						int secondComma = line.lastIndexOf(",");
						
						String name = capitalizeFirstLetter(line.substring(0, firstComma).trim());
						char gender = line.substring(firstComma+1, secondComma).trim().toUpperCase().charAt(0);
						int frequency = Integer.parseInt(line.substring(secondComma+1).trim());

						
						YearFrequency YF = new YearFrequency(year, frequency);
						Record foundRecord = findName(name, gender);
						
						if (foundRecord == null) {
							Record record = new Record(name, gender, sizeOfHeap);
							record.getFrequency().add(YF);
							
							hash.insert(record);
							numberOfInsertedNames++;
							numberOfInsertedRecords++;
							
						
						} else {	
							int flag = 0;
							Object[] objectList = foundRecord.getFrequency().getItems();
							YearFrequency[] frequencyList = new YearFrequency[objectList.length];

							for (int j = 0; j < objectList.length; j++) {
								frequencyList[j] = (YearFrequency)objectList[j];
							}
							

							for (YearFrequency y: frequencyList) {
								if (y.getYear() == year) {
									y.setFrequency(y.getFrequency() + frequency);
									flag = 1;
									break;
								}
							}
							
							if (flag == 0)
								foundRecord.getFrequency().add(YF);
							
							numberOfInsertedRecords++;
						}
						

					} catch (Exception e) {
						sc.close();
						throw new Exception("Cannot read file " + ((FileYear)files.getAt(i)).getFile().getName());
					}
				}
			}
			
			return "";
			
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	
	private static String capitalizeFirstLetter(String str) {
        String words[]=str.split("\\s");
        String capitalizedStr="";
 
        for(String word:words){
            String firstLetter=word.substring(0,1);
            String remainingLetters=word.substring(1);
            capitalizedStr+=firstLetter.toUpperCase()+remainingLetters+" ";
        }
        return capitalizedStr.trim();
	}
	
	
	
	public static boolean insertName(String name, char gender) {
				
		name = capitalizeFirstLetter(name.trim());
		gender = (gender+"").toUpperCase().charAt(0);
		
		Record foundRecord = findName(name, gender);
		if (foundRecord == null) {
			hash.insert(new Record(name, gender, sizeOfHeap));
			numberOfInsertedNames++;
			return true;
		
		} else {							
			return false;
		
		}
	}
	
	
	public static boolean deleteName(String name, char gender) {
		name = capitalizeFirstLetter(name.trim());
		gender = (gender+"").toUpperCase().charAt(0);
		
		return hash.remove(name, gender);
	}
	
	
	
	public static boolean insertFreqRecord(String name, char gender, int year, int frequency) {
		name = capitalizeFirstLetter(name.trim());
		gender = (gender+"").toUpperCase().charAt(0);
		
		YearFrequency YF = new YearFrequency(year, frequency);
		Record foundRecord = findName(name, gender);
		if (foundRecord == null) {
			Record record = new Record(name, gender, sizeOfHeap);
			record.getFrequency().add(YF);
			
			hash.insert(record);
			numberOfInsertedNames++;
			numberOfInsertedRecords++;
		
		} else {							
			int flag = 0;
			
			Object[] objectList = foundRecord.getFrequency().getItems();
			YearFrequency[] frequencyList = new YearFrequency[objectList.length];

			for (int j = 0; j < objectList.length; j++) {
				frequencyList[j] = (YearFrequency)objectList[j];
			}
			
			for (YearFrequency y: frequencyList) {
				if (y.getYear() == year) {
					y.setFrequency(y.getFrequency() + frequency);
					flag = 1;
					break;
				}
			}
			
			if (flag == 0) 
				foundRecord.getFrequency().add(YF);
			
			numberOfInsertedRecords++;
		}
		
		return true;
	}
	
	
	
	
	public static boolean updateFreqRecord(String name, char gender, int year, int frequency) {
		name = capitalizeFirstLetter(name.trim());
		gender = (gender+"").toUpperCase().charAt(0);
		
		YearFrequency YF = new YearFrequency(year, frequency);
		Record foundRecord = findName(name, gender);
		if (foundRecord == null) {
			Record record = new Record(name, gender, sizeOfHeap);
			record.getFrequency().add(YF);
			
			hash.insert(record);
			numberOfInsertedNames++;
			numberOfInsertedRecords++;
		
		} else {							
			int flag = 0;

			Object[] objectList = foundRecord.getFrequency().getItems();
			YearFrequency[] frequencyList = new YearFrequency[objectList.length];

			for (int j = 0; j < objectList.length; j++) {
				frequencyList[j] = (YearFrequency)objectList[j];
			}
			
			for (YearFrequency y: frequencyList) {
				if (y.getYear() == year) {
					y.setFrequency(frequency);
					flag = 1;
					break;
				}
			}
			
			if (flag == 0) {
				foundRecord.getFrequency().add(YF);
				numberOfInsertedRecords++;
			}
		}
		
		return true;
	}
	
	
	
	public static boolean haveRecordForYear(String name, char gender, int year) {
		Record foundRecord = findName(name, gender);
		Object[] objectList = foundRecord.getFrequency().getItems();
		YearFrequency[] frequencyList = new YearFrequency[objectList.length];

		for (int j = 0; j < objectList.length; j++) {
			frequencyList[j] = (YearFrequency)objectList[j];
		}
		
		for (YearFrequency y: frequencyList) {
			if (y.getYear() == year) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	public static Record findName(String name, char gender) {
		name = capitalizeFirstLetter(name.trim());
		gender = (gender+"").toUpperCase().charAt(0);
		
		return hash.find(name, gender);
	}
	
	
	
	public static Record nameWithMaxFrequencyInYear(int year) {
		Object[] objectList = hash.getArrayOfObjects();
	
		Record[] arrayOfRecords = new Record[objectList.length];
		for (int j = 0; j < objectList.length; j++) {
			arrayOfRecords[j] = (Record)objectList[j];
		}
		
		
		int maxFreq = -1;
		Record maxFreqRecord = null;
		
		for (Record record: arrayOfRecords) {
			
			for (int i = 0; i < record.getFrequency().getSize(); i++) {
				YearFrequency YF = ((YearFrequency)(record.getFrequency().getItems())[i]);
				if (YF.getYear() == year) {
					if (YF.getFrequency() > maxFreq) {
						maxFreq = YF.getFrequency();
						maxFreqRecord = record;
					}
					break;
				}
			}
		}
		
		return maxFreqRecord;
	}
	
	
	
	
	public static Record nameWithMaxFrequency() {
		
		Object[] objectList = hash.getArrayOfObjects();
		
		Record[] arrayOfRecords = new Record[objectList.length];
		for (int j = 0; j < objectList.length; j++) {
			arrayOfRecords[j] = (Record)objectList[j];
		}
		
		
		int maxFreq = -1;
		Record maxFreqRecord = null;
		
		for (Record record: arrayOfRecords) {
			int sumFreq = 0;
			for (int i = 0; i < record.getFrequency().getSize(); i++) {
				YearFrequency YF = ((YearFrequency)(record.getFrequency().getItems())[i]);
				sumFreq += YF.getFrequency();
			}
			
			
			if (sumFreq > maxFreq) {
				maxFreq = sumFreq;
				maxFreqRecord = record;
			}
		}
		
		return maxFreqRecord;
	}
	
}
