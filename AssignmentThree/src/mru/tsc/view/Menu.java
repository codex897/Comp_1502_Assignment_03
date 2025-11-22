package mru.tsc.view;

import java.util.Scanner;

import mru.tsc.model.Animal;
import mru.tsc.model.BoardGame;
import mru.tsc.model.Figure;
import mru.tsc.model.Puzzle;
import mru.tsc.model.Toy;
import mru.tsc.exceptions.*;
public class Menu {
	static Scanner input;
	private NegativePriceException error;
	/**
     * Constructor initializes the Scanner object for user input.
     */
	public Menu() {
		input = new Scanner(System.in);
		error = new NegativePriceException();
	}
	
	
	/**
     * Prompts user to enter toy name and validates it is not empty.
     * @return Validated toy name as String
     */
	public String askNameInput() {
	    System.out.print("Enter Toy Name: ");
	    String name = input.nextLine().trim();
	    while (name.isEmpty()) {
	        System.out.print("Invalid input: empty field");
	        tryAgain();
	        System.out.print("Enter Toy Name: ");
	        name = input.nextLine().trim();
	    }
	    return name;
	}
	
	/**
     * Prompts user to enter toy type and validates input matches [F, A, P, B].
     * @return Toy type as String (f, a, p, b)
     */
	public String askTypeInput() {
	    System.out.print("Enter Toy Type || type [F] for figures, [A] for animal, [P] for puzzles, [B] for board-games: ");
	    while(true) {
	    	char type = input.nextLine().toLowerCase().charAt(0);
	    	if (type == 'f' || type == 'a' || type == 'p' || type == 'b') {
	    		return Character.toString(type);
	    	}
	    	else {
	    		System.out.print ("Invalid input: enter either [F], [A], [P], [B]");
	    		tryAgain();
	    	}
	    }
	   
	}
	
	/**
     * Prompts user to enter toy type and validates input matches [F, A, P, B].
     * @param userType users chosen type
     * @return Toy type as String (f, a, p, b)
     */
	public String askTypeInputGift() {
	    String type;
	    System.out.print("Enter Toy Type || type [F] for figures, [A] for animal, [P] for puzzles, [B] for board-games | Press [ENTER] to leave blank: ");
	    
	    while(true) {
	        type = input.nextLine().trim().toLowerCase();
	        
	        // Check if user pressed Enter (empty input)
	        if (type.isEmpty()) {
	            return ""; 
	        }
	        if (type.length() == 1) {
	            char typeCharac = type.charAt(0);
	            if (typeCharac == 'f' || typeCharac == 'a' || typeCharac == 'p' || typeCharac == 'b') {
	                return Character.toString(typeCharac);
	            }
	        }
	        
	        // Invalid input
	        System.out.print("Invalid input: enter either [F], [A], [P], [B] | Press [ENTER] to leave blank: ");
	    }
	   
	}
	
	/**
     * Prompts user to enter toy brand and validates it is not empty.
     * @return Validated toy brand as String
     */
	public String askBrandInput() {
	    System.out.print("Enter Toy Brand: ");
	    String brand = input.nextLine().trim();
	    while (brand.isEmpty()) {
	        System.out.print("Invalid input: empty field");
	        tryAgain();
	        System.out.print("Enter Toy Brand: ");
	        brand = input.nextLine().trim();
	    }
	    return brand;
	}

	/**
     * Prompts user to enter toy price and validates it is a non-negative number.
     * @return Toy price as double
     */
	public double askPriceInput()  {
		double price;
	    while (true) {
	        System.out.print("Enter Toy Price: ");
	        try {
	            price = input.nextDouble();
	            input.nextLine(); // clear leftover newline
	            checkNegativePrice(price);
	            if (price >= 0) {
	                break ;
	            } 
	        } catch (NegativePriceException e) {
	            System.out.println(e.getMessage());
	           
	        }
	        catch (Exception e ) {
	            System.out.println("Invalid input: Input must be a number.");
	            input.nextLine(); // clear invalid input
	        }
	    }
	    return price;
	}
	
	/**
	 * This checks for a negative price and throws an error 
	 * @param price the price the user chose
	 * @return returns the price as a double
	 * @throws NegativePriceException the error when the price is actually less than 0
	 */
	public double checkNegativePrice(double price) throws NegativePriceException{
		 if(price<0.0) throw new NegativePriceException("Price cannot be a negative number, try again");
		    return price;
		
	}

	/**
     * Prompts user to enter available toy count and validates it is non-negative.
     * @return Available count as int
     */
	public int askCountInput() {
	    System.out.print("Enter Available Count: ");
	    while (true) {
	        try {
	            int count = input.nextInt();
	            input.nextLine(); // clear the left over newline
	            if (count >= 0) {
	                return count;
	            } 
	            else {
	                System.out.print("Invalid input: Count cannot be negative number");
	                tryAgain();
	            }
	        } catch (Exception e) {
	            System.out.print("Invalid input: Input must be a number");
	            tryAgain();
	            input.nextLine(); // clear the left over newline
	        }
	        System.out.print("Enter Available Count: ");
	    }
	}

	 /**
     * Prompts user to enter appropriate toy age and validates it is non-negative.
     * @return Age as int
     */
	public int askAgeInput() {
	    System.out.print("Enter Appropriate Age: ");
	    while (true) {
	        try {
	            int age = input.nextInt();
	            input.nextLine(); // clear the left over newline
	            if (age >= 0) {
	                return age;
	            } 
	            else {
	                System.out.print("Invalid input: Age cannot be a negative number");
	                tryAgain();
	            }
	        } catch (Exception e) {
	            System.out.print("Invalid input: Input must be a number");
	            tryAgain();
	            
	        }
	        System.out.print("Enter Appropriate Age: "); 
	    }
	}

	/**
     * Prompts user to enter toy classification and validates it matches [A, D, H].
     * @return Classification as char
     */
	public char askClassificationInput() {
	    System.out.print("Enter Appropriate classification || type [A] for action, [D] for Doll, [H] for historic: ");
	    
	    while (true) {
	    	 char classification = input.nextLine().toLowerCase().charAt(0);
		    	if(classification == 'a' || classification == 'd' || classification == 'h' ) {
		    		return classification;
		    	}
		    	else {
		    		System.out.print("Invalid input: enter either [A], [D], [H]");
		    		tryAgain();
		    	}
	    }
	}

	/**
     * Prompts user to enter toy material and validates it is not empty.
     * @return Material as String
     */
	public String askMaterialInput() {
	    System.out.print("Enter Toy Material: ");
	    String material = input.nextLine().trim();
	    while (material.isEmpty()) {
	        System.out.print("Invalid input: This field is empty");
	        tryAgain();
	        System.out.print("Enter Toy Material: ");
	        material = input.nextLine().trim();
	    }
	    return material;
	}

	/**
     * Prompts user to enter toy size and validates it matches [S, M, L].
     * @return Size as char
     */
	public char askSizeInput() {
	    System.out.print("Enter Appropriate Size || type [S] for small, [M] for medium, [L] for large: ");
	    while(true) {
	    	char size = input.nextLine().toLowerCase().charAt(0);
	    	if (size == 's' || size == 'm' || size == 'l') {
	    		return size;
	    	}
	    	else {
	    		System.out.print ("Invalid input: enter either [S], [M], [L]");
	    		tryAgain();
	    }
	    	
	    }
	}

	/**
     * Prompts user to enter minimum number of players and validates >= 1.
     * @return Minimum number of players as int
     */
	public int askMinPlayersInput() {
	    System.out.print("Enter Minimum Number of Players: ");
	    while (true) {
	        try {
	            int minNum = input.nextInt();
	            input.nextLine(); // clear the left over newline
	            if (minNum >= 1) {
	                return minNum;
	            } else {
	                System.out.print("Invalid input: Must have at least 1 player");
	                tryAgain();
	            }
	        } catch (Exception e) {
	            System.out.print("Invalid input: Input must be a number");
	            tryAgain();
	            
	        }
	        System.out.print("Enter Minimum Number of Players: ");
	    }
	}

	/**
     * Prompts user to enter maximum number of players and validates >= 1.
     * @return Maximum number of players as int
     */
	public int askMaxPlayersInput() {
	    System.out.print("Enter Maximum Number of Players: ");
	    while (true) {
	        try {
	            int maxNum = input.nextInt();
	            input.nextLine(); // clear the left over newline
	            if (maxNum >= 1) {
	                return maxNum;
	            } else {
	                System.out.print("Invalid input: Must have at least 1 player!");
	                tryAgain();
	            }
	        } catch (Exception e) {
	            System.out.print("Invalid input: Input must be a number");
	            tryAgain();
	           
	        }
	        System.out.print("Enter Maximum Number of Players: ");
	    }
	}

	/**
     * Prompts user to enter designer names and validates it is not empty.
     * @return Designer names as String
     */
	public String askDesignerNamesInput() { //should validate this i think 
	    System.out.print("Enter Designer Names (Use ',' to separate names): ");
	    String designers = input.nextLine().trim();
	    while (designers.isEmpty()) {
	        System.out.print("Invalid Input: This field is empty");
	        tryAgain();
	        System.out.print("Enter Designer Names: ");
	        designers = input.nextLine().trim();
	    }
	    return designers;
	}

	 /**
     * Prompts user to enter puzzle type and validates it matches [M, C, L, T, R].
     * @return Puzzle type as char
     */
	public char askPuzzleTypeInput() {
	    System.out.print("Enter Appropriate Puzzle-Type  || type [M] for mechanical, [C] for cryptic, [L] for logic, [T] for trivia, [R] for riddle: : ");
	    while (true) {
	    	 char type = input.nextLine().toLowerCase().charAt(0);
	    	 if(type == 'm' || type == 'c' || type == 'l' || type == 't' || type == 'r' ) {
	    		 return type;
	    		 
	    	 }
	    	 else {
	    		 System.out.print("Invalid input: enter either [M], [C], [L], [T], [R]");
	    		 tryAgain();
	    		
	    	 }
	    }
	}

	 /**
     * Prints a "Try Again" message for invalid input.
     */
	public void tryAgain() {
		System.out.println(" Try Again");
	}

	 /**
     * Prompts user for serial number and validates it
     * @return Serial number entered by user as String
     */
	public String askSerialNumber() {
		//please validate serial number and return value WIP
		System.out.println("enter SN or [ENTER] to go back: "); //temporary placeholder
		String sn;
		while (true) {
	        try {
	             sn = input.nextLine();
	             if (sn.isEmpty()) return sn;
	           
	            Long snLong = Long.parseLong(sn);
	            if (Long.toString(snLong).length() == 10) return Long.toString(snLong);
	            else if(!(Long.toString(snLong).length() == 10))System.out.println("Invalid input: must have 10 digits");
	            

	        } catch (Exception e) {
	            System.out.print( "Invalid input: Input must be a number or must be 10 long");
	            tryAgain();
	            input.nextLine();
	           
	        }
	}
	}

	/**
	 * Show successful toy-add message to user.
	 */
	public void toyAddMessage() {
		System.out.println("New Toy Added!");
	}

	 /**
     * Displays the main menu and returns user's choice.
     * @return User option as String
     */
	public String displayMainMenu() {
		System.out.println("How May We Help You?");
		System.out.println();
		
		System.out.println("(1) Search Inventory and Purchase Toy");
		System.out.println("(2) Add New Toy");
		System.out.println("(3) Remove Toy");
		System.out.println("(4) Gift Suggestion");
		System.out.println("(5) Save & Exit");
		System.out.println();
		
		System.out.print("Enter Option:");
		
		String userInput = input.nextLine();
		return userInput;
	}

	 /**
     * Displays saving message before program exit.
     */
	public void displaySaveExit() {
		// TODO Auto-generated method stub
		System.out.println("Saving data... ");
	}

	/**
	 * Display the "Find Toys With" sub-menu
	 * and return a validated option ("1".."4") as a String.
	 *
	 * @return user's choice as "1", "2", "3", or "4"
	 */
	public String displayFindToys() {
	    System.out.println();
	    System.out.println("Find Toys With:");
	    System.out.println();
	    System.out.println("(1) Serial Number(SN)");
	    System.out.println("(2) Toy Name");
	    System.out.println("(3) Toy Type");
	    System.out.println("(4) Back to Main Menu");
	    System.out.println();
	    System.out.print("Enter Option: ");

	    while (true) {
	        String choice = input.nextLine().trim();
	        if (choice.matches("[1-4]")) {
	            return choice;
	        }
	        System.out.print("This is not a valid option! Try again. Enter Option: ");
	    }
	}
	
	/**
     * Placeholder for going back to main menu.
     */
	public void displayBackToMainMenu() {
		// TODO Auto-generated method stub
		System.out.println("go back ");
	}

	/**
     * Displays message for invalid option selected.
     */
	public void InvalidOption() {
		System.out.print("this is not a valid option");
		this.tryAgain();
	}
	
	/**
     * Displays toy list with index count.
     * @param count Index of toy
     * @param toyList Toy information as String
     */
	public void displaytoylist(int count, String toyList) {
		System.out.print(String.format("%-7s", " (" + count + ")"));
		
		System.out.println(toyList);	
	}
	
	/**
     * Displays message when no toy is found.
     */
	public void toyNotFound() {
		System.out.println("");
		System.out.print("There were no toys found");
		this.tryAgain();
	}
	
	/**
     * Prompts user to enter option number for purchasing a toy and validates numeric input.
     * @return Option number as int
     */
	public int enterOptionNumber() { //validate this please
		// TODO Auto-generated method stub
		System.out.print("Enter option Number to purchase: ");
		while (true) {
	        try {
	            int option = input.nextInt();
	            input.nextLine(); // clear the left over newline
	            return option;

	        } catch (Exception e) {
	            System.out.print("Invalid input: Input must be a number");
	            tryAgain();
	            input.nextLine();
	           
	        }
	        System.out.print("Enter option Number to purchase: ");
	    }
	}
	
	/**
     * Asks user to confirm toy removal.
     * @return true if user inputs "y", false otherwise
     */
	public boolean askToRemove() {
		// TODO Auto-generated method stub
		System.out.println("do you want to remove y/n?: "); //placeholder to call menu class
		
	 	if(input.nextLine().equals("y")) return true;
	 	else return false;
	}

	/**
	 * Tell the user that the Serial Number they tried to add is not unique.
	 */
	public void snNotUnique() {
		System.out.println("SN must be unique!!");
		this.tryAgain();
	}
	
	/**
	 * 	Tell the user that an item was removed.
	 */
	public void displayItemRemoved() {
		System.out.println("Item Removed!");
	}

	/**
	 * Pause and wait for Enter (used instead of calling input.nextLine() directly in manager)
	 */
	public void pressEnter() {
		System.out.print("Press Enter to continue...");
		input.nextLine();
		System.out.println();
	}

	/**
	 * Lets user know that the purchase was successful.
	 */
	public void purchaseSuccess() {
		System.out.println("The transaction successfully Terminated!");
	}
	
	/**
	 * Tell the user that the requested toy is out of stock.
	 */
	public void outOfStock() {
		System.out.println("Error: out of stock");
	}
	
	/**
	 * Display a single toy object.
	 */
	public void displaySingleToy(Toy toy) {
		System.out.println(toy.toString());
	}

	/**
	 * Tell the user that at least one field must be filled for gift suggestion filtering.
	 */
	public void atLeastOneFieldMessage() {
		System.out.println("ERROR: at least one field must be filled out");
	}

	/**
	 * Displays the gift suggestion results for the user.
	 */
	public void displayGiftSuggestionResult() {
		System.out.println("");
		System.out.println("     ==Gift Suggestion Results== \n");
		
	}

	/**
	 * Ask the user for an age filter for the gift suggestion.
	 * @return String entered by the user (can be empty if skipped)
	 */
	public String askGiftAge() {
		System.out.println("");
	    System.out.print("For what age? | Press [ENTER] to leave blank: ");
	    return input.nextLine().trim();
	}

	/**
	 * Ask the user for toy type filter for the gift suggestion.
	 * @return String entered by the user (can be empty if skipped)
	 */
	public String askGiftType() {
		System.out.println("");
	    System.out.print("Enter Toy Type || type [F] for figures, [A] for animal, [P] for puzzles, [B] for board-games | Press [ENTER] to leave blank: ");
	    return input.nextLine().trim();
	}

	/**
	 * Ask the user for minimum price (optional).
	 * @return String entered by the user (can be empty if skipped)
	 */
	public String askMinPrice() {
		System.out.println("");
	    System.out.print("Enter minimum price | Press [ENTER] to leave blank: ");
	    return input.nextLine().trim();
	}

	/**
	 * Asks the user for maximum price (optional).
	 * @return String entered by the user (can be empty if skipped)
	 */
	public String askMaxPrice() {
		System.out.println("");
	    System.out.print("Enter maximum price | Press [ENTER] to leave blank: ");
	    return input.nextLine().trim();
	}

	/**
	 * Display an error message when the minimum price is greater than the maximum price.
	 */
	public void displayMinMoreThanMaxError() {
	    System.out.println("Error: minimum price cannot be greater than the maximum price.");
	    tryAgain();
	}

	/**
	 * displays a qustion asking the price range
	 */
	public void pricerange() {
		System.out.println("what price range? : ");
		
	}

	/**
	 * displays the error message
	 * @param message the message that is for a specific error 
	 */
	public void errorMessage(String message) {
		System.out.println("Error: "+message);
		
	}
	
	/**
	 * Displays an invalid input message
	 * @param message the message that is for specific error
	 */
	public void invalidMessage(String message) {
		System.out.println("Invalid Input: "+message);
		
	}
}
