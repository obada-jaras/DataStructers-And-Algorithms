
public class Area {
	LinkedList Literary;
	LinkedList Scientific;
	
	//**start constructors**//
	Area() {
		Literary = new LinkedList();
		Scientific = new LinkedList();
	}
	//**end constructors**//

	
	public void clear(){
		if (Literary != null) 
			Literary.clear();
		
		if (Scientific != null)
			Scientific.clear();
		
	}
}
