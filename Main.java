// Java Assignment 1
// Ciaran Conlon 15725581

package mybnb;

import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Main 
{
	
	public static void main(String[] args)
	{
		ArrayList<Apartment> apt = new ArrayList<Apartment>(); //initialise array lists
		ArrayList<House> house = new ArrayList<House>();
		ArrayList<Villa> villa = new ArrayList<Villa>();
		
		FillInProperties(apt, house, villa);
		
		int income = CalculateTotalIncome(apt,house,villa);
		JOptionPane.showMessageDialog(null,  "Total income for all properties is €" + income);
		
		
		
		PrintAllProperties(apt, house, villa);
		
	}
	
	//RentProperty method to accept input for number of rental days and return it as int
	public static int RentProperty(int rentalDays, int whichProperty)
	{
		int totalDays = 0;
		
		for (int i = 0; i<3;i++) //need to loop from FIP class instead...
		{
			
		String daysAsString;
		int j = i+1;
		daysAsString = JOptionPane.showInputDialog("Enter rental days for period " + j + " of property " + whichProperty);
		rentalDays = Integer.parseInt(daysAsString);
		totalDays += rentalDays; 
		}
		
		return totalDays;
		
	}
	
	//FillInProperties method to populate the arraylists with information (hard coded for convenience of testing)
	public static void FillInProperties(ArrayList<Apartment> apt, ArrayList<House> house, ArrayList<Villa> villa)
	{
		int days = 0; //initial value for days rented before user input
		apt.add(new Apartment(4, 3, 101, 35, RentProperty(days, 101), "Jones", "12c Strand Road"));
		apt.add(new Apartment(6, 2, 102, 30, RentProperty(days, 102), "Dubochet", "17a Fiesta Terrace"));
		apt.add(new Apartment(1, 5, 103, 20, RentProperty(days, 103), "Bouvier", "75 Spinster City Apts"));
		house.add(new House(2, 50, 201, 80, RentProperty(days, 201), "Simpson", "742 Evergreen Terrace"));
		house.add(new House(3, 50, 202, 75, RentProperty(days, 202), "Scorpio", "15201 Maple Systems Road"));
		house.add(new House(2, 50, 203, 60, RentProperty(days, 203), "Roberts", "85a Terror Lake"));
		villa.add(new Villa(20, 15, 301, 120, RentProperty(days, 301), "Burns", "Burns Manor"));
		villa.add(new Villa(15, 15, 302, 110, RentProperty(days, 302), "Peters", "Bonaparte Lodge"));
		villa.add(new Villa(5, 15, 303, 90, RentProperty(days, 303), "Aherne", "8 Ballymun Villas"));
		
	}
	
	//CalculateTotalIncome method will compute the income generated from all properties
	public static int CalculateTotalIncome(ArrayList<Apartment> apt, ArrayList<House> house, ArrayList<Villa> villa)
	{
		int i = 0;
		int totalRent = 0;
		int villaRent; //to allow for adding luxury tax
		for(i=0;i<apt.size();i++)
		{
			totalRent += apt.get(i).getRPD() * apt.get(i).getTotalDays();
		}
		
		for(i=0;i<house.size();i++)
		{
			
			totalRent += (house.get(i).getRPD() * house.get(i).getTotalDays()) + house.get(i).getFees();
		}
		
		for(i=0;i<villa.size();i++)
		{
			villaRent = 0; //(re)initialise to 0
			
			villaRent += (villa.get(i).getRPD()+villa.get(i).getRS()) * villa.get(i).getTotalDays(); //calculate each villa individually, incuding room service
			villaRent += (villaRent/100)*villa.get(i).getLuxTax(); //add luxury tax
			totalRent += villaRent;
		}
		
		
		return totalRent;
	}
	
	//PrintAllProperties method displays the contents of all array lists
	public static void PrintAllProperties(ArrayList<Apartment> apt, ArrayList<House> house, ArrayList<Villa> villa)
	{
		int i = 0;
		System.out.println("\nApartments\n");
		System.out.println(String.format("%-12s %-13s %-18s %-10s %-25s %-18s %-15s", 
				"Property ID", "Rent Per Day", "Total Rental Days", "Owner", "Address", "Storey Number", "Number of Beds"));
		for (i = 0;i<apt.size();i++)
		{
			System.out.println(String.format("%-12d %-13d %-18d %-10s %-25s %-18d %-15d", 
					apt.get(i).getRegNum(), apt.get(i).getRPD(), apt.get(i).getTotalDays(), apt.get(i).getOwner(),
					apt.get(i).getAddress(), apt.get(i).getStorey(), apt.get(i).getNumBeds()));
		}
		
		System.out.println();
		System.out.println("\nHouses\n");
		System.out.println(String.format("%-12s %-13s %-18s %-10s %-25s %-18s %-15s", 
				"Property ID", "Rent Per Day", "Total Rental Days", "Owner", "Address", "Number of Storeys", "Clearing Fees"));
		for (i = 0;i<house.size();i++)
		{
			System.out.println(String.format("%-12d %-13d %-18d %-10s %-25s %-18d %-15d", 
					house.get(i).getRegNum(), house.get(i).getRPD(), house.get(i).getTotalDays(), house.get(i).getOwner(), 
					house.get(i).getAddress(), house.get(i).getStoreys(), house.get(i).getFees()));
		}
		
		System.out.println();
		System.out.println("\nVillas\n");
		System.out.println(String.format("%-12s %-13s %-18s %-10s %-25s %-18s %-15s", 
				"Property ID", "Rent Per Day", "Total Rental Days", "Owner", "Address", "Room Service Cost", "Luxury Tax"));
		for (i = 0;i<house.size();i++)
		{
			System.out.println(String.format("%-12d %-13d %-18d %-10s %-25s %-18d %-15d", 
					villa.get(i).getRegNum(), villa.get(i).getRPD(), villa.get(i).getTotalDays(), villa.get(i).getOwner(), 
					villa.get(i).getAddress(), villa.get(i).getRS(), villa.get(i).getLuxTax()));
		}
		
	}

}
