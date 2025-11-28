package mru.tsc.model;

/**
 * The Toy class is the parent (superclass) for all toy types in the Toy Store system.  
 * It defines the shared attributes and behaviors that every toy has, such as name, 
 * serial number, brand, price, stock count, and age restriction.  
 * 
 * Other toy types like Figure, Animal, Puzzle, and BoardGame inherit from this class 
 * and add their own unique properties and behavior.
 * 
 * This class also provides methods to display, update, and format toy information 
 * for saving or output.
 * 
 * @author Alexander Yoseph, Lorenzo Sta Maria, Haseeb Ullah
 * @version 1.0
 * @since 2025-11-09
 * @course COMP 1502 - Mount Royal University
 */
public abstract class Toy {

	private String name;
	private String serialNum;
	private String brand;
	private double price;
	private int count;
	private int ageAllowed;

	/**
	 * Creates a new Toy object and fills in all of its common details.
	 * This constructor is called by subclasses like Figure or Animal 
	 * when they are created.
	 * 
	 * @param name the name of the toy
	 * @param serialNum the unique serial number of the toy
	 * @param brand the toy’s brand name
	 * @param price the price of the toy
	 * @param count how many of this toy are currently in stock
	 * @param ageAllowed the minimum recommended age for this toy
	 */
	public Toy(String name, String serialNum, String brand, double price, int count, int ageAllowed) {
		this.name = name;
		this.serialNum = serialNum;
		this.brand = brand;
		this.price = price;
		this.count = count;
		this.ageAllowed = ageAllowed;
	}

	/**
	 * Gets the name of the toy.
	 * 
	 * @return the toy’s name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Updates the toy’s name.
	 * 
	 * @param name the new name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the serial number of the toy.
	 * 
	 * @return the toy’s serial number
	 */
	public String getSerialNum() {
		return serialNum;
	}

	/**
	 * Updates the toy’s serial number.
	 * 
	 * @param serialNum the new serial number to set
	 */
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	/**
	 * Gets the brand name of the toy.
	 * 
	 * @return the toy’s brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * Updates the brand name of the toy.
	 * 
	 * @param brand the new brand name to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * Gets the price of the toy.
	 * 
	 * @return the toy’s price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Updates the price of the toy.
	 * 
	 * @param price the new price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Gets how many of this toy are available in stock.
	 * 
	 * @return the stock count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Updates how many of this toy are available in stock.
	 * 
	 * @param count the new stock count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Gets the recommended minimum age for the toy.
	 * 
	 * @return the minimum age allowed
	 */
	public int getAgeAllowed() {
		return ageAllowed;
	}

	/**
	 * Updates the minimum recommended age for the toy.
	 * 
	 * @param ageAllowed the new age restriction to set
	 */
	public void setAgeAllowed(int ageAllowed) {
		this.ageAllowed = ageAllowed;
	}
	
	/**
	 * Returns the type of toy.  
	 * This method must be implemented by each subclass (e.g., "Figure", "Animal").
	 * 
	 * @return the toy type as a string
	 */
	public abstract String typeOf();

	/**
	 * Returns a readable version of the toy’s details for display in the console.
	 * 
	 * @return a string describing the toy’s main attributes
	 */
	@Override
	public String toString() {
		return "Category: " + this.typeOf() + ", Serial Number: " + serialNum + ", Name: " + name + ", Brand: " + brand 
				+ ", Price: " + price + ", Available Count: " + count + ", Age Appropriate: " + ageAllowed;
	}

	/**
	 * Formats the toy’s information into a text-friendly structure that can 
	 * easily be saved to a file.
	 * 
	 * @return a semicolon-separated string with the toy’s details
	 */
	public String stringToFormat() {
		return serialNum + ";" + name + ";" + brand + ";" + String.format("%.2f", price) + ";" + count + ";" + ageAllowed;
	}

	/**
	 * Decreases the toy’s stock count by one, usually after a purchase.
	 * 
	 * @return the updated stock count after decrementing
	 */
	public int toyDecrement() {
		return count -= 1;
	}
}
