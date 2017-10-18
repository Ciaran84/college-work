// Java Assignment 1
// Ciaran Conlon 15725581

package mybnb;

public class Villa extends Property
{
	int roomService, luxuryTax; //unique vaiables
	
	//constructor
	public Villa(int roomService, int luxuryTax, int regNum, int rentPerDay, int totalRentalDays, String propertyOwner, String propertyAddress)
	{
		super(regNum, rentPerDay, totalRentalDays, propertyOwner, propertyAddress); //inherit from superclass
		this.roomService = roomService;
		this.luxuryTax = luxuryTax;
	}
	
	//accessors
	public int getRS()
	{
		return roomService;
	}
	public int getLuxTax()
	{
		return luxuryTax;
	}
}
