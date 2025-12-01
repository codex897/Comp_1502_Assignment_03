package mru.tsc.model;

/**
 * The  class represents a board game toy in the Toy Store system. 
 * It extends the  class and adds extra details such as the number of players 
 * and the game's designer(s).
 * 
 * This class helps organize and store information about different board games, 
 * making it easy to manage their data and display it in a readable format.
 * 
 * @author Alexander Yoseph, Lorenzo Sta Maria, Haseeb Ullah
 * @version 1.0
 * @since 2025-11-09
 * @course COMP 1502 - Mount Royal University
 */
public class BoardGame extends Toy {

	private String designers;
	private int minPlayers;
	private int maxPlayers;

	/**
	 * Creates a new  object with all its details filled in.
	 * It uses the superclass constructor to set the shared toy information.
	 * 
	 * @param serialNum the serial number of the board game
	 * @param name the name of the board game
	 * @param brand the brand of the board game
	 * @param price the price of the board game
	 * @param count how many copies are available in stock
	 * @param age the recommended minimum age for players
	 * @param minPlayers the smallest number of players needed to play
	 * @param maxPlayers the largest number of players allowed
	 * @param designers the name(s) of the game designer(s), separated by commas if multiple
	 */
	public BoardGame(String serialNum, String name, String brand, double price, int count, int age, int minPlayers,
			int maxPlayers, String designers) {
		super(name, serialNum, brand, price, count, age);
		this.designers = designers;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}

	/**
	 * Returns the type of this toy as a string.
	 * 
	 * @return "BoardGames"
	 */
	public String typeOf() {
		String type = "BoardGames";
		return type;
	}

	/**
	 * Gets the name(s) of the game designer(s).
	 * 
	 * @return the designer names as a string
	 */
	public String getDesigners() {
		return designers;
	}

	/**
	 * Updates the designer name(s) for this board game.
	 * 
	 * @param designers the new designer name(s) to set
	 */
	public void setDesigners(String designers) {
		this.designers = designers;
	}

	/**
	 * Gets the minimum number of players needed to play the game.
	 * 
	 * @return the minimum player count
	 */
	public int getMinPlayers() {
		return minPlayers;
	}

	/**
	 * Sets the minimum number of players needed to play.
	 * 
	 * @param minPlayers the minimum player count
	 */
	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	/**
	 * Gets the maximum number of players that can join the game.
	 * 
	 * @return the maximum player count
	 */
	public int getMaxPlayers() {
		return maxPlayers;
	}

	/**
	 * Sets the maximum number of players allowed.
	 * 
	 * @param maxPlayers the maximum player count
	 */
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	/**
	 * Formats this board game's information into a string that can be stored in a file.
	 * 
	 * @return a string containing all board game details, separated by semicolons
	 */
	public String stringToFormat() {
		return super.stringToFormat() + ";" + minPlayers + "-" + maxPlayers + ";" + designers;
	}
	
	@Override
	public String toString() {
	    return super.toString()
	            + ", Minimum players: " + minPlayers
	            + ", Maximum players: " + maxPlayers
	            + ", Designers: " + designers;
	}

}
