package Project2_Pkg;

import java.util.Date;



public class Record {
	private int numberOfShares;
	private double pricePerShare;
	private String companyName;
	private Date dateOfBuying;
	
	
	
	//**constructors start**//
	public Record(int numberOfShares, double pricePerShare, String companyName, Date dateOfBuying) {
		super();
		this.numberOfShares = numberOfShares;
		this.pricePerShare = pricePerShare;
		this.companyName = companyName;
		this.dateOfBuying = dateOfBuying;
	}
	//**constructors end**//


	
	//**setters and getters start**//
	public int getNumberOfShares() {
		return numberOfShares;
	}


	public void setNumberOfShares(int numberOfShares) {
		this.numberOfShares = numberOfShares;
	}


	public double getPricePerShare() {
		return pricePerShare;
	}


	public void setPricePerShare(double pricePerShare) {
		this.pricePerShare = pricePerShare;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public Date getDateOfBuying() {
		return dateOfBuying;
	}


	public void setDateOfBuying(Date dateOfBuying) {
		this.dateOfBuying = dateOfBuying;
	}	
	//**setters and getters end**//

	
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return numberOfShares + "," + pricePerShare + "," + companyName + "," + 
				dateOfBuying.getDate() + "/" + (dateOfBuying.getMonth() + 1) + "/" + (dateOfBuying.getYear() + 1900);
	}
}
