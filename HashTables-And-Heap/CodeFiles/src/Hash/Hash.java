package Hash;

import Project.Name;
import Project.Record;


public class Hash {
	private int tableSize;
	private HashNode[] table;
	private int currentSize;

	
	public Hash(int size) {
		size = nextPrime(size);
		table = new HashNode[size];
		for (int i = 0; i < size; i++)
			table[i] = null;
		
		tableSize = size;
		currentSize = 0;
	}

	
	public Object[] getArrayOfObjects() {
		Object[] arrayOfObjects = new Object[currentSize];
		
		int i = 0;
		for (HashNode hn: table) {
			if (hn != null) {
				if(hn.getStatus() == 1) {
					arrayOfObjects[i] = hn.getValue();
					i++;
				}
			}		
		}
		
		return arrayOfObjects;
	}
	
	
	
	public void clear() {
		for (int i = 0; i < table.length; i++)
			table[i] = null;

		currentSize = 0;
	}

	
	public int getCurrentSize() {
		return currentSize;
	}

	public int getTableSize() {
		return tableSize;
	}

	public boolean contains(String name, char gender) {
		return find(name, gender) != null;
	}

	
	
	
	public void insert(Record value) {
		if (currentSize >= tableSize / 2)
			rehash();
		
		int hash = getHash(value.getName().getName() + value.getName().getGender());
		int i = 1;
		
		while ((table[hash] != null) && (table[hash].getStatus() != 0) && (table[hash].getStatus() != 2)) {
			hash = (hash + i * i) % tableSize; // Quadratic
			i++;
			
		}

		currentSize++;
		table[hash] = new HashNode(value, 1);
	}
	
	
	
	private int getHash(String key) {
		int hashVal = 0, i = 0;
		while (i != key.length())
			hashVal = (hashVal << 1) + key.charAt(i++);
		return (hashVal % tableSize);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Record find(String name, char gender) {
		int i = 1;
		int location = getHash(name+gender);
		
		while ((table[location] != null) && (table[location].getStatus() != 0) && (table[location].getStatus() != 2)) {
			if (table[location].getValue().getName().equals(new Name(name, gender)))
				return table[location].getValue();
			
			location = (location + i * i) % tableSize; // Quadratic
			i++;
		}
		return null;
	}

	


	
	public boolean remove(String name, char gender) {
		int i = 1;
		if (!contains(name, gender))
			return false;

		int hash = getHash(name+gender);
		while ((table[hash] != null) && (table[hash].getStatus() != 0)
				&& !(table[hash].getValue().getName().equals(new Name(name, gender))) ) {
			hash = (hash + i * i) % tableSize; // Quadratic
			i++;
		}

		currentSize--;
		table[hash].setDeleteStatus();
		return true;
	}
	
	
	

	
	
	
	
	
	
	
	
	private void rehash() {
		Hash newList;
		newList = new Hash(nextPrime(2 * table.length));

		for (int i = 0; i < table.length; i++)
			if ((table[i] != null) && (table[i].getStatus() == 1))
				newList.insert(table[i].getValue());

		table = newList.table;
		tableSize = newList.tableSize;
	}

	
	
	
	private int nextPrime(int n) {
		if (n % 2 == 0)
			n++;
		while (!isPrime(n)) {
			n += 2;
		}
		return n;
	}

	
	private boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;
		if (n == 1 || n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n / 2; i += 2)
			if (n % i == 0)
				return false;
		return true;
	}

	
	
	
	
	public void printHashTable() {
		int j = 1;
		for (int i = 0; i < table.length; i++)
			if (table[i] == null) 
				System.out.println(i + " , NULL");
			
			else if (table[i].getStatus() == 1)
				System.out.println(j++ + " , " + table[i].getValue());
	}
}