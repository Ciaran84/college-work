// Java Assignment 1
// Ciaran Conlon 15725581

package mybnb;

public class Apartment extends Property 
{
	int storey, numOfBeds;
	
	
	public Apartment(int storey, int numOfBeds, int regNum, int rentPerDay, int totalRentalDays, String propertyOwner, String propertyAddress)
	{
		super(regNum, rentPerDay, totalRentalDays, propertyOwner, propertyAddress); //inherit from superclass
		this.storey = storey;
		this.numOfBeds = numOfBeds;
	}
	
	//accessors
	public int getStorey()
	{
		return storey;
	}
	public int getNumBeds()
	{
		return numOfBeds;
	}
}
