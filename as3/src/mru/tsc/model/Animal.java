package mru.tsc.model;

/**
 * The Animal class represents a toy of type Animal in the Toy Store system.
 * It extends the  superclass and adds specific attributes such as 
 * material and size.
 * 
 * This class provides getter and setter methods for its attributes and 
 * overrides the { toString()} and {stringToFormat()} methods 
 * to include animal-specific details.
 *
 * @author Alexander Yoseph, Lorenzo Sta Maria, Haseeb Ullah

 * @version 1.0
 * @since 2025-11-09
 * @course COMP 1502 - Mount Royal University
 */
public class Animal extends Toy {

	private char size;
	private String material;

	/**
	 * Constructs a new {@code Animal} object with all the required fields.
	 * This constructor calls the superclass constructor to initialize the 
	 * inherited attributes.
	 * 
	 * @param serialNum the serial number of the toy
	 * @param name the name of the toy
	 * @param brand the brand of the toy
	 * @param price the price of the toy
	 * @param count the available count of the toy in stock
	 * @param age the appropriate age recommendation for the toy
	 * @param material the material the toy is made from
	 * @param size the size of the toy, represented by a character (S, M, or L)
	 */
	public Animal(String serialNum, String name, String brand, double price, int count, int age, String material,
			char size) {
		super(name, serialNum, brand, price, count, age);
		this.size = size;
		this.material = material;
	}

	/**
	 * Returns the size of the animal toy.
	 * 
	 * @return the size as a character (S, M, or L)
	 */
	public char getSize() {
		return size;
	}

	/**
	 * Sets the size of the animal toy.
	 * 
	 * @param size the size to set (S, M, or L)
	 */
	public void setSize(char size) {
		this.size = size;
	}

	/**
	 * Returns the material of the animal toy.
	 * 
	 * @return the material as a string
	 */
	public String getMaterial() {
		return material;
	}

	/**
	 * Returns the type of this toy as a string.
	 * 
	 * @return the string "Animal"
	 */
	public String typeOf() {
		String type = "Animal";
		return type;
	}

	/**
	 * Sets the material of the animal toy.
	 * 
	 * @param material the material to set
	 */
	public void setMaterial(String material) {
		this.material = material;
	}

	/**
	 * Returns a semicolon-separated string that represents the animal toy,
	 * formatted for saving to a file.
	 * 
	 * @return a formatted string containing the toy information
	 */
	public String stringToFormat() {
		return super.stringToFormat() + ";" + material + ";" + size;
	}
	
	@Override
	public String toString() {
	    return super.toString()
	            + ", Material: " + material
	            + ", Size: " + size;
	}

}
