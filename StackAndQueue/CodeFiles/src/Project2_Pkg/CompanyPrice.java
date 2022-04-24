package Project2_Pkg;

public class CompanyPrice {
	String companyName;
	double price;
	
	
	public CompanyPrice(String companyName, double price) {
		super();
		this.companyName = companyName;
		this.price = price;
	}
	
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "CompanyPrice [companyName=" + companyName + ", price=" + price + "]";
	}
}
