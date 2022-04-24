package Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.PriorityQueue;


public class Compress {
	private static String charactersCodesStr = "";
	private static String[] charactersCodesArr = new String[256];
	private static int reminder = 0;
	
	public static long main(File file, String folder) throws IOException {
//		File file = new File("C:\\Users\\obada\\OneDrive\\Desktop\\Screenshot (64).png");
		String fileName = file.getName();
		String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
		String plainText = readFile(file);
		
		int[] charFreqArr = fillCharFreqArray(plainText.trim());
		
		PriorityQueue<HuffNode> heap = fillHeap(charFreqArr);
		
		HuffNode root = buildHuffTree(heap);
		
		setEncoodedTable(root, "");
		
		String binaryText = getEncryptedBinaryText(plainText);
		setReminder(binaryText);
//		System.out.println(binaryText);
		String header = getHeader(charactersCodesStr, fileExtension);
		String text = getEncryptedAsciiText(binaryText);
		
//		System.out.println(header);
//		System.out.println(text);
		
		return saveCompressedFile(header, text, folder);
	}
	
	
	private static String readFile(File file) throws IOException {
//		System.out.println(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".")) + ".txt");
//		File newFile = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".")) + ".txt");
//		File tempFile = file.
//		file.renameTo(newFile);
//		file = newFile;
		
//		Scanner sc = new Scanner(file);
		String str = "";
//		
//		while (sc.hasNext()) {
//			str += sc.nextLine() + '\n';
//		}
//		sc.close();
		
		
		FileInputStream fin = new FileInputStream(file);
		int ch;
		 
        while ((ch = fin.read()) != -1)
        	str += (char)ch;
 
        fin.close();
		
//		System.out.println(str);
//		convertedFile.delete();
		return str;
	}
	
	
	private static int[] fillCharFreqArray(String str) {
		int[] asciiChar = new int[256];
		
		for (int i = 0; i < str.length(); i++) {
			asciiChar[str.charAt(i)]++;
		}
		
		return asciiChar;
	}
	
	
	private static PriorityQueue<HuffNode> fillHeap(int[] asciiChar){
		PriorityQueue<HuffNode> heap = new PriorityQueue<HuffNode>();
		
		for (int i = 0; i < asciiChar.length; i++) {
			if (asciiChar[i] != 0) {
				HuffNode node = new HuffNode((char)i, asciiChar[i], null, null);
				heap.add(node);
			}
		}
	
		return heap;		
	}
	
	
	private static HuffNode buildHuffTree(PriorityQueue<HuffNode> heap) {
		HuffNode tempNode = new HuffNode();
		while (heap.size() > 1) {	
			tempNode = new HuffNode();
	
			HuffNode left = heap.poll();
			HuffNode right = heap.poll();
			
			tempNode.setFrequency(left.getFrequency() + right.getFrequency());
			tempNode.setCharacter('-');
			tempNode.setLeft(left);
			tempNode.setRight(right);
			heap.add(tempNode);
		}
		
		return tempNode;
	}
	
	
	private static void setEncoodedTable(HuffNode root, String s) {
		
		if (root.getLeft() == null && root.getRight() == null) {
			char character = root.getCharacter();
			charactersCodesStr += character + "" + s + " ";
			charactersCodesArr[character] = s;
			return;
		}
		
		setEncoodedTable(root.getLeft(), s + "0");
		setEncoodedTable(root.getRight(), s + "1");
	}
	
	
	private static String getHeader(String charactersCodes, String fileExtension) {
		return fileExtension + " " + reminder + "\n" + charactersCodes;
	}
	
	
	private static String getEncryptedBinaryText(String planeText) {
		String str = "";
		
		for (int i = 0; i < planeText.length(); i++) {
			str += charactersCodesArr[planeText.charAt(i)];
//			System.out.println(charactersCodesArr[planeText.charAt(i)] + " " + planeText.charAt(i) + " " + i);
		}
		
		return str;
	}
	
	private static String getEncryptedAsciiText(String binaryText) {
		String str = "";
		int binTextLength = binaryText.length();
		setReminder(binaryText);
//		for (int i = 8; i > rem; i--) {
//			binaryText = "0" + binaryText;
//		}
		
		for (int i = 0; i < binTextLength/8; i++) {
			String eightBits = binaryText.substring((8*i), 8*(i+1));
			str += (char)Integer.parseInt(eightBits,2);
//			if(Integer.parseInt(eightBits,2) > 126 && Integer.parseInt(eightBits,2) < 160)
//			System.out.println((char)Integer.parseInt(eightBits,2) + " " + Integer.parseInt(eightBits,2));
//			System.out.println(Integer.parseInt(eightBits,2));
		}
		 
		if (reminder > 0) {
			int x = binTextLength - reminder;
			String eightBits = binaryText.substring(x).trim();
			str += (char)Integer.parseInt(eightBits,2);
//			System.out.println(Integer.parseInt(eightBits,2));

		}
		
		return str;
	}
	
	
	private static void setReminder(String binaryText) {
		int binTextLength = binaryText.length();
		reminder = binTextLength % 8;
	}
	
	
	private static long saveCompressedFile(String header, String encryptedText, String savingPath) throws IOException {
		String str = header + "\n" + encryptedText;
//		File newFile = new File("compressed.hf");
//		FileWriter fw = new FileWriter(newFile);
//		fw.write(str);
//		fw.close();
		if (savingPath.equals("")) {
			savingPath = "compressed.hf";
		}
		
		else {
			savingPath += "\\compressed.hf";
		}

		FileOutputStream fout = new FileOutputStream(savingPath);
		byte[] b = str.getBytes();
		fout.write(b);
		fout.close();
		
		File file = new File(savingPath);
		return file.length();
	}
	
	
}