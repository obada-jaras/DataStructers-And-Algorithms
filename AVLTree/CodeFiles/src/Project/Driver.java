package Project;

 import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Lists.AVLNode;
import Lists.AVLT;
import Lists.LinkedList;
import Lists.Queue;



public class Driver {	
	private static AVLT<Record> tree = new AVLT<Record>();

	public static AVLT<Record> getTree() {
		return tree;
	}	

	
	
	
	
	public static String readFiles(LinkedList files) {
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
						Record foundRecord = (Record)tree.find(new Record(name, gender));
						if (foundRecord == null) {
							Record record = new Record(name, gender);
							record.getFrequency().insertRecordSorted(YF);
							tree.insert(record);
						
						} else {
							foundRecord.getFrequency().insertRecordSorted(YF);
							
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
        String capitalizeStr="";
 
        for(String word:words){
            String firstLetter=word.substring(0,1);
            String remainingLetters=word.substring(1);
            capitalizeStr+=firstLetter.toUpperCase()+remainingLetters+" ";
        }
        return capitalizeStr;
	}
	
	
	
	
	
	
	public static Record find(String name, char gender) {
		return (Record)tree.find(new Record(capitalizeFirstLetter(name), gender));
	}
	
	
	
	public static double getNameAvgFrequencies(String name, char gender) {
		LinkedList frequencies = find(name, gender).getFrequency();
		return frequencies.getAvgFrequencies();
	}
	
	
	
	public static Record getNameWithMaxFrequency(AVLT<Record> tree) {
		if (tree.getRoot() == null)
			return null;
		
		int max = 0, sum;
		Record maxRecord = (Record)tree.getRoot().getData();
		Queue q = new Queue();

		q.enqueue(tree.getRoot());
		q.enqueue(null);

		// Executing loop till queue becomes empty
		while (!q.isEmpty()) {

			AVLNode<Record> curr = (AVLNode<Record>)q.dequeue();
			
			if (curr == null) {
				if (!q.isEmpty()) {
					q.enqueue(null);
				}

			} else {
				if (curr.getLeft() != null)
					q.enqueue(curr.getLeft());

				if (curr.getRight() != null)
					q.enqueue(curr.getRight());
				
				LinkedList years = ((Record)curr.getData()).getFrequency();
				sum = 0;
				for (int i = 0; i < years.size(); i++) {
					sum += ((YearFrequency)years.getAt(i)).getFrequency();
				}
				
				if (sum > max) {
					max = sum;
					maxRecord = (Record)curr.getData();
				}				
			}
		}
		return maxRecord;
	}

	

	public static int getTotalNumberInYear(int year) {
		if (tree.getRoot() == null)
			return -1;

		int sum = 0;
		Queue q = new Queue();

		q.enqueue(tree.getRoot());
		q.enqueue(null);

		while (!q.isEmpty()) {

			AVLNode<Record> curr = (AVLNode<Record>)q.dequeue();
			
			if (curr == null) {
				if (!q.isEmpty()) {
					q.enqueue(null);
				}

			} else {
				if (curr.getLeft() != null)
					q.enqueue(curr.getLeft());

				if (curr.getRight() != null)
					q.enqueue(curr.getRight());
				
				
				sum += ((Record)curr.getData()).getFrequency().getFrequencyForYear(year);
			}
		}
		
		return sum;
	}


	
	
	
	
	
	public static void exportData(AVLT<Record> tree, String filePath) {
		String str = tree.getLevelOrder();
		writeFile(str, filePath);
	}


	
	
	private static Boolean writeFile(String str, String filePath) {
		try {
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(str);
			fileWriter.close();
			return true;
			
		} catch (IOException e) {
			return false;
		}
	}



	
}
