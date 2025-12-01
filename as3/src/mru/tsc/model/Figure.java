package mru.tsc.model;

/**
 * The Figure class represents a figure-type toy in the Toy Store system.  
 * It extends the Toy class and adds an extra attribute called
 * classification, which identifies the kind of figure (for example,
 * Action, Doll, or Historic).
 * 
 * This class helps organize toys that fall under the “Figure” category and 
 * provides methods to easily access, display, or format their information.
 * 
 * @author Alexander Yoseph, Lorenzo Sta Maria, Haseeb Ullah
 * @version 1.0
 * @since 2025-11-09
 * @course COMP 1502 - Mount Royal University
 */
public class Figure extends Toy {

	private char classification;

	/**
	 * Creates a new Figure object and fills in all its details.
	 * It also calls the superclass constructor to set the common toy information.
	 * 
	 * @param serialNum the unique serial number of the toy
	 * @param name the name of the toy
	 * @param brand the toy’s brand name
	 * @param price the price of the toy
	 * @param count how many of this toy are available in stock
	 * @param age the recommended age for the toy
	 * @param classification the toy’s classification — 
	 *        'A' for Action, 'D' for Doll, or 'H' for Historic
	 */
	public Figure(String serialNum, String name, String brand, double price, int count, int age, char classification) {
		super(name, serialNum, brand, price, count, age);
		this.classification = classification;
	}

	/**
	 * Gets the toy’s classification type.
	 * 
	 * @return the classification character ('A', 'D', or 'H')
	 */
	public char getClassification() {
		return classification;
	}

	/**
	 * Updates the toy’s classification type.
	 * 
	 * @param classification the new classification character to set
	 */
	public void setClassification(char classification) {
		this.classification = classification;
	}

	/**
	 * Returns the type of toy as a string.
	 * 
	 * @return "Figure"
	 */
	public String typeOf() {
		String type = "Figure";
		return type;
	}

	/**
	 * Converts this figure’s information into a string format that can be 
	 * saved to a text file.
	 * 
	 * @return a formatted string with all the toy details separated by semicolons
	 */
	public String stringToFormat() {
		return super.stringToFormat() + ";" + classification;
	}
	
	@Override
	public String toString() {
	    return super.toString()
	            + ", Classification: " + classification;
	}

}
