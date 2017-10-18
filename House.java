// Java Assignment 1
// Ciaran Conlon 15725581

package mybnb;

public class House extends Property
{
	int numOfStoreys, clearingFees; //unique variables
	
	//constuctor
	public House(int numOfStoreys, int clearingFees, int regNum, int rentPerDay, int totalRentalDays, String propertyOwner, String propertyAddress)
	{
		super(regNum, rentPerDay, totalRentalDays, propertyOwner, propertyAddress); //inherit from superclass
		this.numOfStoreys = numOfStoreys;
		this.clearingFees = clearingFees;
	}
	
	//accessors
	public int getStoreys()
	{
		return numOfStoreys;
	}
	public int getFees()
	{
		return clearingFees;
	}
}
