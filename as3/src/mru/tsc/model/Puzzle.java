package mru.tsc.model;

/**
 * The Puzzle class represents a puzzle-type toy in the Toy Store system.  
 * It extends the Toy class and adds one specific attribute called type, 
 * which identifies what kind of puzzle it is (for example: Mechanical, 
 * Cryptic, Logic, Trivia, or Riddle).
 * 
 * This class helps organize all puzzles in the database and provides 
 * methods to display, update, and save puzzle information.
 * 
 * Puzzle is one of the four subclasses of the Toy class, along with 
 * Figure, Animal, and BoardGame.
 * 
 * @author Alexander Yoseph, Lorenzo Sta Maria, Haseeb Ullah
 * @version 1.0
 * @since 2025-11-09
 * @course COMP 1502 - Mount Royal University
 */
public class Puzzle extends Toy {

	private char type;

	/**
	 * Creates a new Puzzle object with all the required details.
	 * This constructor also calls the Toy superclass constructor 
	 * to set the common toy attributes.
	 * 
	 * @param serialNum the unique serial number of the puzzle
	 * @param name the name of the puzzle
	 * @param brand the brand of the puzzle
	 * @param price the price of the puzzle
	 * @param count how many of this puzzle are available in stock
	 * @param age the minimum recommended age for the puzzle
	 * @param type the kind of puzzle ('M' for Mechanical, 'C' for Cryptic, 
	 *             'L' for Logic, 'T' for Trivia, or 'R' for Riddle)
	 */
	public Puzzle(String serialNum, String name, String brand, double price, int count, int age, char type) {
		super(name, serialNum, brand, price, count, age);
		this.type = type;
	}

	/**
	 * Gets the puzzle type.
	 * 
	 * @return the puzzle type character ('M', 'C', 'L', 'T', or 'R')
	 */
	public char getType() {
		return type;
	}

	/**
	 * Updates the puzzle type.
	 * 
	 * @param type the new puzzle type character to set
	 */
	public void setType(char type) {
		this.type = type;
	}

	/**
	 * Returns the toy type as a string.
	 * 
	 * @return "Puzzle"
	 */
	public String typeOf() {
		String type = "Puzzle";
		return type;
	}

	/**
	 * Formats the puzzle’s data into a text-friendly structure that can be
	 * saved to a file.
	 * 
	 * @return a semicolon-separated string containing all puzzle details
	 */
	public String stringToFormat() {
		return super.stringToFormat() + ";" + type;
	}
	
	@Override
	public String toString() {
	    return super.toString()
	            + ", Puzzle type: " + type;
	}

}
