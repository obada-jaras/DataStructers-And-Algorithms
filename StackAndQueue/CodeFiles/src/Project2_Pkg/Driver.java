package Project2_Pkg;

import Lists.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;


public class Driver {
	public static Stack stackList = new Stack();		//try to change it after finish the code
	public static Queue queueList = new Queue();
	public static LinkedList dailyPrices = new LinkedList();
	
	public static double capitalGainLoss = 0;

	public static String history = "";
	
	
	
	//read data from file and save it in a Stack or Queue depends on selection chosen
	@SuppressWarnings("deprecation")
	public static void readFile(File file, char selection) throws Exception {		//'S': Stack	|	'Q':Queue
		Scanner in = new Scanner(file);
		
		while (in.hasNext()) {
			
			String line = in.nextLine();
			
			
			int firstComma = line.indexOf(",");
			int secondComma = line.indexOf(",", firstComma+1);
			int lastComma = line.lastIndexOf(",");
			
			int numberOfShares = Integer.parseInt(line.substring(0, firstComma).trim());
			double pricePerShare = Double.parseDouble(line.substring(firstComma+1, secondComma).trim());
			String companyName = line.substring(secondComma+1, lastComma).trim();
			String Str_dateOfBuying = line.substring(lastComma+1).trim();
			
			
			int firstSlash = Str_dateOfBuying.indexOf("/");
			int lastSlash = Str_dateOfBuying.lastIndexOf("/");
			
			int day = Integer.parseInt(Str_dateOfBuying.substring(0, firstSlash).trim());
			int month = Integer.parseInt(Str_dateOfBuying.substring(firstSlash+1, lastSlash).trim()) - 1;
			int year = Integer.parseInt(Str_dateOfBuying.substring(lastSlash+1).trim()) - 1900;
			
			Date dateOfBuying = new Date(year, month, day);
			
			Record record = new Record(numberOfShares, pricePerShare, companyName, dateOfBuying);
			
			
			if (selection == 'S')
				stackList.push(record);
			
			else if (selection == 'Q')
				queueList.enqueue(record);
		}
		
		in.close();
			
	}
	
	
	
	
	
	public static boolean readDailyPrice(File file) {

		try {

			Scanner in = new Scanner(file);
			
			while (in.hasNext()) {
				String line = in.nextLine();

				String companyName = line.substring(0, line.indexOf(",")).trim();
				double price = Double.parseDouble(line.substring(line.lastIndexOf(",")+1).trim());
				
				dailyPrices.insertLast(new CompanyPrice(companyName, price));
			}
	
			in.close();
			return true;
			
		} catch (Exception e) {

			return false;
		}
	}
	
	
	
	public static double getCompanyPrice(String companyName) {
		
		Queue dailyQueue = dailyPrices.toQueue();
		while (!dailyQueue.isEmpty()) {
			CompanyPrice temp = (CompanyPrice)dailyQueue.dequeue();
			
			if (temp.getCompanyName().equalsIgnoreCase(companyName)) {
				return temp.getPrice();
			}
		}
		
		return -1;
	}
	
	
	public static void buyShare(int amount, String company, char selection) {
		
		double CurrentSharePrice = getCompanyPrice(company);
		
		if (selection == 'S')
			stackList.push(new Record(amount, CurrentSharePrice, company, new Date()));
		
		else if (selection == 'Q')
			queueList.enqueue(new Record(amount, CurrentSharePrice, company, new Date()));
		
		history += amount + " shares from company " + company + " was bought at price " + CurrentSharePrice
				+ "\n----------------------------------------------------\n";
	}
	
	
	
	
	
	public static String sellShares(int amount, String company, char selection) {		
		String str = "";
		double orderGainLoss = 0;
		
		if (selection == 'Q') {
		
			//clone queueList to a new queue named restoreQueue
			Queue tempQueue = new Queue();
			Queue restoreQueue = new Queue();
			
			while (!queueList.isEmpty()) {
				Record temp = (Record) queueList.dequeue();
				restoreQueue.enqueue(temp);
				tempQueue.enqueue(temp);
			}
			
			queueList = tempQueue;
			tempQueue = new Queue();
			//end cloning 
			
			
			double CurrentSharePrice = getCompanyPrice(company);
			
			while (!queueList.isEmpty()) {
				Record record = (Record) queueList.dequeue();
				
				if (record.getCompanyName().equalsIgnoreCase(company)) {

					if (amount >= record.getNumberOfShares()) {
						double tempGainLoss = record.getNumberOfShares() * (CurrentSharePrice - record.getPricePerShare());
						orderGainLoss += tempGainLoss;
						str += record.getNumberOfShares() + " stocks was sold at $" + CurrentSharePrice + " with difference price equals $"
								+ (CurrentSharePrice-record.getPricePerShare()) + " (Total: $" + tempGainLoss + ")\n";
						amount -= record.getNumberOfShares();
					}
					
					
					else {
						double tempGainLoss = amount * (CurrentSharePrice - record.getPricePerShare());
						orderGainLoss += tempGainLoss;
						if (amount != 0) str += amount + " stocks was sold at $" + CurrentSharePrice + " with difference price equals $" 
								+ (CurrentSharePrice-record.getPricePerShare()) + " (Total: $" + tempGainLoss + ")\n";
						record.setNumberOfShares(record.getNumberOfShares() - amount);
						amount = 0;
						tempQueue.enqueue(record);
					}
				}
				
				
				else {
					tempQueue.enqueue(record);
				}
				
			}
			
			
			
			if (amount > 0) {
				queueList = restoreQueue;
				return "";
			}
			
			queueList = tempQueue;
			capitalGainLoss += orderGainLoss;
			str += "Total gain/loss of this process: $" + orderGainLoss;
			history += str + "\n----------------------------------------------------\n";
			return str;
		}
		
		
		else if (selection == 'S') {
			
			//cloning stack
			Stack tempStack = new Stack();
			Stack restoreStack = new Stack();
			
			while (!stackList.isEmpty()) {
				Record temp = (Record) stackList.pop();
				restoreStack.push(temp);		//in reverse form 
				tempStack.push(temp);
			}
			
			while (!tempStack.isEmpty()) {
				stackList.push(tempStack.pop());
			}
			//end cloning
			
			
			
			
			double CurrentSharePrice = getCompanyPrice(company);
			
			while (!stackList.isEmpty()) {
				Record record = (Record) stackList.pop();
				
				if (record.getCompanyName().equalsIgnoreCase(company)) {
					
					if (amount >= record.getNumberOfShares()) {	
						double tempGainLoss = record.getNumberOfShares() * (CurrentSharePrice - record.getPricePerShare());
						orderGainLoss += tempGainLoss;
						str += record.getNumberOfShares() + " stocks was sold at $" + CurrentSharePrice + " with difference price equals $"
								+ (CurrentSharePrice-record.getPricePerShare()) + " (Total: $" + tempGainLoss + ")\n";
						amount -= record.getNumberOfShares();
					}
					
					else {
						double tempGainLoss = amount * (CurrentSharePrice - record.getPricePerShare());
						orderGainLoss += tempGainLoss;
						if (amount != 0) str += amount + " stocks was sold at $" + CurrentSharePrice + " with difference price equals $" 
								+ (CurrentSharePrice-record.getPricePerShare()) + " (Total: $" + tempGainLoss + ")\n";
						record.setNumberOfShares(record.getNumberOfShares() - amount);
						amount = 0;
						tempStack.push(record);
					}
				}
				
				
				else {
					tempStack.push(record);
				}

			}
			
			
			if (amount > 0) {
				while (!restoreStack.isEmpty()) {
					stackList.push(restoreStack.pop());
				}
				
				return "";
			}
			
			
			while (!tempStack.isEmpty()) {
				stackList.push(tempStack.pop());
			}
			
			capitalGainLoss += orderGainLoss;
			str += "Total gain/loss of this process: " + orderGainLoss;
			history += str + "\n----------------------------------------------------\n";
			return str;
		}
		
		
		
		else 
			return "";

	}
	
	

	private static Boolean writeFile (String str, String filePath) {
		try {
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(str);
			fileWriter.close();
			return true;
			
			
		} catch (IOException e) {
			return false;
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public static void saveData(File file1, File file2, char selection) {
		String strShares = "";
		
		if (selection == 'S') {
			
			Stack tempStack = new Stack();
			
			while (!stackList.isEmpty()) {
				Record tempRecord = (Record) stackList.pop();
				tempStack.push(tempRecord);
				
				strShares = tempRecord.getNumberOfShares() + ", " + tempRecord.getPricePerShare() + ", " + tempRecord.getCompanyName() + ", " 
						+ tempRecord.getDateOfBuying().getDate()  + "/" + (tempRecord.getDateOfBuying().getMonth()+1)  + "/" + (tempRecord.getDateOfBuying().getYear()+1900) 
						+ "\n" + strShares; 
			}
			
			
			while (!tempStack.isEmpty()) {
				stackList.push(tempStack.pop());
			}
		}
		
		
		else if (selection == 'Q') {
			Queue tempQueue = new Queue();
			
			while (!queueList.isEmpty()) {
				Record tempRecord = (Record) queueList.dequeue();
				tempQueue.enqueue(tempRecord);
				
				strShares += tempRecord.getNumberOfShares() + ", " + tempRecord.getPricePerShare() + ", " + tempRecord.getCompanyName() + ", " 
						+ tempRecord.getDateOfBuying().getDate()  + "/" + (tempRecord.getDateOfBuying().getMonth()+1)  + "/" + (tempRecord.getDateOfBuying().getYear()+1900) 
						+ "\n"; 
			}
			
			
			queueList = tempQueue;
		}
		
		writeFile(strShares, file1.getAbsolutePath());
		
		
		
		
		
		String strCompanyPrices = "";
		Queue tempQueue = dailyPrices.toQueue();
		while (!tempQueue.isEmpty()) {
			CompanyPrice temp = (CompanyPrice)tempQueue.dequeue();
			strCompanyPrices += temp.getCompanyName() + "," + temp.getPrice() + "\n";
		}
		
		writeFile(strCompanyPrices, file2.getAbsolutePath());

	}
	
	
	
	public static String getOwnedSharesAndAveragePrice(String stock, char selection) {
		int amount = 0;
		double sumOfPrices = 0;
		
		if (selection == 'S') {
			Stack tempStack = new Stack();
			int stackSize = stackList.size();
			
			for (int i = 0; i < stackSize; i++) {
				Record tempRecord = (Record) stackList.pop();
				
				if (tempRecord.getCompanyName().equalsIgnoreCase(stock.trim())) {
					amount += tempRecord.getNumberOfShares();
					sumOfPrices += tempRecord.getPricePerShare() * tempRecord.getNumberOfShares();
				}
				
				tempStack.push(tempRecord);
	
			}
			
			
			while (!tempStack.isEmpty()) {
				stackList.push(tempStack.pop());
			}
			
		}
		
		
		
		else if (selection == 'Q') {
			Queue tempQueue = new Queue();
			int queueSize = queueList.size();
			
			for (int i = 0; i < queueSize; i++) {
				Record tempRecord = (Record) queueList.dequeue();
				
				if (tempRecord.getCompanyName().equalsIgnoreCase(stock.trim())) {
					amount += tempRecord.getNumberOfShares();
					sumOfPrices += tempRecord.getPricePerShare() * tempRecord.getNumberOfShares();
				}
				
				tempQueue.enqueue(tempRecord);

			}
			
			queueList = tempQueue;	
		}
		
		else 
			return "";
		
		
		
		
		
		
		if (amount == 0)
			return "0,0";

		
		return amount + "," + String.format("%.02f", sumOfPrices/amount);
	}
	
	
	
	public static String getRecords(char selection) {

		if (selection == 'S') 
			return stackList.toString();
		
		else if (selection == 'Q') 
			return queueList.toString();
		
		else 
			return null;
	}
	
	
	
	
	public static String getCompanyRecords(String companyName, char selection) {
		String str = "";
		
		if (selection == 'S') { 
			Stack tempStack = new Stack();
			int stackSize = stackList.size();

			for (int i = 0; i < stackSize; i++) {
				Record tempRecord = (Record) stackList.pop();
				
				if (tempRecord.getCompanyName().equalsIgnoreCase(companyName)) {
					str = tempRecord.toString() + "\n" + str;
				}
				
				tempStack.push(tempRecord);
			}
			
			
			while (!tempStack.isEmpty()) {
				stackList.push(tempStack.pop());
			}
		
		}
		
		
		
		
		else if (selection == 'Q') {
			Queue tempQueue = new Queue();
			int queueSize = queueList.size();
			
			for (int i = 0; i < queueSize; i++) {
				Record tempRecord = (Record) queueList.dequeue();
				
				if (tempRecord.getCompanyName().equalsIgnoreCase(companyName)) {
					str += tempRecord.toString() + "\n";
				}
				
				tempQueue.enqueue(tempRecord);
			}
			
			queueList = tempQueue;	
		}
		
		
		
		
		else 
			return null;
		
		return str;
	}

}