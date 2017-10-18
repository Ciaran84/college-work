// Java Assignment 1
// Ciaran Conlon 15725581

package mybnb;

public class Property 
{
	int regNum, rentPerDay, totalRentalDays; //variables common to all types
	String propertyOwner, propertyAddress;
	
	//constructor
	public Property(int regNum, int rentPerDay, int totalRentalDays, String propertyOwner, String propertyAddress)
	{
		this.regNum = regNum;
		this.rentPerDay = rentPerDay;
		this.totalRentalDays = totalRentalDays;
		this.propertyOwner = propertyOwner;
		this.propertyAddress = propertyAddress;
	}
	
	//accessors
	public int getRegNum()
	{
		return regNum;
	}
	public int getRPD()
	{
		return rentPerDay;
	}
	public int getTotalDays()
	{
		return totalRentalDays;
	}
	public String getOwner()
	{
		return propertyOwner;
	}
	public String getAddress()
	{
		return propertyAddress;
	}
		
}
