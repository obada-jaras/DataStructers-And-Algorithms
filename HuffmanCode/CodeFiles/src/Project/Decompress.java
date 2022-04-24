package Project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Decompress {
	private static String extension;
	private static int reminder;
	private static String codesTableStr;
	private static String encryptedText;
	private static DeHuffNode root = new DeHuffNode();
	private static String plainText = "";
	
	public static long main(File file, String path) throws IOException {
		readFile(file);
		
//		System.out.println(extension);
//		System.out.println(reminder);
//		System.out.println(codesTableStr);
//		System.out.println(encryptedText);
		
		
		CharCode[] arr = storeCodes(codesTableStr);
		buildHuffmanTree(arr);
		setPlainText(root, getBinaryText());
//		System.out.println(getBinaryText());
//		System.out.println(plainText);
		
		
//		System.out.println(codesTableStr);

//		setEncoodedTable(root, "");
//		System.out.println(getBinaryText());
		
		return saveDecompressedFile(plainText, "output", extension, path);
	}
	
	
	private static void readFile(File file) throws IOException {
		
//		String str = "";
//		FileInputStream fin = new FileInputStream(file);
//		
//		int ch;
//		while ((ch = fin.read()) != -1)
//            str += (char)ch;
//		fin.close();
//
//		String[] lines = str.split("\n");
//		extension = lines[0].split(" ")[0];
//		reminder = Integer.parseInt(lines[0].split(" ")[1]);
//		codesTableStr = lines[1];
//		encryptedText = lines[2];
		
		Scanner sc = new Scanner(file);

		extension = sc.next();
		reminder = sc.nextInt(); sc.nextLine();
		codesTableStr = sc.nextLine();
		encryptedText = sc.nextLine();
		
		sc.close();
	}
	
	
	private static CharCode[] storeCodes(String codesTable) {
		String[] array = codesTable.split(" ");
		CharCode[] arrayOfCodes = new CharCode[array.length];
////		
//		for (int i = 0; i < array.length; i++) {
//			char character = array[i].charAt(0);
//			int freq = Integer.parseInt(array[i].substring(1));
//			arrayOfFreq[i] = new CharCode(character, freq);
//		}
		
//		System.out.println(codesTable);
		
		for (int i = 0; codesTable.length() > 0; i++) {
			char character = codesTable.charAt(0);
			codesTable = codesTable.substring(1);
			int indexOfSpace = codesTable.indexOf(' ');
			String code = codesTable.substring(0, indexOfSpace);
			arrayOfCodes[i] = new CharCode(character, code);
			codesTable = codesTable.substring(indexOfSpace+1);
		}
//		System.out.println();
//		System.out.println();
//		for (int i = 0; codesTable.length() > 0; i++) {
//			char character = codesTable.charAt(0);
//			System.out.println(character);
//			codesTable = codesTable.substring(1);
//			int indexOfSpace = codesTable.indexOf(' ');
//			int code = Integer.parseInt(codesTable.substring(0, indexOfSpace));
//			arrayOfFreq[i] = new CharCode(character, code);
//			codesTable = codesTable.substring(indexOfSpace+1);
//		}
//		
//		for (CharCode charCode : arrayOfCodes) {
//			System.out.println(charCode);
//		}
		return arrayOfCodes;
	}
	
	
	
	private static void buildHuffmanTree(CharCode[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null)
				huffmanTree(root, arr[i].getCharacter(), arr[i].getCode()+"");
		}
	}
	
	
	private static void huffmanTree(DeHuffNode node, char character, String directions) {
		if (directions.equals("0")) {
			DeHuffNode tempNode = new DeHuffNode(character);
			node.setLeft(tempNode);
			
			return;
		}
		
		if (directions.equals("1")) {
			DeHuffNode tempNode = new DeHuffNode(character);
			node.setRight(tempNode);
			
			return;
		}
			
		
		if (directions.charAt(0) == '0') {
			if (node.getLeft() == null) {
				node.setLeft(new DeHuffNode());
			}
			huffmanTree(node.getLeft(), character, directions.substring(1));
		}
		
		else {
			if (node.getRight() == null) {
				node.setRight(new DeHuffNode());
			}
			huffmanTree(node.getRight(), character, directions.substring(1));
		}
	}
	
	
	
	private static String getBinaryText() {
		String str = "";
		
		
		for (int i = 0; i < encryptedText.length()-1; i++) {
			int decimal = encryptedText.charAt(i);
			int binary = Integer.parseInt(Integer.toBinaryString(decimal));
//			System.out.println(encryptedText.charAt(i) + " dec " + decimal + " --- bin " + String.format("%08d", binary));
			str += String.format("%08d", binary) + "";
		}
		
		int decimal = encryptedText.charAt(encryptedText.length()-1);
		String binary = Integer.toBinaryString(decimal);
		binary = String.format("%08d", Integer.parseInt(binary));
//		System.out.println("hhgfdjkdgbhkfjdxhgbjkfdsgjfdghjfdgbjhfsdbgjfdsbghjfdsgbdfs  " + binary);

		int index = 8-reminder;
		str += binary.substring(0, index) + "\n";
//		System.out.println("dec " + decimal + " --- bin " + binary.substring(index));

//		System.out.println(str);
	
		return str;
	}
	
//
//	private static void setEncoodedTable(DeHuffNode root, String s) {
//		
//		if (root.getLeft() == null && root.getRight() == null) {
////			char character = root.getCharacter();
////			System.out.println(character + "" + s + " ");
//			return;
//		}
//		
//		setEncoodedTable(root.getLeft(), s + "0");
//		setEncoodedTable(root.getRight(), s + "1");
//	}
//	
	
	private static void setPlainText(DeHuffNode node, String binaryText) {
//		System.out.println(binaryText + "/////////");
		if (binaryText.length() == 0)
			return;
		
		if (binaryText.charAt(0) == '0') {
			if (isLeaf(node.getLeft())) {
				plainText += node.getLeft().getCharacter();
//				System.out.println(plainText);
				setPlainText(root, binaryText.substring(1));
			}
			
			else 
				setPlainText(node.getLeft(), binaryText.substring(1));
		}
		
		else {
			if (isLeaf(node.getRight())) {
				plainText += node.getRight().getCharacter();
//				System.out.println(plainText);
				setPlainText(root, binaryText.substring(1));
			}
			
			else
				setPlainText(node.getRight(), binaryText.substring(1));
		}
		
		
		 
		
		
	}
	
	private static boolean isLeaf(DeHuffNode node) {
		if (node.getLeft() == null && node.getRight() == null)
			return true;
		
		return false;
	}
	
	
	private static long saveDecompressedFile(String text, String fileName, String extension, String savingPath) throws IOException {
//		File newFile = new File(fileName + "." + extension);
//		FileWriter fw = new FileWriter(newFile);
//		fw.write(text);
//		fw.close();
		if (savingPath.equals("")) {
			savingPath = fileName + "." + extension;
		}
		
		else {
			savingPath += "\\" + fileName + "." + extension;
		}
		
		FileOutputStream fout = new FileOutputStream(savingPath);
		byte[] b = text.getBytes();
		fout.write(b);
		fout.close();
		
		File file = new File(savingPath);
		return file.length(); 
	}
}






